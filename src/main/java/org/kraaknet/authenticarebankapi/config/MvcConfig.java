package org.kraaknet.authenticarebankapi.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        addView(registry, "/home", "home");
        addView(registry, "/", "home");
        addView(registry, "/hello", "hello");
        addView(registry, "/login", "login");
    }

    private void addView(@NonNull ViewControllerRegistry registry, @NonNull String path, @NonNull String name) {
        registry.addViewController(path).setViewName(name);
    }

}