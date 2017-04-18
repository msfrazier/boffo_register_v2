package bundles;

// Will be a transaction object

import database.BoffoDbObject;
import product.ProductObject;

// extends BoffoDatabaseObject
public abstract class TicketElement extends BoffoDbObject{

    protected String name;
    protected String description;

    protected TicketElement(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public abstract double getPrice();
    
    public static double getPriceTicketElements(PairList<TicketElement> elements){
        return 0.0;
    }
    
    public static double getPriceProducts(PairList<ProductObject> products){
        return getPriceTicketElements(toTicketElements(products));
    }
    
    public static PairList<TicketElement> toTicketElements(PairList<ProductObject> products){
        return new PairList();
    }

}
