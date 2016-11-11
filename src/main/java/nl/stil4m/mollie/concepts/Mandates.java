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
import nl.stil4m.mollie.domain.CreateMandate;
import nl.stil4m.mollie.domain.Mandate;
import nl.stil4m.mollie.domain.Page;

public class Mandates extends AbstractConcept<Mandate> {
    
    private static final TypeReference<Page<Mandate>> PAGE_TYPE = new TypeReference<Page<Mandate>>() {};
    private static final TypeReference<Mandate> SINGLE_TYPE = new TypeReference<Mandate>() {};

    public Mandates(String apiKey, String endpoint, RequestExecutor requestExecutor, String customerId) {
        super(apiKey,requestExecutor,SINGLE_TYPE,PAGE_TYPE,endpoint,"customers",customerId,"mandates");
    }
    
    /**
     * @see https://www.mollie.com/nl/docs/reference/mandates/create
     * 
     * @param createMandate
     * @return
     * @throws IOException
     */
    public ResponseOrError<Mandate> create(CreateMandate createMandate) throws IOException {
        HttpPost httpPost = new HttpPost(url());
        httpPost.setEntity(buildHttpEntity(createMandate));
        return requestSingle(httpPost);
    }
    
    /**
     * @see https://www.mollie.com/nl/docs/reference/mandates/get
     * 
     * @param id
     * @return
     * @throws IOException
     */
    public ResponseOrError<Mandate> get(String id) throws IOException {
        HttpGet httpGet = new HttpGet(url(id));
        return requestSingle(httpGet);
    }
    
    /**
     * @see https://www.mollie.com/nl/docs/reference/mandates/revoke
     * 
     * @param id
     * @return
     * @throws IOException
     */
    public ResponseOrError<Void> revoke(String id) throws IOException {
        HttpDelete httpDelete = new HttpDelete(url(id));
        return requestVoid(httpDelete);
    }

    /**
     * @see https://www.mollie.com/nl/docs/reference/mandates/list
     * 
     * @param count
     * @param offset
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public ResponseOrError<Page<Mandate>> list(Optional<Integer> count, Optional<Integer> offset) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url())
                .setParameter("count", String.valueOf(count.orElse(10)))
                .setParameter("offset", String.valueOf(offset.orElse(0)));

        HttpGet httpGet = new HttpGet(builder.build());
        return requestPage(httpGet);
    }
}