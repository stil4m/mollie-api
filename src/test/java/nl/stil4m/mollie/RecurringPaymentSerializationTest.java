package nl.stil4m.mollie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import nl.stil4m.mollie.domain.CustomerPayment;
import nl.stil4m.mollie.domain.customerpayments.FirstRecurringPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.IdealPaymentOptions;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RecurringPaymentSerializationTest {

    @Test
    public void testSerialize() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        Map<String, Object> metaData = new HashMap<>();

        CustomerPayment customerPayment = new FirstRecurringPayment(new CreateIdealPayment(1.0, "Description", "redirectUrl", Optional.empty(), metaData, new IdealPaymentOptions("MyIssuer")));

        String serialized = objectMapper.writeValueAsString(customerPayment);
        Map mapRepresentation = objectMapper.readValue(serialized, Map.class);
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/expected_first_recurring_payment.json");
        Map expected = objectMapper.readValue(resourceAsStream, Map.class);
        assertThat(mapRepresentation, is(expected));
    }

}
