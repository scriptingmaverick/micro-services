package com.hotel.user;

import com.hotel.user.record.SignInRecord;
import com.hotel.user.record.SignUpRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureRestTestClient
class UserControllerTest {
  User testUser = new User("jane", "doe");
  @Autowired
  private RestTestClient client;
  @Autowired
  private UserRepo repo;

  @Test
  void shouldAddUser() {
    repo.deleteAll();
    SignUpRecord responseBody = client.post().uri("/api/users/register").body(testUser).header("content-type", "application/json").exchange().expectStatus().isOk().expectBody(SignUpRecord.class).returnResult().getResponseBody();

    assert (responseBody.message().equals(testUser.getUsername() + " is created successfully"));
  }

  @Test
  void shouldNotAddUser() {
    repo.deleteAll();
    SignUpRecord responseBody = client.post().uri("/api/users/register").body(testUser).header("content-type", "application/json").exchange().expectStatus().isOk().expectBody(SignUpRecord.class).returnResult().getResponseBody();

    assert (responseBody.message().equals(testUser.getUsername() + " is created successfully"));
    SignUpRecord responseBodyOf2ndRegister = client.post().uri("/api/users/register").body(testUser).header("content-type", "application/json").exchange().expectStatus().is4xxClientError().expectBody(SignUpRecord.class).returnResult().getResponseBody();

    assert (responseBodyOf2ndRegister.message().equals("User already exists"));
  }

  @Test
  void shouldLogin() {
    repo.deleteAll();
    SignUpRecord responseBody = client.post().uri("/api/users/register").body(testUser).header("content-type", "application/json").exchange().expectStatus().isOk().expectBody(SignUpRecord.class).returnResult().getResponseBody();

    assert (responseBody.message().equals(testUser.getUsername() + " is created successfully"));

    SignInRecord responseBodyOfLogin = client.post().uri("/api/users/login").body(testUser).header("content-type", "application/json").exchange().expectStatus().isOk().expectBody(SignInRecord.class).returnResult().getResponseBody();

    assert (responseBodyOfLogin.message().equals(testUser.getUsername() + " login successful"));
  }

  @Test
  void shouldLoginFailWhenNotFound() {
    repo.deleteAll();

    SignInRecord responseBodyOfLogin =
            client.post()
                    .uri("/api/users/login")
                    .body(testUser)
                    .header("content-type", "application/json")
                    .exchange()
                    .expectStatus()
                    .is4xxClientError()
                    .expectBody(SignInRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            "User not found",
            responseBodyOfLogin.message()
    );
  }

  @Test
  void shouldLoginFailWhenInvalidCredentials() {
    repo.deleteAll();
    SignUpRecord responseBody = client.post().uri("/api/users/register").body(testUser).header("content-type", "application/json").exchange().expectStatus().isOk().expectBody(SignUpRecord.class).returnResult().getResponseBody();

    assert (responseBody.message().equals(testUser.getUsername() + " is created successfully"));

    SignInRecord responseBodyOfLogin =
            client.post()
                    .uri("/api/users/login")
                    .body(new User("jane", "dae"))
                    .header("content-type", "application/json")
                    .exchange()
                    .expectStatus()
                    .is4xxClientError()
                    .expectBody(SignInRecord.class)
                    .returnResult()
                    .getResponseBody();

    assert (responseBodyOfLogin.message().equals("Invalid credentials"));
  }
}