package fr.epita.assistants.myide.domain.entity;

import fr.epita.assistants.utils.Given;
import lombok.NonNull;


@Given()
public interface Feature {

    /**
     * @param project {@link Project} on which the feature is executed.
     * @param params Parameters given to the features.
     * @return {@link ExecutionReport}
     */
    @NonNull ExecutionReport execute(final Project project, final Object... params);

    /**
     * @return The type of the Feature.
     */
    @NonNull Type type();

    interface ExecutionReport {
        boolean isSuccess();
    }

    interface Type {}
}
