package com.open3hr.adeies.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.sql.DataSource;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) throws Exception {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, role FROM user WHERE username=?");

        return jdbcUserDetailsManager;


    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(configurer ->
        {
            try {
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasAnyRole("*")
                        .requestMatchers(HttpMethod.GET, "api/employee/{id}").hasAnyRole("*")
                        .requestMatchers(HttpMethod.GET, "api/users/info").hasAnyRole("*")
                        .anyRequest().authenticated();;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        httpSecurity.formLogin().disable().formLogin();
        httpSecurity.httpBasic();
        httpSecurity.cors();
        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }

    @Configuration
    @EnableWebMvc
    public class SpringConfig implements WebMvcConfigurer {


        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedOrigins("http:localhost:4200");
        }
    }
}
