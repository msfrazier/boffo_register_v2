package transaction;

public class Payment extends Transaction {

    protected double amount;

    public Payment() {
        this.amount = 0.00;
    }

    public Payment(double _amount) {
        this.amount = _amount;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double _amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Amount: " + amount;
    }
}