package nl.stil4m.mollie;


import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ClientBuilderTest {

    ClientBuilder builder = new ClientBuilder();


    @Test(expected = IllegalArgumentException.class)
    public void testBuildWithoutApiKey() {
        builder.build();
    }

    @Test
    public void testBuildWithApiKey() {
        Client client = builder.withApiKey("test").build();

        assertNotNull(client);
    }
}
