package cm.ex.bug.configuration;

import cm.ex.bug.security.filter.AuthenticationFilter;
import cm.ex.bug.security.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private AuthorizationFilter authorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/signIn").permitAll()
                        .requestMatchers("/signUp").permitAll()
                        .requestMatchers("/signUpImage").permitAll()
                        .requestMatchers("image/{imageId}").permitAll()

                        .requestMatchers("/list-user").permitAll()
                        .requestMatchers("/get-user/{userId}").permitAll()

                        .anyRequest().authenticated()
                )
                .build();
    }
}
