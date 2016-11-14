package nl.stil4m.mollie.concepts;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;

import nl.stil4m.mollie.ResponseOrError;

public interface Update<R extends Object,O extends Object> extends Concept<R> {
    
    default ResponseOrError<R> update(String id, O update) throws IOException {
        HttpPost httpPost = new HttpPost(url(id));
        httpPost.setEntity(buildHttpEntity(update));
        return requestSingle(httpPost);
    }
}