package nl.stil4m.mollie.concepts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import com.fasterxml.jackson.core.type.TypeReference;

import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CreateCustomer;
import nl.stil4m.mollie.domain.Customer;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.UpdateCustomer;

public class Customers extends AbstractConcept<Customer> {
    private static final TypeReference<Page<Customer>> PAGE_TYPE = new TypeReference<Page<Customer>>() {};
    private static final TypeReference<Customer> SINGLE_TYPE = new TypeReference<Customer>() {};

    public Customers(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        super(apiKey,requestExecutor,SINGLE_TYPE,PAGE_TYPE,endpoint,"customers");
    }

    public ResponseOrError<Customer> create(CreateCustomer createCustomer) throws IOException {
        HttpPost httpPost = new HttpPost(url());
        httpPost.setEntity(buildHttpEntity(createCustomer));
        return requestSingle(httpPost);
    }

    public ResponseOrError<Customer> update(String customerId, UpdateCustomer updateCustomer) throws IOException {
        HttpPost httpPost = new HttpPost(url(customerId));
        httpPost.setEntity(buildHttpEntity(updateCustomer));
        return requestSingle(httpPost);
    }

    public ResponseOrError<Page<Customer>> all(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url())
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestPage(httpGet);
    }

    public ResponseOrError<Customer> get(String id) throws IOException {
        HttpGet httpGet = new HttpGet(url(id));
        return requestSingle(httpGet);
    }
}