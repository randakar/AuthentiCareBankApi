package org.kraaknet.authenticarebankapi.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String NOT_A_PASSWORD =
            "wow_the_secret_password_sentence_is_very_long_indeed_but_of_course_its_compromised_it_should_not_be_stored_here";

    @Bean
    public UserDetailsService userDetailsService(@NonNull PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("Floris")
                // Needless to say I would not do this in a real application
                // But for now it will do, as real Oauth2 requires me to store a real secret in this repository.
                .password(encoder.encode(NOT_A_PASSWORD))
                .roles("ADMIN", "USER")
                .build();

        Stream<UserDetails> customers = Stream.of("AlexanderKremer", "JustinLagas", "Ramyaa", "Riekele")
                .map(name -> createUserWithReversePassword(encoder, name));

        List<UserDetails> users = concat(Stream.of(admin), customers).toList();

        return new InMemoryUserDetailsManager(users);
    }

    private static UserDetails createUserWithReversePassword(@NonNull PasswordEncoder encoder, @NonNull String name) {
        return User.withUsername(name)
                // Nor should a real application commit the sin of, this, worse-than-ROT13 'security' encoding.
                // But for now it will do, as real Oauth2 requires me to store a real secret in this repository.
                .password(encoder.encode(StringUtils.reverse(name)))
                .roles("USER")
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }


    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}