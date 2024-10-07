package com.flightDB.DBApp.config;

import com.flightDB.DBApp.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                // Шляхи доступні всім
                                "/api/auth/**",
                                "/api/flight/search",
                                "/api/flight/create",
                                "/api/image/create/**",
                                "/api/flight/all",
                                "/api/v1/new/reservation",
                                "/api/wallet/user/{userId}",
                                "/api/v1/reservation/user/{userId}",
                                "/api/wallet/create/{id}",
                                "/api/user/updateUsername/{id}",
                                "/api/user/updatePassword/{id}",
                                "/api/wallet/user/addMoney/{userId}",
                                "/api/v1/routes",
                                "/api/v1/passengers",
                                "/api/v1/routes/new/routes",
                                "/api/test/all"
                        ).permitAll()

                        .requestMatchers(
                                // Шляхи доступні для користувачів з роллю USER
                                "/api/user/delete",
                                "/api/user/updateUsername",
                                "/api/user/updatePassword",
                                "/api/user/getByID/**"
                        ).hasAnyAuthority("ROLE_USER")

                        .requestMatchers(
                                // Шляхи доступні для користувачів з ролями USER і ADMIN
                                "/api/user"
                        ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .requestMatchers(
                                // Адміністраторські шляхи
                                "/api/flight/**",
                                "/api/v1/new/routes",
                                "/api/v1/update/routes/{id}",
                                "/api/v1/delete/routes/{id}",
                                "/api/v1/update/flight/{id}",
                                "/api/v1/delete/flight/{id}",
                                "/api/v1/new/passengers",
                                "/api/v1/update/passengers/{id}",
                                "/api/v1/delete/passengers/{id}",
                                "/api/test/admin"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                // Спільні шляхи для ADMIN та USER
                                "/api/test/user",
                                "/api/v1/routes/**",
                                "/api/v1/update/reservation/{id}",
                                "/api/v1/delete/reservation/{id}",
                                "/api/v1/reservation/**",
                                "/api/v1/passengers/**"
                        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/api/v1/passengers/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}