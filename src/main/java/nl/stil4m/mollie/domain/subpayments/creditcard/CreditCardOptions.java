package nl.stil4m.mollie.domain.subpayments.creditcard;

public class CreditCardOptions {


    private final String billingAddress;
    private final String billingCity;
    private final String billingRegion;
    private final String billingPostal;
    private final String billingCountry;
    private final String shippingAddress;
    private final String shippingCity;
    private final String shippingRegion;
    private final String shippingPostal;
    private final String shippingCountry;

    public CreditCardOptions(String billingAddress, String billingCity, String billingRegion, String billingPostal, String billingCountry,
                             String shippingAddress, String shippingCity, String shippingRegion, String shippingPostal, String shippingCountry) {
        this.billingAddress = billingAddress;
        this.billingCity = billingCity;
        this.billingRegion = billingRegion;
        this.billingPostal = billingPostal;
        this.billingCountry = billingCountry;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingRegion = shippingRegion;
        this.shippingPostal = shippingPostal;
        this.shippingCountry = shippingCountry;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public String getBillingRegion() {
        return billingRegion;
    }

    public String getBillingPostal() {
        return billingPostal;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public String getShippingRegion() {
        return shippingRegion;
    }

    public String getShippingPostal() {
        return shippingPostal;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }
}
