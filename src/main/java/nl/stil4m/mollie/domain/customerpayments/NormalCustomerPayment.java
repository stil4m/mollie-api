package nl.stil4m.mollie.domain.customerpayments;

import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CustomerPayment;

public class NormalCustomerPayment extends CustomerPayment {


    public NormalCustomerPayment(CreatePayment createPayment) {
        super(createPayment, null);
    }

}
