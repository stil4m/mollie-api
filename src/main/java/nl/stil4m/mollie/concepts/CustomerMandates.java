package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.CreateMandate;
import nl.stil4m.mollie.domain.Mandate;
import nl.stil4m.mollie.domain.Page;

public class CustomerMandates extends AbstractConcept<Mandate> implements ListAll<Mandate>, GetById<Mandate>, Create<Mandate, CreateMandate>, Delete<Mandate> {
    private static final TypeReference<Page<Mandate>> PAGE_TYPE = new TypeReference<Page<Mandate>>() {
    };
    private static final TypeReference<Mandate> SINGLE_TYPE = new TypeReference<Mandate>() {
    };

    public CustomerMandates(String apiKey, String endpoint, RequestExecutor requestExecutor, String customerId) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endpoint, "customers", customerId, "mandates");
    }
}