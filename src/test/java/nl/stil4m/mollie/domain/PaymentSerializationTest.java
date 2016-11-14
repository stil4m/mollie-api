package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stil4m.mollie.domain.customerpayments.FirstRecurringPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.IdealPaymentOptions;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static nl.stil4m.mollie.TestUtil.objectMapper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PaymentSerializationTest {

    @Test
    public void testSerializeFirstRecurringPayment() throws IOException {
        ObjectMapper mapper = objectMapper();
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
        ObjectMapper mapper = objectMapper();
        InputStream resourceAsStream = getClass().getResourceAsStream("/response_create_recurring_payment.json");

        Payment payment = mapper.readValue(resourceAsStream, Payment.class);

        assertThat(payment.getAmount(), is(1.00));
        assertThat(payment.getRecurringType(), is("recurring"));
        assertThat(payment.getCustomerId(), is("cst_Kdp3uq2MeF"));
    }
}
