package a.net;

import java.util.Random;
import administration.Administration;

/*@author DaneHasnen

 Updated: 05/05/17

 This (would) allow for credit cards to be charged. This takes data entered by
transaction and creates a payment object which is then returned to transaction
to be analized.
 */




public class BoffoPayment {

    public static Payment processPayment(double _amount, String _creditCardNumber, String _ticketID) {
        // Here we would call on the Authorize.NET API, but instead, we'll use a stub.
        Payment _pay = new Payment(_creditCardNumber, _amount);
        // Here is where a payment object is created from transaction data.
        Payment _payResp = TakePayment.receivePayment(_amount, _ticketID, _pay);
        /*
        This is where a payment object is created by calling take payment which
        randomly assigns a success/failure code as well as a confirmation code.
        */
        return _payResp;
        // Returns payment object to item calling processPayment.
    }
    
    public static void main(String[] args) {
        Payment yes = processPayment(12.35, "4444444444444444","west");
        if (yes._confirmationResNum == 1) {
            System.out.println("hello");
            System.out.println(yes._confirmationResNum);
        }
        else{
            System.out.println("bye");
            System.out.println(yes._confirmationResNum);
        }
    }
}


class TakePayment {

    public static Payment receivePayment(double _amount, String _ticketID, Payment _p) {
        // Return a pretend confirmation number as though from Authorize.NET.
        Random rand = new Random();
        int randomResponse = rand.nextInt(3) + 1;
        // Random number generated where 3 is the maximum and the 1 is the minimum.

        int randomConfirmation = rand.nextInt(99999999) + 1;
        // Random number generated  where 99999999 is the maximum and the 1 is the minimum.

        switch (randomResponse) {
            case 1:
                _p._confirmationResNum = randomResponse;
                _p.chargeRes("Payment Accepted");
                _p.confirmationNum(randomConfirmation);
                break;
            case 2:
                 _p._confirmationResNum = randomResponse;
                _p.chargeRes("Insufficant Funds");
                _p.confirmationNum(randomConfirmation);
                break;
            case 3:
                 _p._confirmationResNum = randomResponse;
                _p.chargeRes("Invalid Card Number");
                _p.confirmationNum(randomConfirmation);
                break;
        /* This checks which response is randomly generated. When a case is
           matched, the payment object is updated to reflect the generated response.
        */
        }

        return _p;
        // This returns a payment object to processPayment.
    }
}