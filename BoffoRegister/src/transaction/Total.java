package transaction;

public class Total extends Transaction {

    protected double amount;

    public Total() {
        this.amount = 0.00;
    }

    public Total(double _amount) {
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