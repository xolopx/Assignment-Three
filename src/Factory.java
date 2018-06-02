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
    //the number of queues.
    private int numQueues;

    //An arrayList of queues. The waiting lines feed the stages that they precede.
    private ArrayList<WaitingLine<Item>> waitingLines;
    //The stock feeds the first stage.
    private Stock stock;
    //An priority queue of the stages in the factory.
    private SortedList<Stage> stages;



    /**Constructor initializes the necessary components of the factory.*/
    Factory(double runTime, double mean, double range, int ql, int numStages, int nQ) {

        productionTime = runTime;
        M  = mean;
        N = range;
        theTime = 0;
        queueLength = ql;
        numQueues = nQ;
        stock = new Stock(mean, range);
        initWaitingLines(queueLength, nQ);     //Initialized before stages as stages point to the queues.
        initStages(numStages);                          //populate stages collection with stages.
    }

    //This runs the factory.
    public void run() {

        while(theTime < productionTime){
            updateFactory();     //sets all the shit. This is your big boi.
            printStages();
        }



    }
    //returns the statistics object for this factory.
    public Stats getStatistics(){
        return stats;
    }
    //Populates the queue list with queues. Queues must be initialized before stages.
    private void initWaitingLines(int queueLength, int numQueues){

        //List's capacity is numQueues.
        waitingLines = new ArrayList<>(numQueues);
        //Declare a new WaitingLine to go into the list of lines.
        WaitingLine<Item> newLine;

        //populate the list of lines with new lines.
        for(int i = 0; i<numQueues;i++){
            //Create a new waitingLine.
             newLine = new WaitingLine<>(queueLength);
            //and place it into the list of lines.
            waitingLines.add(newLine);
        }
    }
    //Populates the a priority queue of stages with new  stages and pointers to respective queues.
    private void initStages(int numberOfStages) {
        //Create a sorted list for the stages to live in.
        stages = new SortedList<>();
        //Declare a new stage.
        Stage newStage;
        //populate the sortedList with stages.
        for(int i = 0; i<numberOfStages;i++){
            //instantiate the new stage
            newStage  = new Stage();
            //and place it into the priority queue.
            stages.add(newStage);
        }
        //Create an iterator to assign stages.
        Iterator<Stage> stageIterator = stages.iterator();
        //Set Stage 1 next queue.
        Stage stage1 = stageIterator.next();
        //Set Stage 2 previous queue.
        Stage stage2 = stageIterator.next();

        //Assign Queues to stages.
        //stage1 has no previous but does have a next.
        stage1.setNextQueue(waitingLines.get(0));
        //stage2 has no next but does have a previous.
        stage2.setPreviousQueue(waitingLines.get(0));
    }
    //prints out the completion times for the priority queue of stages.
    public void printStages(){
        Iterator<Stage> stagesIterator = stages.iterator();
        System.out.println("\n Printing " + stages.getSize() + " stages. \n");
        while(stagesIterator.hasNext()){
            //Right now poll() is popping the highest priority stage of list .
            System.out.println(stagesIterator.next().getTimeToComplete());
        }
    }

    //NEEDS WORK: Populate stages with dummy items. This is going to become way more complex and occur at the
    // beginning of each snapshot.
    public void updateFactory() {
        //sort the stages. Now the head is the highest priority.
        stages.sort();
        //Set up placeholder for priority stage.
        Stage priorityStage = stages.getHead();
        //This implies that it is the first run through. As such you don't want to update the time to -1. Bad.
        if(theTime!=0) {
            //Advance the global time to the head of the global time.
            theTime = priorityStage.getTimeToComplete();
            //reset the heads time so that it cannot have priority.
        }
        //Change the time of the highest priority stage to -1 so that if it get's blocked it doesn't keep being the
        // highest priority.
        priorityStage.updateTimeToComplete(-1);

        //This flag indicates whether or not changes are still being made to the stages.
        boolean changeFlag;

        //Do while loop will continually update the stages until no more changes can be made.
        // At this point the function will end.
        //There is no case for processing as things that are processing must wait for next time incrementation.
        do {
            //Reset the changeFlag to false at the start of each loop. If I change it made it will go to true.
            changeFlag = false;


            /**FIX STARVATION*/
            //Iterator for starvation update. Must create new one so it resets.
            Iterator<Stage> starvationIterator = stages.iterator();
            //reusable stage placeholder to test on.
            Stage focusStage;
            //Go through stages and update them removing starvation where possible.
            while (starvationIterator.hasNext()) {

                //Hold the stage that changes are being made on.
                focusStage = starvationIterator.next();
                //hold previous queue of stage
                WaitingLine<Item> prevQ = focusStage.getPreviousQueue();

                //if the stage is starving and it's not the first stage.
                if (focusStage.getState() == -1 && focusStage.hasPrevious()) {

                    //Check if previous queue has food in it.
                    if (!prevQ.isEmpty()) {
                        //take from the start of the queue.
                        focusStage.processItem(prevQ.takeItem(0), theTime);
                        //A stage got fed so change occurred.
                        changeFlag = true;
                    }
                    //ELSE the queue was empty so keep on starving poor stage boi.
                }
                //else if it's starving and it's the first stage take from stock.
                else if (focusStage.getState() == -1) {
                    //stock will automatically replenish.
                    focusStage.processItem(stock.takeItem(0), productionTime);
                    //stage got fed so change occurred.
                    changeFlag = true;
                }
            }//END STARVATION WHILE.

            /*FIX BLOCKAGES*/
            //Iterator for blockage update. Must create new one so it resets.
            Iterator<Stage> blockageIterator = stages.iterator();
            //Go through stages and update them removing blockages where possible.
            while (blockageIterator.hasNext()) {

                //Hold the stage that changes are being made on.
                focusStage = blockageIterator.next();

                //if the stage is blocked and it's not the last stage.
                if (focusStage.getState() == 0 && focusStage.hasNext()) {
                    //holds next queue of stage.
                    WaitingLine<Item> nextQ = focusStage.getNextQueue();
                    //Check if next queue has room in it.
                    if (!nextQ.isFull()) {
                        //append the processed item into the end of the next queue.
                        nextQ.addItem(focusStage.ejectItem());
                        //ejection so change was made.
                        changeFlag = true;
                    }
                    //ELSE the queue was empty so keep on starving poor stage boi.
                }
                //If it's the last stage and it's blocked just eject.
                else if (focusStage.getState() == 0) {
                    focusStage.ejectItem();
                    //a change was made.
                    changeFlag = true;
                }
            }//END BLOCKAGE WHILE.

        } while (changeFlag); //END DO-WHILE

    }

}




















