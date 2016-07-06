package nl.stil4m.mollie.domain.subpayments.sepa;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class CreateSepaPayment extends OptionedCreatePayment<SepaOptions> {

    public CreateSepaPayment(@Nonnull Double amount, @Nonnull String description, @Nonnull String redirectUrl, @Nonnull Optional<String> webhookUrl, @Nullable Map<String, Object> metadata, @Nonnull SepaOptions options) {
        super("directdebit", amount, description, redirectUrl, webhookUrl, metadata, options);
    }
}
