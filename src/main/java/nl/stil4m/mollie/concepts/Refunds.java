package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Refund;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static nl.stil4m.mollie.Util.validatePaymentId;

public class Refunds {


    private final String apiKey;
    private final String endpoint;
    private final RequestExecutor requestExecutor;

    public Refunds(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.requestExecutor = requestExecutor;
    }

    public ResponseOrError<Page<Refund>> all(String paymentId, Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        validatePaymentId(paymentId);
        URIBuilder builder = new URIBuilder(endpoint + "/payments/" + paymentId + "/refunds")
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestExecutor.execute(apiKey, httpGet, new TypeReference<Page<Refund>>() {
        });
    }


    public ResponseOrError<Refund> get(String paymentId, String id) throws IOException {
        validatePaymentId(id);
        validatePaymentId(paymentId);
        HttpGet httpGet = new HttpGet(endpoint + "/payments/" + paymentId + "/refunds/" + id);
        return requestExecutor.execute(apiKey, httpGet, new TypeReference<Refund>() {
        });
    }

    public ResponseOrError<Void> cancel(String paymentId, String id) throws IOException {
        validatePaymentId(id);
        validatePaymentId(paymentId);
        HttpDelete httpDelete = new HttpDelete(endpoint + "/payments/" + paymentId + "/refunds/" + id);
        return requestExecutor.execute(apiKey, httpDelete, new TypeReference<Void>() {
        });
    }

    public ResponseOrError<Refund> create(String paymentId, Optional<Double> amount) throws IOException, URISyntaxException {
        validatePaymentId(paymentId);
        URIBuilder builder = new URIBuilder(endpoint + "/payments/" + paymentId + "/refunds");
        if (amount.isPresent()) {
            builder.setParameter("amount", String.valueOf(amount.get()));
        }
        HttpPost httpPost = new HttpPost(builder.build());
        return requestExecutor.execute(apiKey, httpPost, new TypeReference<Refund>() {
        });
    }
}
