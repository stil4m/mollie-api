package nl.stil4m.mollie.domain.subpayments.banktransfer;

public class BankTransferOptions {

    private final String billingEmail;
    private final String dueDate;

    public BankTransferOptions(String billingEmail, String dueDate) {
        this.billingEmail = billingEmail;
        this.dueDate = dueDate;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public String getDueDate() {
        return dueDate;
    }
}
