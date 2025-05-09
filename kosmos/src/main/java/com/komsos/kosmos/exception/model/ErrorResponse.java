package com.komsos.kosmos.exception.model;

import lombok.Data;

@Data
public class ErrorResponse {

    private String mensaje;

    private String code;

    private String error;

    private String path;

    private long timestamp;

}