package uk.co.caprica.sfa.services.security;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import uk.co.caprica.sfa.BaseSpringBootTest;
import uk.co.caprica.sfa.security.SecurityConfig;
import uk.co.caprica.sfa.services.user.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the configuration described by {@link SecurityConfig}.
 * <p>
 * In particular, tests to check the proper routing of requests to either the web-services API, or to the single page
 * application for client-side routing (e.g. deep links).
 */
public class SecurityConfigTest extends BaseSpringBootTest {

    @Test
    public void rootRequestForwardsToSinglePageApplication() {
        ResponseEntity<String> response =  restTemplate.getForEntity(serviceUrl(""), String.class);
        assertThatResponseForwardsToSinglePageApplication(response);
    }

    @Test
    public void indexHtmlRequestForwardsToSinglePageApplication() {
        ResponseEntity<String> response =  restTemplate.getForEntity(serviceUrl("/index.html"), String.class);
        assertThatResponseForwardsToSinglePageApplication(response);
    }

    @Test
    public void topLevelRequestPathForwardsToSinglePageApplication() {
        ResponseEntity<String> response =  restTemplate.getForEntity(serviceUrl("/home"), String.class);
        assertThatResponseForwardsToSinglePageApplication(response);
    }

    @Test
    public void multiLevelRequestPathForwardsToSinglePageApplication() {
        ResponseEntity<String> response =  restTemplate.getForEntity(serviceUrl("/a/b/c/d/e"), String.class);
        assertThatResponseForwardsToSinglePageApplication(response);
    }

    /**
     * This test is for a controller that has its own mapping is routed to correctly, i.e. it has a request mapping
     * that is not under the "/api/" path.
     */
    @Test
    public void nonApiServiceRequestNotForwardedToSinglePageApplication() {
        assertThat(restTemplate.getForObject(serviceUrl("health-check"), String.class)).startsWith("OK: ");
    }

    @Test
    public void apiServiceRequestNotForwardedToSinglePageApplication() {
        assertThat(restTemplate.getForEntity(serviceUrl("/api/me"), User.class).getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void staticResourceRequestReturnedDirectly() {
        ResponseEntity<String> response =  restTemplate.getForEntity(serviceUrl("/manifest.json"), String.class);
        assertThatOKResponseContains(response, "\"name\": \"Some Fine Application\",");
        assertThatResponseContentTypeIs(response, MediaType.APPLICATION_JSON);
    }

    @Test
    public void imageRequestReturnedDirectly() {
        ResponseEntity<byte[]> response =  restTemplate.getForEntity(serviceUrl("/favicon.png"), byte[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThatResponseContentTypeIs(response, MediaType.IMAGE_PNG);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSizeGreaterThan(0);
    }

    private void assertThatResponseForwardsToSinglePageApplication(ResponseEntity<String> response) {
        assertThatOKResponseContains(response, "<meta name=\"description\" content=\"Some Fine Application\"/>");
        assertThatResponseContentTypeIs(response, MediaType.TEXT_HTML);
    }

    private void assertThatOKResponseContains(ResponseEntity<String> response, String expected) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains(expected);
    }

    private void assertThatResponseContentTypeIs(ResponseEntity<?> response, MediaType expected) {
        HttpHeaders headers = response.getHeaders();
        assertThat(headers).isNotNull();
        assertThat(headers.getContentType()).isEqualTo(expected);
    }
}
