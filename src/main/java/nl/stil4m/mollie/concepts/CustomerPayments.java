package nl.stil4m.mollie.concepts;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.CustomerPayment;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;

public class CustomerPayments extends AbstractConcept<Payment> {
    
    public CustomerPayments(String apiKey, String endpoint, RequestExecutor requestExecutor, String customerId) {
        super(apiKey,requestExecutor,endpoint,"customers",customerId,"payments");
    }

    public ResponseOrError<Page<Payment>> all(Optional<Object> count, Optional<Object> offset) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(url())
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestPage(httpGet);
    }

    public ResponseOrError<Payment> create(CustomerPayment customerPayment) throws IOException {
        HttpPost httpPost = new HttpPost(url());
        httpPost.setEntity(buildHttpEntity(customerPayment));
        return requestSingle(httpPost);
    }
}