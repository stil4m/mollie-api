package nl.stil4m.mollie;

import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.IdealPaymentOptions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static java.util.Collections.EMPTY_MAP;
import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;

public class IssuesIntegrationTest {

    private Client client;


    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        client = strictClientWithApiKey(TestUtil.VALID_API_KEY);
    }

    //Issue #13
    @Test
    public void validateInvalidApiKey() throws IOException {
        CreatePayment createPayment = new CreateIdealPayment(36.0, "Test", "http://example.com", Optional.empty(), EMPTY_MAP, new IdealPaymentOptions("ideal_TESTNL99"));
        client.payments().create(createPayment);
        //Should not give a deserialization error.
    }

}
