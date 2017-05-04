package transaction;

public class CashRegister {
	
	/**
	 * Variable that store the cash register money, the payment received and the purchase total
	 */
	private double purchasedTotal = 0;
	private Cash paymentTotal = new Cash();
	private Cash registerTotal = new Cash();
	
	/**
	 * Constructor based on an amount of cash to be placed on an empty cash register
	 * @param registerTotal
	 */
	public CashRegister(Cash registerTotal){
		this.registerTotal.add(registerTotal);
	}
	
	/**
	 * Constructor which fill the cash register with 100 of each component
	 */
	public CashRegister(){
		this(new Cash(100,100,100,100,100));
	}

	/**
	 * Add purchase amount to the total
	 * @param amount
	 * @return Purchase Total
	 */
	public double purchase(double amount){
		purchasedTotal += amount;
		return purchasedTotal;
	}
	
	/**
	 * Receive a payment
	 * @param amount
	 */
	public void payment(Cash amount){
		paymentTotal.add(amount);
	}
	
	/**
	 * Calculates Cash object to be returned, calculates the final cash in the register
	 * and resets the purchase total and the payment total
	 * @return Change returned in a Cash object
	 */
	public Cash giveChange(){
		registerTotal.add(paymentTotal);
		
		Cash change = paymentTotal;
		change.remove(purchasedTotal);
		
		registerTotal.remove(change.getValue());
		
		purchasedTotal = 0;
		paymentTotal = new Cash();

		return change;
	}
	
	/**
	 * Getter; gets purchase total
	 * @return purchasedTotal
	 */
	public double getPurchaseTotal(){
		return purchasedTotal;
	}
	
	/**
	 * Getter; gets Cash object of payment total
	 * @return paymentTotal
	 */
	public Cash getPayment(){
		return paymentTotal;
	}
	
	/**Getter; gets Cash object of register total
	 * @return registerTotal
	 */
	public Cash getRegisterTotal(){
		return registerTotal;
	}
	
}
