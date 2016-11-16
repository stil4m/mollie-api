package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.Client;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Refund;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RefundsIntegrationTest {

    private Refunds refunds;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        Client client = strictClientWithApiKey(VALID_API_KEY);
        refunds = client.refunds();
    }

    @Test
    public void testGetRefunds() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Page<Refund>> all = refunds.all(Optional.empty(), Optional.empty());

        assertThat(all.getSuccess(), is(true));
        Page<Refund> refundPage = all.getData();
        assertThat(refundPage.getCount(), is(0));
        assertThat(refundPage.getData(), is(notNullValue()));
        assertThat(refundPage.getLinks(), is(notNullValue()));
    }
}
