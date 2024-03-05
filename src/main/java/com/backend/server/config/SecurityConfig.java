// package com.backend.server.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.backend.server.security.JwtAuthenticationFilter;
// import com.backend.server.security.Jwtentrypoint;


// @Configuration
// public class SecurityConfig {


//     @Autowired
//     private Jwtentrypoint point;
//     @Autowired
//     private JwtAuthenticationFilter filter;

//      @SuppressWarnings("deprecation")
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         return http
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeRequests(authorizeRequests ->
//                         authorizeRequests
//                                 .requestMatchers("/auth/login").permitAll()
//                                 .anyRequest().authenticated()
//                 )
//                 .exceptionHandling(exceptionHandling ->
//                         exceptionHandling
//                                 .authenticationEntryPoint(point)
//                 )
//                 .sessionManagement(sessionManagement ->
//                         sessionManagement
//                                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 )
//                 .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                 .build();
//     }


// }
