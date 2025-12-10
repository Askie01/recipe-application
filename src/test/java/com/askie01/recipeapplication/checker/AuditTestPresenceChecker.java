package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasAudit;

public interface AuditTestPresenceChecker <Auditable extends HasAudit<?, ?>> {
    boolean hasCreatedAt(Auditable auditable);

    boolean hasCreatedBy(Auditable auditable);

    boolean hasUpdatedAt(Auditable auditable);

    boolean hasUpdatedBy(Auditable auditable);
}
