package nl.stil4m.mollie.domain.subpayments.banktransfer;

import nl.stil4m.mollie.domain.subpayments.base.OptionedCreatePayment;

import java.util.Map;

public class CreateBankTransferPayment extends OptionedCreatePayment<BankTransferOptions> {

    public CreateBankTransferPayment(Double amount, String description, String redirectUrl, Map<String, Object> metadata, BankTransferOptions options) {
        super("banktransfer", amount, description, redirectUrl, metadata, options);
    }

}
