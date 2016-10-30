package nl.stil4m.mollie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import nl.stil4m.mollie.mocks.DummyMethod;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import java.io.IOException;

import static junit.framework.TestCase.fail;
import static nl.stil4m.mollie.TestUtil.strictDynamicClientWithApiKey;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultObjectMappingTest {

    @Test
    public void testShouldNotFailByDefaultOnAdditionalFields() throws IOException {
        DynamicClient client = new DynamicClientBuilder().build();
        RequestExecutor requestExecutor = (RequestExecutor) Whitebox.getInternalState(client, "requestExecutor");
        ObjectMapper objectMapper = (ObjectMapper) Whitebox.getInternalState(requestExecutor, "objectMapper");

        objectMapper.readValue("{\"id\":\"x\", \"name\":\"Foo\"}", DummyMethod.class);
    }

    @Test
    public void testShouldFailForStrictObjectMapperOnAdditionalFields() throws IOException {
        DynamicClient client = strictDynamicClientWithApiKey();
        RequestExecutor requestExecutor = (RequestExecutor) Whitebox.getInternalState(client, "requestExecutor");
        ObjectMapper objectMapper = (ObjectMapper) Whitebox.getInternalState(requestExecutor, "objectMapper");

        try {
            objectMapper.readValue("{\"id\":\"x\", \"name\":\"Foo\"}", DummyMethod.class);
            fail();
        } catch (UnrecognizedPropertyException e) {
            assertThat(e.getPropertyName(), is("name"));
        }
    }

}
