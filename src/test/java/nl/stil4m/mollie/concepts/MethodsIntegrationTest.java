package nl.stil4m.mollie.concepts;

import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Method;
import nl.stil4m.mollie.domain.Page;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static nl.stil4m.mollie.TestUtil.strictClientWithApiKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class MethodsIntegrationTest {

    private Methods methods;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        methods = strictClientWithApiKey(VALID_API_KEY).methods();
    }

    @Test
    public void testGetMethods() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Page<Method>> allResponse = methods.all(Optional.empty(), Optional.empty());

        assertThat(allResponse.getSuccess(), is(true));

        Page<Method> all = allResponse.getData();
        assertThat(all.getLinks(), is(notNullValue()));
        assertThat(all.getData(), is(notNullValue()));

        Set<String> identifiers = all.getData().stream().map(Method::getId).collect(Collectors.toSet());
        assertThat(identifiers, hasItem("ideal"));
        //assertThat(identifiers, hasItem("creditcard"));
    }

    @Test
    public void testGetMethod() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Method> methodResponse = methods.get("ideal");

        assertThat(methodResponse.getSuccess(), is(true));

        Method method = methodResponse.getData();
        assertThat(method.getId(), is("ideal"));
        assertThat(method.getDescription(), is("iDEAL"));
        assertThat(method.getAmount().getMinimum(), is(0.01));
        assertThat(method.getAmount().getMaximum(), is(50000.0));
        assertThat(method.getImage().getNormal(), startsWith("https://www.mollie.com/images/payscreen/methods/ideal"));
        assertThat(method.getImage().getBigger(), startsWith("https://www.mollie.com/images/payscreen/methods/ideal"));
    }

    @Test
    public void testGetInvalidMethod() throws IOException, InterruptedException {
        ResponseOrError<Method> methodResponse = methods.get("no-such-method");

        assertThat(methodResponse.getSuccess(), is(false));
        assertThat(methodResponse.getStatus(), is(404));
        assertThat((Map<String, ?>) methodResponse.getError(), hasKey("error"));
    }
}
