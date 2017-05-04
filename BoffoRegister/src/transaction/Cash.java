package transaction;

public class Cash {
	
	/**
	 * Constants representing the value of the money
	 */
	
	public static final int DOLLARBILL = 100;
	public static final int QUARTER= 25;
	public static final int DIME= 10;
	public static final int NICKEL= 5;
	public static final int PENNY= 1;
	
	/**
	 * Variables which store the amount of each money
	 */
	
	private int bills = 0;
	private int quarters = 0;
	private int dimes = 0;
	private int nickels = 0;
	private int pennies = 0;
	
	/**
	 * Variable that stores the amount of money in pennies
	 */
	
	private int value = 0;
	
	/**
	 * Default constructor, every amount of money is 0
	 */
	public Cash(){
	}

	/**
	 * Constructor specifying amounts of money
	 * @param bills amount of bills to be added
	 * @param quarters amount of quarters to be added
	 * @param dimes amount of dimes to be added
	 * @param nickels amount of nickels to be added
	 * @param pennies amount of pennies to be added
	 */
	public Cash(int bills, int quarters, int dimes, int nickels, int pennies){
		addBills(bills);
		addQuarters(quarters);
		addDimes(dimes);
		addNickels(nickels);
		addPennies(pennies);
	}
	
	/*
	 * @return the bills
	 */
	public int getBills() {
		return bills;
	}

	/**Getter
	 * @return the quarters
	 */
	public int getQuarters() {
		return quarters;
	}

	/**Getter
	 * @return the dimes
	 */
	public int getDimes() {
		return dimes;
	}

	/**Getter
	 * @return the nickels
	 */
	public int getNickels() {
		return nickels;
	}

	/**Getter
	 * @return the pennies
	 */
	public int getPennies() {
		return pennies;
	}
	
	/**Getter
	 * @return value
	 */
	public double getValue(){
		return value/100.0;
	}
	
	/**Adds amount of bills and value
	 * @param amount bills to be added
	 * @return value
	 */
	public double addBills(int amount){
		if(amount > 0){
			bills += amount;
			value += amount*DOLLARBILL;
		}
		return value/100.0;
	}
	
	/**Adds amount of quarters and value
	 * @param amount quarters to be added
	 * @return value
	 */
	public double addQuarters(int amount){
		if(amount > 0){
			quarters += amount;
			value += amount*QUARTER;
		}
		return value/100.0;
		
	}
	
	/**Adds amount of dimes and value
	 * @param amount dimes to be added
	 * @return value
	 */
	public double addDimes(int amount){
		if(amount > 0){
			dimes += amount;
			value += amount*DIME;
		}
		return value/100.0;
	}
	
	/**Adds amount of nickels and value
	 * @param amount nickels to be added
	 * @return value
	 */
	public double addNickels(int amount){
		if(amount > 0){
			nickels += amount;
			value += amount*NICKEL;
		}
		return value/100.0;
	}
	
	/**Adds amount of pennies and value
	 * @param amount pennies to be added
	 * @return value
	 */
	public double addPennies(int amount){
		if(amount > 0){
			pennies += amount;
			value += amount*PENNY;
		}
		return value/100.0;
	}
	
	/**Removes amount of bills and value
	 * @param amount bills to be removed
	 * @return bills removed
	 */
	public int removeBills(int amount){
		int removed = 0;
		
		if(amount > 0){
			removed = Math.min(amount, bills);
			bills -= removed;
			value -= removed*DOLLARBILL;
		}
		return removed;
	}
	
	/**Removes amount of quarters and value
	 * @param amount quarters to be removed
	 * @return quarters removed
	 */
	public int removeQuarters(int amount){
		int removed = 0;
		
		if(amount > 0){
			removed = Math.min(amount, quarters);
			quarters -= removed;
			value -= removed*QUARTER;
		}
		return removed;
	}
	
	/**Removes amount of dimes and value
	 * @param amount dimes to be removed
	 * @return dimes removed
	 */
	public int removeDimes(int amount){
		int removed = 0;
		
		if(amount > 0){
			removed = Math.min(amount, dimes);
			dimes -= removed;
			value -= removed*DIME;
		}
		return removed;
	}
	
	/**Removes amount of nickels and value
	 * @param amount nickels to be removed
	 * @return nickels removed
	 */
	public int removeNickels(int amount){
		int removed = 0;
		
		if(amount > 0){
			removed = Math.min(amount, nickels);
			nickels -= removed;
			value -= removed*NICKEL;
		}
		return removed;
	}
	
	/**Removes amount of pennies and value
	 * @param amount pennies to be removed
	 * @return pennies removed
	 */
	public int removePennies(int amount){
		int removed = 0;
		
		if(amount > 0){
			removed = Math.min(amount, pennies);
			pennies -= removed;
			value -= removed*PENNY;
		}
		return removed;
	}
	
	/**Adds cash
	 * @param c the object Cash to be added
	 * @return value
	 */
	public double add(Cash c){
		this.addBills(c.getBills());
		this.addQuarters(c.getQuarters());
		this.addDimes(c.getDimes());
		this.addNickels(c.getNickels());
		this.addPennies(c.getPennies());
		
		return value/100.0;
	}
	
	/**Removes a value of cash
	 * @param amount value of cash to be removed
	 * @return object Cash with the amount of money an value
	 */
	public Cash remove(double amount){
		Cash c = null;
		
		int toRemove = 0;
		int amountInCents = (int) (amount * 100.0);
		if (amountInCents > 0 && amountInCents <= value){
			c = new Cash();
			
			toRemove = amountInCents / DOLLARBILL;
			c.addBills(removeBills(toRemove));
			amountInCents -= c.getBills()*DOLLARBILL;
			
			toRemove = amountInCents / QUARTER;
			c.addQuarters(removeQuarters(toRemove));
			amountInCents -= c.getQuarters()*QUARTER;
			
			toRemove = amountInCents / DIME;
			c.addDimes(removeDimes(toRemove));
			amountInCents -= c.getDimes()*DIME;
			toRemove = amountInCents / NICKEL;
			c.addNickels(removeNickels(toRemove));
			amountInCents -= c.getNickels()*NICKEL;
			
			toRemove = amountInCents / PENNY;
			c.addPennies(removePennies(toRemove));
			amountInCents -= c.getPennies()*PENNY;
		}
		
		return c;
	}
	
	
}