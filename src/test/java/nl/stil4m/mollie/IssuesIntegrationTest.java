package nl.stil4m.mollie;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.IdealPaymentOptions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static java.util.Collections.EMPTY_MAP;
import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;

public class IssuesIntegrationTest {

    private Client client;


    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        ObjectMapper mapper = new ObjectMapper();
        client = new ClientBuilder()
                .withApiKey(TestUtil.VALID_API_KEY)
                .withMapper(mapper).build();
    }

    //Issue #13
    @Test
    public void validateInvalidApiKey() throws IOException {
        CreatePayment createPayment = new CreateIdealPayment(36.0, "Test", "http://example.com", Optional.empty(), EMPTY_MAP, new IdealPaymentOptions("ideal_TESTNL99"));
        client.payments().create(createPayment);
        //Should not give a deserialization error.
    }

}
