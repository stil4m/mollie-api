package nl.stil4m.mollie.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Optional;

public class Payment {

    private final String resource;
    private final String id;
    private final String profileId;
    private final String mode;
    private final String customerId;
    private final String recurringType;
    private final String createdDatetime;
    private final String status;
    private final String paidDatetime;
    private final String cancelledDatetime;
    private final String expiredDatetime;
    private final Double amount;
    private final Double amountRefunded;
    private final Double amountRemaining;
    private final String description;
    private final String method;
    private final Map<String, Object> details;
    private final Links links;
    private final Map<String, Object> metadata;
    private final String locale;
    private final String expiryPeriod;
    private final Optional<String> mandateId;

    public Payment(@JsonProperty("resource") String resource,
                   @JsonProperty("id") String id,
                   @JsonProperty("profileId") String profileId,
                   @JsonProperty("mode") String mode,
                   @JsonProperty("customerId") String customerId,
                   @JsonProperty("recurringType") String recurringType,
                   @JsonProperty("createdDatetime") String createdDatetime,
                   @JsonProperty("status") String status,
                   @JsonProperty("paidDatetime") String paidDatetime,
                   @JsonProperty("cancelledDatetime") String cancelledDatetime,
                   @JsonProperty("expiredDatetime") String expiredDatetime,
                   @JsonProperty("amount") Double amount,
                   @JsonProperty("amountRefunded") Double amountRefunded,
                   @JsonProperty("amountRemaining") Double amountRemaining,
                   @JsonProperty("description") String description,
                   @JsonProperty("method") String method,
                   @JsonProperty("details") Map<String, Object> details,
                   @JsonProperty("links") Links links,
                   @JsonProperty("metadata") Map<String, Object> metadata,
                   @JsonProperty("locale") String locale,
                   @JsonProperty("expiryPeriod") String expiryPeriod,
                   @JsonProperty("mandateId") Optional<String> mandateId) {
        this.resource = resource;
        this.id = id;
        this.profileId = profileId;
        this.mode = mode;
        this.customerId = customerId;
        this.recurringType = recurringType;
        this.createdDatetime = createdDatetime;
        this.status = status;
        this.paidDatetime = paidDatetime;
        this.cancelledDatetime = cancelledDatetime;
        this.expiredDatetime = expiredDatetime;
        this.amount = amount;
        this.amountRefunded = amountRefunded;
        this.amountRemaining = amountRemaining;
        this.description = description;
        this.method = method;
        this.details = details;
        this.links = links;
        this.metadata = metadata;
        this.locale = locale;
        this.expiryPeriod = expiryPeriod;
        this.mandateId = mandateId;
    }

    public String getResource() {
        return resource;
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

    public String getCustomerId() {
        return customerId;
    }

    public String getRecurringType() {
        return recurringType;
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

    public Double getAmountRefunded() {
        return amountRefunded;
    }

    public Double getAmountRemaining() {
        return amountRemaining;
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

    public String getLocale() {
        return locale;
    }

    public String getExpiryPeriod() {
        return expiryPeriod;
    }

    public Optional<String> getMandateId() {
        return mandateId;
    }
}