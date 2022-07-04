package fr.epita.assistants.myide.myclass.aspectclass;

import java.util.ArrayList;
import java.util.List;


import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.*;
import fr.epita.assistants.myide.myclass.featureclass.gitfeature.AddClass;
import fr.epita.assistants.myide.myclass.featureclass.gitfeature.CommitClass;
import fr.epita.assistants.myide.myclass.featureclass.gitfeature.PullClass;
import fr.epita.assistants.myide.myclass.featureclass.gitfeature.PushClass;
import lombok.NonNull;;


public class GitClass implements Aspect {

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return Mandatory.Aspects.GIT;
    }


    @Override
    public @NonNull List<Feature> getFeatureList() {
        List<Feature> featureList = new ArrayList<Feature>();
        featureList.add(new AddClass());
        featureList.add(new CommitClass());
        featureList.add(new PushClass());
        featureList.add(new PullClass());

        return featureList;
    }
}
