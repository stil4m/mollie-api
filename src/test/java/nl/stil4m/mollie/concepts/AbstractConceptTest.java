package nl.stil4m.mollie.concepts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractConceptTest {

    @Test
    public void testConstructor() {
        assertEquals("https://api.mollie.com/v1", new ConceptStub("https://api.mollie.com/v1").url());
        assertEquals("https://api.mollie.com/v1/api", new ConceptStub("https://api.mollie.com/v1", "api").url());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOmitUrl() {
        new ConceptStub();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyUrl() {
        new ConceptStub("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorBlankUrl() {
        new ConceptStub(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyArray() {
        new ConceptStub(new String[]{});
    }

    @Test
    public void testUrl() {
        AbstractConcept<Boolean> sut = new ConceptStub("https://api.mollie.com/v1/api", "customers");
        assertEquals("https://api.mollie.com/v1/api/customers", sut.url());
        assertEquals("https://api.mollie.com/v1/api/customers/someId", sut.url("someId"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUrlBlankElement() {
        new ConceptStub("https://api.mollie.com/v1").url(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUrlNullElement() {
        new ConceptStub("https://api.mollie.com/v1").url((String) null);
    }

    private class ConceptStub extends AbstractConcept<Boolean> {

        protected ConceptStub(String... url) {
            super(null, null, null, null, url);
        }
    }
}