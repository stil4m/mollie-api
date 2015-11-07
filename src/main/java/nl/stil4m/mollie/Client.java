package nl.stil4m.mollie;

import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.PaymentStatus;

import java.io.IOException;

public class Client {

    private final DynamicClient dynamicClient;
    private final String apiKey;


    public Client(DynamicClient dynamicClient, String apiKey) {
        this.dynamicClient = dynamicClient;
        this.apiKey = apiKey;
    }

    public ResponseOrError<CreatedPayment> createPayment(CreatePayment createPayment) throws IOException {
        return dynamicClient.createPayment(apiKey, createPayment);
    }

    public ResponseOrError<PaymentStatus> getPaymentStatus(String apiKey, String id) throws IOException {
        return dynamicClient.getPaymentStatus(apiKey, id);
    }
}
