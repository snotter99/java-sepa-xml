package org.java.sepaxml.examples;

import org.java.sepaxml.SEPA;
import org.java.sepaxml.SEPABankAccount;
import org.java.sepaxml.SEPACreditTransfer;
import org.java.sepaxml.SEPATransaction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExampleCreditTransferMarketPay {
    public static void main(String[] args) {
        final SEPABankAccount sender = new SEPABankAccount(
                "DE89370400440532013000",
                "DEUTDEBBXXX",
                "MARKET PAY"
        );

        final List<SEPATransaction> transactions = new ArrayList<SEPATransaction>() {{
            try {
                Date dt;
                dt = new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-07");

                add(new SEPATransaction(
                        new SEPABankAccount(
                                "ES8600491500052610115791",
                                "BSCHESMMXXX",
                                "C.C. CARREFOUR S.A."
                        ),
                        // new BigDecimal(18544082.14d),
                        BigDecimal.valueOf(18544082.14),
                        "07.12.2020 BRUTO: 18580731,32 COM: 36649,18",
                        dt,
                        SEPATransaction.Currency.EUR)
                );

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }};


        // final SEPA sepa = new SEPACreditTransfer(sender, transactions);
        try {
            final SEPA sepa = new SEPACreditTransfer(sender, transactions, new SimpleDateFormat("yyyy-MM-ddhh:mm:ss").parse("2020-12-0708:21:05"));
            System.out.println("Market Pay");
            sepa.write(System.out);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
