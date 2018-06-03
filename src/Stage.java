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

    private String stageName;                       //ID of the stage.
    private Item item;                              //Item housed in this stage.
    private int state;                              //Starving -1, blocked 0, processing 1.
    private double mean;                            //used for processing time calculation.
    private double range;                           //used for processing time calculation.
    private WaitingLine nextQueue;            //Points to next queue. For stage5 is null.
    private WaitingLine previousQueue;        //Points to previous queue. For stage0 is null.
    private double time;                            //Time stage will complete processing.
    private int mode;                               //Processing time mode. S[0,1,3,5] 1, S[S2a,S4b] 2, S[2b,4a] 3.
    private double timeStarving;
    private double timeProducing;
    private double timeBlocked;


    Stage(double m, double n){
        state = -1;
        item= null;
        time  = -1;
        stageName = "Stage name not set";
        mean  = m;
        range = n;
    }
    //double theTime - is the time of the factory when Item begins processing.
    public void processItem(Item itemToProcess, double theTime){

        if(state==-1){

            item = itemToProcess;
            time = calculateCompletionTime(theTime);
            item.setTimeEntering(theTime);
            item.updatePath(this.getName());
            state = 1;
        }
        else {

            System.out.println("Stage is already full");
        }
    }
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
    //return 0 - highest priority to this object.
    //return 1 - highest priority to challenger object.
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

    /*Getters and setters*/
    public double getTime(){ return time; }
    public void setTime (double newTime){
        time = newTime;
    }
    public void setNextQ(WaitingLine nextList){nextQueue = nextList;}
    public void setPreviousQ(WaitingLine prevList) {previousQueue = prevList;}
    public WaitingLine getPreviousQ(){
        return previousQueue;
    }
    public WaitingLine getNextQ(){
        return nextQueue;
    }

    public int getState(){return state;}
    public void setState(int theState){
        state = theState;
    }
    public String getName(){
        return stageName;
    }
    public void setName(String name){
        stageName = name;
    }
    public void setMode(int theMode){
        mode = theMode;
    }
    public void updateStatistics(double advanceTime){

        switch(state){
            //starving
            case -1:
                timeStarving += advanceTime;
                break;
            //blocked
            case 0:
                System.out.println("block timer increased");
                timeBlocked += advanceTime;
                break;
            //processing
            case 1:
                timeProducing += advanceTime;
                break;
        }
    }


    public boolean hasPrevious(){
        if(previousQueue == null){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean hasNext(){
        if(nextQueue == null){
            return false;
        }
        else{
            return true;
        }
    }
    private double  calculateCompletionTime(double theTime){
        switch(mode)
        {
            case 1:  //stages 0, 1, 3 & 5.
                return theTime + (mean + range*(item.getRand()-0.5));


            case 2: //Stages 2a & 4b.
                return theTime + (2*mean + 2*range*(item.getRand()-0.5));


            case 3: //Stages 2b & 4a.
                return theTime + (mean + 0.5*range*(item.getRand()-0.5));

        }

        return -1;
    }
    public boolean isPrevEmpty(){
        return previousQueue.isEmpty();
    }
    public boolean isNextFull(){
        return nextQueue.isFull();
    }
    public Item takePrev(int index,double theTime){
        return previousQueue.takeItem(index, theTime);
    }
    public Item getItem(){
        return item;
    }


    public double getTimeStarving() {
        return timeStarving;
    }
    public double getTimeProducing() {
        return timeProducing;
    }
    public double getTimeBlocked() {
        return timeBlocked;
    }
}
