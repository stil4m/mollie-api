package nl.stil4m.mollie.concepts;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;

import nl.stil4m.mollie.ResponseOrError;

public interface GetById<T extends Object> extends Concept<T> {
    
    default ResponseOrError<T> get(String id) throws IOException {
        HttpGet httpGet = new HttpGet(url(id));
        return requestSingle(httpGet);
    }
}