package nl.stil4m.mollie;

import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.PaymentStatus;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

public class ClientIntegrationTest {

    private Client client;

    private String VALID_API_KEY = "test_nVK7W2WFmZXUNWcntBtCwvgCAgZ3c5";

    @Before
    public void before() {
        client = new ClientBuilder().withApiKey(VALID_API_KEY).build();
    }

    @Test
    public void testCreatePayment() throws IOException {
        Date beforeTest = new Date();
        ResponseOrError<CreatedPayment> payment = client.createPayment(new CreatePayment(null, 1.00, "Some description", "http://example.com", null));

        DynamicClientIntegrationTest.assertWithin(beforeTest, payment.getData().getCreatedDatetime(), new Date());
    }

    @Test
    public void testGetPayment() throws IOException {
        ResponseOrError<CreatedPayment> payment = client.createPayment(new CreatePayment(null, 1.00, "Some description", "http://example.com", null));
        String id = payment.getData().getId();

        ResponseOrError<PaymentStatus> paymentStatus = client.getPaymentStatus(VALID_API_KEY, id);
        assertThat(paymentStatus.getData().getStatus(), is("open"));
    }


    @Test
    public void testGetPaymentWithEmptyId() throws IOException {
        try {
            client.getPaymentStatus(VALID_API_KEY, "");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Payment id may not be an empty string"));
        }
    }

    @Test
    public void testGetPaymentWithNullId() throws IOException {
        try {
            client.getPaymentStatus(VALID_API_KEY, null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Payment id may not be null"));
        }
    }
}