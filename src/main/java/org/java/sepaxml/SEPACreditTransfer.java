package org.java.sepaxml;

import org.java.sepaxml.xml.XMLNode;
import java.util.Date;
import java.util.List;

public class SEPACreditTransfer extends SEPA {

    public SEPACreditTransfer(SEPABankAccount sender, List<SEPATransaction> transactions) {
        this(sender, transactions, new Date());
    }

    public SEPACreditTransfer(SEPABankAccount sender, List<SEPATransaction> transactions, Date executionDate) {
        super(sender, transactions, executionDate);
        this.build();
    }

    @Override
    protected String getType() {
        return "Db";
    }

    @Override
    protected void addTransactions() {
        for (SEPATransaction transaction : this.transactions) {
            XMLNode nodeCdtTrfTxInf = this.nodePmtInf.append("CdtTrfTxInf");

            XMLNode nodePmtId = nodeCdtTrfTxInf.append("PmtId");
            nodePmtId.append("InstrId").value("NOTPROVIDED");
            nodePmtId.append("EndToEndId").value("NOTPROVIDED");

            // String valueOfBigDec=String.valueOf(transaction.getValue());
            nodeCdtTrfTxInf.append("Amt").
                    append("InstdAmt")
                    .attr("Ccy", transaction.getCurrency().toString())
                    .value(transaction.getValue().toString());
                    //.value(transaction.getValue().doubleValue());

            XMLNode nodeFinInstnId = nodeCdtTrfTxInf.append("CdtrAgt").append("FinInstnId");
            nodeFinInstnId.append("BIC")
                    .value(transaction.getBankAccount().getBIC());
            XMLNode nodePstlAdr = nodeFinInstnId.append("PstlAdr");
            nodePstlAdr.append("Ctry")
                    .value("ES");

            XMLNode nodeCdtr = nodeCdtTrfTxInf.append("Cdtr");
            nodeCdtr.append("Nm")
                    .value(transaction.getBankAccount().getName());
            nodePstlAdr = nodeCdtr.append("PstlAdr");
            nodePstlAdr.append("StrtNm")
                    .value("STREET NAME");
            nodePstlAdr.append("PstCd")
                    .value("POST CODE");
            nodePstlAdr.append("TwnNm")
                    .value("TOWN NAME");
            nodePstlAdr.append("Ctry")
                    .value("ES");

            nodeCdtTrfTxInf.append("CdtrAcct")
                    .append("Id").append("IBAN")
                    .value(transaction.getBankAccount().getIBAN());

            nodeCdtTrfTxInf.append("RmtInf")
                    .append("Ustrd")
                    .value(transaction.getSubject());

            /*if (transaction.getRemittance() != null) {
                nodeCdtTrfTxInf.append("RmtInf")
                        .append("Ustrd")
                        .value(transaction.getRemittance());
            } else {
                nodeCdtTrfTxInf.append("RmtInf")
                        .append("Ustrd")
                        .value(transaction.getSubject());
            }*/
        }
    }

}
