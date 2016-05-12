package nl.stil4m.mollie.domain.subpayments.ideal;

public class IdealPaymentOptions {

    private final String issuer;

    public IdealPaymentOptions(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuer() {
        return issuer;
    }
}
