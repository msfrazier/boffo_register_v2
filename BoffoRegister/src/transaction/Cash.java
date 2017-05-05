package transaction;


public class Cash extends Transaction{
    protected final double QUARTER = 0.25;
    protected final double DIME = 0.1;
    protected final double NICKEL = 0.05;
    protected final double PENNY = 0.01;
    protected double purchase;
    protected double payment;
    
    /*
    Initializes the variables.
    */
    public Cash(){
        this.purchase = 0.00;
        this.payment = 0.00;
    }
    
    /*
    Will record the price of an item.
    */
    public void recordPurchase(double _amount){
        purchase = purchase + _amount;
    }
    
    /**
     * Enters the payment received by the customer.
     * @param _dollars The number of dollars received.
     * @param _quarters The number of quarters received.
     * @param _dimes The number of dimes received.
     * @param _nickels The number of nickels received.
     * @param _pennies The number of pennies received.
     */
    public void enterPayment(int _dollars, int _quarters, int _dimes, 
            int _nickels, int _pennies){
        payment = _dollars + (_quarters * QUARTER) + (_dimes * DIME) 
                + (_nickels * NICKEL) + (_pennies * PENNY); 
    }
    
    /**
     * Will compute the change due and resets for the next purchase.
     * @return The change that is due to the the customer.
     */
    public double giveChange(){
        double change = payment - purchase;
        purchase = 0;
        payment = 0;
        return change;
    }   
}
