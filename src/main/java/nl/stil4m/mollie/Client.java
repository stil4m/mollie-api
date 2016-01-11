package nl.stil4m.mollie;

import nl.stil4m.mollie.concepts.Payments;
import nl.stil4m.mollie.concepts.Status;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.Payment;

import java.io.IOException;

public class Client {

    private final DynamicClient dynamicClient;
    private final String apiKey;


    public Client(DynamicClient dynamicClient, String apiKey) {
        this.dynamicClient = dynamicClient;
        this.apiKey = apiKey;
    }

    public Payments payments() {
        return dynamicClient.payments(apiKey);
    }

    public Status status() {
        return dynamicClient.status(apiKey);
    }

    @Deprecated
    public ResponseOrError<CreatedPayment> createPayment(CreatePayment createPayment) throws IOException {
        return dynamicClient.payments(apiKey).create(createPayment);
    }

    @Deprecated
    public ResponseOrError<Payment> getPaymentStatus(String apiKey, String id) throws IOException {
        return dynamicClient.payments(apiKey).get(id);
    }

}
