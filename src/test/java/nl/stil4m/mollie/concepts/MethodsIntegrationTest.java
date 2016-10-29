package nl.stil4m.mollie.concepts;

import static nl.stil4m.mollie.TestUtil.TEST_TIMEOUT;
import static nl.stil4m.mollie.TestUtil.VALID_API_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import nl.stil4m.mollie.ClientBuilder;
import nl.stil4m.mollie.ResponseOrError;
import nl.stil4m.mollie.domain.Method;
import nl.stil4m.mollie.domain.Page;

public class MethodsIntegrationTest {
    
    private Methods methods;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        methods = new ClientBuilder().withApiKey(VALID_API_KEY).build().methods();
    }
    
    @Test
    public void testGetMethods() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Page<Method>> allResponse = methods.all(Optional.empty(), Optional.empty());
        
        assertThat(allResponse.getSuccess(), is(true));

        Page<Method> all = allResponse.getData();
        assertThat(all.getCount(), is(10));
        assertThat(all.getOffset(), is(0));
        assertThat(all.getLinks(), is(notNullValue()));

        Set<String> identifiers = all.getData().stream().map(Method::getId).collect(Collectors.toSet());
        assertThat(identifiers.containsAll(Sets.newHashSet("ideal", "creditcard", "paypal")), is(true));
    }

    @Test
    public void testGetMethod() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Method> methodResponse = methods.get("ideal");
        
        assertThat(methodResponse.getSuccess(), is(true));

        Method method = methodResponse.getData();
        assertThat(method.getId(), is("ideal"));
        assertThat(method.getDescription(), is("iDEAL"));
        assertThat(method.getAmount().getMinimum(), is(0.36));
        assertThat(method.getAmount().getMaximum(), is(50000.0));
        assertThat(method.getImage().getNormal(), is("https://www.mollie.com/images/payscreen/methods/ideal.png"));
        assertThat(method.getImage().getBigger(), is("https://www.mollie.com/images/payscreen/methods/ideal@2x.png"));
    }

    @Test
    public void testGetInvalidMethod() throws IOException, InterruptedException {
        ResponseOrError<Method> methodResponse = methods.get("no-such-method");
        
        assertThat(methodResponse.getSuccess(), is(false));
        assertThat(methodResponse.getStatus(), is(404));
        assertThat(methodResponse.getError().keySet(), is(Sets.newHashSet("error")));
    }
}
