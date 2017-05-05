package boffoIO;

/**
 * This class is used for reading inputs and firing events to pass the data to other classes.
 * @author sjwhyatt
 */
import events.BoffoEvent;
import events.BoffoEventData;
import events.BoffoFireObject;
import events.BoffoListenerInterface;
import events.BoffoUPCEvent;
import events.BoffoUpcEventData;
import java.util.Scanner;

public class BoffoIO extends BoffoFireObject implements BoffoListenerInterface{

    protected Scanner input = new Scanner(System.in);

    /* Reads in a double and checks to see if there is a product with that double.
    *  If there is a product, it will then fire an event.
    */
    public void scanDouble(){
        double tempDouble = this.input.nextDouble();
        if(product.ProductObject.loadByPrice(String.valueOf(tempDouble)) != null){
            fireEvent(new BoffoEvent(this,new BoffoEventData(tempDouble)));
        }
    }

    /* Reads in a integer and checks to see if there is a product with that integer.
    *  If there is a product it will, then fire an event.
    */
    public void scanInt(){
        int tempInt = this.input.nextInt();
        if(product.ProductObject.loadByQuantity(String.valueOf(tempInt)) != null){
            fireEvent(new BoffoEvent(this,new BoffoEventData(tempInt)));
        }
    }

    // Reads in two strings, puts them in an array, and fires an event with the array
    public void scanLogin(){
        String name = input.next();
        String pass = input.next();
        String[] login = new String[2];
        login[0] = name;
        login[1] = pass;
        fireEvent(new BoffoEvent(this,new BoffoEventData(login)));
    }

    /* Reads in a string and checks to see if there is a product with that string.
    *  If there is a product it will, then fire an event.
    */
    public void scanString(){
        String tempString = input.next();
        if(product.ProductObject.loadByName(tempString) != null){
            fireEvent(new BoffoEvent(this,new BoffoEventData(tempString)));
        }
        else if(product.ProductObject.loadBySKU(tempString) != null){
            fireEvent(new BoffoEvent(this,new BoffoEventData(tempString)));
        }
        else if(product.ProductObject.loadByUpc(tempString) != null){
            fireEvent(new BoffoUPCEvent(this,
                new BoffoUpcEventData(BoffoUpcEventData.EventType.NEW_UPC, tempString)));
        }
    }

    // Checks to see what is the event is and calls the proper method.
    @Override
    public void messageReceived(BoffoEvent _event) {
        if(_event.getMessage().getCode() instanceof BoffoEventData){
            // Calls scanDouble if the message reads double.
            if(_event.getMessage().getCode().getEventData().toString().equalsIgnoreCase("double")){
                scanDouble();
            }
            // Calls scanInt if the message reads int.
            else if(_event.getMessage().getCode().getEventData().toString().equalsIgnoreCase("int")){
                scanInt();
            }
            // Calls scanLogin if the message reads login.
            else if(_event.getMessage().getCode().getEventData().toString().equalsIgnoreCase("login")){
                scanLogin();
            }
            // Calls scanString if the message reads string.
            else if(_event.getMessage().getCode().getEventData().toString().equalsIgnoreCase("string")){
                scanString();
            }
        }
    }
}

