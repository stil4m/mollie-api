package nl.stil4m.mollie.domain.subpayments.ideal;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import java.util.Map;

public class CreateIdealPayment extends OptionedCreatePayment<IdealPaymentOptions> {

    public CreateIdealPayment(Double amount, String description, String redirectUrl, Map<String, Object> metadata, IdealPaymentOptions options) {
        super("ideal", amount, description, redirectUrl, metadata, options);
    }

    public CreateIdealPayment(Double amount, String description, String redirectUrl, String webhookUrl, Map<String, Object> metadata, IdealPaymentOptions options) {
        super("ideal", amount, description, redirectUrl, webhookUrl, metadata, options);
    }

}
