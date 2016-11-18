package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.ResponseOrError;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public interface Create<R, O> extends Concept<R> {

    default ResponseOrError<R> create(O create) throws IOException {
        HttpPost httpPost = new HttpPost(url());
        httpPost.setEntity(buildHttpEntity(create));
        return requestSingle(httpPost);
    }
}