package nl.stil4m.mollie.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import nl.stil4m.mollie.domain.customerpayments.FirstRecurringPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.IdealPaymentOptions;

public class PaymentSerializationTest {
	private ObjectMapper mapper;
	
	@Before 
	public void setUp() {
		mapper = new ObjectMapper();
		mapper.registerModule(new Jdk8Module());
	}
	
	@Test
    public void testSerializeFirstRecurringPayment() throws IOException {
        Map<String, Object> metaData = new HashMap<>();

        CustomerPayment customerPayment = new FirstRecurringPayment(new CreateIdealPayment(1.0, "Description", "redirectUrl", Optional.empty(), metaData, new IdealPaymentOptions("MyIssuer")));

        String serialized = mapper.writeValueAsString(customerPayment);
        Map mapRepresentation = mapper.readValue(serialized, Map.class);
        InputStream resourceAsStream = getClass().getResourceAsStream("/expected_first_recurring_payment.json");
        Map expected = mapper.readValue(resourceAsStream, Map.class);
        assertThat(mapRepresentation, is(expected));
    }
	
	@Test
    public void testDeserializeRecurringPaymentResponse() throws IOException {
		// FIXME #32 response on recurring payments contains more info; test fails if FAIL_ON_UNKNOWN_PROPERTIES is true 
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		InputStream resourceAsStream = getClass().getResourceAsStream("/response_create_recurring_payment.json");
		
		Payment payment = mapper.readValue(resourceAsStream, Payment.class);
		
		assertThat(payment.getAmount(), is(1.00));
		assertThat(payment.getRecurringType(), is("recurring"));
		assertThat(payment.getCustomerId(), is("cst_Kdp3uq2MeF"));
	}
}