package com.autobots.automanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ResponseModelDTO {
    private int status;
    private Object model;
    private String error;

    public ResponseModelDTO(HttpStatus status, Object model) {
        this.status = status.value();
        this.model = model;
        this.error = "";
    }

    public ResponseModelDTO(HttpStatus status, String error) {
        this.status = status.value();
        this.model = null;
        this.error = error;
    }

    public ResponseModelDTO(Object model) {
        this.status = 200;
        this.model = model;
        this.error = "";
    }

    public ResponseModelDTO(String model) {
        this.status = 200;
        this.model = model;
        this.error = "";
    }
}
