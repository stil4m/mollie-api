package nl.stil4m.mollie.concepts;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractConceptTest {

    @Test
    public void testConstructor() {
        assertEquals("https://api.mollie.com/v1",new ConceptStub("https://api.mollie.com/v1").url());
        assertEquals("https://api.mollie.com/v1/api",new ConceptStub("https://api.mollie.com/v1","api").url());
    }
    
    @Test(expected=NullPointerException.class)
    public void testConstructorNullUrl() {
        new ConceptStub((String)null);
    }
    
    @Test(expected=NullPointerException.class)
    public void testConstructorNullArrayUrl() {
        new ConceptStub((String[])null);
    }
    
    @Test(expected=NullPointerException.class)
    public void testConstructorEmptyUrl() {
        new ConceptStub("");
    }
    
    @Test(expected=NullPointerException.class)
    public void testConstructorBlankUrl() {
        new ConceptStub(" ");
    }
    
    @Test(expected=NullPointerException.class)
    public void testConstructorEmptyArray() {
        new ConceptStub(new String[]{});
    }
    
    @Test
    public void testUrl() {
        AbstractConcept<Boolean> sut = new ConceptStub("https://api.mollie.com/v1/api","customers");
        assertEquals("https://api.mollie.com/v1/api/customers",sut.url());
        assertEquals("https://api.mollie.com/v1/api/customers/someId",sut.url("someId"));
    }
    
    @Test(expected=NullPointerException.class)
    public void testUrlBlankElement() {
        new ConceptStub("https://api.mollie.com/v1").url(" ");
    }
    
    @Test(expected=NullPointerException.class)
    public void testUrlNullElement() {
        new ConceptStub("https://api.mollie.com/v1").url((String)null);
    }
    
    private class ConceptStub extends AbstractConcept<Boolean> {

        protected ConceptStub(String... url) {
            super(null, null,null,null,url);
        }
    }
}