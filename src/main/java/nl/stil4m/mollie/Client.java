package nl.stil4m.mollie;

import nl.stil4m.mollie.concepts.*;
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

    public Methods methods() {
        return dynamicClient.methods(apiKey);
    }

    public Issuers issuers() {
        return dynamicClient.issuers(apiKey);
    }

    public Refunds refunds() {
        return dynamicClient.refunds(apiKey);
    }

}
