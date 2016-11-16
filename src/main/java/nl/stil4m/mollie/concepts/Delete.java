package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.ResponseOrError;
import org.apache.http.client.methods.HttpDelete;

import java.io.IOException;

public interface Delete<T> extends Concept<T> {

    default ResponseOrError<Void> delete(String id) throws IOException {
        HttpDelete httpDelete = new HttpDelete(url(id));
        return requestVoid(httpDelete);
    }
}
