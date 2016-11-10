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
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.Page;
import nl.stil4m.mollie.domain.Payment;

public class Payments extends AbstractConcept<Payment> {
    private static final TypeReference<Page<Payment>> PAGE_TYPE = new TypeReference<Page<Payment>>() {};
    private static final TypeReference<Payment> SINGLE_TYPE = new TypeReference<Payment>() {};
    
    public Payments(String apiKey, String endpoint, RequestExecutor requestExecutor) {
        super(apiKey,requestExecutor,SINGLE_TYPE,PAGE_TYPE,endpoint,"payments");
    }

    public ResponseOrError<Payment> create(CreatePayment createPayment) throws IOException {
        HttpPost httpPost = new HttpPost(url());
        httpPost.setEntity(buildHttpEntity(createPayment));
        return requestSingle(httpPost);
    }

    public ResponseOrError<Payment> get(String id) throws IOException {
        HttpGet httpGet = new HttpGet(url(id));
        return requestSingle(httpGet);
    }

    public ResponseOrError<Page<Payment>> list(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url())
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestPage(httpGet);
    }

    public ResponseOrError<Page<Payment>> next(Page<Payment> page) throws IOException {
        if (!page.getLinks().getNext().isPresent()) {
            throw new IllegalArgumentException("Page does not have next");
        }
        HttpGet httpGet = new HttpGet(page.getLinks().getNext().get());
        return requestPage(httpGet);
    }

    public ResponseOrError<Page<Payment>> previous(Page<Payment> page) throws IOException {
        if (!page.getLinks().getPrevious().isPresent()) {
            throw new IllegalArgumentException("Page does not have next");
        }
        HttpGet httpGet = new HttpGet(page.getLinks().getPrevious().get());
        return requestPage(httpGet);
    }
}