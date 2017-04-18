package boffoIO;

/**
 *
 * @author sjwhyatt
 */
import java.util.Scanner;

public class BoffoIO {

    protected Scanner input;

    //reads the next int and returns it
    public int scanInt(){

        return input.nextInt();

    }

    //reads next string and returns it
    public String scanString(){

        return input.next();

    }

    //reads next double and returns it
    public double scanDouble(){

        return input.nextDouble();

    }
}
