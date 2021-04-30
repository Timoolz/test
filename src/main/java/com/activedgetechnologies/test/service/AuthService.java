package com.activedgetechnologies.test.service;



import com.activedgetechnologies.test.config.security.JwtTokenHandler;
import com.activedgetechnologies.test.exception.InvalidCredentialException;
import com.activedgetechnologies.test.model.TokenInfo;
import com.activedgetechnologies.test.model.TokenType;
import com.activedgetechnologies.test.model.user.User;
import com.activedgetechnologies.test.model.user.UserLogin;
import com.activedgetechnologies.test.model.user.UserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenHandler tokenHandler;



    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder, JwtTokenHandler tokenHandler) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenHandler = tokenHandler;

    }


    public UserResult login(UserLogin userLogin) throws InvalidCredentialException {
        User existingUser;

        existingUser = userService.findUserByEmail(userLogin.getEmail());

        if (existingUser == null) {
            throw new InvalidCredentialException();
        }

        if (!passwordEncoder.matches(userLogin.getPassword(), existingUser.getPassword())) {
            throw new InvalidCredentialException();
        }

        String accessToken = tokenHandler.createToken(existingUser, null);
        return UserResult
                .builder()
                .tokenInfo(
                        TokenInfo
                                .builder()
                                .accessToken(accessToken)
                                .tokenType(TokenType.BEARER)
                                .build()
                )
                .user(existingUser)
                .build();
    }

}
