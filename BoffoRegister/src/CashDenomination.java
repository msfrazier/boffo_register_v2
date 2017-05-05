
import transaction.Transaction;

public class CashDenomination extends Transaction{
    //initialize variables
    protected int count = 0;
    protected String label;
    protected int value;
    protected int index;
    
    /**
     * Constructor that takes the label of the denomination and the
     * value in cents.
     * @param name The label
     * @param value The value in cents
     */
    public CashDenomination(String _label, int _value){
        this.label = _label;
        this.value = _value;
        //Increments so that the next denomination has the next index
        this.index = count++;
    }
    
    /**
     * Return the label.
     */
    public String getLabel(){
        return label;
    }
    
    /**
     * Return the index
     */
    public int getIndex(){
        return index;
    }
    
    
    
    
}