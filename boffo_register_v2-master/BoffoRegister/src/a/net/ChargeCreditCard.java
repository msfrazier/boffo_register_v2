package a.net;

import administration.Administration;

class ChargeCreditCard extends Administration {
    final String transactionKey = printTransactionKey();
    final String SecretKey = printSecretKey();
    double amtCharge;
    int cardNumber;
    int expDate;
  
    
    
    public String ChargeCard(int cardNum, int expDate, double chargeAmt){
        this.cardNumber = cardNum;
        this.expDate = expDate;
        this.amtCharge = chargeAmt;
        
        return "ERR";
    }
    
    
}