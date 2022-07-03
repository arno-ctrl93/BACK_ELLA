package fr.epita.assistants.myide.myclass.featureclass.gitfeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;

public class PushClass implements Feature {

	@Override
	public @NotNull ExecutionReport execute(Project project, Object... params) {
		// TODO Auto-generated method stub
		try (Git git = Git.open(project.getRootNode().getPath().toFile())) { // TODO: check the params parameters
			PushCommand pushCommand = git.push();
			pushCommand.add("master");
			pushCommand.setRemote("origin");
			pushCommand.call();
			return new ExecutionReport() {
				@Override
				public boolean isSuccess() {
					return true;
				}
			};
		} catch (Exception e) {
			// System.out.println("Error in gitPush" + e.getMessage());
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
		return Mandatory.Features.Git.PUSH;
	}
}