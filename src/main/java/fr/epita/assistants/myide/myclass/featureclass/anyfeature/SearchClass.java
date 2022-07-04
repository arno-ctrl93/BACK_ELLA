package fr.epita.assistants.myide.myclass.featureclass.anyfeature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import lombok.NonNull;

public class SearchClass implements Feature {

    public List<File> search(String pathDir, String searchTerm) {

        List<File> result = new ArrayList<File>();

        String[] pathnames;

        File file = new File(pathDir);
        pathnames = file.list();

        for (String pathname : pathnames) {
            File temp = new File(pathDir + "/" + pathname);
            if (temp.isDirectory()) {
                result.addAll(search(pathDir + "/" + pathname, searchTerm));
            } else {
                try {
                    StringBuilder resultStringBuilder = new StringBuilder();
                    Scanner sc = new Scanner(temp);

                    while (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        resultStringBuilder.append(line).append("\n");
                    }
                    String content = resultStringBuilder.toString();

                    if (content.indexOf(searchTerm) != -1)
                        result.add(temp);
                } catch (Exception e) {
                    // System.out.println("Error: " + e.getMessage());
                }
            }
        }

        return result;

    }

    @Override
    public @NonNull ExecutionReport execute(Project project, Object... params) {

        List<File> TextFiles = new ArrayList<File>();
        String word = (String) params[0];

        try {
            TextFiles = search(project.getRootNode().getPath().toString(), word);
            //System.out.println("FILES SEARCH" + TextFiles);
            return new ExecutionReport() {
                @Override
                public boolean isSuccess() {
                    return true;
                }
            };

            // return TextFiles;

        } catch (Exception ex) {
            // System.out.println("Exception: " + ex.getMessage());
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
        return Mandatory.Features.Any.SEARCH;
    }
}