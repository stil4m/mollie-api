package nl.stil4m.mollie;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.stil4m.mollie.domain.ApiKeyCheck;
import nl.stil4m.mollie.domain.CreatePayment;
import nl.stil4m.mollie.domain.CreatedPayment;

import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.Map;

public class DynamicClient extends BaseClient {

    private final String endpoint;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public DynamicClient(String endpoint, HttpClient httpClient, ObjectMapper objectMapper) {
        this.endpoint = endpoint;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public CreatedPayment createPayment(String apiKey, CreatePayment createPayment) throws IOException {
        HttpPost httpPost = new HttpPost(endpoint + "/payments");
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(createPayment), ContentType.APPLICATION_JSON));

        Response<Map> mapResponse = executeRequest(apiKey, httpPost, Map.class);
        System.out.println(mapResponse.payload);
        System.out.println(mapResponse.status);
        return null;
    }

    public ApiKeyCheck checkApiKey(String apiKey) throws IOException {
        HttpGet httpGet = new HttpGet(endpoint + "/payments/unknown");

        Response<Map> map = executeRequest(apiKey, httpGet, Map.class);
        return new ApiKeyCheck(map.status == 404);
    }

    private <T,V> ResponseOrError<T,V> executeRequest(String apiKey, HttpUriRequest httpRequest, Class<T> type, Class<T> errorType) throws IOException {
        httpRequest.addHeader("Content-Type", "application/json");
        httpRequest.addHeader("Authorization", String.format("Bearer %s", apiKey));
        return httpClient.execute(httpRequest, response -> {

            return new Response<>(
                    response.getStatusLine().getStatusCode(),
                    objectMapper.readValue(response.getEntity().getContent(), type)
            );
        });
    }

    private <T> Response<T> executeRequest(String apiKey, HttpUriRequest httpRequest, Class<T> type) throws IOException {
        httpRequest.addHeader("Content-Type", "application/json");
        httpRequest.addHeader("Authorization", String.format("Bearer %s", apiKey));
        return httpClient.execute(httpRequest, response -> {

            return new Response<>(
                    response.getStatusLine().getStatusCode(),
                    objectMapper.readValue(response.getEntity().getContent(), type)
            );
        });
    }

}
