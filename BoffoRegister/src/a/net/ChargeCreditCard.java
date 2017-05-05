package a.net;

import java.util.Random;
import administration.Administration;

/*@author DaneHasnen

 Updated: 05/04/17

 This (would) allow for credit cards to be charged. This takes data entered by
transaction and creates a payment object which is then returned to transaction
to be analized.
 */


class BoffoPayment {

    public static Payment processPayment(double amount, String creditCardNumber, String ticketID) {
        // Here we would call on the Authorize.NET API, but instead, we'll use a stub.
        Payment pay = new Payment(creditCardNumber, amount);
        // Here is where a payment object is created from transaction data.
        Payment payResp = TakePayment.receivePayment(amount, ticketID, pay);
        /*
        This is where a payment object is created by calling take payment which
        randomly assigns a success/failure code as well as a confirmation code.
        */
        return payResp;
        // Returns payment object to item calling processPayment.
    }
}

class TakePayment {

    public static Payment receivePayment(double amount, String ticketID, Payment p) {
        // Return a pretend confirmation number as though from Authorize.NET.
        Random rand = new Random();
        int randomResponse = rand.nextInt(3) + 1;
        // Random number generated where 3 is the maximum and the 1 is the minimum.

        int randomConfirmation = rand.nextInt(99999999) + 1;
        // Random number generated  where 99999999 is the maximum and the 1 is the minimum.

        switch (randomResponse) {
            case 1:
                p.chargeRes("Payment Accepted");
                p.confirmationNum(randomConfirmation);
                break;
            case 2:
                p.chargeRes("Insufficant Funds");
                p.confirmationNum(randomConfirmation);
                break;
            case 3:
                p.chargeRes("Invalid Card Number");
                p.confirmationNum(randomConfirmation);
                break;
        /* This checks which response is randomly generated. When a case is
           matched, the payment object is updated to reflect the generated response.
        */
        }

        return p;
        // This returns a payment object to processPayment.
    }
}

class Payment{
    /* This declares a payment object where a payment's information is stored
    in one data structure.
    */
    String cardNumber;
    double chargeAmt;
    int confirmationNum;
    String confirmation;
    String chargeResponse;
    // Creating the variables for the payment object.

    public Payment(String cardNumber, double amt) {
        this.cardNumber = cardNumber;
        this.chargeAmt = amt;
        this.confirmationNum = -1;
        this.chargeResponse = "No Card Charged";
        this.confirmation = "No Card Charged";
        // Constructing the payment object and setting variables to "default."
    }

    public void chargeAmt(double chargeChange) {
        this.chargeAmt = chargeChange;
        /* Modifies chargeAmt by replacing the current value with the value
        in chargeChange.
        */
    }

    public void confirmation(String conf) {
        this.confirmation = conf;
        /* Modifies confirmation by replacing the current value with the value
        in conf.
        */
    }

    public void confirmationNum(int confNum) {
        this.confirmationNum = confNum;
        /* Modifies confirmaton by replacing the current value with the value
        in confNum.
        */
    }

    public void chargeRes(String response) {
        chargeResponse = response;
        /* Modifies chargeRes by replacing the current value with the value
        in response.
        */
    }

}
