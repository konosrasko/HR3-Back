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
                        // ### LEAVE REQUEST SECURE ENDPOINTS ###
                        .requestMatchers(HttpMethod.GET,"/api/leaverequests/searchemployeeleaverequest/{id}").hasAnyRole("HR")
                        .requestMatchers(HttpMethod.GET,"/api//leaverequests").hasAnyRole("HR", "Employee", "Admin")
                        .requestMatchers(HttpMethod.GET,"/api/leaverequests/{id}").hasAnyRole("HR", "Employee", "Admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/leaverequests/{id}").hasAnyRole("HR", "Employee", "Admin")
                        .requestMatchers(HttpMethod.GET,"/api/pending").hasAnyRole("Employee") //μαλλον για πεταμα
                        // ### LEAVE REQUEST SECURE ENDPOINTS ###

                        // ### LEAVE CATEGORY SECURE ENDPOINTS ###
                        .requestMatchers(HttpMethod.GET,"/api/leavecategory").hasAnyRole("HR", "Employee", "Admin")//find all
                        .requestMatchers(HttpMethod.GET,"/api/leavecategory/{id}").hasAnyRole("*")//μαλλον για πεταμα
                        .requestMatchers(HttpMethod.POST,"/api/leavecategory").hasAnyRole("HR")//create new leave category
                        .requestMatchers(HttpMethod.PUT,"/api/leavecategory").hasAnyRole("HR")//update leave category
                        .requestMatchers(HttpMethod.DELETE,"/api/leavecategory/{id}").hasAnyRole("HR")//delete leave category
                        // ### LEAVE CATEGORY SECURE ENDPOINTS ###

                        // ### USER SECURE ENDPOINTS ###
                        .requestMatchers(HttpMethod.GET,"/api/users/info").hasAnyRole("Employee", "HR", "Admin")
                        .requestMatchers(HttpMethod.GET,"/api/users/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/{id}").hasAnyRole("ADNIN")
                        .requestMatchers(HttpMethod.POST,"/api/users/createAccount").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/users/{id}/changeStatus").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/users/{id}/supervisorRights").hasAnyRole("ADMIN") //μαλλον για πεταμα
                        .requestMatchers(HttpMethod.PUT,"/api/users/{userId}/assignToEmployee/{employeeId}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/users/{userId}/unassign}").hasAnyRole("*")
                        // ### USER SECURE ENDPOINTS ###

                        // ### LEAVE BALANCE SECURE ENDPOINTS ###
                        .requestMatchers(HttpMethod.GET,"/api/leavebalance").hasAnyRole("*")
                        .requestMatchers(HttpMethod.GET,"/api/leavebalance/{id}").hasAnyRole("HR")
                        .requestMatchers(HttpMethod.DELETE,"/api/leavebalance/{id}").hasAnyRole("Employee","HR")
                        // ### LEAVE BALANCE SECURE ENDPOINTS ###

                        // ### EMPLOYEE SECURE ENDPOINTS ###
                        .requestMatchers(HttpMethod.GET,"/api/employees").hasAnyRole("*")
                        .requestMatchers(HttpMethod.GET,"/api/employees/{id}").hasAnyRole("*")
                        .requestMatchers(HttpMethod.POST,"/api/employees").hasAnyRole("*")
                        .requestMatchers(HttpMethod.DELETE,"/api/employees/{id}").hasAnyRole("*")
                        .requestMatchers(HttpMethod.POST,"/api/employees/leaveRequest").hasAnyRole("*")
                        .requestMatchers(HttpMethod.POST,"/api/employees/withoutAccount").hasAnyRole("*")
                        .requestMatchers(HttpMethod.GET,"/api/employees/{id}/leavebalance").hasAnyRole("*")
                        .requestMatchers(HttpMethod.POST,"/api/employees/{id}/leavebalance").hasAnyRole("*")
                        .requestMatchers(HttpMethod.PUT,"/api/employees/{id}/changeProfile").hasAnyRole("*")
                        .requestMatchers(HttpMethod.PUT,"/api/employees/{employeeId}/approve/{leaveRequestId}").hasAnyRole("*")
                        .requestMatchers(HttpMethod.PUT,"/api/employees/{employeeId}/reject/{leaveRequestId}").hasAnyRole("*")
                        .requestMatchers(HttpMethod.PUT,"/api/employees/{employeeId}/assign/{supervisorId}").hasAnyRole("*")
                        .requestMatchers(HttpMethod.PUT,"/api/employees/{employeeId}/unassigned/{supervisorId}").hasAnyRole("*")
                        .requestMatchers(HttpMethod.GET,"/api/employees/{employeeId}/leaveRequestHistory").hasAnyRole("*")
                        // ### EMPLOYEE SECURE ENDPOINTS ###


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
