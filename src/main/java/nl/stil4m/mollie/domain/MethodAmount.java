package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MethodAmount {

    private final Double minimum;
    private final Double maximum;

    @JsonCreator
    public MethodAmount(@JsonProperty("minimum") Double minimum,
                        @JsonProperty("maximum") Double maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Double getMinimum() {
        return minimum;
    }

    public Double getMaximum() {
        return maximum;
    }
}
