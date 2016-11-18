package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.CreateRefund;
import nl.stil4m.mollie.domain.Refund;

public class PaymentRefunds extends AbstractConcept<Refund> implements ListAll<Refund>, GetById<Refund>, Create<Refund, CreateRefund>, Delete<Refund> {

    public PaymentRefunds(String apiKey, String endpoint, RequestExecutor requestExecutor, String paymentId) {
        super(apiKey, requestExecutor, Refunds.SINGLE_TYPE, Refunds.PAGE_TYPE, endpoint, "payments", paymentId, "refunds");
    }
}