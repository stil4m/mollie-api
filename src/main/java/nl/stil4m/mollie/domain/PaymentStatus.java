package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class PaymentStatus {

    private String id;
    private String profileId;
    private String mode;
    private String createdDatetime;
    private String status;
    private String paidDatetime;
    private String cancelledDatetime;
    private String expiredDatetime;
    private Double amount;
    private String description;
    private String method;
    private Map<String, Object> details;
    private Links links;
    private Map<String, Object> metadata;
    private String expiryPeriod;

    public PaymentStatus(@JsonProperty("id") String id,
                         @JsonProperty("profileId") String profileId,
                         @JsonProperty("mode") String mode,
                         @JsonProperty("createdDatetime") String createdDatetime,
                         @JsonProperty("status") String status,
                         @JsonProperty("paidDatetime") String paidDatetime,
                         @JsonProperty("cancelledDatetime") String cancelledDatetime,
                         @JsonProperty("expiredDatetime") String expiredDatetime,
                         @JsonProperty("amount") Double amount,
                         @JsonProperty("description") String description,
                         @JsonProperty("method") String method,
                         @JsonProperty("details") Map<String, Object> details,
                         @JsonProperty("links") Links links,
                         @JsonProperty("metadata") Map<String, Object> metadata,
                         @JsonProperty("expiryPeriod") String expiryPeriod) {
        this.id = id;
        this.profileId = profileId;
        this.mode = mode;
        this.createdDatetime = createdDatetime;
        this.status = status;
        this.paidDatetime = paidDatetime;
        this.cancelledDatetime = cancelledDatetime;
        this.expiredDatetime = expiredDatetime;
        this.amount = amount;
        this.description = description;
        this.method = method;
        this.details = details;
        this.links = links;
        this.metadata = metadata;
        this.expiryPeriod = expiryPeriod;
    }

    public String getId() {
        return id;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getMode() {
        return mode;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public String getStatus() {
        return status;
    }

    public String getPaidDatetime() {
        return paidDatetime;
    }

    public String getCancelledDatetime() {
        return cancelledDatetime;
    }

    public String getExpiredDatetime() {
        return expiredDatetime;
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

    public Map<String, Object> getDetails() {
        return details;
    }

    public Links getLinks() {
        return links;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public String getExpiryPeriod() {
        return expiryPeriod;
    }

}
