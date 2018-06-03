import java.util.Random;

/**
 * Created by tom on 26/05/2018.
 *
 * Is an item with an associated (random) time that it takes to be completed.
 * When an item is created it generates its own time using timeGenerator.
 */


public class Item {

    private double randValue;
    private double timeEnteringQ;
    private double timeLeavingQ;
    private String path = "";            //path through stages taken by item.

    //Constructor simply takes the mean and range and the item will generate its own time.
    Item(){
        randValue = timeGenerator();
        timeEnteringQ = -1;
        timeLeavingQ = -1;
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
        return timeEnteringQ;
    }
    public void setTimeEntering(double timeEntering) {
        this.timeEnteringQ = timeEntering;
    }
    public double getTimeLeaving() {
        return timeLeavingQ;
    }
    public void setTimeLeaving(double timeLeaving) {
        this.timeLeavingQ = timeLeaving;
    }
    public void updatePath(String stageName){
        path += stageName;
    }
    public String getPath() {
        return path;
    }
}
