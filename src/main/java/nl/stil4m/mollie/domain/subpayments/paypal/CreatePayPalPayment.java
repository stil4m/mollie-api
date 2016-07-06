package nl.stil4m.mollie.domain.subpayments.paypal;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import java.util.Map;

public class CreatePayPalPayment extends OptionedCreatePayment<PayPalOptions> {

    public CreatePayPalPayment(Double amount, String description, String redirectUrl, Map<String, Object> metadata, PayPalOptions options) {
        super("paypal", amount, description, redirectUrl, metadata, options);
    }

    public CreatePayPalPayment(Double amount, String description, String redirectUrl, String webhookUrl, Map<String, Object> metadata, PayPalOptions options) {
        super("paypal", amount, description, redirectUrl, webhookUrl, metadata, options);
    }
}

