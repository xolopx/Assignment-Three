import java.util.Random;

/**
 * Created by tom on 26/05/2018.
 *
 * Is an item with an associated (random) time that it takes to be completed.
 * When an item is created it generates its own time using timeGenerator.
 */


public class Item {

    private double randValue;
    private double timeEntering;
    private double timeLeaving;
    private String path;

    //Constructor simply takes the mean and range and the item will generate its own time.
    Item(){
        randValue = timeGenerator();
        timeEntering = -1;
        timeLeaving = -1;
    }


    //generates the time for the item.
    private double timeGenerator(){
        Random r = new Random();
       return r.nextDouble();

    }
    //TESTING: Prints out the time that the item needs to be produced.
    public String timeToString(){
        return String.format("%4.2f",randValue);
    }

    public double getRand(){
        return randValue;
    }
    public double getTimeEntering() {
        return timeEntering;
    }
    public void setTimeEntering(double timeEntering) {
        this.timeEntering = timeEntering;
    }
    public double getTimeLeaving() {
        return timeLeaving;
    }
    public void setTimeLeaving(double timeLeaving) {
        this.timeLeaving = timeLeaving;
    }
}
