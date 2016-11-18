package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.ResponseOrError;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public interface Update<R, O> extends Concept<R> {

    default ResponseOrError<R> update(String id, O update) throws IOException {
        HttpPost httpPost = new HttpPost(url(id));
        httpPost.setEntity(buildHttpEntity(update));
        return requestSingle(httpPost);
    }
}