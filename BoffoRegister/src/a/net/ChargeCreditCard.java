package a.net;

import administration.Administration;

class ChargeCreditCard extends Administration {
    final String transactionKey = getTransactionKey();
    final String SecretKey = getSecretKey();
    double amtCharge;
    int cardNumber;
    int expDate;
  
    
    
    public String ChargeCard(int cardNum, int expDate, double chargeAmt){
        this.cardNumber = cardNum;
        this.expDate = expDate;
        this.amtCharge = chargeAmt;
        
        return "ERR";
    }
    
    public static ANetApiResponse run(String apiLoginId, String transactionKey, Double amount){
        
    }
}