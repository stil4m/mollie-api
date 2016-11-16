package nl.stil4m.mollie;

import nl.stil4m.mollie.concepts.CustomerPayments;
import nl.stil4m.mollie.concepts.Customers;
import nl.stil4m.mollie.concepts.Issuers;
import nl.stil4m.mollie.concepts.CustomerMandates;
import nl.stil4m.mollie.concepts.Methods;
import nl.stil4m.mollie.concepts.Payments;
import nl.stil4m.mollie.concepts.Refunds;
import nl.stil4m.mollie.concepts.PaymentRefunds;
import nl.stil4m.mollie.concepts.Status;

public class DynamicClient {

    private final String endpoint;
    private final RequestExecutor requestExecutor;

    public DynamicClient(String endpoint, RequestExecutor requestExecutor) {
        this.endpoint = endpoint;
        this.requestExecutor = requestExecutor;
    }

    public Payments payments(String apiKey) {
        return new Payments(apiKey, endpoint, requestExecutor);
    }

    public Status status(String apiKey) {
        return new Status(payments(apiKey));
    }

    public Methods methods(String apiKey) {
        return new Methods(apiKey, endpoint, requestExecutor);
    }

    public Issuers issuers(String apiKey) {
        return new Issuers(apiKey, endpoint, requestExecutor);
    }
    
    public Refunds refunds(String apiKey) {
        return new Refunds(apiKey, endpoint, requestExecutor);
    }

    public PaymentRefunds paymentRefunds(String apiKey, String paymentId) {
        return new PaymentRefunds(apiKey, endpoint, requestExecutor, paymentId);
    }

    public Customers customers(String apiKey) {
        return new Customers(apiKey, endpoint, requestExecutor);
    }

    public CustomerPayments customerPayments(String apiKey, String customerId) {
        return new CustomerPayments(apiKey, endpoint, requestExecutor, customerId);
    }

    public CustomerMandates customerMandates(String apiKey, String customerId) {
        return new CustomerMandates(apiKey, endpoint, requestExecutor, customerId);
    }
}
