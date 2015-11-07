package nl.stil4m.mollie;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.stil4m.mollie.domain.ApiKeyCheck;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;
import nl.stil4m.mollie.domain.ErrorData;
import nl.stil4m.mollie.domain.PaymentStatus;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

import static nl.stil4m.mollie.ResponseOrError.withData;
import static nl.stil4m.mollie.ResponseOrError.withError;

public class DynamicClient {

    private final String endpoint;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public DynamicClient(String endpoint, HttpClient httpClient, ObjectMapper objectMapper) {
        this.endpoint = endpoint;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public ResponseOrError<CreatedPayment> createPayment(String apiKey, CreatePayment createPayment) throws IOException {
        HttpPost httpPost = new HttpPost(endpoint + "/payments");
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(createPayment), ContentType.APPLICATION_JSON));
        return executeRequest(apiKey, httpPost, CreatedPayment.class);
    }

    public ResponseOrError<PaymentStatus> getPaymentStatus(String apiKey, String id) throws IOException {
        HttpGet httpGet = new HttpGet(endpoint + "/payments/" + id);
        return executeRequest(apiKey, httpGet, PaymentStatus.class);
    }

    public ApiKeyCheck checkApiKey(String apiKey) throws IOException {
        int status = getPaymentStatus(apiKey, "unknown").getStatus();
        return new ApiKeyCheck(status == 404);
    }

    private <T> ResponseOrError<T> executeRequest(String apiKey, HttpUriRequest httpRequest, Class<T> type) throws IOException {
        httpRequest.addHeader("Content-Type", "application/json");
        httpRequest.addHeader("Authorization", String.format("Bearer %s", apiKey));
        return httpClient.execute(httpRequest, response -> {
            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status <= 300) {
                return withData(status, DynamicClient.this.deserialize(response, type));
            } else {
                return withError(status, DynamicClient.this.deserialize(response, ErrorData.class));
            }
        });
    }

    private <S> S deserialize(HttpResponse response, Class<S> clazz) throws IOException {
        return objectMapper.readValue(response.getEntity().getContent(), clazz);
    }

}
