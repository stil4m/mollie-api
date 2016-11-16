package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.CreateRefund;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Refund;

public class PaymentRefunds extends AbstractConcept<Refund> implements ListAll<Refund>, GetById<Refund>, Create<Refund, CreateRefund>, Delete<Refund> {
    private static final TypeReference<Page<Refund>> PAGE_TYPE = new TypeReference<Page<Refund>>() {
    };
    private static final TypeReference<Refund> SINGLE_TYPE = new TypeReference<Refund>() {
    };

    public PaymentRefunds(String apiKey, String endpoint, RequestExecutor requestExecutor, String paymentId) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endpoint, "payments", paymentId, "refunds");
    }
}