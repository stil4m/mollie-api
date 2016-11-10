package nl.stil4m.mollie.concepts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Issuer;
import nl.stil4m.mollie.domain.Page;

public class Issuers extends AbstractConcept<Issuer> {

    public Issuers(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        super(apiKey,requestExecutor,endpoint,"issuers");
    }

    public ResponseOrError<Page<Issuer>> all(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url())
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestPage(httpGet);
    }

    public ResponseOrError<Issuer> get(String id) throws IOException {
        HttpGet httpGet = new HttpGet(url(id));
        return requestSingle(httpGet);
    }
}
