package fr.epita.assistants.myide.model;

import java.util.ArrayList;
import java.util.List;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.myclass.nodeclass.FileClass;
import fr.epita.assistants.myide.myclass.nodeclass.FolderClass;

public class NodeDTO {
    public String name;
    public String path;
    public List<NodeDTO> children;
    public boolean isFolder;
    public boolean isFile;

    public NodeDTO(FolderClass fc) {
        this.name = fc.getName();
        this.path = fc.getPath().toString();
        this.isFolder = true;
        this.isFile = false;
        this.children = new ArrayList<NodeDTO>();
        for (Node n : fc.getChildren()) {
            if (n.isFolder()) {
                this.children.add(new NodeDTO((FolderClass) n));
            } else {
                this.children.add(new NodeDTO((FileClass) n));
            }
        }
    }

    public NodeDTO(FileClass fc) {
        this.name = fc.getName();
        this.path = fc.getPath().toString();
        this.isFolder = false;
        this.isFile = true;
        this.children = new ArrayList<NodeDTO>();
    }

}