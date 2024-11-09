package com.auth.sistema.dto.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ResponseModelDTO<T> {
    private int status;
    private T model;
    private String error;

    public ResponseModelDTO(HttpStatus status, T model) {
        this.status = status.value();
        this.model = model;
        this.error = "";
    }

    public ResponseModelDTO(HttpStatus status, String error) {
        this.status = status.value();
        this.model = null;
        this.error = error;
    }

    public ResponseModelDTO(T model) {
        this.status = 200;
        this.model = model;
        this.error = "";
    }

    public ResponseModelDTO(String model) {
        this.status = 200;
        this.model = (T) model;
        this.error = "";
    }
}
