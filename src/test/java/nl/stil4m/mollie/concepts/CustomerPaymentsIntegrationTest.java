package nl.stil4m.mollie.concepts;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import nl.stil4m.mollie.Client;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreateCustomer;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.Customer;
import nl.stil4m.mollie.domain.CustomerPayment;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;
import nl.stil4m.mollie.domain.customerpayments.FirstRecurringPayment;

public class CustomerPaymentsIntegrationTest {

    private CustomerPayments customerPayments;

    @Before
    public void before() throws InterruptedException, IOException {
        Thread.sleep(TEST_TIMEOUT);
        Client client = strictClientWithApiKey(VALID_API_KEY);

        Map<String, Object> defaultMetadata = new HashMap<>();
        defaultMetadata.put("foo", "bar");

        String uuid = UUID.randomUUID().toString();
        String name = "Test Customer " + uuid;
        Customer customer = client.customers().create(new CreateCustomer(name, "test@foobar.com", Optional.empty(), defaultMetadata)).getData();
        
        customerPayments = client.customerPayments(customer.getId());
    }

    @Test
    public void testGetCustomerPayments() throws IOException, URISyntaxException {
        ResponseOrError<Page<Payment>> all = customerPayments.all(Optional.empty(), Optional.empty());
        
        assertThat(all.getSuccess(), is(true));
    }

    @Test
    public void testCreateCustomerPayment() throws IOException, URISyntaxException {
        CustomerPayment customerPayment = new FirstRecurringPayment(new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null));
        
        ResponseOrError<Payment> all = customerPayments.create(customerPayment);
        
        assertThat(all.getSuccess(), is(true));
    }
}
