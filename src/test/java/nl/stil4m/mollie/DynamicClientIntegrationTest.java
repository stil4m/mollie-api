package nl.stil4m.mollie;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DynamicClientIntegrationTest {

    private DynamicClient client;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        ObjectMapper mapper = new ObjectMapper();
        client = new DynamicClientBuilder()
                .withMapper(mapper).build();
    }

    @Test
    public void validateInvalidApiKey() throws IOException {
        assertThat(client.status("invalid").checkApiKey().getValid(), is(false));
    }

    @Test
    public void validateValidApiKey() throws IOException {
        assertThat(client.status(VALID_API_KEY).checkApiKey().getValid(), is(true));
    }
}
