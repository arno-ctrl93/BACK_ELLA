package fr.epita.assistants.myide.model;

import java.util.ArrayList;
import java.util.List;

import fr.epita.assistants.myide.myclass.nodeclass.FolderClass;

public class arboDTO {
     public String id;
     public String name;
     public String path;
     public List<arboDTO> children;
     

    public arboDTO(NodeDTO nd) {
        this.id = "0";
        this.name = nd.name;
        this.path = nd.path;
        this.children = new ArrayList<arboDTO>();
        for (NodeDTO n : nd.children) {
            if (n.isFolder) {
                this.children.add(new arboDTO(n));
            } else {
                this.children.add(new arboDTO(n));
            }
        }
    }
}
