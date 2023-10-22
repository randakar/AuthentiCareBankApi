package org.kraaknet.authenticarebankapi.annotations;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "Floris", roles = {"USER, ADMIN"})
public @interface WithMockAdminUser {
}
