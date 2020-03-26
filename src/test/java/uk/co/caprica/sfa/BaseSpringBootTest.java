package uk.co.caprica.sfa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.caprica.sfa.security.UsernamePasswordCredentials;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract public class BaseSpringBootTest {

    private static final String AUTHENTICATION_PATH = "/login";

    @LocalServerPort
    private int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected final String serviceUrl(String path) {
        return String.format("http://localhost:%d/%s", port, path);
    }

    protected final String login(String username, String password) {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        ResponseEntity<String> response = restTemplate.postForEntity(serviceUrl(AUTHENTICATION_PATH), credentials, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<String> authorizationHeaders = response.getHeaders().get(HttpHeaders.AUTHORIZATION);
        assertThat(authorizationHeaders).isNotNull();
        assertThat(authorizationHeaders.size()).isEqualTo(1);
        String token = response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        assertThat(token).isNotNull();
        return token;
    }

    protected final HttpHeaders authorizationHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return headers;
    }
}
