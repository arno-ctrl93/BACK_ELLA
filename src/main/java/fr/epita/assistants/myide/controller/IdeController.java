package fr.epita.assistants.myide.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import fr.epita.assistants.myide.model.arboDTO;
import fr.epita.assistants.myide.model.gitAddDTO;
import fr.epita.assistants.myide.model.gitCommitDTO;
import fr.epita.assistants.myide.model.mvnCompileDTO;
import fr.epita.assistants.myide.model.mvnExecuteDTO;
import fr.epita.assistants.myide.myclass.nodeclass.FolderClass;
import fr.epita.assistants.myide.myclass.projectclass.projectclass;
import fr.epita.assistants.myide.myclass.nodeclass.FileClass;




@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class IdeController {


    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }


    @GetMapping("/search/{word}")
    public Feature.ExecutionReport search(@PathVariable String word) {
        //return project.getFeature(Mandatory.Features.Any.SEARCH).get().execute(project, word);
        return null;
    }

    @PostMapping(
        value = "/git/add",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Feature.ExecutionReport gitAdd(@RequestBody gitAddDTO dto) throws IOException {
        
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);

        return project.getFeature(Mandatory.Features.Git.ADD).get().execute(project, dto.files);
        //return null;
    }

    @PostMapping("/git/commit")
    public Feature.ExecutionReport gitCommit(@RequestBody gitCommitDTO message) throws IOException {
        
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);

        return project.getFeature(Mandatory.Features.Git.COMMIT).get().execute(project, message.message);
        //return null;
    }

    @PostMapping("/git/push")
    public Feature.ExecutionReport gitPush(@RequestBody gitCommitDTO remote) throws IOException {
        
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);

        return project.getFeature(Mandatory.Features.Git.PUSH).get().execute(project);
        //return null;
    }

    @PostMapping("/git/pull")
    public Feature.ExecutionReport gitPull(@RequestBody gitCommitDTO remote) throws IOException {
        
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);

        return project.getFeature(Mandatory.Features.Git.PULL).get().execute(project);
        //return null;
    }



    @PostMapping("/mvn/install")
    public Feature.ExecutionReport mvnInstall(@RequestBody mvnCompileDTO dto) throws IOException {
        
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);

        return project.getFeature(Mandatory.Features.Maven.INSTALL).get().execute(project);
        //return null;
    }

    @PostMapping(
    value = "/mvn/compile",
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Feature.ExecutionReport mvnCompile(@RequestBody mvnCompileDTO path) throws IOException {
        
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);
        NodeService ns = ps.getNodeService();
        return project.getFeature(Mandatory.Features.Maven.COMPILE).get().execute(project);
    }

    @PostMapping(
        value = "/mvn/test",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Feature.ExecutionReport mvnTest(@RequestBody mvnCompileDTO path) throws IOException {
        
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);
        NodeService ns = ps.getNodeService();
        return project.getFeature(Mandatory.Features.Maven.TEST).get().execute(project);
        //return null;
    }

    @PostMapping(
        value = "/mvn/package",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Feature.ExecutionReport mvnPackage(@RequestBody mvnCompileDTO path) throws IOException {
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);

        return project.getFeature(Mandatory.Features.Maven.PACKAGE).get().execute(project);
        //return null;
    }

    @PostMapping(
        value = "/mvn/clean",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Feature.ExecutionReport mvnClean(@RequestBody mvnCompileDTO path) throws IOException {
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);
        //NodeService ns = ps.getNodeService();

        return project.getFeature(Mandatory.Features.Maven.CLEAN).get().execute(project);
        //return null;
    }

    @PostMapping(
        value = "/mvn/execute",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Feature.ExecutionReport mvnExec(@RequestBody mvnExecuteDTO command) throws IOException {
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);

        return project.getFeature(Mandatory.Features.Maven.COMPILE).get().execute(project, command.command);
        //return null;
    }

    @PostMapping(
        value = "/mvn/tree",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Feature.ExecutionReport mvnTree(mvnCompileDTO command) throws IOException {
        File doc = new File("the-file-name.txt");
        BufferedReader obj = new BufferedReader(new FileReader(doc));
        String strng = obj.readLine();
        System.out.println("test : " + strng);
        
        Path p = Path.of(strng);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);
        NodeService ns = ps.getNodeService();

        return project.getFeature(Mandatory.Features.Maven.TREE).get().execute(project);
        //return null;
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
        public Boolean CreateNode(@RequestBody CreateDTO path) throws IOException{
            File doc = new File("the-file-name.txt");
            BufferedReader obj = new BufferedReader(new FileReader(doc));
            String strng = obj.readLine();
            System.out.println("test : " + strng);
            
            Path p = Path.of(strng);
            Configuration configuration = new Configuration(null, null);
            ProjectService ps = MyIde.init(configuration);
            Project project = ps.load(p);
            NodeService ns = ps.getNodeService();



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
            public Boolean DeleteNode(@RequestBody PathOnly path) throws IOException{
                File f = new File(path.path);
                if (!f.exists())
                    return false;
            File doc = new File("the-file-name.txt");
            BufferedReader obj = new BufferedReader(new FileReader(doc));
            String strng = obj.readLine();
            System.out.println("test : " + strng);
            
            Path p = Path.of(strng);
            Configuration configuration = new Configuration(null, null);
            ProjectService ps = MyIde.init(configuration);
            Project project = ps.load(p);
            NodeService ns = ps.getNodeService();

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

    //@GetMapping("/getfilearbo")






    @PostMapping(
    value = "/init",
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ProjectDTO init(@RequestBody PathDTO dto) throws FileNotFoundException, UnsupportedEncodingException {
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

        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
        writer.println(dto.path);
        writer.close();

        return new ProjectDTO((projectclass)project);
    }


    public int arboidincr(arboDTO ab, int count){
        if (count == 0)
            ab.id = "root";
        else
            ab.id = Integer.toString(count);
        count ++;
        for (arboDTO a : ab.children){
            count = arboidincr(a, count);
        }
        return count;
    }

    @PostMapping(
    value = "/arbofile",
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public arboDTO arboFile(@RequestBody PathOnly dto) {
        //System.out.println("======LOAD A PROJECT======");
        System.out.println("Path: " + dto.path);
        Path p = Path.of(dto.path);
        Configuration configuration = new Configuration(null, null);
        ProjectService ps = MyIde.init(configuration);
        Project project = ps.load(p);
        ProjectDTO pd = new ProjectDTO((projectclass)project);
        arboDTO ad =  new arboDTO(pd.root);
        arboidincr(ad, 0);
        return ad;

    }
}



