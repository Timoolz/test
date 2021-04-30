package com.activedgetechnologies.test.api;



import com.activedgetechnologies.test.model.ApiResponse;
import com.activedgetechnologies.test.model.ResponseCode;
import com.activedgetechnologies.test.model.user.UserLogin;
import com.activedgetechnologies.test.model.user.UserResult;
import com.activedgetechnologies.test.service.AuthService;
import com.activedgetechnologies.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("v1/auth")
@Validated
public class AuthController {


    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse login(@Valid @RequestBody UserLogin login) throws Exception {
        return ApiResponse.builder()
                .message("Login Successful")
                .data(authService.login(login))
                .responseCode(ResponseCode.SUCCESS)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getUser(@AuthenticationPrincipal UserResult userResult) {

        return ApiResponse
                .builder()
                .message("Fetched User successfully")
                .data(userResult)
                .responseCode(ResponseCode.SUCCESS)
                .build();
    }

}
