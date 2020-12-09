package org.java.sepaxml.examples;

import org.java.sepaxml.SEPA;
import org.java.sepaxml.SEPABankAccount;
import org.java.sepaxml.SEPACreditTransfer;
import org.java.sepaxml.SEPATransaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExampleCreditTransferMarketPay {
    public static void main(String[] args) {
        final SEPABankAccount sender = new SEPABankAccount(
                "DE89370400440532013000",
                "DEUTDEBBXXX",
                "Joe Doe"
        );


        final List<SEPATransaction> transactions = new ArrayList<SEPATransaction>() {{
            add(new SEPATransaction(
                            new SEPABankAccount(
                                    "ES8600491500052610115791",
                                    "BSCHESMMXXX",
                                    "C.C. CARREFOUR S.A."
                            ),
                            // new BigDecimal(18544082.14d),
                            BigDecimal.valueOf(18544082.14),
                            "07.12.2020 BRUTO: 18580731,32 COM: 36649,18",
                            SEPATransaction.Currency.EUR
                    )
            );
        }};

        final SEPA sepa = new SEPACreditTransfer(sender, transactions);
        System.out.println("Market Pay");
        sepa.write(System.out);


    }
}
