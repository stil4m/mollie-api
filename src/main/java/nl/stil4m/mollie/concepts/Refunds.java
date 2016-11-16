package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Refund;

public class Refunds extends AbstractConcept<Refund> implements ListAll<Refund> {
    protected static final TypeReference<Page<Refund>> PAGE_TYPE = new TypeReference<Page<Refund>>() {
    };
    protected static final TypeReference<Refund> SINGLE_TYPE = new TypeReference<Refund>() {
    };

    public Refunds(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endpoint, "refunds");
    }
}