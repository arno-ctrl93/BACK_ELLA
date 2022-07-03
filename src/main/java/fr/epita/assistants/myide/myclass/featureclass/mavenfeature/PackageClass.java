package fr.epita.assistants.myide.myclass.featureclass.mavenfeature;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

public class PackageClass implements Feature {

	@Override
	public @NotNull ExecutionReport execute(Project project, Object... params) {
		try {
			
            Runtime.getRuntime().exec("mvn package");
			//Runtime.getRuntime().exec("touch prout.txt");
            return new ExecutionReport() {
				@Override
				public boolean isSuccess() {
					return true;
				}
			};
        } catch (Exception e) {
            return new ExecutionReport() {
				@Override
				public boolean isSuccess() {
					return false;
				}
			};
        }
	}

	@Override
	public @NotNull Type type() {
		return Mandatory.Features.Maven.PACKAGE;
	}
}