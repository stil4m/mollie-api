package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;


@JsonInclude(value = JsonInclude.Include.NON_ABSENT, content = JsonInclude.Include.NON_EMPTY)
public class UpdateCustomer {


    private final Optional<String> name;
    private final Optional<String> email;
    private final Optional<String> locale;
    private final Map<String, Object> metadata;

    public UpdateCustomer(@JsonProperty("name") Optional<String> name,
                          @JsonProperty("email") Optional<String> email,
                          @JsonProperty("locale") Optional<String> locale,
                          @JsonProperty("metadata") Map<String, Object> metadata) {
        this.name = name;
        this.email = email;
        this.locale = locale;
        this.metadata = metadata;
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getLocale() {
        return locale;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

}
