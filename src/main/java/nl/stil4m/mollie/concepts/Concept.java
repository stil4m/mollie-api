package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Page;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;

public interface Concept<T> {
    String url(String... elements);

    ResponseOrError<Page<T>> requestPage(HttpUriRequest request) throws IOException;

    ResponseOrError<T> requestSingle(HttpUriRequest request) throws IOException;

    ResponseOrError<Void> requestVoid(HttpUriRequest request) throws IOException;

    HttpEntity buildHttpEntity(Object value) throws UnsupportedCharsetException, JsonProcessingException;
}