package fr.epita.assistants.myide.myclass.featureclass.mavenfeature;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

public class ExecClass implements Feature {

	@Override
	public @NotNull ExecutionReport execute(Project project, Object... params) {
		// TODO Auto-generated method stub
		try {
            Runtime.getRuntime().exec("mvn exec -Dexec.args=\"" + params[0] + "\"");
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
		// TODO Auto-generated method stub
		return Mandatory.Features.Maven.EXEC;
	}
}