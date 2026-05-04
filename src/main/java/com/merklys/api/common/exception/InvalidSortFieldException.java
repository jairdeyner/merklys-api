package com.merklys.api.common.exception;

import java.util.Set;

public class InvalidSortFieldException extends RuntimeException {
    public InvalidSortFieldException(String field, Set<String> allowedFields) {
        super("El campo de ordenamiento '" + field + "' no es válido. " +
                "Campos permitidos: " + allowedFields);
    }
}
