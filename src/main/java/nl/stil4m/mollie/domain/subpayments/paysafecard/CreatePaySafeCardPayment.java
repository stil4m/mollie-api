package nl.stil4m.mollie.domain.subpayments.paysafecard;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class CreatePaySafeCardPayment extends OptionedCreatePayment<PaySafeCardOptions> {

    public CreatePaySafeCardPayment(@Nonnull Double amount, @Nonnull String description, @Nonnull String redirectUrl, @Nonnull Optional<String> webhookUrl, @Nullable Map<String, Object> metadata, @Nonnull PaySafeCardOptions options) {
        super("paysafecard", amount, description, redirectUrl, webhookUrl, metadata, options);
    }
}
