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

    //This is the item that's getting processed.
    private Item stageItem;
    //This indicates whether or not the Stage is starving (0), blocked (1) or processing (2).
    private int state;
    //This is a pointer to the next WaitingLine/Queue.
    private WaitingLine<Item> nextQueue;
    //This is a pointer to the previous WaitingLine/Queue.
    private WaitingLine<Item> previousQueue;
    //This is time at which the item being processed will complete.
    private double timeToComplete;


    //Constructor.
    Stage(){
        //initially state is starving.
        state = 0;
        //initially there is no item in any stages.
        stageItem= null;
        //initially there is no item inside the stage so there's no time to complete.
        timeToComplete = 0;
    }
    //Puts in a new item to be processed by the Stage and records the time when it will finish processing.
    public void processItem(Item itemToProcess, double theTime){
        //if state is already processing cannot take another item.
        if(state != 2) {
            stageItem= itemToProcess;
            timeToComplete = stageItem.getTime() + theTime;
        }else{
            System.out.println("Homes you just tried to put an item into a stage that's processing. Check yourself.");
        }
    }
    //Ejects the item from the stage. Sets Item inside stage to null and returns the item that was being processed.
    //Resets the time to completion to zero indicating that the stage is not processing an item.
    public Item ejectItem(){
        if(stageItem!=null){
            //stored temporary copy of the item that was being processed.
            Item temp = stageItem;
            //set the item inside the stage to zero
            stageItem = null;
            //set the time to complete
            return temp;
        }
        else{
            System.out.println("There was not item being processed in this stage to be passed on.");
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
            return -1;
        }else{
            return 1;
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
    // 0 = starving
    // 1 = blocked (implies that the stage has an item in it and cannot get rid of it.
    // 2 = processing.
    public int getState(){
        return state;
    }
    //sets the state of the stage.
    public void setState(int theState){
        state = theState;
    }






}
