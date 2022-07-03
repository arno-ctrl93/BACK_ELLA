package fr.epita.assistants.myide.myclass.aspectclass;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.ArrayList;
import java.util.List;


import fr.epita.assistants.myide.myclass.featureclass.anyfeature.CleanUpClass;
import fr.epita.assistants.myide.myclass.featureclass.anyfeature.DistClass;
import fr.epita.assistants.myide.myclass.featureclass.anyfeature.SearchClass;
import lombok.NonNull;

public class AnyClass implements Aspect {

    @Override
    public Type getType() {
        return Mandatory.Aspects.ANY;
    }

    @Override
    public @NonNull List<Feature> getFeatureList()
    {
        List<Feature> featureList = new ArrayList<Feature>();
        featureList.add(new CleanUpClass());
        featureList.add(new DistClass());
        featureList.add(new SearchClass());

        return featureList;
    }

}
