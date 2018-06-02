/**
 * Created by tom on 26/05/2018.
 *
 * Stores the statistics for the production line.
 */
public class Stats {

    private int itemsProcessed;
    private int itemsCreated;
    private double globalTime;

    Stats(){
        itemsProcessed = 0;
        itemsCreated = 0;
        globalTime = 0;
    }
    public void incItmProcessed(){
        itemsProcessed++;
    }
    public void incItmCreate(){itemsCreated++;}
    public void updateTime(double theTime){globalTime = theTime;}
    //Prints out a table of the statistics for the factory.
    public void printStats(){

        System.out.println("\nThe number of items created is: " + itemsCreated);
        System.out.println("The number of items processed is: " + itemsProcessed);
        System.out.println("The factory finished processing at: " + globalTime);
    }
}
