package fr.epita.assistants.myide.model;

import java.util.ArrayList;
import java.util.List;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.myclass.aspectclass.GitClass;
import fr.epita.assistants.myide.myclass.aspectclass.MavenClass;
import fr.epita.assistants.myide.myclass.nodeclass.FolderClass;
import fr.epita.assistants.myide.myclass.projectclass.projectclass;

public class ProjectDTO {
    public NodeDTO root;
    public List<String> aspects;

    public ProjectDTO(projectclass pc) {
        this.root = new NodeDTO((FolderClass)pc.getRootNode());
        this.aspects = new ArrayList<String>();
        for (Aspect s : pc.getAspects()) {
            if (s.getClass() == MavenClass.class) {
                this.aspects.add("Maven");
            }
            else if (s.getClass() == GitClass.class) {
                this.aspects.add("Git");
            }
        }
        this.aspects.add("Any");
    }
}

