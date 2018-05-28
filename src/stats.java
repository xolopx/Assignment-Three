/**
 * Created by tom on 26/05/2018.
 *
 * Stores the statistics for the production line.
 */
public class Stats {

    private int itemsProcessed;

    Stats(){
        itemsProcessed = 0;
    }


    //Prints out a table of the statistics for the factory.
    public void printStats(){
        System.out.println("The number of items processed is: " + itemsProcessed);
    }

    public void incNoItemsProcessed(){
        itemsProcessed++;
    }
}
