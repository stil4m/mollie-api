package nl.stil4m.mollie.domain.subpayments.base;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import nl.stil4m.mollie.domain.CreatePayment;

import java.util.Map;

public abstract class OptionedCreatePayment<T> extends CreatePayment {

    @JsonUnwrapped
    private final T options;

    public OptionedCreatePayment(String method, Double amount, String description, String redirectUrl, Map<String, Object> metadata, T options) {
        super(method, amount, description, redirectUrl, metadata);
        this.options = options;
    }

    public OptionedCreatePayment(String method, Double amount, String description, String redirectUrl, String webhookUrl, Map<String, Object> metadata, T options) {
        super(method, amount, description, redirectUrl, webhookUrl, metadata);
        this.options = options;
    }

    public T getOptions() {
        return options;
    }
}
