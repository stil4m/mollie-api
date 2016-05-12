package nl.stil4m.mollie.domain.subpayments.paypal;

public class PayPalOptions {

    private final String shippingAddress;
    private final String shippingCity;
    private final String shippingRegion;
    private final String shippingPostal;
    private final String shippingCountry;

    public PayPalOptions(String shippingAddress, String shippingCity, String shippingRegion, String shippingPostal, String shippingCountry) {
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingRegion = shippingRegion;
        this.shippingPostal = shippingPostal;
        this.shippingCountry = shippingCountry;
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
