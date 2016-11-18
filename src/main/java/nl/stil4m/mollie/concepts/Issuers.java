package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.domain.Issuer;
import nl.stil4m.mollie.domain.Page;

public class Issuers extends AbstractConcept<Issuer> implements ListAll<Issuer>, GetById<Issuer> {
    private static final TypeReference<Page<Issuer>> PAGE_TYPE = new TypeReference<Page<Issuer>>() {
    };
    private static final TypeReference<Issuer> SINGLE_TYPE = new TypeReference<Issuer>() {
    };

    public Issuers(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        super(apiKey, requestExecutor, SINGLE_TYPE, PAGE_TYPE, endpoint, "issuers");
    }
}