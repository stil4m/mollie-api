package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Method;
import nl.stil4m.mollie.domain.Page;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static nl.stil4m.mollie.Util.validatePaymentId;

public class Methods {

    private final String apiKey;
    private final String endpoint;
    private final RequestExecutor requestExecutor;

    public Methods(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.requestExecutor = requestExecutor;
    }

    public ResponseOrError<Page<Method>> all(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(endpoint + "/methods")
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestExecutor.execute(apiKey, httpGet, new TypeReference<Page<Method>>() {
        });
    }

    public ResponseOrError<Method> get(String id) throws IOException {
        validatePaymentId(id);
        HttpGet httpGet = new HttpGet(endpoint + "/methods/" + id);
        return requestExecutor.execute(apiKey, httpGet, new TypeReference<Method>() {
        });
    }
}
