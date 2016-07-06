package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Issuer;
import nl.stil4m.mollie.domain.Page;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static nl.stil4m.mollie.Util.validatePaymentId;

public class Issuers {


    private static final TypeReference<Page<Issuer>> PAGE_ISSUER_TYPE = new TypeReference<Page<Issuer>>() {
    };
    private static final TypeReference<Issuer> ISSUER_TYPE = new TypeReference<Issuer>() {
    };
    
    private final String apiKey;
    private final String endpoint;
    private final RequestExecutor requestExecutor;

    public Issuers(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.requestExecutor = requestExecutor;
    }

    public ResponseOrError<Page<Issuer>> all(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(endpoint + "/issuers")
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestExecutor.execute(apiKey, httpGet, PAGE_ISSUER_TYPE);
    }

    public ResponseOrError<Issuer> get(String id) throws IOException {
        validatePaymentId(id);
        HttpGet httpGet = new HttpGet(endpoint + "/issuers/" + id);
        return requestExecutor.execute(apiKey, httpGet, ISSUER_TYPE);
    }
}
