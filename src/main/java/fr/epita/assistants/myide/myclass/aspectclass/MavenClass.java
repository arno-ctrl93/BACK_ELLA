package fr.epita.assistants.myide.myclass.aspectclass;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import fr.epita.assistants.myide.domain.entity.Feature;
import java.util.ArrayList;
import java.util.List;

import fr.epita.assistants.myide.myclass.featureclass.mavenfeature.CleanClass;
import fr.epita.assistants.myide.myclass.featureclass.mavenfeature.CompileClass;
import fr.epita.assistants.myide.myclass.featureclass.mavenfeature.InstallClass;
import fr.epita.assistants.myide.myclass.featureclass.mavenfeature.PackageClass;
import fr.epita.assistants.myide.myclass.featureclass.mavenfeature.TestClass;
import fr.epita.assistants.myide.myclass.featureclass.mavenfeature.TreeClass;
import lombok.NonNull;


public class MavenClass implements Aspect {
    
    @Override
    public Type getType() {
        return Mandatory.Aspects.MAVEN;
    }

    @Override
    public @NonNull List<Feature> getFeatureList()
    {
        List<Feature> featureList = new ArrayList<Feature>();
        featureList.add(new CleanClass());
        featureList.add(new CompileClass());
        featureList.add(new TestClass());
        featureList.add(new InstallClass());
        featureList.add(new PackageClass());
        featureList.add(new TestClass());
        featureList.add(new TreeClass());

        return featureList;
    }
}