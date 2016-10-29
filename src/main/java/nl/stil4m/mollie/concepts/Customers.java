package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreateCustomer;
import nl.stil4m.mollie.domain.Customer;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.UpdateCustomer;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static nl.stil4m.mollie.Util.validatePaymentId;

public class Customers {


    private static final TypeReference<Page<Customer>> PAGE_CUSTOMER_TYPE = new TypeReference<Page<Customer>>() {
    };
    private static final TypeReference<Customer> CUSTOMER_TYPE = new TypeReference<Customer>() {
    };

    private final String apiKey;
    private final String endpoint;
    private final RequestExecutor requestExecutor;

    public Customers(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.requestExecutor = requestExecutor;
    }

    public ResponseOrError<Customer> create(CreateCustomer createCustomer) throws IOException {
        HttpPost httpPost = new HttpPost(endpoint + "/customers");
        httpPost.setEntity(new StringEntity(requestExecutor.serialize(createCustomer), ContentType.APPLICATION_JSON));
        return requestExecutor.execute(apiKey, httpPost, new TypeReference<Customer>() {
        });
    }

    public ResponseOrError<Customer> update(String customerId, UpdateCustomer updateCustomer) throws IOException {
        HttpPost httpPost = new HttpPost(endpoint + "/customers/" + customerId);
        httpPost.setEntity(new StringEntity(requestExecutor.serialize(updateCustomer), ContentType.APPLICATION_JSON));
        return requestExecutor.execute(apiKey, httpPost, new TypeReference<Customer>() {
        });
    }

    public ResponseOrError<Page<Customer>> all(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(endpoint + "/customers")
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestExecutor.execute(apiKey, httpGet, PAGE_CUSTOMER_TYPE);
    }

    public ResponseOrError<Customer> get(String id) throws IOException {
        validatePaymentId(id);
        HttpGet httpGet = new HttpGet(endpoint + "/customers/" + id);
        return requestExecutor.execute(apiKey, httpGet, CUSTOMER_TYPE);
    }

}
