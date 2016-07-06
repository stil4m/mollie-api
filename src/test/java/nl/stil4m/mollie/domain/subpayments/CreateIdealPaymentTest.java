package nl.stil4m.mollie.domain.subpayments;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stil4m.mollie.domain.subpayments.ideal.CreateIdealPayment;
import nl.stil4m.mollie.domain.subpayments.ideal.IdealPaymentOptions;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateIdealPaymentTest {

    @Test
    public void testSerialize() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("mySpecialKey", "value");
        String serialized = objectMapper.writeValueAsString(new CreateIdealPayment(1.0, "Description", "redirectUrl",
                metaData, new IdealPaymentOptions("MyIssuer")));

        Map mapRepresentation = objectMapper.readValue(serialized, Map.class);
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/expected_create_ideal_payment.json");
        Map expected = objectMapper.readValue(resourceAsStream, Map.class);
        assertThat(mapRepresentation, is(expected));
    }

    @Test
    public void testSerializeWithWebhookUrl() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("mySpecialKey", "value");
        String serialized = objectMapper.writeValueAsString(new CreateIdealPayment(1.0, "Description", "redirectUrl",
                "webhookUrl", metaData, new IdealPaymentOptions("MyIssuer")));

        Map mapRepresentation = objectMapper.readValue(serialized, Map.class);
        InputStream resourceAsStream =
                this.getClass().getResourceAsStream("/expected_create_ideal_payment_with_webhookurl.json");
        Map expected = objectMapper.readValue(resourceAsStream, Map.class);
        assertThat(mapRepresentation, is(expected));
    }
}
