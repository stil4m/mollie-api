package nl.stil4m.mollie.domain;

import javax.annotation.Nullable;
import java.util.Optional;

public class CreateRefund {
    private final Optional<Double> amount;

    public CreateRefund(@Nullable Double amount) {
        this.amount = Optional.ofNullable(amount);
    }

    public Optional<Double> getAmount() {
        return amount;
    }
}