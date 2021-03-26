package com.account.processor.controller;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResourceResponse<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private T dto;
    private List<String> errorMessages;

    public ResourceResponse(final T dto) {
        this.dto = dto;
    }

    public ResourceResponse(final List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}