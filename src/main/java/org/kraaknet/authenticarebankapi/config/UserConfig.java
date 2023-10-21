package org.kraaknet.authenticarebankapi.config;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;

public record UserConfig(
        @NonNull String name,
        @NonNull String encodedPassword,
        @NonNull Set<String> roles
        ) {
}
