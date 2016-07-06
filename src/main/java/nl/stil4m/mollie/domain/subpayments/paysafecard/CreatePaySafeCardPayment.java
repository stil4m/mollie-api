package nl.stil4m.mollie.domain.subpayments.paysafecard;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import java.util.Map;

public class CreatePaySafeCardPayment extends OptionedCreatePayment<PaySafeCardOptions> {

    public CreatePaySafeCardPayment(Double amount, String description, String redirectUrl, Map<String, Object> metadata, PaySafeCardOptions options) {
        super("paysafecard", amount, description, redirectUrl, metadata, options);
    }

    public CreatePaySafeCardPayment(Double amount, String description, String redirectUrl, String webhookUrl, Map<String, Object> metadata, PaySafeCardOptions options) {
        super("paysafecard", amount, description, redirectUrl, webhookUrl, metadata, options);
    }
}
