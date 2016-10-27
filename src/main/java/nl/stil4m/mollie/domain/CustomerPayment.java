package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public abstract class CustomerPayment {

    @JsonUnwrapped
    private final CreatePayment createPayment;
    private final String recurringType;

    public CustomerPayment(CreatePayment createPayment, String recurringType) {
        this.createPayment = createPayment;
        this.recurringType = recurringType;
    }

    public CreatePayment getCreatePayment() {
        return createPayment;
    }

    public String getRecurringType() {
        return recurringType;
    }
}
