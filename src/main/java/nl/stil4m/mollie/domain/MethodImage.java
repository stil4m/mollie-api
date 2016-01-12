package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MethodImage {

    private final String normal;
    private final String bigger;

    @JsonCreator
    public MethodImage(@JsonProperty("normal") String normal,
                       @JsonProperty("bigger") String bigger) {
        this.normal = normal;
        this.bigger = bigger;
    }

    public String getNormal() {
        return normal;
    }

    public String getBigger() {
        return bigger;
    }
}
