package nl.stil4m.mollie.domain.subpayments.creditcard;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import java.util.Map;

public class CreateCreditCardPayment extends OptionedCreatePayment<CreditCardOptions> {

    public CreateCreditCardPayment(Double amount, String description, String redirectUrl, Map<String, Object> metadata, CreditCardOptions options) {
        super("creditcard", amount, description, redirectUrl, metadata, options);
    }
}
