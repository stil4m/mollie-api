package nl.stil4m.mollie.concepts;

import java.io.IOException;

import org.apache.http.client.methods.HttpDelete;

import nl.stil4m.mollie.ResponseOrError;

public interface Delete<T extends Object> extends Concept<T> {

    default ResponseOrError<Void> delete(String id) throws IOException {
        HttpDelete httpDelete = new HttpDelete(url(id));
        return requestVoid(httpDelete);
    }
}
