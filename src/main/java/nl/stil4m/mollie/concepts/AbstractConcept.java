package nl.stil4m.mollie.concepts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import nl.stil4m.mollie.RequestExecutor;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Page;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractConcept<T> implements Concept<T> {
    private static final TypeReference<Void> VOID_TYPE_REFERENCE = new TypeReference<Void>() {
    };

    private final TypeReference<Page<T>> pageTypeReference;
    private final TypeReference<T> singleTypeReference;
    private final String apiKey;
    private final String endpoint;
    private final RequestExecutor requestExecutor;

    protected AbstractConcept(String apiKey, RequestExecutor requestExecutor, TypeReference<T> singleTypeReference, TypeReference<Page<T>> pageTypeReference, String... endPoint) {
        this.apiKey = apiKey;
        this.endpoint = joinUrl(endPoint);
        this.requestExecutor = requestExecutor;
        this.singleTypeReference = singleTypeReference;
        this.pageTypeReference = pageTypeReference;
    }

    private static String joinUrl(String... elements) {
        return checkAndTrimToNull(Stream.of(elements)
                .map(AbstractConcept::checkAndTrimToNull)
                .collect(Collectors.joining("/")));
    }

    private static String checkAndTrimToNull(String value) {
    	String result = value != null && !value.trim().isEmpty() ? value.trim() : null;
    	if(result==null) {
    		throw new IllegalArgumentException("URL cannot contain null or blank elements");
    	}
        return result;
    }

    @Override
    public String url(String... elements) {
        if (elements == null || elements.length == 0) {
            return this.endpoint;
        }
        return joinUrl(this.endpoint, joinUrl(elements));
    }

    @Override
    public ResponseOrError<Page<T>> requestPage(HttpUriRequest httpRequest) throws IOException {
        return requestExecutor.execute(apiKey, httpRequest, pageTypeReference);
    }

    @Override
    public ResponseOrError<T> requestSingle(HttpUriRequest httpRequest) throws IOException {
        return requestExecutor.execute(apiKey, httpRequest, singleTypeReference);
    }

    @Override
    public ResponseOrError<Void> requestVoid(HttpUriRequest httpRequest) throws IOException {
        return requestExecutor.execute(apiKey, httpRequest, VOID_TYPE_REFERENCE);
    }

    @Override
    public HttpEntity buildHttpEntity(Object value) throws UnsupportedCharsetException, JsonProcessingException {
        return new StringEntity(requestExecutor.serialize(value), ContentType.APPLICATION_JSON);
    }
}