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


    //An arrayList of queues. The waiting lines feed the stages that they precede.
    private ArrayList<WaitingLine<Item>> waitingLines;
    //The stock feeds the first stage.
    private Stock stock;
    //An priority queue of the stages in the factory.
    private SortedList<Stage> stages;



    /**Constructor initializes the necessary components of the factory.*/
    Factory(double runTime, double mean, double range, int qLength) {

        productionTime = runTime;
        M  = mean;
        N = range;
        theTime = 0;
        stock = new Stock(mean, range);
        initWaitingLines(qLength);
        initStages();
    }

    //This runs the factory.
    public void run() {


        while(theTime < productionTime){
            updateFactory();     //sets all the shit. This is your big boi.
            //printStages();
        }

        //factory has completed manufacture so print statistics
        stats.printStats();
    }
    //Populates the queue list with queues. Queues must be initialized before stages.
    private void initWaitingLines(int queueLength){

        //List's capacity is numQueues.
        waitingLines = new ArrayList<>(5);
        //Declare a new WaitingLine to go into the list of lines.
        WaitingLine<Item> newLine;

        //populate the list of lines with new lines.
        for(int i = 0; i<5;i++){
            //Create a new waitingLine.
             newLine = new WaitingLine<>(queueLength);
            //and place it into the list of lines.
            waitingLines.add(newLine);
        }
    }
    //Populates the a priority queue of stages with new  stages and pointers to respective queues.
    private void initStages() {
        //Create a sorted list for the stages to live in.
        stages = new SortedList<>();
        //Declare a new stage.
        Stage newStage;
        //populate the sortedList with stages.
        for(int i = 0; i<8;i++){
            //instantiate the new stage
            newStage  = new Stage(M,N);
            //and place it into the priority queue.
            stages.add(newStage);
        }
        //Create an iterator to assign stages.
        Iterator<Stage> stageIterator = stages.iterator();
        /**Assign stages*/
        Stage stage0 = stageIterator.next();
        Stage stage1 = stageIterator.next();//Set Stage 1 next queue.
        Stage stage2a = stageIterator.next();//Set Stage 1 next queue.
        Stage stage2b = stageIterator.next();//Set Stage 1 next queue.
        Stage stage3 = stageIterator.next();//Set Stage 1 next queue.
        Stage stage4a = stageIterator.next();//Set Stage 1 next queue.
        Stage stage4b = stageIterator.next();//Set Stage 1 next queue.
        Stage stage5 = stageIterator.next();//Set Stage 1 next queue.

        /**Assign Queues to stages.*/
        //stage1 has no previous but does have a next.
        stage0.setNextQueue(waitingLines.get(0));
        //stage2 has prev and next.
        stage1.setPreviousQueue(waitingLines.get(0));
        stage1.setNextQueue(waitingLines.get(1));
        //stage2a has both and shares with stage2b.
        stage2a.setPreviousQueue(waitingLines.get(1));
        stage2a.setNextQueue(waitingLines.get(2));
        //stage2b has both and shares with stage 2a.
        stage2b.setPreviousQueue(waitingLines.get(1));
        stage2b.setNextQueue(waitingLines.get(2));
        //stage3 has both
        stage3.setPreviousQueue(waitingLines.get(2));
        stage3.setNextQueue(waitingLines.get(3));
        //stage4a has both and shares with stage4b.
        stage4a.setPreviousQueue(waitingLines.get(3));
        stage4a.setNextQueue(waitingLines.get(4));
        //stage4b has both and shares with stage4a.
        stage4b.setPreviousQueue(waitingLines.get(3));
        stage4b.setNextQueue(waitingLines.get(4));
        //stage5 has no next but it does have a previous.
        stage5.setPreviousQueue(waitingLines.get(4));

        /**Set the modes of stages*/
        stage0.setMode(1);
        stage1.setMode(1);
        stage2a.setMode(2);
        stage2b.setMode(3);
        stage3.setMode(1);
        stage4a.setMode(3);
        stage4b.setMode(2);
        stage5.setMode(1);




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
    //updates the stages, queues and statistics of the factory.
    public void updateFactory() {

        //By sorting the stages the stage that will next process an item becomes the list's head.
        stages.sort();
        //Take the head (highest priority stage).
        Stage priorityStage = stages.getHead();
        //If this value is true it means that this is the first run through and the time should not be updated yet.
        if(priorityStage.getTimeToComplete()!=-1) {
            //Advance the global time to the head of the global time.
            theTime = priorityStage.getTimeToComplete();
            //reset the heads time so that it cannot have priority.
            priorityStage.updateTimeToComplete(-1);
            //Set priority stages state to blocked as it is finished processing and it's item is to be moved on.
            priorityStage.setState(0);
            //Update the time in the statistics.
            stats.updateTime(theTime);
        }

        //This flag indicates whether or not changes are still being made to the stages.
        boolean changeFlag;

        /**Do while loop will continually update the stages until no more changes can be made.
         * At this point the function will end.
         * There is no case for processing as things that are processing must wait for next time incrementation.
         * */
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
                    focusStage.processItem(stock.takeItem(0), theTime);
                    //stage got fed so change occurred.
                    changeFlag = true;
                    //increment the items that have been created.
                    stats.incItmCreate();
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
                        nextQ.addItem(focusStage.ejectItem(theTime));
                        //ejection so change was made.
                        changeFlag = true;
                    }
                    //ELSE the queue was empty so keep on starving poor stage boi.
                }
                //If it's the last stage and it's blocked just eject.
                else if (focusStage.getState() == 0) {
                    focusStage.ejectItem(theTime);
                    //a change was made.
                    changeFlag = true;
                    //increment the items that have been completely processed.
                    stats.incItmProcessed();
                }
            }//END BLOCKAGE WHILE.




        } while (changeFlag); //END DO-WHILE

    }





    //returns the statistics object for this factory.
    public Stats getStatistics(){
        return stats;
    }
}




















