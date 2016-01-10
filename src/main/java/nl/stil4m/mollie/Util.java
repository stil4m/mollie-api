package nl.stil4m.mollie;

public class Util {

    public static void validatePaymentId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Payment id may not be null");
        }
        if ("".equals(id)) {
            throw new IllegalArgumentException("Payment id may not be an empty string");
        }
    }
}
