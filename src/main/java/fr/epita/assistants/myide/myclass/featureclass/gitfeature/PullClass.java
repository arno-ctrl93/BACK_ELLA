package fr.epita.assistants.myide.myclass.featureclass.gitfeature;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.TrackingRefUpdate;

public class PullClass implements Feature {

	@Override
	public @NotNull ExecutionReport execute(Project project, Object... params) {
		// TODO Auto-generated method stub
		try (Git git = Git.open(project.getRootNode().getPath().toFile())) {
			PullResult result = git.pull().call();
			FetchResult fetchResult = result.getFetchResult();

			for (TrackingRefUpdate update : fetchResult.getTrackingRefUpdates()) {
				// get info about what changes have been done
				// System.out.println(update.getLocalName());
				// System.out.println(update.getRemoteName());
				// System.out.println(update.getResult());
			}
			return new ExecutionReport() {
				@Override
				public boolean isSuccess() {
					return true;
				}
			};
		} catch (Exception e) {
			// System.out.println("Error in gitPull : "+e.getMessage());
			return new ExecutionReport() {
				@Override
				public boolean isSuccess() {
					return false;
				}
			};
		}
		// TODO: check the return value
	}

	@Override
	public @NotNull Type type() {
		// TODO Auto-generated method stub
		return Mandatory.Features.Git.PULL;
	}

}