package nl.stil4m.mollie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.util.Map;

import static nl.stil4m.mollie.ResponseOrError.withData;
import static nl.stil4m.mollie.ResponseOrError.withError;

public class RequestExecutor {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RequestExecutor(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public <T> ResponseOrError<T> execute(String apiKey, HttpUriRequest httpRequest, TypeReference<T> type) throws IOException {
        httpRequest.addHeader("Content-Type", "application/json");
        httpRequest.addHeader("Authorization", String.format("Bearer %s", apiKey));
        return httpClient.execute(httpRequest, (ResponseHandler<ResponseOrError<T>>) response -> {
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status <= 300) {
                return withData(status, RequestExecutor.this.deserialize(response, type));
            } else {
                return withError(status, RequestExecutor.this.deserialize(response, new TypeReference<Map>() {
                }));
            }
        });
    }

    private <S> S deserialize(HttpResponse response, TypeReference<S> clazz) throws IOException {
        return objectMapper.readValue(response.getEntity().getContent(), clazz);
    }

    public String serialize(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}
