package fr.epita.assistants.myide.myclass.featureclass.gitfeature;

import java.nio.file.Path;

import javax.validation.constraints.NotNull;

import org.eclipse.jgit.api.Git;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

public class AddClass implements Feature {

	@Override
	public @NotNull ExecutionReport execute(Project project, Object... params) {
		// TODO Auto-generated method stub
		Path repoPath = project.getRootNode().getPath();
		try (Git git = Git.open(repoPath.toFile())) {
			for (Object filename : params) {
				String filePath = String.valueOf(filename);
				git.add().addFilepattern(filePath).call();
			}
			return new ExecutionReport() {
				@Override
				public boolean isSuccess() {
					return true;
				}
			};
		}

		catch (Exception e) {
			//System.out.println("Error in gitAdd : " + e.getMessage());
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
		return Mandatory.Features.Git.ADD;
	}

	/*
	 * public void gitAdd(String filename) {
	 * try (Git git = Git.open(repoPath.toFile())) {
	 * git.add().addFilepattern(filename).call();
	 * }
	 * catch(Exception e) {
	 * //System.out.println("Error in gitAdd : "+e.getMessage());
	 * }
	 * }
	 */
}