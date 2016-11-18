package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.CustomerPayment;
import nl.stil4m.mollie.domain.Payment;

public class CustomerPayments extends AbstractConcept<Payment> implements ListAll<Payment>, Create<Payment, CustomerPayment> {

    public CustomerPayments(String apiKey, String endpoint, RequestExecutor requestExecutor, String customerId) {
        super(apiKey, requestExecutor, Payments.SINGLE_TYPE, Payments.PAGE_TYPE, endpoint, "customers", customerId, "payments");
    }
}