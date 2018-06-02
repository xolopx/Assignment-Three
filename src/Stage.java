import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by tom on 26/05/2018.
 *
 * Processes an item. Points to next and previous WaitLine.
 * Has 3 stages:
 *  - processing
 *  - blocked
 *  - starving
 */
public class Stage implements Comparable<Stage> {

    private String stageName;
    //This is the item that's getting processed.
    private Item stageItem;
    //This indicates whether or not the Stage is STARVING (-1) or BLOCKED (0) or PROCESSING (1).
    // Starving implies it is empty and blocked implies it is full and complete. Processing means it's full but item
    // is not complete yet.
    private int state;
    //the mean used for time calculation
    private double mean;
    //the range used for time calculation
    private double range;
    //This is a pointer to the next WaitingLine/Queue.
    private WaitingLine<Item> nextQueue;
    //This is a pointer to the previous WaitingLine/Queue.
    private WaitingLine<Item> previousQueue;
    //This is time at which the item being processed will complete.
    private double timeToComplete;
    //this is the mode that determines the time calculation for the stage.
    // 1, 2 or 3.
    private int mode;
    //time spent in production
    private double timeProducing;


    //Constructor.
    Stage(){
        //initially state is starving.
        state = -1;
        //initially there is no item in any stages.
        stageItem= null;
        //initially there is no item inside the stage so there's no time to complete. -1 no item inside.
        timeToComplete = -1;
        //default stage name
        stageName = "Stage name not set";
    }
    //Constructor.
    Stage(String name){
        //initially state is starving.
        state = -1;
        //initially there is no item in any stages.
        stageItem= null;
        //initially there is no item inside the stage so there's no time to complete. -1 no item inside.
        timeToComplete = -1;
        //Name of the stage. Helps with debugging etc.
        stageName = name;

    }
    //Constructor.
    Stage(double m, double n){
        //initially state is starving.
        state = -1;
        //initially there is no item in any stages.
        stageItem= null;
        //initially there is no item inside the stage so there's no time to complete. -1 no item inside.
        timeToComplete = -1;
        //Name of the stage. Helps with debugging etc.
        stageName = "Stage name not set";
        mean  = m;
        range = n;


    }
    //Puts in a new item to be processed by the Stage and records the time when it will finish processing.
    //mode determines which time calculation is used.
    public void processItem(Item itemToProcess, double theTime){
        //State is starving so it can take more food.
        if(state==-1) {
            stageItem= itemToProcess;
            //calculation selection.
            switch(mode){

                //stages 0, 1, 3 & 5.
                case 1:
                    timeToComplete = theTime + (mean + range*(stageItem.getTime()-0.5));
                break;
                //Stages 2a & 4b.
                case 2:
                    timeToComplete = theTime + (2*mean + 2*range*(stageItem.getTime()-0.5));
                break;
                //Stages 2b & 4a.
                case 3:
                    timeToComplete = theTime + (mean + 0.5*range*(stageItem.getTime()-0.5));
                break;
            }
            //give the item the time it is entering the stage.
            stageItem.setTimeEntering(theTime);

            //stage is now eating.
            state = 1;
        }else{
            System.out.println("Homes you just tried to put an item into a stage that's already full.");
        }
    }
    //Removes the item inside the stage and returns it. Resets timer to -1 and state to -1 indicating
    // that it's starving.
    public Item ejectItem(double theTime){
        if(stageItem!=null){
            stageItem.setTimeLeaving(theTime);
            //stored temporary copy of the item that was being processed.
            Item temp = stageItem;
            //set the item inside the stage to zero
            stageItem = null;
            //set the time of stage to complete to -1 because it's empty.
            timeToComplete = - 1;
            //State is now starving as it's empty.
            state = -1;
            return temp;
        }
        else{
            System.out.println("There was not item being processed in this stage to be passed on. Null was returned.");
            return null;
        }
    }
    //Compares priority of the stages, where highest priority is given to the stage with an item that will complete
    //processing the soonest.
    public int compareTo(Stage stageToCheck){
        /**State indicates how two stages compare to each other based on their items processing tim to completion. If
         * an item has no item processing inside it right now, its pointer will be set to null and timeToCompletion will
         * be set to 0.
         * -1 - less than
         *  1 - greater than
         */

        //Greater than cases. Includes equal to event.
        if(timeToComplete == stageToCheck.getTimeToComplete() || timeToComplete < stageToCheck.getTimeToComplete()){
            return 1;
        }else{
            return -1;
        }

    }
    //Returns the time when the item being processed will be complete by the stage.
    public double getTimeToComplete(){ return timeToComplete; }
    //Updates the time to complete for an item because another stage will have completed processing.
    public void updateTimeToComplete(double newTime){
        timeToComplete = newTime;
    }
    /**QUEUES*/
    //set the next queue.
    public void setNextQueue(WaitingLine<Item> nextList){nextQueue = nextList;}
    //set the previous array list.
    public void setPreviousQueue(WaitingLine<Item> prevList) {previousQueue = prevList;}
    //Returns the previous queue.
    public WaitingLine<Item> getPreviousQueue(){
        return previousQueue;
    }
    //Returns the next queue.
    public WaitingLine<Item> getNextQueue(){
        return nextQueue;
    }
    //Does the Stage have a previous?
    public boolean hasPrevious(){
        if(previousQueue == null){
            return false;
        }
        else{
            return true;
        }
    }
    //Does the stage have a next queue?
    public boolean hasNext(){
        if(nextQueue == null){
            return false;
        }
        else{
            return true;
        }
    }
    /**STATE*/
    //returns the state of the stage.
    //STARVING   = -1 (stage is empty).
    //BLOCKED    =  0  (stage is complete and cannot eject item).
    //PROCESSING =  1  (stage has an item in it but processing has not finished).
    public int getState(){return state;}
    //sets the state of the stage.
    public void setState(int theState){
        state = theState;
    }
    //returns the name of the stage.
    public String getName(){
        return stageName;
    }
    //sets the stage name.
    public void setName(String name){
        stageName = name;
    }
    //set the mode of the stage. The mode determines the type of calculation for time.
    public void setMode(int theMode){
        mode = theMode;
    }








}
