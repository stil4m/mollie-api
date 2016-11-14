package nl.stil4m.mollie.concepts;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Page;

public interface Concept<T extends Object> {
    String url(String... elements);
    
    ResponseOrError<Page<T>> requestPage(HttpUriRequest get) throws IOException;
    
    ResponseOrError<T> requestSingle(HttpUriRequest get) throws IOException;
    
    ResponseOrError<Void> requestVoid(HttpUriRequest get) throws IOException;
    
    HttpEntity buildHttpEntity(Object value) throws UnsupportedCharsetException, JsonProcessingException;
}