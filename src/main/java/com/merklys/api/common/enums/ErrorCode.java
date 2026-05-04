package com.merklys.api.common.enums;

public enum ErrorCode {
    // Authentication
    UNAUTHORIZED,
    INVALID_CREDENTIALS,
    ACCOUNT_DISABLED,
    ACCOUNT_LOCKED,
    ACCESS_DENIED,

    // Validation
    VALIDATION_ERROR,
    TYPE_MISMATCH,
    METHOD_NOT_ALLOWED,
    INVALID_SORT_FIELD,

    // Resource
    RESOURCE_NOT_FOUND,
    RESOURCE_ALREADY_EXISTS,

    // Business
    BUSINESS_RULE_VIOLATION,

    // System
    INTERNAL_SERVER_ERROR,
}
