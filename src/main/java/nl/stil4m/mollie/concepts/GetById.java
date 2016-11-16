package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.ResponseOrError;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public interface GetById<T> extends Concept<T> {

    default ResponseOrError<T> get(String id) throws IOException {
        HttpGet httpGet = new HttpGet(url(id));
        return requestSingle(httpGet);
    }
}