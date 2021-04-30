package com.activedgetechnologies.test.config;


import com.activedgetechnologies.test.model.entity.User;
import com.activedgetechnologies.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupBean {

    @Value("${default.user}")
    private String defaultUser;
    @Value("${default.password}")
    private String defaultPassword;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Custom: Activeedge test has started");

        User user = userService.findUserByEmail(defaultUser);
        if (user == null || user.getId() <= 0) {
            user = new User();
            user.setFirstName("Admin");
            user.setLastName("User");
            user.setEmail(defaultUser);
            user.setPassword(passwordEncoder.encode(defaultPassword));

            user = userService.createOrUpdateUser(user);
            logger.info("Successfully created default User");

        }


    }
}
