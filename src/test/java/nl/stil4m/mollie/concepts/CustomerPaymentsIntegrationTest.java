package nl.stil4m.mollie.concepts;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
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
import nl.stil4m.mollie.domain.customerpayments.NormalCustomerPayment;
import nl.stil4m.mollie.domain.customerpayments.RecurringPayment;

public class CustomerPaymentsIntegrationTest {

    private CustomerPayments customerPayments;

    @Before
    public void before() throws InterruptedException, IOException {
        Thread.sleep(TEST_TIMEOUT);
        Client client = strictClientWithApiKey(VALID_API_KEY);
        
        String uuid = UUID.randomUUID().toString();
        Map<String, Object> defaultMetadata = new HashMap<>();
        defaultMetadata.put("foo", "bar");
        defaultMetadata.put("id", uuid);
        
        String name = "Test Customer " + uuid;
        Customer customer = client.customers().create(new CreateCustomer(name, uuid+"@foobar.com", Optional.empty(), defaultMetadata)).getData();
        
        customerPayments = client.customerPayments(customer.getId());
    }

    @Test
    public void testGetCustomerPayments() throws IOException, URISyntaxException {
        ResponseOrError<Page<Payment>> all = customerPayments.all(Optional.empty(), Optional.empty());
        
        assertThat(all.getSuccess(), is(true));
    }

    @Test
    public void testCreateNormalCustomerPayment() throws IOException, URISyntaxException {
    	// https://www.mollie.com/nl/docs/reference/payments/create
    	Optional<String> method = Optional.empty();
    	double amount = 1.00;
    	String description = "Some description";
    	String redirectUrl = "https://example.com/thank/you";
    	Optional<String> webhookUrl = Optional.of("https://example.com/api/payment");
    	CreatePayment createPayment = new CreatePayment(method, amount, description, redirectUrl,webhookUrl, null);
    	
    	CustomerPayment payment = new NormalCustomerPayment(createPayment);
        ResponseOrError<Payment> result = customerPayments.create(payment);
        
        assertThat(result.getSuccess(), is(true));
        assertThat(result.getData().getRecurringType(),nullValue());
        assertThat(result.getData().getLinks().getPaymentUrl(),startsWith("https://www.mollie.com/payscreen/"));
        assertThat(result.getData().getLinks().getRedirectUrl(),is(redirectUrl));
        assertThat(result.getData().getLinks().getWebhookUrl(),is(webhookUrl.get()));
    }
    
    @Test
    public void testCreateFirstRecurringPayment() throws IOException, URISyntaxException {
    	CreatePayment createPayment = new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null);
        CustomerPayment payment = new FirstRecurringPayment(createPayment);
        
        ResponseOrError<Payment> result = customerPayments.create(payment);
        
        assertThat(result.getSuccess(), is(true));
        assertThat(result.getData().getRecurringType(),is("first"));
        assertThat(result.getData().getLinks().getPaymentUrl(),startsWith("https://www.mollie.com/payscreen/"));
    }
    
    @Test
    @Ignore
    public void testCreateRecurringPayment() throws IOException, URISyntaxException {
    	//FIXME only runnable if a the paymentUrl of FirstRecurringPayment is followed and completed as "success" for the same customer
    	CreatePayment createPayment = new CreatePayment(Optional.empty(), 1.00, "Some description", "http://example.com", Optional.empty(), null);
    	assertThat(customerPayments.create(new FirstRecurringPayment(createPayment)).getSuccess(), is(true));
        CustomerPayment payment = new RecurringPayment(createPayment);
        
        ResponseOrError<Payment> result = customerPayments.create(payment);
        
        assertThat(result.getSuccess(), is(true));
    }
}
