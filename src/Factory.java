import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tom on 27-May-18.
 * Factory houses the operation of a discrete time simulation for a production line that processes 'Items'.
 * The number of stages and queues are hardcoded in but changing them will not damage functionality.
 * At the end of factory run time a series of statistics are returned pertaining to the stages and queues of the
 * factory.
 */
public class Factory {


    private double productionTime;                      //This is the time limit for the factory to run.
    private double currentTime;                         //This is where time is up to.
    private double M;                                   //The mean used to calculate processing time for stages.
    private double N;                                   //The range used to calculate processing time for stages.
    private Stats stats = new Stats();                  //Statistics objects that holds information about the factory.
    private ArrayList<WaitingLine>  waitingLines;       //All of the queues for the factory.
    private Stock stock;                                //The infinite supply of items for the factory.
    private SortedList<Stage> stages;                   //The stages of the factory.
    private Stage[] arrayStage= new Stage[8];           //An array of the stages. Keeps them in a nice order for me.


    /**
     * Creating a Factory instantiates the queues and stages of the production lines as well as the stock.
     * The time of the factory is initially 0.
     * @param runTime   The total amount of time the factory is allowed to run for.
     * @param mean      A value used to calculate the processing times for each stage of the factory.
     * @param range     A value used to calculate the processing times for each stage of the factory.
     * @param qLength   The length of the queues used in the factory.
     */
    Factory(double runTime, double mean, double range, int qLength) {

        productionTime = runTime;
        M  = mean;
        N = range;
        currentTime = 0;
        stock = new Stock();
        initWaitingLines(qLength);
        initStages();
        copyStages();
    }

    /**
     * Populates the Array of waitingLines (queues) with waitingLines.
     * @param queueLength This is the initial capacity of all of the queues in the factory.
     *                    The queues do not exceed this size.
     */
    private void initWaitingLines(int queueLength){

        waitingLines = new ArrayList<>(5);
        WaitingLine newLine;

        for(int i = 0; i<5;i++){
            newLine = new WaitingLine(queueLength);
            waitingLines.add(newLine);
        }
    }

    /**
     * Populates the sorted list of stages with new stages. This number of stages is hardcoded.
     */
    private void initStages() {

        stages = new SortedList<>();
        Stage newStage;
        for(int i = 0; i<8;i++){
            newStage  = new Stage(M,N);
            stages.add(newStage);
        }

        assignQueues();
        nameStages();
        setStageModes();
    }

    /**
     * Assigns the connections between the stages and queues. Hardcoded for a specific structure.
     */
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
    }

    /**
     * Assign the names for each stage.
     */
    private void nameStages(){
        Iterator<Stage> stageIterator = stages.iterator();
        Stage stage0 = stageIterator.next();
        Stage stage1 = stageIterator.next();
        Stage stage2a = stageIterator.next();
        Stage stage2b = stageIterator.next();
        Stage stage3 = stageIterator.next();
        Stage stage4a = stageIterator.next();
        Stage stage4b = stageIterator.next();
        Stage stage5 = stageIterator.next();

        stage0.setName("S0");
        stage1.setName("S1");
        stage2a.setName("S2a");
        stage2b.setName("S2b");
        stage3.setName("S3");
        stage4a.setName("S4a");
        stage4b.setName("S4b");
        stage5.setName("S5");

    }

    /**
     * Assign the modes for each stage.
     */
    private void setStageModes(){
        Iterator<Stage> stageIterator = stages.iterator();
        Stage stage0 = stageIterator.next();
        Stage stage1 = stageIterator.next();
        Stage stage2a = stageIterator.next();
        Stage stage2b = stageIterator.next();
        Stage stage3 = stageIterator.next();
        Stage stage4a = stageIterator.next();
        Stage stage4b = stageIterator.next();
        Stage stage5 = stageIterator.next();

        /**Set the modes of stages*/
        stage0.setMode(1);
        stage1.setMode(1);
        stage3.setMode(1);
        stage5.setMode(1);
        stage2a.setMode(2);
        stage4b.setMode(2);
        stage2b.setMode(3);
        stage4a.setMode(3);

    }

    /**
     * Simulates the factories operation for the duration 'productionTime'. At conclusion returns factory statistics.
     */
    public void run()  {

        while(currentTime < productionTime){
            updateFactory();
        }

        stats.printStats();

    }

    /**
     * At the end of each time event (the ejection of an item from a stage) the system must be updated. This includes
     * moving items around in queues and stages, updating statistics and updating time.
     *//*CHECK*/
    public void updateFactory() {


        processChanges();                               //updates item allocation in system.

        stages.sort();
        Stage priorityStage = stages.getHead();         //Highest priority stage.

        /*GET SUS ON WHEN AND HOW STATISTICS ARE STORED*/
        if(priorityStage.getTime()!=-1) {               //-1 means this is the first run. No items have been processed.

            updateStageStats(priorityStage);
            updateTime(priorityStage);
            priorityStage.setState(0);                  //The priorityStage state must be blocked in order to eject.
            priorityStage.setTime(-1);                  //PriorityStage must be reset to zero to cease its priority.

        }
        updateStatistics();
    }

    /**
     * Iterates through the sorted list of stages moving around items forward where possible unblocking stages and
     * feeding starving stages.
     */
    private void processChanges()  {

        boolean changeFlag;                             //Changes made = true. No more changes = false;
        do {
            changeFlag = false;

            for (Stage focusStage : stages) {
                switch (focusStage.getState()) {

                    case -1://Attend to starving stages.
                        if (focusStage.hasPrevious()) {
                            if (!focusStage.isPrevEmpty()) {
                                focusStage.processItem(focusStage.takePrev(currentTime), currentTime);
                                changeFlag = true;
                            }
                        } else {
                            //if there is no previous it is stage0. Take from stock.
                            focusStage.processItem(stock.takeStock(), currentTime);
                            changeFlag = true;
                            stats.updateNumCreated();
                        }
                        break;

                    case 0://Attend to blocked stages.
                        if (focusStage.hasNext()) {
                            if (!(focusStage.isNextFull())) {

                                focusStage.getNextQ().addItem(focusStage.ejectItem(currentTime), currentTime);
                                //System.out.println("Size of queue is: " + focusStage.getNextQ().getSize());
                                changeFlag = true;
                            }
                        }
                        else {
                            //if there is no next queue this is stage5 so just eject.

                            stats.updatePaths(focusStage.ejectItem(currentTime));
                            stats.updateNumProcessed();
                            changeFlag = true;
                        }

                        break;

                        /*Essentially redundant*/
//                    case 1: //If processing
//                        //if this is the case try to offload your shit bruh.
//                        if(focusStage.getTime()<= currentTime) {
//                            if (focusStage.hasNext()) {
//
//                                if (focusStage.isNextFull()) {
//                                    focusStage.setState(0);//blocked now.
//                                } else {
//                                    focusStage.getNextQ().addItem(focusStage.ejectItem(currentTime), currentTime);
//                                    changeFlag = true;
//                                }
//                            } else {//yo is stage 5.
//
//                                stats.updatePaths(focusStage.ejectItem(currentTime));
//                                stats.updateNumProcessed();
//                                changeFlag = true;
//                            }
//                        }
//                        break;
                }

            }
        } while (changeFlag);
    }

    /**
     * Advances the time forward based on the priority stage whose completion time will become the new 'currentTime'.
     * @param priorityStage The stage that will complete processing an item next.
     */
    public void updateTime(Stage priorityStage){

        currentTime = priorityStage.getTime();
        stats.updateTime(currentTime);
    }

    /**
     * Passes the statistics for both the stages and the queues to statistics object.
     */
    private void updateStatistics(){
        for(Stage focusStage: stages){
            stats.updateStageStateTimes(focusStage,currentTime);
        }
        for(int i = 0; i<5;i++){
            stats.updateQueues(waitingLines.get(i), i);
        }
    }

    /**
     * Updates the amount of time each stage has spent in their current state. The amount of time to add
     * is calculated as the highest priority stage's completion time minus the actual time .
     * @param priorityStage The stage which currentTime will next advance to.
     */
    private void updateStageStats(Stage priorityStage){

        for(Stage focusStage : stages) {
            focusStage.updateStatistics(priorityStage.getTime() - currentTime);
        }
    }

    /**
     * Prints out statistics for stages. Used for debugging.
     */
    public void printStages(){
        System.out.println("\nThe time: " + String.format("%4.2f",currentTime)+"\n");
        for(Stage focusStage: stages){


            if(focusStage.getItem()!=null) {

                String timeToComplete = String.format("%4.2f",focusStage.getTime());
                String timeStarving = String.format("%4.2f",focusStage.getTimeStarving());
                String timeBlocked = String.format("%4.2f",focusStage.getTimeBlocked());
                String timeProcessing = String.format("%4.2f",focusStage.getTimeProducing());
                String name = focusStage.getName();


                System.out.println(
                        name + "\t\tCompletion Time: " + timeToComplete + "\t\t\t\tTime Processing: " + timeProcessing
                                + "\t\t\tTime Starving: " +  timeStarving + "\t\t\tTime Blocked: " + timeBlocked
                );
            }

        }
        int counter = 0;
        System.out.println("\n");
        for(WaitingLine line: waitingLines){
            System.out.println("Queue " + counter + ": " + line.getSize());
            counter++;
        }

        System.out.println("\n\n");
    }

    /**
     * Populates the array of the stages creating a fix order representing their order in the production line.
     */
    private void copyStages(){
        Iterator<Stage> stageIterator = stages.iterator();
        for(int i = 0;i<8;i++){
            arrayStage[i] = stageIterator.next();
        }
    }
}























