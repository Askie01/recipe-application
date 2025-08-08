package com.askie01.recipeapplication.model.common;

public interface Auditable <Time, Auditor> {
    Time getCreatedAt();

    void setCreatedAt(Time createdAt);

    Auditor getCreatedBy();

    void setCreatedBy(Auditor createdBy);

    Time getUpdatedAt();

    void setUpdatedAt(Time updatedAt);

    Auditor getUpdatedBy();

    void setUpdatedBy(Auditor updatedBy);
}
