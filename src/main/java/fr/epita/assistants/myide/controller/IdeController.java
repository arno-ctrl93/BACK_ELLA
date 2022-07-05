package fr.epita.assistants.myide.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epita.assistants.MyIde;
import fr.epita.assistants.MyIde.*;
import fr.epita.assistants.myide.model.CreateDTO;
import fr.epita.assistants.myide.model.PathDTO;
import fr.epita.assistants.myide.model.PathOnly;
import fr.epita.assistants.MyIde.Configuration;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.model.ProjectDTO;
import fr.epita.assistants.myide.model.SaveDTO;
import fr.epita.assistants.myide.myclass.nodeclass.FolderClass;
import fr.epita.assistants.myide.myclass.projectclass.projectclass;
import fr.epita.assistants.myide.myclass.nodeclass.FileClass;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping(
        value = "/savefile",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
        public Boolean saveFile(@RequestBody SaveDTO path) throws IOException {
            try {
                if (path.path.equals("")) {
                    return false;
                }
                File f = new File(path.path);
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileWriter fw = new FileWriter(f.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(path.content);
                bw.close();
                return true;
            }
            catch (IOException e) {
                return false;
            }
        }

    @PostMapping(
        value = "/createnode",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
        public Boolean CreateNode(@RequestBody CreateDTO path){
            String[] strArray = path.path.split("/");
            System.out.println(strArray);
            System.out.println(project);
            Node n = project.getRootNode();
            for (int i = 0; i < strArray.length - 1; i++){
                for (Node child : n.getChildren()){
                    if (child.isFile()){
                        if (((FileClass)child).getName().equals(strArray[i])){
                            n = child;
                            break;
                        }
                    }
                    else {
                        if (((FolderClass)child).getName().equals(strArray[i])){
                            n = child;
                            break;
                        }
                    }
                }
            }
            Node.Types t = Node.Types.FILE;
            if (path.isFolder){
                t = Node.Types.FOLDER;
            }
            ns.create(n, strArray[strArray.length - 1], t);
            return true;
        }

    
        @PostMapping(
            value = "/deletenode",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
            public Boolean DeleteNode(@RequestBody PathOnly path){
                File f = new File(path.path);
                if (!f.exists())
                    return false;

                String[] strArray = path.path.split("/");
                System.out.println(strArray);
                Node n = project.getRootNode();

                for (int i = 0; i < strArray.length; i++){
                    for (Node child : n.getChildren()){
                        if (child.isFile()){
                            if (((FileClass)child).getName().equals(strArray[i])){
                                n = child;
                                break;
                            }
                        }
                        else {
                            if (((FolderClass)child).getName().equals(strArray[i])){
                                n = child;
                                break;
                            }
                        }
                    }
                }
                ns.delete(n);
                return true;
            }

    @PostMapping(
    value = "/contentFile",
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public String contentFile(@RequestBody PathOnly dto) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(dto.path));
    try {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        String everything = sb.toString();
        return everything;
        } finally {
            br.close();
        }
    }



    @PostMapping(
    value = "/init",
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ProjectDTO init(@RequestBody PathDTO dto) {
        System.out.println("======LOAD A PROJECT======");
        System.out.println("Path: " + dto.path);
        Path p = Path.of(dto.path);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);
        ((FolderClass)project.getRootNode()).name = dto.name; 
        System.out.println("Project: " );
        System.out.println("path: "+ project.getRootNode());
        System.out.println("aspect: "+ project.getAspects());
        System.out.println("children size: "+ project.getRootNode().getChildren().size());
        return new ProjectDTO((projectclass)project);
    }
}
