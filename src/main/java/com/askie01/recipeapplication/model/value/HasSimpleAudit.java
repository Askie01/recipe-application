package com.askie01.recipeapplication.model.value;

import java.time.LocalDateTime;

public interface HasSimpleAudit
        extends HasAudit<LocalDateTime, String> {
}
