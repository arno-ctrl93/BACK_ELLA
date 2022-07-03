package fr.epita.assistants.myide.myclass.projectclass;

import java.io.File;
import java.nio.file.Path;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.myclass.aspectclass.AnyClass;
import fr.epita.assistants.myide.myclass.aspectclass.GitClass;
import fr.epita.assistants.myide.myclass.aspectclass.MavenClass;
import fr.epita.assistants.myide.myclass.nodeclass.FolderClass;
import fr.epita.assistants.myide.myclass.nodeclass.NodeServiceClass;

public class projectserviceclass implements ProjectService {

    private NodeServiceClass NodeService = null;

    @Override
    public @NotNull Project load(@NotNull Path root) {
        System.out.println("ProjectService.load()");
        System.out.println("root: " + root);
        if (!(new File(root.toString())).exists()) {
            File D = new File(root.toString());
            Boolean B = D.mkdir();
            if (!B) {
                throw new IllegalArgumentException("Cannot create directory of the project");
            }
        }
        NodeService = new NodeServiceClass();
        FolderClass rootFolder = new FolderClass(root, "root", null);
        projectclass project = new projectclass(rootFolder);

        // check if git or maven
        File[] file_root = (new File(root.toString())).listFiles();
        //// System.out.println(file_root.length);
        for (File file : file_root) {

            //// System.out.println("File : "+ file.toString());
            if (file.getName().equals(".git")) {
                // git
                project.addAspect(new GitClass());
            } else if (file.getName().equals("pom.xml")) {
                // maven
                project.addAspect(new MavenClass());
            }
        }

        NodeService = new NodeServiceClass();
        AnyClass anyClass = new AnyClass();
        project.addAspect(anyClass);
        return project;
    }

    /**
     * Execute the given feature on the given project.
     *
     * @param project     Project for which the features is executed.
     * @param featureType Type of the feature to execute.
     * @param params      Parameters given to the features.
     * @return Execution report of the feature.
     */
    @Override
    public @NotNull Feature.ExecutionReport execute(@NotNull Project project, @NotNull Feature.Type featureType,
            Object... params) {
        // TODO Auto-generated method stub
        return project.getFeature(featureType).get().execute(project, params);

    }

    @Override
    public @NotNull NodeService getNodeService() {
        // TODO Auto-generated method stub
        return NodeService;

    }

}
