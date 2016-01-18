package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefundLinks {

    private final String self;

    @JsonCreator
    public RefundLinks(@JsonProperty("self") String self) {
        this.self = self;
    }

    public String getSelf() {
        return self;
    }
}
