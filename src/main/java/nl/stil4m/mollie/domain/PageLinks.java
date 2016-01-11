package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class PageLinks {

    private final Optional<String> previous;
    private final Optional<String> next;
    private final Optional<String> first;
    private final Optional<String> last;

    @JsonCreator
    public PageLinks(@JsonProperty("last") String last,
                     @JsonProperty("first") String first,
                     @JsonProperty("next") String next,
                     @JsonProperty("previous") String previous) {
        this.last = Optional.ofNullable(last);
        this.first = Optional.ofNullable(first);
        this.next = Optional.ofNullable(next);
        this.previous = Optional.ofNullable(previous);
    }

    public Optional<String> getPrevious() {
        return previous;
    }

    public Optional<String> getNext() {
        return next;
    }

    public Optional<String> getFirst() {
        return first;
    }

    public Optional<String> getLast() {
        return last;
    }
}
