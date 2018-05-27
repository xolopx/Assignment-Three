/**
 * Created by Tom on 27-May-18.
 * Runs the operation of a single production line.
 * This class is made specifically for items at this point.
 */
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Factory {

    //This is the amount of time the factory will simulate for.
    private double productionTime;
    //This is the mean.
    private double M;
    //This is the range.
    private double N;
    //This is the ever changing arrayList of Items.
    private ArrayList<Item> stock = new ArrayList<Item>();
    //A priority queue of the stages in the factory sorted by the next item to be completed.
    private PriorityQueue<Item> queue = new PriorityQueue<>();
    //stats for the factory
    private Stats stats = new Stats();



    /**Constructor initializes the necessary components of the factory.
     * 1. Populate with Items
    */
    Factory(double runTime, double mean, double range){

        productionTime = runTime;
        M  = mean;
        N = range;
        stockUp(1);             //initially have one item in stock.
    }

    //This runs the factory.
    public void run(){

        /** Main factory running loop.
         * This loop runs as long as there is time left on the clock.
         */
        while(productionTime > 0){
            //we want to get items out and "process" them in the simplest way possible. I.e. subtract their time.
            //Take an item from head (index 0);
            //stock.get(0).printItem();
            //subtract from total time.
            updateTimeByItem(stock.get(0));
            //Increment the number of items processed in the factory's statistics.
            stats.incNoItemsProcessed();
            //Print out time remaining.
            //System.out.print("\tTime remaining: " + productionTime + "\n");
            //remove the item as it has been "processed".
            stock.remove(0);
            //add new stock to replace old stock.
            stockUp(1);

        }




    }
    //append Items to the stock by the integer value specified.
    private void stockUp(int i){
       //Populate the stock.
        for(int j = 0;j<i;j++){
            Item newItem = new Item(M,N);
            stock.add(newItem);
        }
    }
    //updates the productionTime remaining for the factory by item.
    private void updateTimeByItem(Item processedItem){
        productionTime -= processedItem.getTime();
    }
    //Creates a priority queue of the stages.





    //TESTING: Returns a stock item.
    public void printStock(int index){
         stock.get(index).printItem();
    }

    public Stats getStatistics(){
        return stats;
    }
}
