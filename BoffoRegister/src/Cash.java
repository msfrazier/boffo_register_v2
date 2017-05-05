
import java.util.ArrayList;
import transaction.Transaction;

public class Cash extends Transaction{
    /**
     * Array with names and values of denominations.
     */
    public static final CashDenomination DENOMINATIONS[] = new CashDenomination[]{
        new CashDenomination("DollarBill", 100),
        new CashDenomination("Quarter", 25),
        new CashDenomination("Dime", 10),
        new CashDenomination("Nickel", 5),
        new CashDenomination("Penny", 1)
    };
    
    private final ArrayList<Integer> amounts;
    protected int value = 0;
    
    public Cash(){
        amounts = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            amounts.add(0);
        }
    }
    
    public Cash(int[] amounts){
        this();
        if(amounts != null && amounts.length == DENOMINATIONS.length){
            for (CashDenomination cd: DENOMINATIONS) {
              //  this.add(cd, amounts[cd.getIndex()]);
            }
        }
    }
    
    public double add(Cash c){
        if(c!= null){
            for(CashDenomination cd : DENOMINATIONS)
                this.add(cd,c.getAmount(cd));
        }
        return value / 100.0;
    }
    
    public double remove(Cash c){
        if(c != null){
            for (CashDenomination cd : DENOMINATIONS) {
                this.remove(cd, c.getAmount);
            }
            return value / 100.0;
        }
    }
    public int remove(CashDenomination denom, int amount){
        int remove = 0;
        if(amount > 0 && denom != null){
            int index = denom.getIndex();
            removed = Math.min(amounts.get(index), amount);
            amounts.set(index, amounts.get(index) - removed);
            
        }
        
    }
    
    	public Cash remove(double amount){
		Cash c = null;
		
		int toRemove = 0;
		int amountInCents = (int)(amount * 100.0);
		if (amountInCents > 0 && amountInCents <= value){
			c = new Cash();
			
			for(CashDenomination cd : DENOMINATIONS){
				toRemove = amountInCents / cd.getValue();
				c.add(cd, remove(cd, toRemove));
				amountInCents -= toRemove*cd.getValue();
			}
		}
		
		return c;
	}
        
        public int getAmount(CashDenomination denom){
		return amounts.get(denom.getIndex());
	}
	
	/**
	 * Value getter
	 * @return returns the value of the cash object
	 */
	public double getValue(){
		return value / 100.0;
	}
    
}