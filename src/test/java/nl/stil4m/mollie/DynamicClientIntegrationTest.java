package nl.stil4m.mollie;

import nl.stil4m.mollie.domain.CreatePayment;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DynamicClientIntegrationTest {

    private DynamicClient client;

    private String VALID_API_KEY = "test_nVK7W2WFmZXUNWcntBtCwvgCAgZ3c5";

    @Before
    public void before() {
        client = new DynamicClientBuilder().build();
    }

    @Test
    public void validateInvalidApiKey() throws IOException {
        assertThat(client.checkApiKey("invalid").getValid(), is(false));
    }

    @Test
    public void validateValidApiKey() throws IOException {
        assertThat(client.checkApiKey(VALID_API_KEY).getValid(), is(true));
    }

    @Test
    public void testCreatePayment() throws IOException {
        client.createPayment(VALID_API_KEY, new CreatePayment());
    }
}