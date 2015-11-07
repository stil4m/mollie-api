package nl.stil4m.mollie;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.PaymentStatus;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DynamicClientIntegrationTest {

    private DynamicClient client;

    private String VALID_API_KEY = "test_nVK7W2WFmZXUNWcntBtCwvgCAgZ3c5";

    @Before
    public void before() {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        client = new DynamicClientBuilder()
                .withMapper(mapper).build();
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
        Date beforeTest = new Date();
        ResponseOrError<CreatedPayment> payment = client.createPayment(VALID_API_KEY, new CreatePayment(null, 1.00, "Some description", "http://example.com", null));

        assertWithin(beforeTest, payment.getData().getCreatedDatetime(), new Date());
    }

    public static void assertWithin(Date before, Date target, Date after) {
        long beforeTime = before.getTime() - (before.getTime() % 1000) - 1000; //Subtract another 1000 just to be safe
        long afterTime = after.getTime() - (after.getTime() % 1000) + 1000;
        assertThat(beforeTime <= target.getTime(), is(true));
        assertThat(target.getTime() <= afterTime, is(true));
    }
    @Test
    public void testGetPayment() throws IOException {
        ResponseOrError<CreatedPayment> payment = client.createPayment(VALID_API_KEY, new CreatePayment(null, 1.00, "Some description", "http://example.com", null));
        String id = payment.getData().getId();

        ResponseOrError<PaymentStatus> paymentStatus = client.getPaymentStatus(VALID_API_KEY, id);
        assertThat(paymentStatus.getData().getStatus(), is("open"));
    }
}