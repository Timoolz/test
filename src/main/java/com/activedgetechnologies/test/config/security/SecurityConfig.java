package com.activedgetechnologies.test.config.security;


import com.activedgetechnologies.test.config.security.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppAuthenticationEntryPoint appAuthenticationEntryPoint;

    @Autowired
    private JwtTokenHandler jwtTokenHandler;


    @Value("${public.paths:}")
    private String publicPaths;

    @Value("${allowed.origins:}")
    private String allowedOrigins;

    @Value("${allowed.methods:}")
    private String allowedMethods;

    @Value("${allowed.headers:}")
    private String allowedHeaders;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] splitPublicPaths = new String[0];
        if (StringUtils.hasText(publicPaths)) {
            splitPublicPaths = publicPaths.split(",");
            for (int i = 0; i < splitPublicPaths.length; i++) {
                splitPublicPaths[i] = splitPublicPaths[i].trim();
            }
            http.authorizeRequests().antMatchers(splitPublicPaths).permitAll()
            ;
        }
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(splitPublicPaths).permitAll()
                .antMatchers("/health", "/info", "/trace", "/metrics", "/monitoring", "/webjars/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new AuthenticationFilter(jwtTokenHandler, allowedOrigins, allowedMethods, allowedHeaders), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(appAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        //To enable h2 database viewing
        http.headers().frameOptions().disable();


    }


}
