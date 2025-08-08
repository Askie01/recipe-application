package com.askie01.recipeapplication.model.common;

public interface Versionable <Version> {
    Version getVersion();

    void setVersion(Version version);
}
