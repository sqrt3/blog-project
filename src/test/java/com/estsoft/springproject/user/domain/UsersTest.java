package com.estsoft.springproject.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class UsersTest {

  @Test
  void testCreateUserWithEmailAndPassword() {
    String email = "test@example.com";
    String password = "password123";

    Users user = new Users(email, password);

    assertNotNull(user);
    assertEquals(email, user.getEmail());
    assertEquals(password, user.getPassword());
    assertEquals(email, user.getUsername());
    assertTrue(user.isAccountNonExpired());
    assertTrue(user.isAccountNonLocked());
    assertTrue(user.isCredentialsNonExpired());
    assertTrue(user.isEnabled());

    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
    assertEquals(2, authorities.size());
    assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
  }

  @Test
  void testGetUsernameWorks() {
    String email = "test@example.com";
    String password = "password123";

    Users user = new Users(email, password);

    assertEquals(email, user.getUsername());
  }


}
