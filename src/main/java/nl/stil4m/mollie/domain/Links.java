package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Links {

    private final String paymentUrl;
    private final String redirectUrl;
    private final String webhookUrl;

    @JsonCreator
    public Links(@JsonProperty("paymentUrl") String paymentUrl,
                 @JsonProperty("redirectUrl") String redirectUrl,
                 @JsonProperty("webhookUrl") String webhookUrl) {
        this.paymentUrl = paymentUrl;
        this.redirectUrl = redirectUrl;
        this.webhookUrl = webhookUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }
}
