package nl.stil4m.mollie.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

public class CreateMandate {
    private final String method;
    private final String consumerName;
    private final String consumerAccount;
    private final Optional<String> consumerBic;
    private final Optional<Date> signatureDate;
    private final Optional<String> mandateReference;

    public CreateMandate(@Nonnull String method, @Nonnull String consumerName, @Nonnull String consumerAccount) {
        this(method, consumerName, consumerAccount, null, null, null);
    }

    public CreateMandate(@Nonnull String method, @Nonnull String consumerName, @Nonnull String consumerAccount, @Nullable String consumerBic, @Nullable Date signatureDate, @Nullable String mandateReference) {
        super();
        this.method = method;
        this.consumerName = consumerName;
        this.consumerAccount = consumerAccount;
        this.consumerBic = Optional.ofNullable(consumerBic);
        this.signatureDate = Optional.ofNullable(signatureDate);
        this.mandateReference = Optional.ofNullable(mandateReference);
    }

    public String getMethod() {
        return method;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public String getConsumerAccount() {
        return consumerAccount;
    }

    public Optional<String> getConsumerBic() {
        return consumerBic;
    }

    public Optional<Date> getSignatureDate() {
        return signatureDate;
    }

    public Optional<String> getMandateReference() {
        return mandateReference;
    }
}