package fr.epita.assistants.myide.domain.entity;

import fr.epita.assistants.utils.Given;

import java.util.Collections;
import java.util.List;

@Given()
public interface Aspect {

    /**
     * @return The type of the Aspect.
     */
    Aspect.Type getType();

    /**
     * @return The list of features associated with the Aspect.
     */
    default List<Feature> getFeatureList() { return Collections.emptyList(); }

    interface Type {

    }
}
