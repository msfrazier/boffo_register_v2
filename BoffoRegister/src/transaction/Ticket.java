package transaction;

import java.util.ArrayList;
import java.util.List;
import product.ProductObject;


public class Ticket extends Transaction {

    private List<ProductObject> products;

    public Ticket(List<ProductObject> products) {
        ArrayList<ProductObject> list = new ArrayList<ProductObject>();

    }
	
    public Ticket(){
	
    }

    public ProductObject addProduct(int UPC) {
       // ProductObject product = (ProductObject) product.add(null, null);
       // this.products.add(product);
       // return product;
	    return null;
    }
    
     public ProductObject removeProduct(int UPC){

        return null; 
      }

    public ProductObject getProduct(int UPC) {
      //  for (Product product : products) {
      //      if (product.getUPC() == UPC) {
      //          return product;
      //      }
      //  }
        return null;
    }
    
      public double getTotalPrice() {
	double totalPrice = 0;
//	for (int i=0; i< ProductObject.size(); i++) {
//	    Object obj = ProductObject.get(i);
//	    Product p = (ProductObject)obj;
//	    totalPrice = totalPrice + p.getPrice();
//	}
	return totalPrice;
    }

}
