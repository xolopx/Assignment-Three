/**
 * Created by tom on 26/05/2018.
 *
 * Is an item with an associated (random) time that is used in the stages it goes to to determine how long it will take
 * to process. The item stored when it enters a stage and when it leaves. Furthermore it knows what path it has taken
 * to reach its journey's end.
 */
public class Item {

    private double randValue;           //Value used to determine the processing time for the item by stages.
    private double timeEnteringQ;       //Time entering a queue.
    private double timeLeavingQ;        //Time leaving a queue.
    private String path = "";           //path through stages taken by item.


    /**
     * When an item is constructed its times entering and leaving are -1 and it is given a random value to which it will
     * take on its journey to many stages and distant lands.
     * @param r
     */
    Item(double r){

        randValue = r;
        timeEnteringQ = -1;
        timeLeavingQ = -1;
    }

    /**
     * When a stage needs to calculate the processing time for an item it needs that items random value so it calls
     * this method.
     * @return The random value of the item.
     */
    public double getRand(){
        return randValue;
    }

    /**
     * Appends the stage name of the stages the item visits to to "path". Used to determine statistics of production
     * line.
     * @param stageName The name of the stage that the item has just visited.
     */
    public void updatePath(String stageName){
        path += stageName;
    }

    public void setTimeEntering(double timeEntering) {
        this.timeEnteringQ = timeEntering;
    }
    public void setTimeLeaving(double timeLeaving) {
        this.timeLeavingQ = timeLeaving;
    }
    public double getTimeEntering() {
        return timeEnteringQ;
    }
    public double getTimeLeaving() {
        return timeLeavingQ;
    }
    public String getPath() {
        return path;
    }
}
