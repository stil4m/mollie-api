package nl.stil4m.mollie;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class TestUtil {

    public static final String VALID_API_KEY = "test_35Rjpa5ETePvpeQVU2JQEwzuNyq8BA";

    public static final Long TEST_TIMEOUT = 2000L;
    
    public static void assertWithin(Date before, Date target, Date after, Long additionalSpan) {
        long beforeTime = before.getTime() - (before.getTime() % 1000) - additionalSpan;
        long afterTime = after.getTime() - (after.getTime() % 1000) + additionalSpan;
        
        assertTrue(target+" expected to be after "+new Date(beforeTime),beforeTime <= target.getTime());
        assertTrue(target+" expected to be before "+new Date(afterTime),target.getTime() <= afterTime);
    }
}
