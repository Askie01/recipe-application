package com.askie01.recipeapplication.builder;

import com.askie01.recipeapplication.model.value.HasSimpleAudit;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class HasSimpleAuditTestBuilder implements HasSimpleAudit {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
