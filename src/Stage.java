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
    private Item itemProcessing;
    //This indicates whether or not the Stage is starving (0), blocked (1) or processing (2).
    private int state;
    //This is a pointer to the next WaitingLine/Queue.
    private ArrayList<Item> nextQueue;
    //This is a pointer to the previous WaitingLine/Queue.
    private ArrayList<Item> previousQueue;
    //This is time at which the item being processed will complete.
    private int timeToComplete;


    //Constructor.
    Stage(){
        //initially state is starving.
        state = 0;
        //initially there is no item in any stages.
        itemProcessing = null;
        //initially there is no item inside the stage so there's no time to complete.
        timeToComplete = 0;
    }
    //Takes an item to be processed.
    public void processItem(Item itemToProcess){
        itemProcessing = itemToProcess;
    }
    //Passes on the item that has been processed.
    //TO BE MODIFIED. CURRENTLY RETURNS VOID.
    public void ejectItem(){
        if(itemProcessing!=null){
            //Currently just removes the item from the stage. TO BE MODIFIED TO RETURN THE ITEM ALSO.

        }
        else{
            System.out.println("There was not item being processed in this stage to be passed on.");
        }
    }
    //Compares priority of the stages, where highest priority is given to the stage with an item that will complete
    //processing the soonest.
    public int compareTo(Stage stageToCheck){
        /**State indicates how two stages compare to each other based on their items processing tim to completion. If
         * an item has no item processing inside it right now, its pointer will be set to null and timeToCompletion will
         * be set to 0.
         * -1 - less than
         * 0 - equal to
         * 1 - greater than
         */


        //If the stage currently has no item being processed return less than. It has no priority.
        if(itemProcessing == null){
            return -1;
        }else if(stageToCheck == null){
            return 1;
        }
        //Time of stage was less than other so priority is higher. or equal to (highly unlikely).
        else if(timeToComplete <= stageToCheck.getTimeToComplete()){
            return 1;
        }
        //time of stage was greater so it has less priority.
        else if(timeToComplete > stageToCheck.getTimeToComplete()){
            return -1;
        }
        //If we get to this stage something strange happened.
        else {
            System.out.println("How did you get here? What happened in compareTo that is went so wrong.");
            return 1;
        }


    }
    //Returns the time when the item being processed will be complete.
    public double getTimeToComplete(){
       if(itemProcessing != null){
           return timeToComplete;
       }else{
           //There was no item stored in the
           System.out.println("You just tried to get a completion time for a stage that has no item in it. Panic.");
           return -1;
       }

    }


}
