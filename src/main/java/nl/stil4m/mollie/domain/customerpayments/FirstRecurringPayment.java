package nl.stil4m.mollie.domain.customerpayments;

import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CustomerPayment;

public class FirstRecurringPayment extends CustomerPayment {

    public FirstRecurringPayment(CreatePayment createPayment) {
        super(createPayment, "first");
    }

}
