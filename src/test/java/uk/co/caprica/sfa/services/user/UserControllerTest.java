package uk.co.caprica.sfa.services.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.caprica.sfa.BaseSpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

// FIXME maybe create a "test" user

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends BaseSpringBootTest {

    @Test
    public void unauthenticatedRequestReturnsUnauthorized() {
        assertThat(restTemplate.getForEntity(serviceUrl("/api/me"), User.class).getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void authenticatedRequestReturnsUser() {
        String token = login("mark", "toomanysecrets");

        ResponseEntity<User> response = restTemplate.exchange(serviceUrl("/api/me"), HttpMethod.GET, new HttpEntity<>(authorizationHeaders(token)), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.hasBody()).isTrue();
        User user = response.getBody();
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("mark");
    }
}
