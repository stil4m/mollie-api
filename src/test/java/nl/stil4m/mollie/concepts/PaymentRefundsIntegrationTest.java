package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.Client;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreateRefund;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;
import nl.stil4m.mollie.domain.Refund;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PaymentRefundsIntegrationTest {

    private Client client;
    private Payments payments;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        client = strictClientWithApiKey(VALID_API_KEY);
        payments = client.payments();
    }

    @Test
    public void testGetPaymentRefunds() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Payment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();
        PaymentRefunds refunds = client.paymentRefunds(id);

        ResponseOrError<Page<Refund>> all = refunds.all(Optional.empty(), Optional.empty());

        assertThat(all.getSuccess(), is(true));
        Page<Refund> refundPage = all.getData();
        assertThat(refundPage.getCount(), is(0));
        assertThat(refundPage.getData(), is(notNullValue()));
        assertThat(refundPage.getLinks(), is(notNullValue()));
    }

    @Test
    public void testListRefundsForExistingPayment() throws IOException, URISyntaxException, InterruptedException {
        Payment payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null)).getData();
        PaymentRefunds refunds = client.paymentRefunds(payment.getId());

        ResponseOrError<Page<Refund>> all = refunds.all(Optional.empty(), Optional.empty());

        assertThat(all.getSuccess(), is(true));
        Page<Refund> data = all.getData();
        assertThat(data.getData().size(), is(0));
    }


    @Test
    public void testCancelNonExistingRefund() throws IOException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The refund id is invalid");
        ResponseOrError<Payment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        assertThat(payment.getSuccess(), is(true));
        PaymentRefunds refunds = client.paymentRefunds(payment.getData().getId());

        ResponseOrError<Void> cancel = refunds.delete("foo_bar");

        assertThat(cancel.getSuccess(), is(false));
        assertThat(cancel.getError().get("error"), is(errorData));
    }

    @Test
    public void testGetNonExistingRefund() throws IOException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The refund id is invalid");
        ResponseOrError<Payment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        PaymentRefunds refunds = client.paymentRefunds(payment.getData().getId());

        ResponseOrError<Refund> get = refunds.get("foo_bar");

        assertThat(get.getSuccess(), is(false));
        assertThat(get.getError().get("error"), is(errorData));
    }

    @Test
    public void testCreateRefund() throws IOException, URISyntaxException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The payment is already refunded or has not been paid for yet");
        ResponseOrError<Payment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        PaymentRefunds refunds = client.paymentRefunds(payment.getData().getId());

        CreateRefund createRefund = new CreateRefund(1.00);
        ResponseOrError<Refund> create = refunds.create(createRefund);

        assertThat(create.getSuccess(), is(false));
        assertThat(create.getError().get("error"), is(errorData));
    }
}
