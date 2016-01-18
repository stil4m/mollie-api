package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class Links {

    private final String paymentUrl;
    private final String redirectUrl;
    private final String webhookUrl;
    private final Optional<String> refunds;

    @JsonCreator
    public Links(@JsonProperty("paymentUrl") String paymentUrl,
                 @JsonProperty("redirectUrl") String redirectUrl,
                 @JsonProperty("webhookUrl") String webhookUrl,
                 @JsonProperty("refunds") String refunds) {
        this.paymentUrl = paymentUrl;
        this.redirectUrl = redirectUrl;
        this.webhookUrl = webhookUrl;
        this.refunds = Optional.ofNullable(refunds);
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

    public Optional<String> getRefunds() {
        return refunds;
    }
}
