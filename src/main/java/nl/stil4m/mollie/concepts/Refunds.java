package nl.stil4m.mollie.concepts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import com.fasterxml.jackson.core.type.TypeReference;

import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Refund;

public class Refunds extends AbstractConcept<Refund> {
    private static final TypeReference<Page<Refund>> PAGE_TYPE = new TypeReference<Page<Refund>>() {};
    private static final TypeReference<Refund> SINGLE_TYPE = new TypeReference<Refund>() {};
    
    public Refunds(String apiKey, String endpoint, RequestExecutor requestExecutor, String paymentId) {
        super(apiKey,requestExecutor,SINGLE_TYPE,PAGE_TYPE,endpoint,"payments",paymentId,"refunds");
    }

    public ResponseOrError<Page<Refund>> all(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url())
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestPage(httpGet);
    }

    public ResponseOrError<Refund> get(String id) throws IOException {
        HttpGet httpGet = new HttpGet(url(id));
        return requestSingle(httpGet);
    }

    public ResponseOrError<Void> cancel(String id) throws IOException {
        HttpDelete httpDelete = new HttpDelete(url(id));
        return requestVoid(httpDelete);
    }

    public ResponseOrError<Refund> create(Optional<Double> amount) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url());
        amount.ifPresent(a-> builder.setParameter("amount", String.valueOf(a)));
        HttpPost httpPost = new HttpPost(builder.build());
        return requestSingle(httpPost);
    }
}
