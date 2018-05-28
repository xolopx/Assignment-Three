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
    //The stock feeds the first stage.
    private ArrayList<Item> stock = new ArrayList<Item>(1);
    //stats for the factory
    private Stats stats = new Stats();
    //An arrayList of queues. The waiting lines feed the stages that they precede.
    private ArrayList<ArrayList<Item>> queues;
    //An ArrayList of the stages in the factory.
    private ArrayList<Stage> stages;







    /**Constructor initializes the necessary components of the factory.*/
    Factory(double runTime, double mean, double range, int queueLength, int numStages){

        productionTime = runTime;
        M  = mean;
        N = range;
        stockUp(1);                                 //initially have one item in stock.
        initQueues(queueLength, 1);         //Initialized before stages as stages point to the queues.
        initStages(numStages);                          //populate stages collection with stages.

    }

    //This runs the factory.
    public void run(){


        //Main running loop for the factory. It runs as long as there is time left on the clock.
        while(productionTime > 0){
            //Take stock and put it into the first queue.
            queues.get(0).add(stock.get(0));
            //take the item out of the queue and add it to the first stage.
            stages.get(0).processItem(queues.get(0).remove(0));
            //Update the time of the item that has been processed.
            updateTimeByItem(stock.get(0));
            //Item has been processed, remove it from the stage.
            stock.remove(0);
            //Increment the number of items processed in the factory's statistics.
            stats.incNoItemsProcessed();
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
    //updates the productionTime remaining for the factory due to item's completion.
    private void updateTimeByItem(Item processedItem){
        productionTime -= processedItem.getTime();
    }
    //Prints ou the stock item's production times.
    public void printStock(int index){
        System.out.println(stock.get(index).timeToString());
    }
    //returns the statistics object for this factory.
    public Stats getStatistics(){
        return stats;
    }
    //Populates the queue list with queues. Queues must be initialized before stages.
    private void initQueues(int queueLength, int numQueues){
        queues = new ArrayList<>(numQueues);
        ArrayList<Item> newQueue;
        //populate the list of lists lol.
        for(int i = 0; i<numQueues;i++){
            newQueue = new ArrayList<>(queueLength);
            queues.add(newQueue);
        }
    }
    //Populates the list of stages with stages.
    private void initStages(int listLength) {
        stages = new ArrayList<>(listLength);
        Stage newStage;
        //populate the list of stages with stages!!
        for(int i = 0; i<listLength;i++){
            newStage  = new Stage();
            stages.add(newStage);
        }
    }

}
