import java.util.Random;

/**
 * Created by tom on 26/05/2018.
 *
 * Is an item with an associated (random) time that it takes to be completed.
 * When an item is created it generates its own time using timeGenerator.
 */


public class Item {

    private double productionTime;
    private double timeEntering;
    private double timeLeaving;
    private String path;

    //Constructor simply takes the mean and range and the item will generate its own time.
    Item(){
        productionTime = timeGenerator();
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
        return String.format("%4.2f",productionTime);
    }
    //Returns the production time of the item.
    public double getTime(){
        return productionTime;
    }
    //gets the time entering.
    public double getTimeEntering() {
        return timeEntering;
    }
    //gets time leaving.
    public void setTimeEntering(double timeEntering) {
        this.timeEntering = timeEntering;
    }
    //gets time leaving.
    public double getTimeLeaving() {
        return timeLeaving;
    }
    //sets time leaving.
    public void setTimeLeaving(double timeLeaving) {
        this.timeLeaving = timeLeaving;
    }
}
