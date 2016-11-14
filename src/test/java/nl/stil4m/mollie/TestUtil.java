package nl.stil4m.mollie;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class TestUtil {

    public static final String VALID_API_KEY = "test_35Rjpa5ETePvpeQVU2JQEwzuNyq8BA";

    public static final Long TEST_TIMEOUT = 2000L;

    public static final String TEST_ISSUER = "ideal_TESTNL99";

    public static void assertWithin(Date before, Date target, Date after, Long additionalSpan) {
        long beforeTime = before.getTime() - (before.getTime() % 1000) - additionalSpan;
        long afterTime = after.getTime() - (after.getTime() % 1000) + additionalSpan;

        assertThat(new Date(beforeTime), lessThanOrEqualTo(target));
        assertThat(target, lessThanOrEqualTo(new Date(afterTime)));
    }

    public static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }

    public static Client strictClientWithApiKey(String apiKey) {
        return new ClientBuilder()
                .withApiKey(apiKey)
                .withMapper(objectMapper())
                .build();
    }

    public static DynamicClient strictDynamicClientWithApiKey() {
        return new DynamicClientBuilder()
                .withMapper(objectMapper())
                .build();
    }
}
