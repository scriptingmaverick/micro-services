package com.hotel.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
class UserControllerTest {
  @Autowired
  private RestTestClient client;

  @Autowired
  private UserRepo repo;

  @Test
  void shouldAddUser() {
    repo.deleteAll();
    User testUser = new User("jane", "doe");
    SignUpRecord responseBody = client.post().uri("/api/users/register").body(testUser).header("content-type", "application/json").exchange().expectStatus().isOk().expectBody(SignUpRecord.class).returnResult().getResponseBody();

    assert (responseBody.message().equals(testUser.getUsername() + " is created successfully"));
  }
}