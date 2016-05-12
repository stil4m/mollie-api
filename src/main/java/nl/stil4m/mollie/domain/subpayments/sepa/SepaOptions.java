package nl.stil4m.mollie.domain.subpayments.sepa;

public class SepaOptions {

    private final String consumerName;
    private final String consumerAccount;

    public SepaOptions(String consumerName, String consumerAccount) {
        this.consumerName = consumerName;
        this.consumerAccount = consumerAccount;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public String getConsumerAccount() {
        return consumerAccount;
    }
}
