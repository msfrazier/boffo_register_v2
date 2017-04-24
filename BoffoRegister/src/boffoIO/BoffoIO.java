package boffoIO;

/**
 *
 * @author sjwhyatt
 */
import events.BoffoEvent;
import events.BoffoFireObject;
import events.BoffoListenerInterface;
import java.util.Scanner;

public class BoffoIO extends BoffoFireObject implements BoffoListenerInterface{

    protected Scanner input = new Scanner(System.in);

    /* Reads in a double and checks to see if there is a product with that double.
    *  If there is a product, it will then fire an event.
    */
    public void scanDouble(){
        double tempDouble = this.input.nextDouble();
        if(product.ProductObject.loadByPrice(String.valueOf(tempDouble)) != null){
//            fireEvent(new BoffoEvent(this,message));
        }
    }

    /* Reads in a integer and checks to see if there is a product with that integer.
    *  If there is a product it will, then fire an event.
    */
    public void scanInt(){
        int tempInt = this.input.nextInt();
        if(product.ProductObject.loadByQuantity(String.valueOf(tempInt)) != null){
//            fireEvent(new BoffoEvent(this,message));
        }
        else if(product.ProductObject.loadByUpc(String.valueOf(tempInt)) != null){
//            fireEvent(new BoffoEvent(this,message));
        }
    }

    /* Reads in a string and checks to see if there is a product with that string.
    *  If there is a product it will, then fire an event.
    */
    public void scanString(){
        String tempString = input.next();
        if(product.ProductObject.loadByName(tempString) != null){
//            fireEvent(new BoffoEvent(this,message));
        }
        else if(product.ProductObject.loadBySKU(tempString) != null){
//            fireEvent(new BoffoEvent(this,message));
        }
    }

    @Override
    public void messageReceived(BoffoEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

