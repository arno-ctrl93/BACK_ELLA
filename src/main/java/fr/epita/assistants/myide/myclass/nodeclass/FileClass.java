package fr.epita.assistants.myide.myclass.nodeclass;

import java.nio.file.Path;
import java.util.List;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Node;

public class FileClass implements Node {

    public String name;
    private Path path = null;
    private Node parent = null;

    public FileClass(Path string, String name, Node parent) {
        this.path = string;
        this.name = name;
        this.parent = parent;
    }

    @Override
    public @NotNull Path getPath() {
        return path;
    }

    @Override
    public @NotNull Type getType() {
        return Types.FILE;
    }

    @Override
    public @NotNull List<@NotNull Node> getChildren() {
        return null;
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
         System.out.println(indent + " > " + name);
    }
}
