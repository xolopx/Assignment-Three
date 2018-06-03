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


        System.out.println("STAGE\t\tProduction %\t\tStarving %\t\tBlocked %\t\t");
        System.out.println(
                "S0   \t\t" + String.format("%4.2f",stageStateTimes[0][0]) +
                "\t\t\t\t" + String.format("%4.2f",stageStateTimes[0][1]) +
                "\t\t\t" + String.format("%4.2f",stageStateTimes[0][2])
        );
        System.out.println(
                "S1   \t\t" + String.format("%4.2f",stageStateTimes[1][0]) +
                        "\t\t\t\t" + String.format("%4.2f",stageStateTimes[1][1]) +
                        "\t\t\t" + String.format("%4.2f",stageStateTimes[1][2])
        );
        System.out.println(
                "S2a  \t\t" + String.format("%4.2f",stageStateTimes[2][0]) +
                        "\t\t\t\t" + String.format("%4.2f",stageStateTimes[2][1]) +
                        "\t\t\t" + String.format("%4.2f",stageStateTimes[2][2])
        );
        System.out.println(
                "S2b  \t\t" + String.format("%4.2f",stageStateTimes[3][0]) +
                        "\t\t\t\t" + String.format("%4.2f",stageStateTimes[3][1]) +
                        "\t\t\t" + String.format("%4.2f",stageStateTimes[3][2])
        );
        System.out.println(
                "S3   \t\t" + String.format("%4.2f",stageStateTimes[4][0]) +
                        "\t\t\t\t" + String.format("%4.2f",stageStateTimes[4][1]) +
                        "\t\t\t" + String.format("%4.2f",stageStateTimes[4][2])
        );
        System.out.println(
                "S4a  \t\t" + String.format("%4.2f",stageStateTimes[5][0]) +
                        "\t\t\t\t" + String.format("%4.2f",stageStateTimes[5][1]) +
                        "\t\t\t" + String.format("%4.2f",stageStateTimes[5][2])
        );
        System.out.println(
                "S4b  \t\t" + String.format("%4.2f",stageStateTimes[6][0]) +
                        "\t\t\t\t" + String.format("%4.2f",stageStateTimes[6][1]) +
                        "\t\t\t" + String.format("%4.2f",stageStateTimes[6][2])
        );
        System.out.println(
                "S5   \t\t" + String.format("%4.2f",stageStateTimes[7][0]) +
                        "\t\t\t\t" + String.format("%4.2f",stageStateTimes[7][1]) +
                        "\t\t\t" + String.format("%4.2f",stageStateTimes[7][2])
        );





    }
    public void updateStageStateTimes(Stage focusStage, double finishTime) {

        //Time. Col 1: Processing, Col 2: Starving, Col 3: Blocked.
        switch(focusStage.getName()){
            case "S0":
                stageStateTimes[0][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[0][1] = (focusStage.getTimeStarving()/finishTime)*100 ;
                stageStateTimes[0][2] = (focusStage.getTimeBlocked()/finishTime)*100 ;
                break;
            case "S1":
                stageStateTimes[1][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[1][1] = (focusStage.getTimeStarving()/finishTime)*100 ;
                stageStateTimes[1][2] = (focusStage.getTimeBlocked()/finishTime)*100 ;
                break;
            case "S2a":
                stageStateTimes[2][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[2][1] = (focusStage.getTimeStarving()/finishTime)*100 ;
                stageStateTimes[2][2] = (focusStage.getTimeBlocked()/finishTime)*100 ;
                break;
            case "S2b":
                stageStateTimes[3][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[3][1] = (focusStage.getTimeStarving()/finishTime)*100 ;
                stageStateTimes[3][2] = (focusStage.getTimeBlocked()/finishTime)*100 ;
                break;
            case "S3":
                stageStateTimes[4][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[4][1] = (focusStage.getTimeStarving()/finishTime)*100 ;
                stageStateTimes[4][2] = (focusStage.getTimeBlocked()/finishTime)*100 ;
                break;
            case "S4a":
                stageStateTimes[5][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[5][1] = (focusStage.getTimeStarving()/finishTime)*100 ;
                stageStateTimes[5][2] = (focusStage.getTimeBlocked()/finishTime)*100 ;
                break;
            case "S4b":
                stageStateTimes[6][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[6][1] = (focusStage.getTimeStarving()/finishTime)*100 ;
                stageStateTimes[6][2] = (focusStage.getTimeBlocked()/finishTime)*100 ;
                break;
            case "S5":
                stageStateTimes[7][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[7][1] = (focusStage.getTimeStarving()/finishTime)*100 ;
                stageStateTimes[7][2] = (focusStage.getTimeBlocked()/finishTime)*100 ;
                break;


        }

    }
}
