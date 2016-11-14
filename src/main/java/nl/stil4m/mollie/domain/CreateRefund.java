package nl.stil4m.mollie.domain;

import java.util.Optional;

import javax.annotation.Nullable;

public class CreateRefund {
    private final Optional<Double> amount;

    public CreateRefund(@Nullable Double amount) {
        this.amount = Optional.ofNullable(amount);
    }

    public Optional<Double> getAmount() {
        return amount;
    }
}