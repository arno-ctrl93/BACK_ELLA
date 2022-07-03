package fr.epita.assistants.myide.myclass.featureclass.mavenfeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

public class CleanClass implements Feature {

	@Override
	public @NotNull ExecutionReport execute(Project project, Object... params) {
		ArrayList list = new ArrayList();
		try {
			Process proc = Runtime.getRuntime().exec("mvn clean");
			InputStream istr = proc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(istr));
			String str;
			while ((str = br.readLine()) != null) 
						list.add(str);
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				return new ExecutionReport() {
					@Override
					public boolean isSuccess() {
						return false;
					}
				};
				};
			br.close();
		}
		catch (IOException e) {
			return new ExecutionReport() {
				@Override
				public boolean isSuccess() {
					return false;
				}
			};
		}
		return new ExecutionReport() {
			@Override
			public boolean isSuccess() {
				return true;
			}
		};
		
	}

	@Override
	public @NotNull Type type() {
		// TODO Auto-generated method stub
		return Mandatory.Features.Maven.CLEAN;
	}
}