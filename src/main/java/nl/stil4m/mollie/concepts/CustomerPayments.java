package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CustomerPayment;
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

public class CustomerPayments {

    private static final TypeReference<Page<Payment>> PAGE_PAYMENT_TYPE = new TypeReference<Page<Payment>>() {
    };

    private static final TypeReference<Payment> PAYMENT_TYPE = new TypeReference<Payment>() {
    };


    private final String apiKey;
    private final String endpoint;
    private final RequestExecutor requestExecutor;
    private final String customerId;

    public CustomerPayments(String apiKey, String endpoint, RequestExecutor requestExecutor, String customerId) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.requestExecutor = requestExecutor;
        this.customerId = customerId;
    }


    public ResponseOrError<Page<Payment>> all(Optional<Object> count, Optional<Object> offset) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(endpoint + "/customers/" + customerId + "/payments")
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestExecutor.execute(apiKey, httpGet, PAGE_PAYMENT_TYPE);
    }

    public ResponseOrError<Payment> create(CustomerPayment customerPayment) throws IOException {
        HttpPost httpPost = new HttpPost(endpoint + "/customers/" + customerId + "/payments");
        httpPost.setEntity(new StringEntity(requestExecutor.serialize(customerPayment), ContentType.APPLICATION_JSON));
        return requestExecutor.execute(apiKey, httpPost, PAYMENT_TYPE);
    }
}
