/**
 * Created by Tom on 27-May-18.
 * Runs the operation of a single production line and gather statistics on production.
 */
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;


public class Factory {


    private double productionTime;                      //This is the time limit for the factory to run.
    private double theTime;                             //This is where time is up to.
    private double M;                                   //The mean used to calculate processing time for stages.
    private double N;                                   //The range used to calculate processing time for stages.
    private Stats stats = new Stats();                  //Statistics objects that holds information about the factory.
    private ArrayList<WaitingLine<Item>> waitingLines;  //All of the queues for the factory.
    private Stock stock;                                //The infinite supply of items for the factory.
    private SortedList<Stage> stages;                   //The stages of the factory.




    Factory(double runTime, double mean, double range, int qLength) {

        productionTime = runTime;
        M  = mean;
        N = range;
        theTime = 0;
        stock = new Stock(mean, range);
        initWaitingLines(qLength);
        initStages();
    }

    /*PUBLIC*/

    public void run()  {
        int round = 0;
        while(theTime < productionTime){
            updateFactory();
            System.out.println("Round: " + round + "\t Time: " + theTime);
            printStages();
            System.out.println("\n");

            round++;
        }
        stats.printStats();
    }
    public void updateFactory() {


        stages.sort();
        Stage priorityStage = stages.getHead();         //Highest priority stage.

        if(priorityStage.getTime()!=-1) {               //-1 means this is the first run. No items have been processed.
            updateStageStats(priorityStage);
            updateTime(priorityStage);
            priorityStage.setState(0);                  //The priorityStage state must be blocked in order to eject.
            priorityStage.setTime(-1);                  //PriorityStage must be reset to zero to cease its priority.
        }

        processChanges();                               //updates item allocation in system.

    }

    /*PRIVATE*/

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
    private void initStages() {

        stages = new SortedList<>();
        Stage newStage;
        for(int i = 0; i<8;i++){
            newStage  = new Stage(M,N);
            stages.add(newStage);
        }

        assignQueues();
    }
    private void updateStageStats(Stage priorityStage){

        for(Stage focusStage : stages) {
            focusStage.updateStatistics(priorityStage.getTime() - theTime);
        }


    }
    private void assignQueues(){
        Iterator<Stage> stageIterator = stages.iterator();
        Stage stage0 = stageIterator.next();
        Stage stage1 = stageIterator.next();
        Stage stage2a = stageIterator.next();
        Stage stage2b = stageIterator.next();
        Stage stage3 = stageIterator.next();
        Stage stage4a = stageIterator.next();
        Stage stage4b = stageIterator.next();
        Stage stage5 = stageIterator.next();

        /**Assign Queues to stages.*/
        //stage1 has no previous but does have a next.
        stage0.setNextQ(waitingLines.get(0));
        //stage2 has prev and next.
        stage1.setPreviousQ(waitingLines.get(0));
        stage1.setNextQ(waitingLines.get(1));
        //stage2a has both and shares with stage2b.
        stage2a.setPreviousQ(waitingLines.get(1));
        stage2a.setNextQ(waitingLines.get(2));
        //stage2b has both and shares with stage 2a.
        stage2b.setPreviousQ(waitingLines.get(1));
        stage2b.setNextQ(waitingLines.get(2));
        //stage3 has both
        stage3.setPreviousQ(waitingLines.get(2));
        stage3.setNextQ(waitingLines.get(3));
        //stage4a has both and shares with stage4b.
        stage4a.setPreviousQ(waitingLines.get(3));
        stage4a.setNextQ(waitingLines.get(4));
        //stage4b has both and shares with stage4a.
        stage4b.setPreviousQ(waitingLines.get(3));
        stage4b.setNextQ(waitingLines.get(4));
        //stage5 has no next but it does have a previous.
        stage5.setPreviousQ(waitingLines.get(4));

        /**Set the modes of stages*/
        stage0.setMode(1);
        stage1.setMode(1);
        stage2a.setMode(2);
        stage2b.setMode(3);
        stage3.setMode(1);
        stage4a.setMode(3);
        stage4b.setMode(2);
        stage5.setMode(1);

        stage0.setName("S0");
        stage1.setName("S1");
        stage2a.setName("S2a");
        stage2b.setName("S2b");
        stage3.setName("S3");
        stage4a.setName("S4a");
        stage4b.setName("S4b");
        stage5.setName("S5");



    }
    private void processChanges() {

        boolean changeFlag;                             //Changes made = true. No more changes = false;
        do {
            changeFlag = false;

            for (Stage focusStage : stages) {
                switch (focusStage.getState()) {

                    case -1://Attend to starving stages.
                        if (focusStage.hasPrevious()) {
                            if (!focusStage.isPrevEmpty()) {
                                focusStage.processItem(focusStage.takePrev(0), theTime);
                                changeFlag = true;
                            }
                        } else {
                            //if there is no previous it is stage0. Take from stock.
                            focusStage.processItem(stock.takeItem(0), theTime);
                            changeFlag = true;
                            stats.updateNumCreated();
                        }
                        break;

                    case 0://Attend to blocked stages.
                        if (focusStage.hasNext()) {
                            if (!focusStage.isNextFull()) {
                                Item ejectedItem  = focusStage.ejectItem(theTime);
                                focusStage.getNextQ().addItem(ejectedItem);

                                changeFlag = true;
                            }
                        } else {
                            //if there is no next queue this is stage5 so just eject.

                            focusStage.ejectItem(theTime);
                            stats.updateNumProcessed();
                            changeFlag = true;
                        }
                        break;
                }
            }

        } while (changeFlag);
    }
    public void updateTime(Stage priorityStage){

        theTime = priorityStage.getTime();
        stats.updateTime(theTime);
    }
    public void printStages(){

        for(Stage focusStage: stages){
            if(focusStage.getItem()!=null) {
                double timeToComplete = focusStage.getTime();
                String name = focusStage.getName();
                double itemStart = focusStage.getItem().getTimeEntering();
                double itemEnd = focusStage.getItem().getTimeLeaving();

                System.out.println(
                        name + ": \tTime to Complete: " + timeToComplete + "\tItem Start: " + itemStart + "\t"
                        + "Item End: " + itemEnd
                );
            }

        }

    }

}























