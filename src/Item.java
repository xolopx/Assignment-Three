import java.util.Random;

/**
 * Created by tom on 26/05/2018.
 *
 * Is an item with an associated (random) time that it takes to be completed.
 * When an item is created it generates its own time using timeGenerator.
 */


public class Item {

    private double productionTime;

    //Constructor simply takes the mean and range and the item will generate its own time.
    Item(double mean, double range){
        productionTime = timeGenerator(mean, range);
    }


    //generates the time for the item.
    private double timeGenerator(double M, double N){
        Random r = new Random();
        double d = r.nextDouble();
        return  M+ (N*(d-0.5));
    }
    //TESTING: Prints out the time that the item needs to be produced.
    public String timeToString(){
        return String.format("%4.2f",productionTime);
    }
    //TESTING: Returns the production time of the item.
    public double getTime(){
        return productionTime;
    }


}
