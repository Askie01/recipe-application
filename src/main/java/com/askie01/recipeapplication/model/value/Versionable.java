package com.askie01.recipeapplication.model.value;

public interface Versionable <Version> {
    Version getVersion();

    void setVersion(Version version);
}
