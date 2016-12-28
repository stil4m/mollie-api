package nl.stil4m.mollie.concepts;

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

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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

        for (Customer customer : all.getData().getData()) {
            assertThat(customer.getId(), notNullValue());
        }
    }

    @Test
    public void testCreateCustomer() throws IOException, URISyntaxException {
        String uuid = UUID.randomUUID().toString();
        String name = "Test Customer " + uuid;
        String email = uuid + "@foobar.nl";
        Optional<String> locale = Optional.of("en_US");
        Map<String, Object> metadata = new HashMap<>(defaultMetadata);
        metadata.put("uuid", uuid);

        CreateCustomer createCustomer = new CreateCustomer(name, email, locale, metadata);
        ResponseOrError<Customer> created = customers.create(createCustomer);

        assertThat(created.getSuccess(), is(true));
        assertThat(created.getData().getName(), is(name));
        assertThat(created.getData().getEmail(), is(email));
        assertThat(created.getData().getLocale(), is(locale));
        assertThat(created.getData().getMetadata(), is(metadata));
    }

    @Test
    public void testGetCustomer() throws IOException, URISyntaxException {
        String uuid = UUID.randomUUID().toString();
        String name = "Test Customer " + uuid;
        String email = uuid + "@foobar.nl";
        Optional<String> locale = Optional.of("nl_NL");
        Map<String, Object> metadata = new HashMap<>(defaultMetadata);
        metadata.put("uuid", uuid);
        CreateCustomer createCustomer = new CreateCustomer(name, email, locale, metadata);
        ResponseOrError<Customer> createdCustomer = customers.create(createCustomer);
        String newCustomerId = createdCustomer.getData().getId();

        ResponseOrError<Customer> fetched = customers.get(newCustomerId);

        assertThat(fetched.getSuccess(), is(true));
        assertThat(fetched.getData().getName(), is(name));
        assertThat(fetched.getData().getEmail(), is(email));
        assertThat(fetched.getData().getLocale(), is(locale));
        assertThat(fetched.getData().getMetadata(), is(metadata));
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
