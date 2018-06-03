/**
 * Created by tom on 26/05/2018.
 *
 * Stores the statistics for the production line.
 */
public class Stats {

    private int itemsProcessed;
    private int itemsCreated;
    private double globalTime;
    private double[][] stageStateTimes = new double[8][3];   //Time. Col 1: Processing, Col 2: Starving, Col 3: Blocked.

    Stats(){
        itemsProcessed = 0;
        itemsCreated = 0;
        globalTime = 0;
    }
    public void updateNumProcessed(){
        itemsProcessed++;
    }
    public void updateNumCreated(){itemsCreated++;}
    public void updateTime(double theTime){globalTime = theTime;}

    public void printStats(){

        System.out.println("\nThe number of items created is: " + itemsCreated);
        System.out.println("The number of items processed is: " + itemsProcessed);
        System.out.println("The factory finished processing at: " + globalTime);
    }

    public void updateStageStateTimes(Item item, Stage focusStage) {

        int row = 0;
        int column = 0;
        switch(focusStage.getName()){
            case "S0":

                break;
            case "S1":
                break;
            case "S2a":
                break;
            case "S2b":
                break;
            case "S3":
                break;
            case "S4a":
                break;
            case "S4b":
                break;
            case "S5":
                break;


        }

    }
}
