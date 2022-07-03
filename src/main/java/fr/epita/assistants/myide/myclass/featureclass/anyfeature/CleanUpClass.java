package fr.epita.assistants.myide.myclass.featureclass.anyfeature;


import java.io.File;
import java.io.FileReader;
import java.util.*;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import lombok.NonNull;

public class CleanUpClass implements Feature {

    void delDir(File file) {
        System.out.println("delete file");
        try {
            if (file.isDirectory()) {
                File[] entries = file.listFiles();
                if (entries != null) {
                    for (File entry : entries) {
                        delDir(entry);
                    }
                }

            }
            file.delete();
        } catch (Exception e) {
        }
    }

    public void subDir(String line, File filesList[], File root) {
        System.out.println("list files = " + filesList.length);

        System.out.println("CleanUpClass: sc.nextLine : " + line + " in " + root);
        for (File f : filesList) {
            System.out.println("file = " + f.getName());
            System.out.println("CleanUpClass: matches : '" + f.getPath() + "' and '" + root + "/" + line + "'");
             if (f.getPath().equals(root + "/" + line)) {
                System.out.println("CleanUpClass: delete : " + f.getPath());
                if (f.isDirectory())
                    delDir(f);
                else
                    f.delete();
            }
            if (f.isDirectory()) {
                //File subRoot = new File(f.getPath());
                System.out.println("CleanUpClass: is dir : " + f.getPath() );
                subDir(line, f.listFiles(), root);
                System.out.println("get back");
            }
        }
    }

    @Override
    public @NonNull ExecutionReport execute(Project project, Object... params) {

        try {
            FileReader fr = new FileReader(project.getRootNode().getPath().toString() + "/.myideignore");
            Scanner sc = new Scanner(fr);
            File root = new File(project.getRootNode().getPath().toString());
            System.out.println("root = " + root);
            File filesList[] = root.listFiles();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.startsWith("#")) {
                    continue;
                }

                subDir(line, filesList, root);
            }
            sc.close();

            return new ExecutionReport() {
                @Override
                public boolean isSuccess() {
                    return true;
                }
            };

        } catch (Exception e) {
            return new ExecutionReport() {
                @Override
                public boolean isSuccess() {
                    return false;
                }
            };
        }
    }

    @Override
    public @NonNull Type type() {
        return Mandatory.Features.Any.CLEANUP;
    }
}