package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

public class CreatedPayment {

    private final String id;
    private final String mode;
    private final Date createdDatetime;
    private final String status;
    private final String expiryPeriod;
    private final Double amount;
    private final String description;
    private final String method;
    private final Map<String, Object> metadata;
    private final String details;
    private final String profileId;
    private final Links links;

    @JsonCreator
    public CreatedPayment(@JsonProperty("id") String id,
                         @JsonProperty("mode") String mode,
                         @JsonProperty("createdDatetime") Date createdDatetime,
                         @JsonProperty("status") String status,
                         @JsonProperty("expiryPeriod") String expiryPeriod,
                         @JsonProperty("amount") Double amount,
                         @JsonProperty("description") String description,
                         @JsonProperty("method") String method,
                         @JsonProperty("metadata") Map<String,Object> metadata,
                         @JsonProperty("details") String details,
                         @JsonProperty("profileId") String profileId,
                         @JsonProperty("links") Links links) {
        this.id = id;
        this.mode = mode;
        this.createdDatetime = createdDatetime;
        this.status = status;
        this.expiryPeriod = expiryPeriod;
        this.amount = amount;
        this.description = description;
        this.method = method;
        this.metadata = metadata;
        this.details = details;
        this.profileId = profileId;
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public String getStatus() {
        return status;
    }

    public String getExpiryPeriod() {
        return expiryPeriod;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public String getDetails() {
        return details;
    }

    public String getProfileId() {
        return profileId;
    }

    public Links getLinks() {
        return links;
    }
}
