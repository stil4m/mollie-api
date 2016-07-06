package nl.stil4m.mollie.domain.subpayments.creditcard;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class CreateCreditCardPayment extends OptionedCreatePayment<CreditCardOptions> {

    public CreateCreditCardPayment(@Nonnull Double amount, @Nonnull String description, @Nonnull String redirectUrl, @Nonnull Optional<String> webhookUrl, @Nullable Map<String, Object> metadata, @Nonnull CreditCardOptions options) {
        super("creditcard", amount, description, redirectUrl, webhookUrl, metadata, options);
    }
}
