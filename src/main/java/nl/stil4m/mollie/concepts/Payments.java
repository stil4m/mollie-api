package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;

import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static nl.stil4m.mollie.Util.validatePaymentId;

public class Payments {

    private final String apiKey;
    private final String endpoint;
    private final RequestExecutor requestExecutor;

    public Payments(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.requestExecutor = requestExecutor;
    }

    public ResponseOrError<Payment> create(CreatePayment createPayment) throws IOException {
        HttpPost httpPost = new HttpPost(endpoint + "/payments");
        httpPost.setEntity(new StringEntity(requestExecutor.serialize(createPayment), ContentType.APPLICATION_JSON));
        return requestExecutor.execute(apiKey, httpPost, new TypeReference<Payment>() {
        });
    }

    public ResponseOrError<Payment> get(String id) throws IOException {
        validatePaymentId(id);
        HttpGet httpGet = new HttpGet(endpoint + "/payments/" + id);
        return requestExecutor.execute(apiKey, httpGet, new TypeReference<Payment>() {
        });
    }

    public ResponseOrError<Page<Payment>> list(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(endpoint + "/payments")
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestExecutor.execute(apiKey, httpGet, new TypeReference<Page<Payment>>() {
        });
    }

    public ResponseOrError<Page<Payment>> next(Page<Payment> page) throws IOException {
        if (!page.getLinks().getNext().isPresent()) {
            throw new IllegalArgumentException("Page does not have next");
        }
        HttpGet httpGet = new HttpGet(page.getLinks().getNext().get());
        return requestExecutor.execute(apiKey, httpGet, new TypeReference<Page<Payment>>() {
        });
    }

    public ResponseOrError<Page<Payment>> previous(Page<Payment> page) throws IOException {
        if (!page.getLinks().getPrevious().isPresent()) {
            throw new IllegalArgumentException("Page does not have next");
        }
        HttpGet httpGet = new HttpGet(page.getLinks().getPrevious().get());
        return requestExecutor.execute(apiKey, httpGet, new TypeReference<Page<Payment>>() {
        });
    }
}
