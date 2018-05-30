/**
 * Created by Tom on 27-May-18.
 * Runs the operation of a single production line.
 * This class is made specifically for items at this point.
 */
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.PriorityQueue;

//Is a generic type where T specifies the type of thing that the factory will process.
public class Factory {

    //This is the amount of time the factory will simulate for.
    private double productionTime;
    //This is the mean.
    private double M;
    //This is the range.
    private double N;
    //The stock feeds the first stage.
    private WaitingLine<Item> stock = new WaitingLine<>(1);
    //stats for the factory
    private Stats stats = new Stats();
    //An arrayList of queues. The waiting lines feed the stages that they precede.
    private ArrayList<WaitingLine<Item>> listOfLines;
    //An priority queue of the stages in the factory.
    private PriorityQueue<Stage> stages;


    /**Constructor initializes the necessary components of the factory.*/
    Factory(double runTime, double mean, double range, int queueLength, int numStages){

        productionTime = runTime;
        M  = mean;
        N = range;
        stockUp(1);                                 //initially have one item in stock.
        initListOfLines(queueLength, 1);         //Initialized before stages as stages point to the queues.
        initStages(numStages);                          //populate stages collection with stages.

    }

    //This runs the factory.
    public void run(){
        //Main running loop for the factory. It runs as long as there is time left on the clock.
        while(productionTime > 0){

        }




    }
    //append Items to the stock by the integer value specified.
    private void stockUp(int i){
       //declare a new item
        Item newItem;
        //Populate the stock.
        for(int j = 0;j<i;j++){
            newItem = new Item(M,N);
            stock.addItem(newItem);
        }
    }
    //updates the productionTime remaining for the factory due to item's completion.
    private void updateTimeByItem(Item processedItem){
        productionTime -= processedItem.getTime();
    }
    //Prints ou the stock item's production times.
    public void printStock(int index){
        System.out.println()
    }
    //returns the statistics object for this factory.
    public Stats getStatistics(){
        return stats;
    }
    //Populates the queue list with queues. Queues must be initialized before stages.
    private void initListOfLines(int queueLength, int numQueues){

        //Initialize the list of lines to size numQueue.
        listOfLines = new ArrayList<>(numQueues);
        //Declare a new WaitingLine to go into the list of lines.
        WaitingLine<T> newLine;

        //populate the list of lines with new lines.
        for(int i = 0; i<numQueues;i++){
            //instantiate the new line
            newLine = new WaitingLine<>(queueLength);
            //and place it into the list of lines.
            listOfLines.add(newLine);
        }
    }
    //Populates the list of stages with stages.
    private void initStages(int listLength) {
        //Instantiate the priority queue for the stages to live in.
        stages = new PriorityQueue<>(listLength);
        //Declare a new stage.
        Stage newStage;
        //populate the list of stages with stages!!
        for(int i = 0; i<listLength;i++){
            //instantiate the new stage
            newStage  = new Stage();
            //and place it into the priority queue.
            stages.add(newStage);
        }
    }

}
