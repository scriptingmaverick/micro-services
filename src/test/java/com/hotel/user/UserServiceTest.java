package com.hotel.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  String username = "khasim";
  @Mock
  private UserRepo repo;
  @InjectMocks
  private UserService service;

  @Test
  void shouldLoadUserByUsername() {
    User user = new User(username, "pass");

    when(repo.findByUsername(username)).thenReturn(user);

    User result = service.loadByUsername(username);

    assertNotNull(result);
    assertEquals(username, result.getUsername());

    verify(repo).findByUsername(username);
  }

  @Test
  void shouldRegisterUser() {
    User user = new User(username, "pass");

    when(repo.save(user)).thenReturn(user);

    SignUpRecord result = service.register(user);

    assertEquals(
            "khasim is created successfully",
            result.message()
    );

    verify(repo).save(user);
  }

  @Test
  void shouldReturnTrueWhenUserPresent() {
    User user = new User(username, "p");

    when(repo.findByUsername(username)).thenReturn(user);

    Boolean result = service.isUserPresent(user);

    assertTrue(result);

    verify(repo).findByUsername(username);
  }

  @Test
  void shouldReturnFalseWhenUserNotPresent() {
    User user = new User(username, "pass");

    when(repo.findByUsername(username)).thenReturn(null);

    Boolean result = service.isUserPresent(user);

    assertFalse(result);

    verify(repo).findByUsername(username);
  }
}