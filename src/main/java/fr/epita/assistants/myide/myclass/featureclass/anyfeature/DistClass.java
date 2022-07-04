package fr.epita.assistants.myide.myclass.featureclass.anyfeature;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import lombok.NonNull;

public class DistClass implements Feature {

	private static final int BUFFER_SIZE = 4096;

	List<File> delDir(File file, List<File> filesIgnore) {
		filesIgnore.add(file);
        System.out.println("delete file");
        try {
            if (file.isDirectory()) {
                File[] entries = file.listFiles();
                if (entries != null) {
                    for (File entry : entries) {
                        filesIgnore = delDir(entry, filesIgnore);
                    }
                }

            }
			System.out.println("add elemnt : " + file.getName());
			
            //file.delete();
        } catch (Exception e) {
        }

		return filesIgnore;
    }

	public List<File> subDir(String line, File filesList[], File root, List<File> filesIgnore) {
        System.out.println("list files = " + filesList.length);

        System.out.println("CleanUpClass: sc.nextLine : " + line + " in " + root);
        for (File f : filesList) {
            System.out.println("file = " + f.getName());
            System.out.println("CleanUpClass: matches : '" + f.getPath() + "' and '" + root + "/" + line + "'");
			if (f.getPath().equals(root + "/" + line)) {
				System.out.println("CleanUpClass: delete : " + f.getPath());
				if (f.isDirectory())
                    filesIgnore = delDir(f, filesIgnore);
                else {
					System.out.println("add elemnt : " + f.getName());
					
					filesIgnore.add(f);
					//f.delete();
				}
                    
			}
			if (f.isDirectory()) {
				System.out.println("CleanUpClass: is dir : " + f.getPath() );
				filesIgnore = subDir(line, f.listFiles(), root, filesIgnore);
				System.out.println("get back");
			}
        }
		return filesIgnore;
    }

	@Override
	public @NonNull ExecutionReport execute(Project project, Object... params) {

		//String path = project.getRootNode().getPath().toString();

		try {

			List<File> filesIgnore = new ArrayList<File>();

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

                filesIgnore = subDir(line, filesList, root, filesIgnore);
            }
            sc.close();

			System.out.println("length of dist = " + filesIgnore.size());
			for (File file : filesIgnore) {
				System.out.println("file to zip : " + file.getPath());
			}

			Path zipPath = Paths.get(root.getPath());
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.getFileName().toString() + ".zip"));
			for (File file : filesIgnore) {
				if (file.isDirectory()) {
					System.out.println("zip dir : " + file.getPath());
					zipDirectory(file, file.getName(), zos);
				} else {
					System.out.println("zip file : " + file.getPath());
					zipFile(file, zos);
				}
			}
			zos.flush();
			zos.close();

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

	private void zipFile(File file, ZipOutputStream zos) {
		try {
			zos.putNextEntry(new ZipEntry(file.getName()));
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			long bytesRead = 0;
			byte[] bytesIn = new byte[BUFFER_SIZE];
			int read = 0;
			while ((read = bis.read(bytesIn)) != -1) {
				zos.write(bytesIn, 0, read);
				bytesRead += read;
			}
			zos.closeEntry();
		} catch (Exception e) {
			// System.out.println("Error while creating the archive : " + e.getMessage());
		}
	}

	private void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) {
		try {
			for (File file : folder.listFiles()) {
				if (file.isDirectory()) {
					zipDirectory(file, parentFolder + "/" + file.getName(), zos);
					continue;
				}
				zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));
				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(file));
				long bytesRead = 0;
				byte[] bytesIn = new byte[BUFFER_SIZE];
				int read = 0;
				while ((read = bis.read(bytesIn)) != -1) {
					zos.write(bytesIn, 0, read);
					bytesRead += read;
				}
				zos.closeEntry();
			}
		} catch (Exception e) {
			// System.out.println("Error while creating the archive : " + e.getMessage());
		}
	}

	@Override
	public @NonNull Type type() {
		return Mandatory.Features.Any.DIST;
	}
}