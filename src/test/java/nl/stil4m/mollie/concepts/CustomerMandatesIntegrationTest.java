package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.Client;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreateCustomer;
import nl.stil4m.mollie.domain.CreateMandate;
import nl.stil4m.mollie.domain.Customer;
import nl.stil4m.mollie.domain.Mandate;
import nl.stil4m.mollie.domain.Page;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class CustomerMandatesIntegrationTest {

    private CustomerMandates mandates;

    @Before
    public void before() throws InterruptedException, IOException {
        Thread.sleep(TEST_TIMEOUT);
        Client client = strictClientWithApiKey(VALID_API_KEY);

        String uuid = UUID.randomUUID().toString();
        Map<String, Object> defaultMetadata = new HashMap<>();
        defaultMetadata.put("foo", "bar");
        defaultMetadata.put("id", uuid);

        String name = "Test Customer " + uuid;
        Customer customer = client.customers().create(new CreateCustomer(name, uuid + "@foobar.com", Optional.empty(), defaultMetadata)).getData();

        mandates = client.customerMandates(customer.getId());
    }

    @Test
    public void testList() throws IOException, URISyntaxException {
        ResponseOrError<Page<Mandate>> list = mandates.list(Optional.empty(), Optional.empty());

        assertThat(list.getSuccess(), is(true));
    }

    @Test
    public void testCreate() throws IOException, URISyntaxException {
        // https://www.mollie.com/nl/docs/reference/mandates/create
        String method = "directdebit";
        String name = "Test geit";
        String account = "NL91ABNA0417164300";
        CreateMandate createMandate = new CreateMandate(method, name, account);

        ResponseOrError<Mandate> result = mandates.create(createMandate);

        assertThat(result.getSuccess(), is(true));
        assertThat(result.getData().getId(),notNullValue());
        assertThat(result.getData().getMethod(),is(method));
        assertThat(result.getData().getDetails().get("consumerName"),is(name));
        assertThat(result.getData().getDetails().get("consumerAccount"),is(account));
        assertThat(result.getData().getCreatedDatetime(), is(notNullValue()));
        assertThat(result.getData().getSignatureDate(), is(notNullValue()));
    }
}
