package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public class CreateCustomer {

    private final String name;
    private final String email;
    private final Optional<String> locale;
    private final Map<String, Object> metadata;

    public CreateCustomer(@JsonProperty("name") String name,
                          @JsonProperty("email") String email,
                          @JsonProperty("locale") Optional<String> locale,
                          @JsonProperty("metadata") Map<String, Object> metadata) {
        this.name = name;
        this.email = email;
        this.locale = locale;
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Optional<String> getLocale() {
        return locale;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
