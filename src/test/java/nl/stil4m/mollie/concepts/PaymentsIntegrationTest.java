package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.Client;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.Issuer;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.IdealPaymentOptions;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.assertWithin;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.fail;

public class PaymentsIntegrationTest {

    private Payments payments;
    private Issuers issuers;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        Client client = strictClientWithApiKey(VALID_API_KEY);
        payments = client.payments();
        issuers = client.issuers();
    }

    @Test
    public void testCreateIdealPayment() throws IOException, URISyntaxException, InterruptedException {
        Date beforeTest = new Date();
        ResponseOrError<Page<Issuer>> all = issuers.all(Optional.empty(), Optional.empty());
        assertThat(all.getSuccess(), is(true));
        Issuer issuer = all.getData().getData().get(0);

        ResponseOrError<Payment> payment = payments.create(new CreateIdealPayment(1.00, "Some description", "http://example.com", Optional.empty(), null, new IdealPaymentOptions(issuer.getId())));

        assertThat(payment.getSuccess(), is(true));
        assertWithin(beforeTest, payment.getData().getCreatedDatetime(), new Date(), 5000L);
    }

    @Test
    public void testGetPayment() throws IOException, InterruptedException {
        ResponseOrError<Payment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Payment> paymentStatus = payments.get(id);

        assertThat(paymentStatus.getData().getStatus(), is("open"));
    }

    @Test
    public void testGetPaymentWithRefunds() throws IOException, InterruptedException {
        ResponseOrError<Payment> getResponse = payments.get("tr_3AdTKpQGii");

        getResponse.get(payment -> assertThat(payment.getLinks().getRefunds().isPresent(), is(true)), errorData -> {
        });

    }

    @Test
    public void testGetPaymentWithEmptyId() throws IOException, InterruptedException {
        try {
            payments.get("");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("URL cannot contain null or blank elements"));
        }
    }

    @Test
    public void testGetPaymentWithNullId() throws IOException, InterruptedException {
        try {
            payments.get(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("URL cannot contain null or blank elements"));
        }
    }


    @Test
    public void testCreatePayment() throws IOException {
        Date beforeTest = new Date();
        Map<String, Object> meta = new HashMap<>();
        meta.put("foo", "bar");

        ResponseOrError<Payment> payment = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.of("https://stil4m.github.io"), meta));

        Payment createdPayment = payment.getData();
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

        ResponseOrError<Payment> payment = payments.create(new CreatePayment(Optional.of("ideal"), 1.00, "Some description", "http://example.com", Optional.of("https://stil4m.github.io"), meta));

        Payment createdPayment = payment.getData();
        assertWithin(beforeTest, createdPayment.getCreatedDatetime(), new Date(), 5000L);

        assertThat(createdPayment.getMethod(), is("ideal"));
        assertThat(createdPayment.getAmount(), is(1.00));
        assertThat(createdPayment.getDescription(), is("Some description"));
        assertThat(createdPayment.getId(), is(notNullValue()));
        assertThat(createdPayment.getDetails(), is(nullValue()));
        assertThat(createdPayment.getLinks(), is(notNullValue()));
        assertThat(createdPayment.getLinks().getPaymentUrl(),startsWith("https://www.mollie.com/paymentscreen/issuer/select/ideal/"));
        assertThat(createdPayment.getLinks().getRedirectUrl(), is("http://example.com"));
        assertThat(createdPayment.getLinks().getWebhookUrl(), is("https://stil4m.github.io"));
        assertThat(createdPayment.getMode(), is("test"));
        assertThat(createdPayment.getStatus(), is("open"));
        assertThat(createdPayment.getMetadata(), is(meta));
    }


    @Test
    public void testCreateAndGetPayment() throws IOException {
        Map<String, Object> meta = new HashMap<>();
        meta.put("foo", "bar");

        ResponseOrError<Payment> createResponse = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.of("https://stil4m.github.io"), meta));
        ResponseOrError<Payment> paymentResponse = payments.get(createResponse.getData().getId());
        Payment payment = paymentResponse.getData();

        assertThat(payment.getMethod(), is(nullValue()));
        assertThat(payment.getAmount(), is(1.00));
        assertThat(payment.getDescription(), is("Some description"));
        assertThat(payment.getId(), is(notNullValue()));
        assertThat(payment.getDetails(), is(nullValue()));
        assertThat(payment.getLinks(), is(notNullValue()));
        assertThat(payment.getLinks().getPaymentUrl().matches("https://www.mollie.com/payscreen/select-method/[A-Za-z0-9]+"), is(true));
        assertThat(payment.getLinks().getRedirectUrl(), is("http://example.com"));
        assertThat(payment.getLinks().getWebhookUrl(), is("https://stil4m.github.io"));
        assertThat(payment.getMode(), is("test"));
        assertThat(payment.getStatus(), is("open"));
        assertThat(payment.getMetadata(), is(meta));

    }

    @Test
    @Ignore
    public void testCreateAndGetCreditCardPayment() throws IOException {
        Map<String, Object> meta = new HashMap<>();
        meta.put("foo", "bar");

        ResponseOrError<Payment> createResponse = payments.create(new CreatePayment(Optional.of("creditcard"), 2.00, "Some credit card description", "http://example.com", Optional.of("https://stil4m.github.io"), meta));
        assertThat(createResponse,is(notNullValue()));
        assertThat(createResponse.getSuccess(),is(true));
        ResponseOrError<Payment> paymentResponse = payments.get(createResponse.getData().getId());
        Payment payment = paymentResponse.getData();

        assertThat(payment.getMethod(), is("creditcard"));
        assertThat(payment.getAmount(), is(2.00));
        assertThat(payment.getDescription(), is("Some credit card description"));
        assertThat(payment.getId(), is(notNullValue()));
        assertThat(payment.getDetails(), is(notNullValue())); // feeRegion=other
        assertThat(payment.getLinks(), is(notNullValue()));
        assertThat(payment.getLinks().getPaymentUrl().matches("https://www.mollie.com/paymentscreen/creditcard/testmode/[A-Za-z0-9]+"), is(true));
        assertThat(payment.getLinks().getRedirectUrl(), is("http://example.com"));
        assertThat(payment.getLinks().getWebhookUrl(), is("https://stil4m.github.io"));
        assertThat(payment.getMode(), is("test"));
        assertThat(payment.getStatus(), is("open"));
        assertThat(payment.getMetadata(), is(meta));

    }

    @Test
    public void testGetPayments() throws IOException, URISyntaxException {
        ResponseOrError<Page<Payment>> result = payments.list(Optional.empty(), Optional.empty());
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
        ResponseOrError<Page<Payment>> result = payments.list(Optional.of(20), Optional.of(40));
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
        ResponseOrError<Page<Payment>> result = payments.list(Optional.empty(), Optional.empty());
        assertThat(result.getSuccess(), is(true));
        Page<Payment> page = result.getData();

        Page<Payment> nextPage = payments.next(page).getData();

        assertThat(nextPage.getOffset(), is(10));
        assertThat(nextPage.getCount(), is(10));
    }

    @Test
    public void testPreviousPayments() throws IOException, URISyntaxException {
        ResponseOrError<Page<Payment>> result = payments.list(Optional.of(20), Optional.of(40));
        assertThat(result.getSuccess(), is(true));
        Page<Payment> page = result.getData();

        Page<Payment> previousPage = payments.previous(page).getData();

        assertThat(previousPage.getOffset(), is(20));
        assertThat(previousPage.getCount(), is(20));
    }

    @Test
    public void testDeleteBanktranferPayment() throws IOException, URISyntaxException, InterruptedException {
        String id = payments.create(new CreatePayment(Optional.of("banktransfer"), 1.00, "Some payment to delete", "http://example.com", Optional.empty(), null)).getData().getId();

        ResponseOrError<Payment> response = payments.delete(id);

        assertThat(response.getSuccess(),is(true));
        assertThat(response.getData(),is(notNullValue()));
        assertThat(response.getData().getCancelledDatetime(),is(notNullValue()));
    }

    @Test
    public void testDeleteIdealPayment() throws IOException, URISyntaxException, InterruptedException {
        String id = payments.create(new CreatePayment(Optional.of("ideal"), 1.00, "Some payment to delete", "http://example.com", Optional.empty(), null)).getData().getId();

        ResponseOrError<Payment> response = payments.delete(id);

        assertThat(response.getSuccess(),is(false));
        assertThat(response.getStatus(),is(HttpStatus.SC_UNPROCESSABLE_ENTITY));
    }

    @Test
    public void testDeleteUncancellablePayment() throws IOException, URISyntaxException, InterruptedException {
        String id = payments.create(new CreatePayment(Optional.empty(), 1.00, "Some payment to delete", "http://example.com", Optional.empty(), null)).getData().getId();

        ResponseOrError<Payment> response = payments.delete(id);

        assertThat(response.getSuccess(),is(false));
        assertThat(response.getStatus(),is(HttpStatus.SC_UNPROCESSABLE_ENTITY));
    }
}
