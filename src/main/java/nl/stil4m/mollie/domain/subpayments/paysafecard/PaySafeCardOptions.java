package nl.stil4m.mollie.domain.subpayments.paysafecard;

public class PaySafeCardOptions {

    private final String customerReference;

    public PaySafeCardOptions(String customerReference) {
        this.customerReference = customerReference;
    }

    public String getCustomerReference() {
        return customerReference;
    }
}
