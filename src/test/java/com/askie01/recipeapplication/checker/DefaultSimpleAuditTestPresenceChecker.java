package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.value.HasSimpleAudit;

import java.time.LocalDateTime;

public class DefaultSimpleAuditTestPresenceChecker implements SimpleAuditTestPresenceChecker {

    @Override
    public boolean hasCreatedAt(HasSimpleAudit hasSimpleAudit) {
        return createdAtIsNotFutureDate(hasSimpleAudit);
    }

    private boolean createdAtIsNotFutureDate(HasSimpleAudit hasSimpleAudit) {
        final LocalDateTime createdAt = hasSimpleAudit.getCreatedAt();
        return createdAtIsNotNull(createdAt) && createdAtIsBeforeCurrentTime(createdAt);
    }

    private boolean createdAtIsNotNull(LocalDateTime createdAt) {
        return isNotNull(createdAt);
    }

    private boolean createdAtIsBeforeCurrentTime(LocalDateTime createdAt) {
        return isBeforeCurrentTime(createdAt);
    }

    @Override
    public boolean hasCreatedBy(HasSimpleAudit hasSimpleAudit) {
        return hasNonBlankCreatedBy(hasSimpleAudit);
    }

    private boolean hasNonBlankCreatedBy(HasSimpleAudit hasSimpleAudit) {
        final String createdBy = hasSimpleAudit.getCreatedBy();
        return createdByIsNotNull(createdBy) && createdByIsNonBlank(createdBy);
    }

    private boolean createdByIsNotNull(String createdBy) {
        return isNotNull(createdBy);
    }

    private boolean createdByIsNonBlank(String createdBy) {
        return isNonBlank(createdBy);
    }

    @Override
    public boolean hasUpdatedAt(HasSimpleAudit hasSimpleAudit) {
        return updatedAtIsNotFutureDate(hasSimpleAudit);
    }

    private boolean updatedAtIsNotFutureDate(HasSimpleAudit hasSimpleAudit) {
        final LocalDateTime updatedAt = hasSimpleAudit.getUpdatedAt();
        return updatedAtIsNotNull(updatedAt) && updatedAtIsBeforeCurrentTime(updatedAt);
    }

    private boolean updatedAtIsNotNull(LocalDateTime updatedAt) {
        return isNotNull(updatedAt);
    }

    private boolean updatedAtIsBeforeCurrentTime(LocalDateTime updatedAt) {
        return isBeforeCurrentTime(updatedAt);
    }

    @Override
    public boolean hasUpdatedBy(HasSimpleAudit hasSimpleAudit) {
        return hasNonBlankUpdatedBy(hasSimpleAudit);
    }

    private boolean hasNonBlankUpdatedBy(HasSimpleAudit hasSimpleAudit) {
        final String updatedBy = hasSimpleAudit.getUpdatedBy();
        return updatedByIsNotNull(updatedBy) && updatedByIsNonBlank(updatedBy);
    }

    private boolean updatedByIsNotNull(String updatedBy) {
        return isNotNull(updatedBy);
    }

    private boolean updatedByIsNonBlank(String updatedBy) {
        return isNonBlank(updatedBy);
    }

    private boolean isNotNull(LocalDateTime time) {
        return time != null;
    }

    private boolean isBeforeCurrentTime(LocalDateTime time) {
        final LocalDateTime currentTime = LocalDateTime.now();
        return time.isBefore(currentTime);
    }

    private boolean isNotNull(String auditor) {
        return auditor != null;
    }

    private boolean isNonBlank(String auditor) {
        return !auditor.isBlank();
    }
}
