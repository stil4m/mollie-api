package nl.stil4m.mollie.concepts;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreateCustomer;
import nl.stil4m.mollie.domain.Customer;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.UpdateCustomer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CustomersIntegrationTest {

    private Customers customers;
    private Map<String, Object> defaultMetadata;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        customers = strictClientWithApiKey(VALID_API_KEY).customers();
        defaultMetadata = new HashMap<>();
        defaultMetadata.put("foo", "bar");
    }


    @Test
    public void testGetCustomers() throws IOException, URISyntaxException {
        ResponseOrError<Page<Customer>> all = customers.all(Optional.empty(), Optional.empty());
        assertThat(all.getSuccess(), is(true));
        //TODO Expand
    }

    @Test
    public void testCreateCustomer() throws IOException, URISyntaxException {
        String uuid = UUID.randomUUID().toString();

        ResponseOrError<Customer> createdCustomer = customers.create(new CreateCustomer(
                "Test Customer " + uuid,
                "test@foobar.nl",
                Optional.of("gb_EN"),
                defaultMetadata
        ));

        assertThat(createdCustomer.getSuccess(), is(true));
        //TODO Expand
    }

    @Test
    public void testGetCustomer() throws IOException, URISyntaxException {
        String uuid = UUID.randomUUID().toString();
        String originalName = "Test Customer " + uuid;
        ResponseOrError<Customer> createdCustomer = customers.create(new CreateCustomer(originalName, "test@foobar.nl", Optional.empty(), defaultMetadata));

        ResponseOrError<Customer> fetchedCustomer = customers.get(createdCustomer.getData().getId());
        assertThat(fetchedCustomer.getData().getName(), is(originalName));
        //TODO Expand
    }

    @Test
    public void testUpdateCustomerEmail() throws IOException, URISyntaxException {
        String uuid = UUID.randomUUID().toString();
        String originalName = "Test Customer " + uuid;
        ResponseOrError<Customer> createdCustomer = customers.create(new CreateCustomer(originalName, "test@foobar.nl", Optional.empty(), null));

        ResponseOrError<Customer> update = customers.update(createdCustomer.getData().getId(), new UpdateCustomer(
                Optional.empty(),
                Optional.of("test+2@foobar.nl"),
                Optional.empty(),
                defaultMetadata
        ));

        assertThat(update.getSuccess(), is(true));
        assertThat(update.getData().getName(), is(originalName));
        assertThat(update.getData().getEmail(), is("test+2@foobar.nl"));
    }

    @Test
    public void testUpdateCustomerName() throws IOException, URISyntaxException {
        String uuid = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();
        String originalName = "Test Customer " + uuid;
        String newName = "Test Customer " + uuid2;
        ResponseOrError<Customer> createdCustomer = customers.create(new CreateCustomer(originalName, "test@foobar.nl", Optional.empty(), null));

        ResponseOrError<Customer> update = customers.update(createdCustomer.getData().getId(), new UpdateCustomer(
                Optional.of(newName),
                Optional.empty(),
                Optional.empty(),
                defaultMetadata
        ));

        assertThat(update.getSuccess(), is(true));
        assertThat(update.getData().getName(), is(newName));
        assertThat(update.getData().getEmail(), is("test@foobar.nl"));
    }


}
