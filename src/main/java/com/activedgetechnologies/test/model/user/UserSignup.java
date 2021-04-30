package com.activedgetechnologies.test.model.user;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserSignup {

    @NotBlank
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    private String password;


}
