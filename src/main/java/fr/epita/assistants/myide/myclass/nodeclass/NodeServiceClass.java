package fr.epita.assistants.myide.myclass.nodeclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.lucene.store.Directory;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Node.Type;
import fr.epita.assistants.myide.domain.entity.Node.Types;
import fr.epita.assistants.myide.domain.service.NodeService;

public class NodeServiceClass implements NodeService {

    /**
     * Update the content in the range [from, to[.
     *
     * @param node            Node to update (must be a file).
     * @param from            Beginning index of the text to update.
     * @param to              Last index of the text to update (Not included).
     * @param insertedContent Content to insert.
     * @throws Exception upon update failure.
     * @return The node that has been updated.
     */
    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent) {
        if (!node.isFile()) {
            throw new IllegalArgumentException("Node must be a file");
        }
        try {
            String content = new String(insertedContent, StandardCharsets.UTF_8);
            System.out.println(content);
            File file = new File(node.getPath().toString());
            FileInputStream fis = new FileInputStream(file);
            int r = 0;
            StringBuilder new_content_file = new StringBuilder();
            int index = 0;
            while (index < from && (r = fis.read()) != -1) {
                new_content_file.append((char) r);
                index++;
            }
            if (index == from) {
                new_content_file.append(content);
            }
            while (index < to && (r = fis.read()) != -1) {
                index++;
            }
            while ((r = fis.read()) != -1) {
                new_content_file.append((char) r);
            }
            fis.close();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(new_content_file.toString().getBytes(StandardCharsets.UTF_8));
            fos.close();

        } catch (Exception e) {
            throw new IllegalArgumentException("error: " + e);
        }
        return null;
    }

    /**
     * Delete the node given as parameter.
     *
     * @param node Node to remove.
     * @return True if the node has been deleted, false otherwise.
     */
    @Override
    public boolean delete(Node node) {
        try {
            if (node.isFile()) {
                File file = new File(node.getPath().toString());
                // System.out.println("File exists: " + file.exists());
                file.delete();
                ((FileClass) node).getParent().getChildren().remove(node);
                // System.out.println("File deleted: " + node.getPath().toString());
                return true;
            } else {
                File file = new File(node.getPath().toString());
                // System.out.println("Folder exists: " + file.exists());
                while (node.getChildren().size() > 0) {
                    // System.out.println("child: " +
                    // node.getChildren().get(0).getPath().toString());
                    delete(node.getChildren().get(0));
                }
                file.delete();
                ((FolderClass) node).getParent().getChildren().remove(node);
                // System.out.println("Folder deleted: " + node.getPath().toString());
                return true;
            }
        } catch (Exception e) {
            // System.out.println("error: " + e);
            return false;
        }
    }

    /**
     * Create a new node.
     *
     * @param folder Parent node of the new node.
     * @param name   Name of the new node.
     * @param type   Type of the new node.
     * @throws Exception upon creation failure.
     * @return Node that has been created.
     */
    @Override
    public Node create(Node folder, String name, Type type) {
        try {
            if (folder.getType() != Types.FOLDER) {
                throw new IllegalArgumentException("Node must be a folder");
            }
            if (type == Types.FILE) {
                File file = new File(folder.getPath().toString() + "/" + name);
                if (file.exists()) {
                    throw new IllegalArgumentException("File already exists");
                }
                file.createNewFile();
                if (file.exists()) {
                    FileClass fileClass = new FileClass(file.toPath(), name, folder);
                    folder.getChildren().add(fileClass);
                    return fileClass;
                } else {
                    throw new Exception("Error: File creation failed");
                }
            } else {
                File D = new File(folder.getPath().toString() + "/" + name);
                // System.out.println("D: " + D.toString());
                boolean D1 = D.mkdir();
                if (D1) {
                    FolderClass F = new FolderClass(D.toPath(), name, folder);
                    folder.getChildren().add(F);
                    return F;
                } else {
                    throw new Exception("Error: Folder creation failed");
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("error: " + e);
        }
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        String name = "";
        if (nodeToMove.getType() == Types.FILE) {
            File file = new File(nodeToMove.getPath().toString());
            if (!file.exists()) {
                throw new IllegalArgumentException("File does not exist");
            }
            name = ((FileClass) nodeToMove).getName();
        } else {
            File dossier = new File(nodeToMove.getPath().toString());
            if (!dossier.exists() && dossier.isDirectory()) {
                throw new IllegalArgumentException("Directory does not exist");
            }
            name = ((FolderClass) nodeToMove).getName();
        }
        Node n = create(destinationFolder, name, nodeToMove.getType());
        delete(nodeToMove);
        return n;
    }
}
