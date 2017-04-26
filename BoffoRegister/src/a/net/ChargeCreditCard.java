package a.net;

import administration.Administration;

class ChargeCreditCard extends Administration {
    final String transactionKey = getTransactionKey();
    final String SecretKey = getSecretKey();

    public void ChargeCard(int cardNum, int expDate, double chargeAmt){
    
    }
}