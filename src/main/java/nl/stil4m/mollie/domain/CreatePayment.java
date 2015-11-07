package nl.stil4m.mollie.domain;

import java.util.Map;

public class CreatePayment {

    private final Double amount;
    private final String description;
    private final String redirectUrl;
    private final String method;
    private final Map<String, Object> metadata;

    public CreatePayment(String method, Double amount, String description, String redirectUrl, Map<String, Object> metadata) {
        this.method = method;
        this.amount = amount;
        this.description = description;
        this.redirectUrl = redirectUrl;
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

    public String getMethod() {
        return method;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
