package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.domain.ApiKeyCheck;

import java.io.IOException;

public class Status {

    private Payments payments;

    public Status(Payments payments) {
        this.payments = payments;
    }

    public ApiKeyCheck checkApiKey() throws IOException {
        int status = payments.get("unknown").getStatus();
        return new ApiKeyCheck(status == 404);
    }

}
