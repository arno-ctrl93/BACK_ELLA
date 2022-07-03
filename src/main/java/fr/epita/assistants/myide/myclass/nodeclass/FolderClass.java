package fr.epita.assistants.myide.myclass.nodeclass;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Node;

public class FolderClass implements Node {

    private String name;
    private Path path = null;
    private List<Node> children = null;
    private Node parent = null;

    public void LoadChildren() {
        File[] lst = (new File(path.toString())).listFiles();
        if (lst == null) {
            return;
        }
        for (File file : lst) {
            if (file.isDirectory()) {
                FolderClass f = new FolderClass(file.toPath(), file.getName(), this);
                children.add(f);
            } else {
                FileClass f = new FileClass(file.toPath(), file.getName(), this);
                children.add(f);
            }
        }
    }

    public FolderClass(Path string, String name, Node parent) {

        this.path = string;
        this.name = name;
        children = new ArrayList<Node>();
        LoadChildren();
        this.parent = parent;
    }

    @Override
    public @NotNull Path getPath() {
        return path;
    }

    @Override
    public @NotNull Type getType() {
        return Types.FOLDER;
    }

    @Override
    public @NotNull List<@NotNull Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void printNode(String indent) {
        indent += " > " + name;
        // System.out.println(indent);
        for (Node node : children) {
            if (node.getType() == Types.FOLDER) {
                FolderClass folder = (FolderClass) node;
                folder.printNode(indent);
            } else if (node.getType() == Types.FILE) {
                FileClass file = (FileClass) node;
                file.printNode(indent);
            }
        }
    }
}