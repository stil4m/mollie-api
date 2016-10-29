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
import nl.stil4m.mollie.domain.Issuer;
import nl.stil4m.mollie.domain.Page;

public class IssuersIntegrationTest {

    private Issuers issuers;

    @Before
    public void before() throws InterruptedException {
        Thread.sleep(TEST_TIMEOUT);
        issuers = new ClientBuilder().withApiKey(VALID_API_KEY).build().issuers();
    }

    @Test
    public void testGetIssuers() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Page<Issuer>> allResponse = issuers.all(Optional.empty(), Optional.empty());
        
        assertThat(allResponse.getSuccess(), is(true));

        Page<Issuer> all = allResponse.getData();
        assertThat(all.getCount(), is(1));
        assertThat(all.getTotalCount(), is(1));
        assertThat(all.getOffset(), is(0));
        assertThat(all.getLinks(), is(notNullValue()));
        assertThat(all.getLinks().getPrevious().isPresent(), is(false));
        assertThat(all.getLinks().getNext().isPresent(), is(false));

        Set<String> identifiers = all.getData().stream().map(Issuer::getId).collect(Collectors.toSet());
        assertThat(identifiers.containsAll(Sets.newHashSet("ideal_TESTNL99")), is(true));
    }

    @Test
    public void testGetIssuer() throws IOException, URISyntaxException, InterruptedException {
        ResponseOrError<Issuer> allResponse = issuers.get("ideal_TESTNL99");
        
        assertThat(allResponse.getSuccess(), is(true));

        Issuer issuer = allResponse.getData();
        assertThat(issuer.getResource(), is("issuer"));
        assertThat(issuer.getId(), is("ideal_TESTNL99"));
        assertThat(issuer.getName(), is("TBM Bank"));
        assertThat(issuer.getMethod(), is("ideal"));
    }
}