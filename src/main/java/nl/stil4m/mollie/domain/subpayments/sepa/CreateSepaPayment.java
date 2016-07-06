package nl.stil4m.mollie.domain.subpayments.sepa;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import java.util.Map;

public class CreateSepaPayment extends OptionedCreatePayment<SepaOptions> {

    public CreateSepaPayment(Double amount, String description, String redirectUrl, Map<String, Object> metadata, SepaOptions options) {
        super("directdebit", amount, description, redirectUrl, null, metadata, options);
    }

    public CreateSepaPayment(Double amount, String description, String redirectUrl, String webhookUrl, Map<String, Object> metadata, SepaOptions options) {
        super("directdebit", amount, description, redirectUrl, webhookUrl, metadata, options);
    }
}
