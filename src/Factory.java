/**
 * Created by Tom on 27-May-18.
 * Runs the operation of a single production line.
 * This class is made specifically for items at this point.
 */
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

//Is a generic type where T specifies the type of thing that the factory will process.
public class Factory {

    //This is the max time.
    private double productionTime;
    //this is the actual time
    private double theTime;
    //This is the mean.
    private double M;
    //This is the range.
    private double N;
    //stats for the factory
    private Stats stats = new Stats();
    //the queue length
    private int queueLength;

    //An arrayList of queues. The waiting lines feed the stages that they precede.
    private ArrayList<WaitingLine<Item>> listOfLines;
    //The stock feeds the first stage.
    private WaitingLine<Item> stock = new WaitingLine<>(1);
    //An priority queue of the stages in the factory.
    private SortedList<Stage> stages;



    /**Constructor initializes the necessary components of the factory.*/
    Factory(double runTime, double mean, double range, int ql, int numStages){

        productionTime = runTime;
        M  = mean;
        N = range;
        queueLength = ql;
        theTime = 0;
        stockUp(1);                                   //initially have one item in stock.
        initListOfLines(queueLength, 1);     //Initialized before stages as stages point to the queues.
        initStages(numStages);                          //populate stages collection with stages.
    }

    //This runs the factory.
    public void run() {
       updateFactory();     //sets all the shit.
       stages.sort();
       printStages();
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
    //returns the statistics object for this factory.
    public Stats getStatistics(){
        return stats;
    }
    //Populates the queue list with queues. Queues must be initialized before stages.
    private void initListOfLines(int queueLength, int numQueues){

        //Initialize the list of lines to getSize numQueue.
        listOfLines = new ArrayList<>(numQueues);
        //Declare a new WaitingLine to go into the list of lines.
        WaitingLine<Item> newLine;

        //populate the list of lines with new lines.
        for(int i = 0; i<numQueues;i++){
            //instantiate the new line
            newLine = new WaitingLine<>(queueLength);
            //and place it into the list of lines.
            listOfLines.add(newLine);
        }
    }
    //Populates the a priority queue of stages with new stages and pointers to respective queues.
    private void initStages(int listLength) {
        //Instantiate the priority queue for the stages to live in.
        stages = new SortedList<>();
        //Declare a new stage.
        Stage newStage;
        //populate the list of stages with stages!!
        for(int i = 0; i<listLength;i++){
            //instantiate the new stage
            newStage  = new Stage();
            //and place it into the priority queue.
            stages.add(newStage);
        }
        Iterator<Stage> stageIterator = stages.iterator();
        //Set Stage 1 next queue.
        stageIterator.next().setNextQueue(listOfLines.get(0));
        //Set Stage 2 previous queue.
        stageIterator.next().setPreviousQueue(listOfLines.get(0));

    }
    //prints out the completion times for the priority queue of stages.
    public void printStages(){
        Iterator<Stage> stagesIterator = stages.iterator();
        System.out.println("\n Printing " + stages.getSize() + " stages. Out of order. \n");
        while(stagesIterator.hasNext()){
            //Right now poll() is popping the highest priority stage of list .
            System.out.println(stagesIterator.next().getTimeToComplete());
        }
    }

    //NEEDS WORK: Populate stages with dummy items. This is going to become way more complex and occur at the
    // beginning of each snapshot.
    public void updateFactory(){
        //This flag indicates whether or not changes are still being made.
        boolean changeFlag = true;
        //Set up placeholder for priority stage.
        Stage priorityStage = stages.getHead();
        //Update the global time based on the priority stage.
        theTime += priorityStage.getTimeToComplete();
        //reset the prioritised stage so it will now not have priority. Ever.
        priorityStage.updateTimeToComplete(-1);

        //Now scan for changes
        do {

            /**Fix Starvation*/
            //Iterator for starvation update. Must create new one so it resets.
            Iterator<Stage> starvationIterator = stages.iterator();
            //Go through stages and update them.
            while(starvationIterator.hasNext()){
                switch(starvationIterator.next().getState()){
                    //STARVING
                    case 0:

                    break;

                    //BLOCKED
                    case 1:

                    break;

                    //chill
                    default:

                    break;


                }
            }

            /**Fix Blockages*/
            //Iterator for blockage update. Must create new one so it resets.
            Iterator<Stage> blockageIterator = stages.iterator();
            //Go through stages and update them.
            while(blockageIterator.hasNext()){

            }






        }while(changeFlag); //Continue loop until changes settle.

        //Update global time based on priority item.

        /*Starting by updating the priority stage
        //Pass the completed item to next queue if the stage isn't the last stage in production line.
        if(stages.getHead().getNextQueue()!=null){
            //give the item to the queue if it isn't full
            if(!stages.getHead().getNextQueue().isFull()){
                //pass on the item
                stages.getHead().getNextQueue().addItem(stages.getHead().ejectItem());

                //if the stage was able to pass on its completed item it now wants another one.
                //If this isn't the first stage it will have a previous queue.
                if(stages.getHead().getPreviousQueue()!=null){
                    //if the queue is not empty
                    if(stages.getHead().getPreviousQueue().isEmpty()){
                        //take the item at the head of the queue and give it to the stage.
                        stages.getHead().processItem(stages.getHead().getPreviousQueue().takeItem(queueLength),productionTime);
                    }
                    //else it is empty and now yall gonna STARVE.
                    else{
                        stages.getHead().setState(0);
                    }
                }
                //else if it is the first stage it backs onto stock and yall can just take it.
                else{
                    stages.getHead().processItem(stock.getItem(0),productionTime);
                    stockUp(1);
                }
            }
            //if the queue is full then the stage is BLOCKED. Starving(0), blocked(1), processing(2).
            else{
                //sets state to blocked.
                stages.getHead().setState(1);
            }
        }*/




    }



}
















