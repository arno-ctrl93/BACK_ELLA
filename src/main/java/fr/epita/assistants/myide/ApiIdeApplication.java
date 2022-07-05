package fr.epita.assistants.myide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;

@SpringBootApplication
public class ApiIdeApplication {
	
    static ProjectService ps;
    static NodeService ns;
    static Project project;

	public static void main(String[] args) {
		SpringApplication.run(ApiIdeApplication.class, args);
	}

}
