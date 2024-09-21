package com.iba.springbootdeveloper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
public class RoleTest {

    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    public void testUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                System.out.println("Authorities: " + userDetails.getAuthorities());
                assertThat(userDetails.getAuthorities().toString()).contains("ROLE_ADMIN");
            } else {
                System.out.println("Principal is not an instance of UserDetails.");
            }
        } else {
            System.out.println("Authentication is null.");
        }
    }
}
