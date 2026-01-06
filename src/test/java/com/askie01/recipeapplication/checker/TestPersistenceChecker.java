package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasId;

public interface TestPersistenceChecker <Entity extends HasId<?>> {
    boolean wasCreated(Entity entity);

    boolean wasUpdated(Entity entity);
}
