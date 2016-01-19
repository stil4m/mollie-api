package nl.stil4m.mollie;

import nl.stil4m.mollie.concepts.*;
import nl.stil4m.mollie.domain.ApiKeyCheck;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.Payment;

import java.io.IOException;

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

}
