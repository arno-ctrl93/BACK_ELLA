package fr.epita.assistants.myide.controller;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epita.assistants.MyIde;
import fr.epita.assistants.MyIde.*;
import fr.epita.assistants.MyIde.Configuration;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.model.ProjectDTO;
import fr.epita.assistants.myide.myclass.nodeclass.FolderClass;
import fr.epita.assistants.myide.myclass.projectclass.projectclass;
import fr.epita.assistants.myide.myclass.nodeclass.FileClass;

@RestController
public class IdeController {

    
    ProjectService ps;
    NodeService ns;
    Project project;

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }


    @GetMapping("/search/{word}")
    public Feature.ExecutionReport search(@PathVariable String word) {
        return project.getFeature(Mandatory.Features.Any.SEARCH).get().execute(project, word);
    }

    @PostMapping("/git/add")
    public Feature.ExecutionReport gitAdd(@RequestBody List<String> files) {
        return project.getFeature(Mandatory.Features.Git.ADD).get().execute(project, files);
    }

    @PostMapping("/git/commit")
    public Feature.ExecutionReport gitCommit(@RequestBody String message) {
        return project.getFeature(Mandatory.Features.Git.COMMIT).get().execute(project, message);
    }

    @PostMapping("/git/push")
    public Feature.ExecutionReport gitPush(@RequestBody String remote) {
        return project.getFeature(Mandatory.Features.Git.PUSH).get().execute(project, remote);
    }

    @PostMapping("/git/pull")
    public Feature.ExecutionReport gitPull(@RequestBody String remote) {
        return project.getFeature(Mandatory.Features.Git.PULL).get().execute(project, remote);
    }



    @PostMapping("/mvn/install")
    public Feature.ExecutionReport mvnInstall(@RequestBody String artifact) {
        return project.getFeature(Mandatory.Features.Maven.INSTALL).get().execute(project, artifact);
    }

    @PostMapping("/mvn/compile")
    public Feature.ExecutionReport mvnCompile() {
        return project.getFeature(Mandatory.Features.Maven.COMPILE).get().execute(project);
    }

    @PostMapping("/mvn/test")
    public Feature.ExecutionReport mvnTest() {
        return project.getFeature(Mandatory.Features.Maven.TEST).get().execute(project);
    }

    @PostMapping("/mvn/package")
    public Feature.ExecutionReport mvnPackage() {
        return project.getFeature(Mandatory.Features.Maven.PACKAGE).get().execute(project);
    }

    @PostMapping("/mvn/clean")
    public Feature.ExecutionReport mvnClean() {
        return project.getFeature(Mandatory.Features.Maven.CLEAN).get().execute(project);
    }

    @PostMapping("/mvn/exec")
    public Feature.ExecutionReport mvnExec(@RequestBody String command) {
        return project.getFeature(Mandatory.Features.Maven.COMPILE).get().execute(project, command);
    }

    @PostMapping("/mvn/tree")
    public Feature.ExecutionReport mvnTree() {
        return project.getFeature(Mandatory.Features.Maven.TREE).get().execute(project);
    }

    @PostMapping("/move")
    public Boolean moveNode() {
        return false;
    }

    @PostMapping("/create")
    public Boolean createNode() {
        return false;
    }

    @PostMapping("/delete")
    public Boolean deleteNode() {
        return false;
    }








    @GetMapping("/init/{path}")
    public ProjectDTO init(@PathVariable("path") final String path) {
        System.out.println("======LOAD A PROJECT======");
        System.out.println("Path: " + path);
        Path p = Path.of(path);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);
        System.out.println("Project: " );
        System.out.println("path: "+ project.getRootNode());
        System.out.println("aspect: "+ project.getAspects());
        System.out.println("children size: "+ project.getRootNode().getChildren().size());
        //removeparent(project.gelstRootNode());
        return new ProjectDTO((projectclass)project);
    }


}
