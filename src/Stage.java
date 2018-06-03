/**
 * Created by tom on 26/05/2018.
 * A Stage processes an item until it is finished processing. The time it takes for a stage to process an item is
 * specific to the stage and is based on a mean, range and item's random value. A stage has 3 states; starving,
 * blocked and processing. The state of the stage is dependent on the queues that precede and proceed it. There are
 * three important statistics for a stage, the time it is blocked, starving and processing. These values are updated
 * every time time advances.
 */
public class Stage implements Comparable<Stage> {

    private String stageName;                       //ID of the stage.
    private Item item;                              //Item housed in this stage.
    private int state;                              //Starving -1, blocked 0, processing 1.
    private double mean;                            //used for processing time calculation.
    private double range;                           //used for processing time calculation.
    private WaitingLine nextQueue;                  //Points to next queue. For stage5 is null.
    private WaitingLine previousQueue;              //Points to previous queue. For stage0 is null.
    private double time;                            //Time stage will complete processing.
    private int mode;                               //Processing time mode. S[0,1,3,5] 1, S[S2a,S4b] 2, S[2b,4a] 3.
    private double timeStarving;
    private double timeProducing;
    private double timeBlocked;


    /**
     * Initializes a new Stage with default values.
     * @param m The Mean time value. Used for calculating item completion time.
     * @param n The Range time value. USed for calculating item completion time.
     */
    Stage(double m, double n){
        state = -1;
        item= null;
        time  = -1;
        stageName = "Stage name not set";
        mean  = m;
        range = n;
    }

    /**
     * Takes an item and begins processing it in the stage. It sets the entering time for the item and records that the
     * item has entered this stage on its path to completion. The state of the stage is set to processing (1).
     * If the stage is blocked or processing the item will not be admitted and an error message is generated.
     * @param itemToProcess The item that is being processed in the stage.
     * @param theTime The time that the item began processing in the stage.
     */
    public void processItem(Item itemToProcess, double theTime){

        if(state==-1){

            item = itemToProcess;
            calculateCompletionTime(theTime);
            item.setTimeEntering(theTime);
            item.updatePath(this.getName());
            state = 1;
        }
        else {

            System.out.println("Stage is already full");
        }
    }

    /**
     * Removes the item being processed in the stage by setting "item" to null. The item that has been removes is
     * returned. The time at which the item leaves the stage is recorded in the item.
     * @param theTime The current time when the item is ejected from the stage.
     * @return The item that is getting ejected is returned.
     */
    public Item ejectItem(double theTime){

        if(item==null){

            System.out.println("There is no item in this stage. Null was returned.");
            return null;
        }
        else{

            item.setTimeLeaving(theTime);           //theTime is the current time of the factory.
            Item temp = item;
            item = null;
            time = - 1;
            state = -1;                             //If the stage is empty then it is starving.
            return temp;
        }
    }

    /**
     * Override of the compareTo method of the Comparable interface. It returns the priority of this stage as compared
     * to another based on the time at which the stages will finish processing where the sooner this is the higher
     * the priority of that stage.
     * @param stageToCheck The stage that is being compared to this stage.
     * @return 0 - if this stage has priority. 1 - if the stage being compared to has higher priority.
     */
    public int compareTo(Stage stageToCheck){

        if(time == -1 && stageToCheck.getTime() == -1) {
            return 0;                                       //priority to this.
        }
        else if(time == -1) {
            return 1;                                       //priority to challenger.
        }else if(stageToCheck.getTime() == -1){
            return 0;                                       //priority to this.
        }else{
            if(time < stageToCheck.getTime() || time == stageToCheck.getTime()){
                return 0;                                   //priority to this.
            }else{
                return 1;                                   //priority to challenger.
            }

        }
    }

    /**
     * Updates the amount of time that the stage has spent in a state. I.e. starving, blocked or processing.
     * @param advanceTime This is the amount that the value for time spent in a state will be increased by. It is the
     *                    difference between the current time and the future time that the system is about to go to.
     */
    public void updateStatistics(double advanceTime){

        switch(state){
            //starving
            case -1:
                timeStarving += advanceTime;
                break;
            //blocked
            case 0:
                timeBlocked += advanceTime;
                break;
            //processing
            case 1:
                timeProducing += advanceTime;
                break;
        }
    }

    /**
     * Checks if the stage has a previous queue. If it doesn't then it must be the first queue in the production line.
     * @return True - if stage has a previous queue. False - if the stage has no previous queue.
     */
    public boolean hasPrevious(){
        if(previousQueue == null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks if the stage has a queue following it. If it doesn't it must be the last queue in the production line.
     * @return True - if the stage has a next queue. False - if the stage doesn't have a next queue.
     */
    public boolean hasNext(){
        if(nextQueue == null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks if the previous queue has any items stored in it. If it is empty the stage will starve.
     * @return True - the previous queue is empty. False - items are stored in the previous queue.
     */
    public boolean isPrevEmpty(){
        return previousQueue.isEmpty();
    }

    /**
     * Checks if the next queue is full. If it is full the stage will be blocked.
     * @return True - the next queue is full. False - the next queue is not full.
     */
    public boolean isNextFull(){
        return nextQueue.isFull();
    }

    /**
     * Calculates the time at which the item that is in the stage will complete processing. Each stage has a specific
     * expression that determines their completion times and it is selected using their mode. The processing time is
     * dependent on mean, range, item random value and the time that the item was placed in the stage.
     * @param theTime The time that the item was placed in the stage.
     */
    private void  calculateCompletionTime(double theTime){
        switch(mode)
        {
            case 1:  //stages 0, 1, 3 & 5.
                time =  theTime + (mean + range*(item.getRand()-0.5));
                break;

            case 2: //Stages 2a & 4b.
                time = theTime + (2*mean + 2*range*(item.getRand()-0.5));
                break;

            case 3: //Stages 2b & 4a.
                time =  theTime + (mean + 0.5*range*(item.getRand()-0.5));
                break;
        }
    }

    /**
     * Takes an item from the previous queue. The item will be taken from the head of the queue.
     * @param theTime The time when the item is taken. This is used to set the time that the item is leaving the queue.
     * @return The item that was taken from the previous queue.
     */
    public Item takePrev(double theTime){
        return previousQueue.takeItem(theTime);
    }

    public void setNextQ(WaitingLine nextList){nextQueue = nextList;}
    public void setPreviousQ(WaitingLine prevList) {previousQueue = prevList;}
    public void setState(int theState){
        state = theState;
    }
    public void setName(String name){
        stageName = name;
    }
    public void setMode(int theMode){
        mode = theMode;
    }
    public int getState() {return state;}
    public double getTimeStarving() {
        return timeStarving;
    }
    public double getTimeProducing() {
        return timeProducing;
    }
    public double getTimeBlocked() {
        return timeBlocked;
    }
    public double getTime(){ return time; }
    public Item getItem(){
        return item;
    }
    public String getName(){
        return stageName;
    }
    public WaitingLine getNextQ(){ return nextQueue; }

    public void setTime(double i) {
        time = i;
    }
}
