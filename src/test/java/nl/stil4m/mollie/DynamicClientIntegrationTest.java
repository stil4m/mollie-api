package nl.stil4m.mollie;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.assertWithin;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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

    @Test
    public void testCreatePayment() throws IOException {
        Date beforeTest = new Date();
        Map<String, Object> meta = new HashMap<>();
        meta.put("foo", "bar");

        ResponseOrError<CreatedPayment> payment = client.payments(VALID_API_KEY).create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), meta));

        CreatedPayment createdPayment = payment.getData();
        assertWithin(beforeTest, createdPayment.getCreatedDatetime(), new Date(), 5000L);

        assertThat(createdPayment.getMethod(), is(nullValue()));
        assertThat(createdPayment.getAmount(), is(1.00));
        assertThat(createdPayment.getDescription(), is("Some description"));
        assertThat(createdPayment.getId(), is(notNullValue()));
        assertThat(createdPayment.getDetails(), is(nullValue()));
        assertThat(createdPayment.getLinks(), is(notNullValue()));
        assertThat(createdPayment.getLinks().getPaymentUrl().matches("https://www.mollie.com/payscreen/select-method/[A-Za-z0-9]+"), is(true));
        assertThat(createdPayment.getLinks().getRedirectUrl(), is("http://example.com"));
        assertThat(createdPayment.getLinks().getWebhookUrl(), is("https://stil4m.github.io"));
        assertThat(createdPayment.getMode(), is("test"));
        assertThat(createdPayment.getStatus(), is("open"));
        assertThat(createdPayment.getMetadata(), is(meta));
    }

    @Test
    public void testCreatePaymentWithMethod() throws IOException {
        Date beforeTest = new Date();
        Map<String, Object> meta = new HashMap<>();
        meta.put("foo", "bar");

        ResponseOrError<CreatedPayment> payment = client.payments(VALID_API_KEY).create(new CreatePayment(Optional.of("ideal"), 1.00, "Some description", "http://example.com", Optional.empty(), meta));

        CreatedPayment createdPayment = payment.getData();
        assertWithin(beforeTest, createdPayment.getCreatedDatetime(), new Date(), 5000L);

        assertThat(createdPayment.getMethod(), is("ideal"));
        assertThat(createdPayment.getAmount(), is(1.00));
        assertThat(createdPayment.getDescription(), is("Some description"));
        assertThat(createdPayment.getId(), is(notNullValue()));
        assertThat(createdPayment.getDetails(), is(nullValue()));
        assertThat(createdPayment.getLinks(), is(notNullValue()));
        assertThat(createdPayment.getLinks().getPaymentUrl().matches("https://www.mollie.com/paymentscreen/ideal/select-issuer/[A-Za-z0-9]+"), is(true));
        assertThat(createdPayment.getLinks().getRedirectUrl(), is("http://example.com"));
        assertThat(createdPayment.getLinks().getWebhookUrl(), is(is("https://stil4m.github.io")));
        assertThat(createdPayment.getMode(), is("test"));
        assertThat(createdPayment.getStatus(), is("open"));
        assertThat(createdPayment.getMetadata(), is(meta));
    }


    @Test
    public void testCreateAndGetPayment() throws IOException {
        Map<String, Object> meta = new HashMap<>();
        meta.put("foo", "bar");

        ResponseOrError<CreatedPayment> createResponse = client.payments(VALID_API_KEY).create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), meta));
        ResponseOrError<Payment> paymentResponse = client.payments(VALID_API_KEY).get(createResponse.getData().getId());
        Payment payment = paymentResponse.getData();

        assertThat(payment.getMethod(), is(nullValue()));
        assertThat(payment.getAmount(), is(1.00));
        assertThat(payment.getDescription(), is("Some description"));
        assertThat(payment.getId(), is(notNullValue()));
        assertThat(payment.getDetails(), is(nullValue()));
        assertThat(payment.getLinks(), is(notNullValue()));
        assertThat(payment.getLinks().getPaymentUrl().matches("https://www.mollie.com/payscreen/select-method/[A-Za-z0-9]+"), is(true));
        assertThat(payment.getLinks().getRedirectUrl(), is("http://example.com"));
        assertThat(payment.getLinks().getWebhookUrl(), is(is("https://stil4m.github.io")));
        assertThat(payment.getMode(), is("test"));
        assertThat(payment.getStatus(), is("open"));
        assertThat(payment.getMetadata(), is(meta));

    }

    @Test
    public void testCreateAndGetCreditCardPayment() throws IOException {
        Map<String, Object> meta = new HashMap<>();
        meta.put("foo", "bar");

        ResponseOrError<CreatedPayment> createResponse = client.payments(VALID_API_KEY).create(new CreatePayment(Optional.of("creditcard"), 2.00, "Some credit card description", "http://example.com", Optional.empty(), meta));
        ResponseOrError<Payment> paymentResponse = client.payments(VALID_API_KEY).get(createResponse.getData().getId());
        Payment payment = paymentResponse.getData();

        assertThat(payment.getMethod(), is("creditcard"));
        assertThat(payment.getAmount(), is(2.00));
        assertThat(payment.getDescription(), is("Some credit card description"));
        assertThat(payment.getId(), is(notNullValue()));
        assertThat(payment.getDetails(), is(notNullValue())); // feeRegion=other
        assertThat(payment.getLinks(), is(notNullValue()));
        assertThat(payment.getLinks().getPaymentUrl().matches("https://www.mollie.com/paymentscreen/creditcard/testmode/[A-Za-z0-9]+"), is(true));
        assertThat(payment.getLinks().getRedirectUrl(), is("http://example.com"));
        assertThat(payment.getLinks().getWebhookUrl(), is(is("https://stil4m.github.io")));
        assertThat(payment.getMode(), is("test"));
        assertThat(payment.getStatus(), is("open"));
        assertThat(payment.getMetadata(), is(meta));

    }

    @Test
    public void testGetPayment() throws IOException {
        ResponseOrError<CreatedPayment> payment = client.payments(VALID_API_KEY).create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Payment> paymentStatus = client.payments(VALID_API_KEY).get(id);
        assertThat(paymentStatus.getData().getStatus(), is("open"));
    }

    @Test
    public void testGetPayments() throws IOException, URISyntaxException {
        ResponseOrError<Page<Payment>> result = client.payments(VALID_API_KEY).list(Optional.empty(), Optional.empty());
        assertThat(result.getSuccess(), is(true));
        Page<Payment> page = result.getData();

        assertThat(page.getTotalCount(), is(notNullValue()));
        assertThat(page.getData().size(), is(Math.min(10, page.getTotalCount())));
        assertThat(page.getOffset(), is(0));
        assertThat(page.getCount(), is(10));

        assertThat(page.getLinks().getPrevious().isPresent(), is(false));
        assertThat(page.getLinks().getNext().isPresent(), is(true));
        assertThat(page.getLinks().getLast().isPresent(), is(true));
        assertThat(page.getLinks().getFirst().isPresent(), is(true));
    }

    @Test
    public void testGetPaymentsWithOffsetAndCount() throws IOException, URISyntaxException {
        ResponseOrError<Page<Payment>> result = client.payments(VALID_API_KEY).list(Optional.of(20), Optional.of(40));
        assertThat(result.getSuccess(), is(true));
        Page<Payment> page = result.getData();

        assertThat(page.getTotalCount(), is(notNullValue()));
        assertThat(page.getData().size(), is(Math.min(20, page.getTotalCount())));
        assertThat(page.getOffset(), is(40));
        assertThat(page.getCount(), is(20));

        assertThat(page.getLinks().getPrevious().isPresent(), is(true));
        assertThat(page.getLinks().getNext().isPresent(), is(true));
        assertThat(page.getLinks().getLast().isPresent(), is(true));
        assertThat(page.getLinks().getFirst().isPresent(), is(true));
    }

    @Test
    public void testNextPayments() throws IOException, URISyntaxException {
        ResponseOrError<Page<Payment>> result = client.payments(VALID_API_KEY).list(Optional.empty(), Optional.empty());
        assertThat(result.getSuccess(), is(true));
        Page<Payment> page = result.getData();

        Page<Payment> nextPage = client.payments(VALID_API_KEY).next(page).getData();

        assertThat(nextPage.getOffset(), is(10));
        assertThat(nextPage.getCount(), is(10));
    }

    @Test
    public void testPreviousPayments() throws IOException, URISyntaxException {
        ResponseOrError<Page<Payment>> result = client.payments(VALID_API_KEY).list(Optional.of(20), Optional.of(40));
        assertThat(result.getSuccess(), is(true));
        Page<Payment> page = result.getData();

        Page<Payment> previousPage = client.payments(VALID_API_KEY).previous(page).getData();

        assertThat(previousPage.getOffset(), is(20));
        assertThat(previousPage.getCount(), is(20));
    }
}
