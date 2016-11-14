package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.Method;
import nl.stil4m.mollie.domain.Page;

public class Methods extends AbstractConcept<Method> implements ListAll<Method>, GetById<Method> {
    private static final TypeReference<Page<Method>> PAGE_TYPE = new TypeReference<Page<Method>>() {
    };
    private static final TypeReference<Method> SINGLE_TYPE = new TypeReference<Method>() {
    };

    public Methods(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endpoint, "methods");
    }
}