package nl.stil4m.mollie;

import com.google.common.collect.Sets;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.Issuer;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Refund;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ClientIntegrationTest {

    private Client client;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        client = new ClientBuilder().withApiKey(VALID_API_KEY).build();
    }

    @Test
    public void testGetIssuers() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Page<Issuer>> allResponse = client.issuers().all(Optional.empty(), Optional.empty());
        assertThat(allResponse.getSuccess(), is(true));

        Page<Issuer> all = allResponse.getData();
        assertThat(all.getCount(), is(1));
        assertThat(all.getTotalCount(), is(1));
        assertThat(all.getOffset(), is(0));
        assertThat(all.getLinks(), is(notNullValue()));
        assertThat(all.getLinks().getPrevious().isPresent(), is(false));
        assertThat(all.getLinks().getNext().isPresent(), is(false));

        Set<String> identifiers = all.getData().stream().map(Issuer::getId).collect(Collectors.toSet());
        assertThat(identifiers.containsAll(Sets.newHashSet("ideal_TESTNL99")), is(true));
    }

    @Test
    public void testGetIssuer() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Issuer> allResponse = client.issuers().get("ideal_TESTNL99");
        assertThat(allResponse.getSuccess(), is(true));

        Issuer issuer = allResponse.getData();
        assertThat(issuer.getResource(), is("issuer"));
        assertThat(issuer.getId(), is("ideal_TESTNL99"));
        assertThat(issuer.getName(), is("TBM Bank"));
        assertThat(issuer.getMethod(), is("ideal"));
    }

    @Test
    public void testGetRefunds() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<CreatedPayment> payment = client.payments().create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Page<Refund>> all = client.refunds().all(id, Optional.empty(), Optional.empty());
        assertThat(all.getSuccess(), is(true));
        Page<Refund> refundPage = all.getData();
        assertThat(refundPage.getCount(), is(0));
        assertThat(refundPage.getData(), is(notNullValue()));
        assertThat(refundPage.getLinks(), is(notNullValue()));
    }

    @Test
    public void testListRefundsForExistingPayment() throws IOException, URISyntaxException, InterruptedException {
        CreatedPayment createdPayment = client.payments().create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null)).getData();
        ResponseOrError<Page<Refund>> all = client.refunds().all(createdPayment.getId(), Optional.empty(), Optional.empty());

        assertThat(all.getSuccess(), is(true));

        Page<Refund> data = all.getData();
        assertThat(data.getData().size(), is(0));
    }


    @Test
    public void testCancelNonExistingRefund() throws IOException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The refund id is invalid");
        ResponseOrError<CreatedPayment> payment = client.payments().create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));

        assertThat(payment.getSuccess(), is(true));
        String id = payment.getData().getId();

        ResponseOrError<Void> cancel = client.refunds().cancel(id, "foo_bar");
        assertThat(cancel.getSuccess(), is(false));
        assertThat(cancel.getError().get("error"), is(errorData));
    }

    @Test
    public void testGetNonExistingRefund() throws IOException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The refund id is invalid");
        ResponseOrError<CreatedPayment> payment = client.payments().create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Refund> get = client.refunds().get(id, "foo_bar");
        assertThat(get.getSuccess(), is(false));
        assertThat(get.getError().get("error"), is(errorData));
    }

    @Test
    public void testCreateRefund() throws IOException, URISyntaxException {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("type", "request");
        errorData.put("message", "The payment is already refunded or has not been paid for yet");

        ResponseOrError<CreatedPayment> payment = client.payments().create(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        String id = payment.getData().getId();

        ResponseOrError<Refund> create = client.refunds().create(id, Optional.of(1.00));
        assertThat(create.getSuccess(), is(false));
        assertThat(create.getError().get("error"), is(errorData));
    }
}
