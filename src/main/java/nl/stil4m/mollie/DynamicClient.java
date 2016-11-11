package nl.stil4m.mollie;

import nl.stil4m.mollie.concepts.CustomerPayments;
import nl.stil4m.mollie.concepts.Customers;
import nl.stil4m.mollie.concepts.Issuers;
import nl.stil4m.mollie.concepts.Mandates;
import nl.stil4m.mollie.concepts.Methods;
import nl.stil4m.mollie.concepts.Payments;
import nl.stil4m.mollie.concepts.Refunds;
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

    public Refunds refunds(String apiKey, String paymentId) {
        return new Refunds(apiKey, endpoint, requestExecutor, paymentId);
    }

    public Customers customers(String apiKey) {
        return new Customers(apiKey, endpoint, requestExecutor);
    }

    public CustomerPayments customerPayments(String apiKey, String customerId) {
        return new CustomerPayments(apiKey, endpoint, requestExecutor, customerId);
    }
    
    public Mandates mandates(String apiKey, String customerId) {
        return new Mandates(apiKey, endpoint, requestExecutor, customerId);
    }
}
