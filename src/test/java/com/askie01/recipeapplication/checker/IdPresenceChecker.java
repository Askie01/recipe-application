package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasId;

public interface IdPresenceChecker <Identifiable extends HasId<?>> {
    boolean hasId(Identifiable identifiable);
}
