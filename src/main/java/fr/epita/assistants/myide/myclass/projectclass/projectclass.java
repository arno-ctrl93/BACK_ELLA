

package fr.epita.assistants.myide.myclass.projectclass;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;

public class projectclass implements Project {

    private Node rootNode = null;
    private Set<Aspect> aspects = null;

    public projectclass(Node rootNode) {
        this.rootNode = rootNode;
        this.aspects = new java.util.HashSet<Aspect>();
    }


    @Override
    public @NotNull Node getRootNode() {
        // TODO Auto-generated method stub
        return rootNode;
    }

    @Override
    public @NotNull Set<Aspect> getAspects() {
        // TODO Auto-generated method stub
        return aspects;
    }

    @Override
    public @NotNull Optional<Feature> getFeature(@NotNull Feature.Type featureType) {

        for (Aspect aspect : aspects) {
            List<Feature> features = aspect.getFeatureList();
            for (Feature feature : features) {
                if (feature.type() == featureType) {
                    return Optional.of(feature);
                }
            }
        }
        return Optional.empty();

    }

    public void addAspect(Aspect aspect) {
        this.aspects.add(aspect);
    }
    
}
