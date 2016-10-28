package nl.stil4m.mollie.concepts;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.assertWithin;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import nl.stil4m.mollie.Client;
import nl.stil4m.mollie.ClientBuilder;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.Issuer;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.IdealPaymentOptions;

public class PaymentsIntegrationTest {
    
    private Payments payments;
    private Issuers issuers;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        Client client = new ClientBuilder().withApiKey(VALID_API_KEY).build();
        payments = client.payments();
        issuers = client.issuers();
    }
    
    @Test
    public void testCreatePayment() throws IOException, InterruptedException {
        Date beforeTest = new Date();
        
        ResponseOrError<CreatedPayment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));

        assertWithin(beforeTest, payment.getData().getCreatedDatetime(), new Date(), 5000L);
    }

    @Test
    public void testCreateIdealPayment() throws IOException, URISyntaxException, InterruptedException {
        Date beforeTest = new Date();
        ResponseOrError<Page<Issuer>> all = issuers.all(Optional.empty(), Optional.empty());
        assertThat(all.getSuccess(), is(true));
        Issuer issuer = all.getData().getData().get(0);

        ResponseOrError<CreatedPayment> payment = payments.create(new CreateIdealPayment(1.00, "Some description", "http://example.com", Optional.empty(), null, new IdealPaymentOptions(issuer.getId())));

        assertThat(payment.getSuccess(), is(true));
        assertWithin(beforeTest, payment.getData().getCreatedDatetime(), new Date(), 5000L);
    }

    @Test
    public void testGetPayment() throws IOException, InterruptedException {
        ResponseOrError<CreatedPayment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Payment> paymentStatus = payments.get(id);
        
        assertThat(paymentStatus.getData().getStatus(), is("open"));
    }

    @Test
    public void testGetPaymentWithRefunds() throws IOException, InterruptedException {
        ResponseOrError<Payment> getResponse = payments.get("tr_3AdTKpQGii");

        getResponse.get(payment -> assertThat(payment.getLinks().getRefunds().isPresent(), is(true)), errorData -> System.out.println());

    }

    @Test
    public void testGetPaymentWithEmptyId() throws IOException, InterruptedException {
        try {
            payments.get("");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Payment id may not be an empty string"));
        }
    }

    @Test
    public void testGetPaymentWithNullId() throws IOException, InterruptedException {
        try {
            payments.get(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Payment id may not be null"));
        }
    }
}
