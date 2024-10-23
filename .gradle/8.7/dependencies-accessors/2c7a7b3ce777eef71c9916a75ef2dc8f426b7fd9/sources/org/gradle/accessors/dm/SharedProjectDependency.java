package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class SharedProjectDependency extends DelegatingProjectDependency {

    @Inject
    public SharedProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":shared:composeApp"
     */
    public Shared_ComposeAppProjectDependency getComposeApp() { return new Shared_ComposeAppProjectDependency(getFactory(), create(":shared:composeApp")); }

    /**
     * Creates a project dependency on the project at path ":shared:pokertracker"
     */
    public Shared_PokertrackerProjectDependency getPokertracker() { return new Shared_PokertrackerProjectDependency(getFactory(), create(":shared:pokertracker")); }

}
