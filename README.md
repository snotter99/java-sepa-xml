[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# Simple Java SEPA XML Generator


The Single Euro Payments Area (SEPA) is a payment-integration initiative of the European Union for simplification of bank transfers denominated in euro. As of 2018, SEPA consists of the 28 member states of the European Union, the four member states of the European Free Trade Association (Iceland, Liechtenstein, Norway and Switzerland), Andorra, Monaco and San Marino.

This Java library proviedes SEPA XML files to automate direct debit and credit transfer payment processes. For direct debit transactions you need a creaditor identifier the central bank of your countty is providing to you. The following standarts are supported: pain.001.002.03.xsd pain.008.002.02.xsd

## How to use?

#### SEPA Direct Debit

```java

import org.simple.sepa.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExampleDirectDebit {
    public static void main(String[] args) {
        final String creditorID = "DE98ZZZ09999999999";

        final SEPABankAccount reciver = new SEPABankAccount(
                "DE89370400440532013000",
                "DEUTDEBBXXX",
                "Joe Doe"
        );

        final List<SEPATransaction> transactions = new ArrayList<SEPATransaction>() {{
            add(new SEPATransaction(
                            new SEPABankAccount(
                                    "DE05500105173195282731",
                                    "VBRSDE33347",
                                    "Peter Parker"
                            ),
                            10.20d,
                            "Invoice 4711564",
                            new Date(),
                            "MYCOMP11111111",
                            new Date(),
                            SEPATransaction.Currency.EUR
                    )
            );

            add(new SEPATransaction(
                            new SEPABankAccount(
                                    "DK5250511963137134",
                                    "UINVDEFFXXX",
                                    "Carl White"
                            ),
                            1000.00d,
                            "Invoice 789765",
                            new Date(),
                            "MYCOMP22222222",
                            new Date(),
                            SEPATransaction.Currency.EUR
                    )
            );

            add(new SEPATransaction(
                            new SEPABankAccount(
                                    "CZ7627005991764514418145",
                                    "SWBSDESSXXX",
                                    "Frank Black"
                            ),
                            50.00d,
                            "Invoice 7856",
                            new Date(),
                            "MYCOMP11111111",
                            new Date(),
                            SEPATransaction.Currency.EUR
                    )
            );
        }};

        final SEPA sepa = new SEPADirectDebit(reciver, transactions, creditorID);
        System.out.println(sepa.toString());
    }
}
```

#### SEPA Credit Transfer

```java
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


```


#### XML File Result for SEPA Direct Debit

```xml
      
<?xml version="1.0" encoding="UTF-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.002.02" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.008.002.02 pain.008.002.02.xsd">
   <CstmrDrctDbtInitn>
      <GrpHdr>
         <MsgId>DEUTDEBBXXX0020181403121442</MsgId>
         <CreDtTm>2018-07-03T12:14:42.000Z</CreDtTm>
         <NbOfTxs>3</NbOfTxs>
         <InitgPty>
            <Nm>Joe Doe</Nm>
         </InitgPty>
      </GrpHdr>
      <PmtInf>
         <PmtInfId>PMT-ID0-20181403121442</PmtInfId>
         <PmtMtd>DD</PmtMtd>
         <BtchBookg>true</BtchBookg>
         <NbOfTxs>3</NbOfTxs>
         <CtrlSum>1060.2</CtrlSum>
         <PmtTpInf>
            <SvcLvl>
               <Cd>SEPA</Cd>
            </SvcLvl>
            <LclInstrm>
               <Cd>CORE</Cd>
            </LclInstrm>
            <SeqTp>
               <Cd>FRST</Cd>
            </SeqTp>
         </PmtTpInf>
         <ReqdColltnDt>2018-07-03</ReqdColltnDt>
         <Cdtr>
            <Nm>Joe Doe</Nm>
         </Cdtr>
         <CdtrAcct>
            <Id>
               <IBAN>DE89370400440532013000</IBAN>
            </Id>
         </CdtrAcct>
         <CdtrAgt>
            <FinInstnId>
               <BIC>DEUTDEBBXXX</BIC>
            </FinInstnId>
         </CdtrAgt>
         <ChrgBr>SLEV</ChrgBr>
         <CdtrSchmeId>
            <Id>
               <PrvtId>
                  <Othr>
                     <Id>DE98ZZZ09999999999</Id>
                     <SchmeNm>
                        <Prtry>SEPA</Prtry>
                     </SchmeNm>
                  </Othr>
               </PrvtId>
            </Id>
         </CdtrSchmeId>
         <DrctDbtTxInf>
            <PmtId>
               <EndToEndId>NOTPROVIDED</EndToEndId>
            </PmtId>
            <Amt>
               <InstdAmt Ccy="EUR">10.2</InstdAmt>
            </Amt>
            <DrctDbtTx>
               <MndtRltdInf>
                  <MndtId>MYCOMP11111111</MndtId>
                  <DtOfSgntr>2018-07-03</DtOfSgntr>
                  <AmdmntInd>false</AmdmntInd>
               </MndtRltdInf>
            </DrctDbtTx>
            <DbtrAgt>
               <FinInstnId>
                  <BIC>VBRSDE33347</BIC>
               </FinInstnId>
            </DbtrAgt>
            <Dbtr>
               <Nm>Peter Parker</Nm>
            </Dbtr>
            <DbtrAcct>
               <Id>
                  <IBAN>DE05500105173195282731</IBAN>
               </Id>
            </DbtrAcct>
            <UltmtDbtr>
               <Nm>Peter Parker</Nm>
            </UltmtDbtr>
            <RmtInf>
               <Ustrd>Invoice 4711564</Ustrd>
            </RmtInf>
         </DrctDbtTxInf>
         <DrctDbtTxInf>
            <PmtId>
               <EndToEndId>NOTPROVIDED</EndToEndId>
            </PmtId>
            <Amt>
               <InstdAmt Ccy="EUR">1000.0</InstdAmt>
            </Amt>
            <DrctDbtTx>
               <MndtRltdInf>
                  <MndtId>MYCOMP22222222</MndtId>
                  <DtOfSgntr>2018-07-03</DtOfSgntr>
                  <AmdmntInd>false</AmdmntInd>
               </MndtRltdInf>
            </DrctDbtTx>
            <DbtrAgt>
               <FinInstnId>
                  <BIC>UINVDEFFXXX</BIC>
               </FinInstnId>
            </DbtrAgt>
            <Dbtr>
               <Nm>Carl White</Nm>
            </Dbtr>
            <DbtrAcct>
               <Id>
                  <IBAN>DK5250511963137134</IBAN>
               </Id>
            </DbtrAcct>
            <UltmtDbtr>
               <Nm>Carl White</Nm>
            </UltmtDbtr>
            <RmtInf>
               <Ustrd>Invoice 789765</Ustrd>
            </RmtInf>
         </DrctDbtTxInf>
         <DrctDbtTxInf>
            <PmtId>
               <EndToEndId>NOTPROVIDED</EndToEndId>
            </PmtId>
            <Amt>
               <InstdAmt Ccy="EUR">50.0</InstdAmt>
            </Amt>
            <DrctDbtTx>
               <MndtRltdInf>
                  <MndtId>MYCOMP11111111</MndtId>
                  <DtOfSgntr>2018-07-03</DtOfSgntr>
                  <AmdmntInd>false</AmdmntInd>
               </MndtRltdInf>
            </DrctDbtTx>
            <DbtrAgt>
               <FinInstnId>
                  <BIC>SWBSDESSXXX</BIC>
               </FinInstnId>
            </DbtrAgt>
            <Dbtr>
               <Nm>Frank Black</Nm>
            </Dbtr>
            <DbtrAcct>
               <Id>
                  <IBAN>CZ7627005991764514418145</IBAN>
               </Id>
            </DbtrAcct>
            <UltmtDbtr>
               <Nm>Frank Black</Nm>
            </UltmtDbtr>
            <RmtInf>
               <Ustrd>Invoice 7856</Ustrd>
            </RmtInf>
         </DrctDbtTxInf>
      </PmtInf>
   </CstmrDrctDbtInitn>
</Document>

  
```

#### XML File Result for SEPA Credit Transfer

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03 pain.001.001.03.xsd">
    <CstmrCdtTrfInitn>
        <GrpHdr>
            <MsgId>20201207082105</MsgId>
            <CreDtTm>2020-12-07T08:21:05</CreDtTm>
            <NbOfTxs>1</NbOfTxs>
            <CtrlSum>18544082.14</CtrlSum>
            <InitgPty>
                <Nm>MARKET PAY</Nm>
                <Id>
                    <OrgId>
                        <Othr>
                            <Id>Not provided</Id>
                        </Othr>
                    </OrgId>
                </Id>
            </InitgPty>
        </GrpHdr>
        <PmtInf>
            <PmtInfId>20201207082105</PmtInfId>
            <PmtMtd>TRF</PmtMtd>
            <BtchBookg>false</BtchBookg>
            <NbOfTxs>1</NbOfTxs>
            <CtrlSum>18544082.14</CtrlSum>
            <PmtTpInf>
                <SvcLvl>
                    <Cd>SEPA</Cd>
                </SvcLvl>
            </PmtTpInf>
            <ReqdExctnDt>2020-12-07</ReqdExctnDt>
            <Dbtr>
                <Nm>MARKET PAY</Nm>
                <PstlAdr>
                    <Ctry>ES</Ctry>
                    <AdrLine>LINEA DE DIRECCION 1</AdrLine>
                    <AdrLine>LINEA DE DIRECCION 1</AdrLine>
                    <AdrLine>LINEA DE DIRECCION 1</AdrLine>
                </PstlAdr>
                <Id>
                    <OrgId>
                        <Othr>
                            <Id>To be provided</Id>
                        </Othr>
                    </OrgId>
                </Id>
            </Dbtr>
            <DbtrAcct>
                <Id>
                    <IBAN>DE89370400440532013000</IBAN>
                </Id>
                <Ccy>EUR</Ccy>
            </DbtrAcct>
            <DbtrAgt>
                <FinInstnId>
                    <BIC>DEUTDEBBXXX</BIC>
                    <PsltAdr>
                        <Ctry>ES</Ctry>
                    </PsltAdr>
                </FinInstnId>
            </DbtrAgt>
            <ChrgBr>SLEV</ChrgBr>
            <CdtTrfTxInf>
                <PmtId>
                    <InstrId>MP202012070821050001</InstrId>
                    <EndToEndId>MP202012070821050001</EndToEndId>
                </PmtId>
                <Amt>
                    <InstdAmt Ccy="EUR">18544082.14</InstdAmt>
                </Amt>
                <CdtrAgt>
                    <FinInstnId>
                        <BIC>BSCHESMMXXX</BIC>
                        <PstlAdr>
                            <Ctry>ES</Ctry>
                        </PstlAdr>
                    </FinInstnId>
                </CdtrAgt>
                <Cdtr>
                    <Nm>C.C. CARREFOUR S.A.</Nm>
                    <PstlAdr>
                        <StrtNm>STREET NAME</StrtNm>
                        <PstCd>POST CODE</PstCd>
                        <TwnNm>TOWN NAME</TwnNm>
                        <Ctry>ES</Ctry>
                    </PstlAdr>
                </Cdtr>
                <CdtrAcct>
                    <Id>
                        <IBAN>ES8600491500052610115791</IBAN>
                    </Id>
                </CdtrAcct>
                <RmtInf>
                    <Ustrd>07.12.2020 BRUTO: 18580731,32 COM: 36649,18</Ustrd>
                </RmtInf>
            </CdtTrfTxInf>
        </PmtInf>
    </CstmrCdtTrfInitn>
</Document>

```

## License

The MIT License (MIT)

Copyright (c) 2016 JohannesLaier

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

