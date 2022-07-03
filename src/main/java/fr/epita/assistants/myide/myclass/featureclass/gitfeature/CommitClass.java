package fr.epita.assistants.myide.myclass.featureclass.gitfeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.Git;

public class CommitClass implements Feature {

	@Override
	public @NotNull ExecutionReport execute(Project project, Object... params) {
		if (params.length < 2) {
			return new ExecutionReport() {
				@Override
				public boolean isSuccess() {
					return false;
				}
			};
		}
		var message = (String) params[1]; // -m is the [0], [1] the message
		try (Git git = Git.open(project.getRootNode().getPath().toFile())) {
			git.commit().setMessage(message).call();
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
		return Mandatory.Features.Git.COMMIT;
	}
}