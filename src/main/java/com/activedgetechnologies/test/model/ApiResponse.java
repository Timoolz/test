package com.activedgetechnologies.test.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponse {
    private String message;
    private Object data;
    private ResponseCode responseCode;
    private List<String> errors;
    private String logId;
}
