package nl.stil4m.mollie.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CreatePayment {

    private final Double amount;
    private final String description;
    private final String redirectUrl;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String webhookUrl;

    private final String method;
    private final Map<String, Object> metadata;

    public CreatePayment(String method, Double amount, String description, String redirectUrl, Map<String, Object> metadata) {
        this(method, amount, description, redirectUrl, null, metadata);
    }

    public CreatePayment(String method, Double amount, String description, String redirectUrl, String webhookUrl,
            Map<String, Object> metadata) {
        this.method = method;
        this.amount = amount;
        this.description = description;
        this.redirectUrl = redirectUrl;
        this.webhookUrl = webhookUrl;
        this.metadata = metadata;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
