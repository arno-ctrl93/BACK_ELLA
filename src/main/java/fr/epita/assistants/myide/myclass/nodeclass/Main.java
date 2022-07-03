package fr.epita.assistants.myide.myclass.nodeclass;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.dump.DumpArchiveEntry.TYPE;

import fr.epita.assistants.myide.domain.entity.Node;

interface Animal {
    public void eat();
    public void sleep();
}

class Tiger implements Animal {
    public void eat() {
        //System.out.println("Tiger eats meat");
    }
    public void sleep() {
        //System.out.println("Tiger sleeps for 8 hours");
    }
}

class Cat implements Animal {
    public void eat() {
        //System.out.println("Cat eats fish");
    }
    public void sleep() {
        //System.out.println("Cat sleeps for 4 hours");
    }
}

class Wolf implements Animal {
    public void eat() {
        //System.out.println("Wolf eats meat");
    }
    public void sleep() {
        //System.out.println("Wolf sleeps for 12 hours");
    }
}


public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //System.out.println("Hello World!");

        Path p = Path.of("C:/Users/msitest/Documents/PING_ELLA/myproject");
        FolderClass n = new FolderClass(p, "myproject", null);
        
        NodeServiceClass nsc = new NodeServiceClass();
        nsc.create(n, "folder1", Node.Types.FOLDER);
        nsc.create(n, "file1", Node.Types.FILE);
        nsc.create(n.getChildren().get(0), "folder2", Node.Types.FOLDER);
        nsc.create(n.getChildren().get(0), "file2", Node.Types.FILE);
        n.printNode("");

        String s = "je suis un string";
        String s1 = "[je m'insère dans un string]";

        nsc.update(n.getChildren().get(1), 0, 10, s.getBytes());
        nsc.update(n.getChildren().get(1), 8, 9, s1.getBytes());

        //nsc.delete(n.getChildren().get(0));
        //nsc.delete(n.getChildren().get(0));
        n.printNode("");
        






        List<Animal> animals = new ArrayList<>();
        
        Tiger t = new Tiger();
        animals.add(t);
        animals.add(new Tiger());
        animals.add(new Cat());
        animals.add(new Wolf());

        animals.forEach(action -> action.eat());

    }

    
}
