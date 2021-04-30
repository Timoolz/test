package com.activedgetechnologies.test.model;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class TokenInfo {
    @NotBlank
    private String accessToken;

    private TokenType tokenType;

}

