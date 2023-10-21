package org.kraaknet.authenticarebankapi.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "userconfiguration")
public record UserListConfig(
        @NonNull List<UserConfig> users) {
}
