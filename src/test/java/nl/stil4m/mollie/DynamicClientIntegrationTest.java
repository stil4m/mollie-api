package nl.stil4m.mollie;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictDynamicClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DynamicClientIntegrationTest {

    private DynamicClient client;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        client = strictDynamicClientWithApiKey();
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
