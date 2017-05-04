package a.net;

import java.util.Random;
import administration.Administration;

class Charge{
    public static void main(String[] args) {
        Payment New  = BoffoPayment.processPayment(100.57,"4444444444444444", "AX3T5");
        System.out.println(New.chargeResponse + " :: " + New.confirmation);
    }
}

class BoffoPayment {

    public static Payment processPayment(double amount, String creditCardNumber, String ticketID) {
        // Here we would call on the Authorize.NET API, but instead, we'll use a stub.
        Payment pay = new Payment(creditCardNumber, amount);
        Payment payResp = FakePayment.receivePayment(amount, ticketID, pay);
        
        return payResp;
    }
}

class FakePayment {

    public static Payment receivePayment(double amount, String ticketID, Payment p) {
        // Return a pretend confirmation number as though from Authorize.NET.
        Random rand = new Random();
        int randomResponse = rand.nextInt(3) + 1;
        //3 is the maximum and the 1 is our minimum 

        int randomConfirmation = rand.nextInt(99999999) + 1;
        // 99999999 is the maximum and the 1 is our minimum 

        switch (randomResponse) {
            case 1:
                p.chargeRes("Payment Accepted");
                p.confermation("Confirmation Number:" + randomConfirmation);
                break;
            case 2:
                p.chargeRes("Insufficant Funds");
                p.confermation("Confirmation Number:" + randomConfirmation);
                break;
            case 3:
                p.chargeRes("Invalid Card Number");
                p.confermation("Confirmation Number:" + randomConfirmation);
                break;
        }

        return p;
    }
}

class Payment extends administration.Administration{
//    final String secretKey = printSecretKey();
//    final String transactionKey = printTransactionKey();
//    final String currency = printCurrency();
    String cardNumber;
    double chargeAmt;
    String confirmation;
    String chargeResponse;

    public Payment(String cardNumber, double amt) {
        this.cardNumber = cardNumber;
        this.chargeAmt = amt;
        this.chargeResponse = "No Card Charged";
        this.confirmation = "No Card Charged";
    }

    public void chargeAmt(double C) {
        this.chargeAmt = C;
    }

    public void confermation(String confNum) {
        this.confirmation = confNum;
    }

    public void chargeRes(String response) {
        chargeResponse = response;
    }

}
