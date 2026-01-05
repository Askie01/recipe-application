package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasName;

public interface NameTestPresenceChecker <Nameable extends HasName<?>> {
    boolean hasName(Nameable nameable);
}
