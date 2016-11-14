package nl.stil4m.mollie.concepts;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;

import nl.stil4m.mollie.ResponseOrError;

public interface Create<R extends Object,O extends Object> extends Concept<R> {
    
    default ResponseOrError<R> create(O create) throws IOException {
        HttpPost httpPost = new HttpPost(url());
        httpPost.setEntity(buildHttpEntity(create));
        return requestSingle(httpPost);
    }
}