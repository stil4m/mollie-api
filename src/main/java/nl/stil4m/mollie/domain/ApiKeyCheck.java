package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiKeyCheck {

    private final Boolean valid;

    @JsonCreator
    public ApiKeyCheck(@JsonProperty Boolean valid) {
        this.valid = valid;
    }

    public Boolean getValid() {
        return valid;
    }
}
