package nl.stil4m.mollie.concepts;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import nl.stil4m.mollie.Client;
import nl.stil4m.mollie.ClientBuilder;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Refund;

public class RefundsIntegrationTest {

    private Refunds refunds;
    private Payments payments;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        Client client = new ClientBuilder().withApiKey(VALID_API_KEY).build();
        refunds = client.refunds();
        payments = client.payments();
    }

    @Test
    public void testGetRefunds() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<CreatedPayment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Page<Refund>> all = refunds.all(id, Optional.empty(), Optional.empty());
        
        assertThat(all.getSuccess(), is(true));
        Page<Refund> refundPage = all.getData();
        assertThat(refundPage.getCount(), is(0));
        assertThat(refundPage.getData(), is(notNullValue()));
        assertThat(refundPage.getLinks(), is(notNullValue()));
    }

    @Test
    public void testListRefundsForExistingPayment() throws IOException, URISyntaxException, InterruptedException {
        CreatedPayment createdPayment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null)).getData();
        
        ResponseOrError<Page<Refund>> all = refunds.all(createdPayment.getId(), Optional.empty(), Optional.empty());

        assertThat(all.getSuccess(), is(true));
        Page<Refund> data = all.getData();
        assertThat(data.getData().size(), is(0));
    }


    @Test
    public void testCancelNonExistingRefund() throws IOException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The refund id is invalid");
        ResponseOrError<CreatedPayment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        assertThat(payment.getSuccess(), is(true));
        String id = payment.getData().getId();

        ResponseOrError<Void> cancel = refunds.cancel(id, "foo_bar");
        
        assertThat(cancel.getSuccess(), is(false));
        assertThat(cancel.getError().get("error"), is(errorData));
    }

    @Test
    public void testGetNonExistingRefund() throws IOException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The refund id is invalid");
        ResponseOrError<CreatedPayment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Refund> get = refunds.get(id, "foo_bar");
        
        assertThat(get.getSuccess(), is(false));
        assertThat(get.getError().get("error"), is(errorData));
    }

    @Test
    public void testCreateRefund() throws IOException, URISyntaxException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The payment is already refunded or has not been paid for yet");
        ResponseOrError<CreatedPayment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Refund> create = refunds.create(id, Optional.of(1.00));
        
        assertThat(create.getSuccess(), is(false));
        assertThat(create.getError().get("error"), is(errorData));
    }
}
