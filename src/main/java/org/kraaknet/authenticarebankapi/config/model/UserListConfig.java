package org.kraaknet.authenticarebankapi.config.model;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@ConfigurationProperties(prefix = "userconfiguration")
public record UserListConfig(
        @NonNull List<UserConfig> users) {

    public List<UserDetails> getUsersAsUserDetails() {
        return users.stream()
                .map(userConfig -> User.withUsername(userConfig.name())
                        .password("{bcrypt}" + userConfig.encodedPassword()) // Let's not store plain text passwords ..
                        .roles(userConfig.roles().toArray(String[]::new))
                        .build())
                .toList();
    }
}
