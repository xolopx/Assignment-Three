/**
 * Created by tom on 26/05/2018.
 *
 * Stores the statistics for the production line. Important statistics stored are:
 * The time that the stages spend starving, blocked and processing.
 * The average number of items in a queue at any time and the average time an item spend in a queue.
 * The path taken by an item to be completed.
 */
public class Stats {

    private int itemsProcessed;
    private int itemsCreated;
    private double globalTime;
    private int[] numItemPerPath = new int[4];               //0: S2a - S4a. 1: S2a - S4b. 2: S2b - S4a. 3: S2b - S4b.
    private double[][] stageStateTimes = new double[8][3];   //Time. Col 1: Processing, Col 2: Starving, Col 3: Blocked.
    private double[][] queueStatistics = new double [5][2];  //Col 1: Collective wait time, Col 2: Number of Item

    /**
     * Initially a statistics item's values are zero.
     */
    Stats(){
        itemsProcessed = 0;
        itemsCreated = 0;
        globalTime = 0;
    }

    /**
     * This increases the number of items processed by 1 and is invoked every time an item is ejected from the final
     * stage in the production line.
     */
    public void updateNumProcessed(){
        itemsProcessed++;
    }

    /**
     * Increments the num ber of items that have been created every time the stock is taken from.
     */
    public void updateNumCreated(){itemsCreated++;}

    /**
     * Gives the statistics object the currentTime. The currentTime is required to calculate some important statistics.
     * @param theTime The current time of the factory.
     */
    public void updateTime(double theTime){globalTime = theTime;}

    /**
     * Prints out the important statistics in a table.
     */
    public void printStats(){


        System.out.println("\n\nThe number of items created is: " + itemsCreated);
        System.out.println("The number of items processed is: " + itemsProcessed);
        System.out.println("The factory finished processing at: " + globalTime +"\n");


        System.out.println("STAGE\t\tProduction %\t\tStarving  \t\tBlocked  \t\t");
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


        System.out.println("\nQueue\t\tAverage time item spends in queue\t\tAverage number of items in queue");
        for(int i = 0;i<5;i++){

            System.out.println(
                    i +"\t\t\t" + String.format("%4.2f",queueStatistics[i][0]) + "\t\t\t\t\t\t\t\t\t"
                    + String.format("%4.2f",queueStatistics[i][1])
            );
        }

        System.out.println("\nPath\t\tItems on path");
        System.out.println("S2a - S4a   " + numItemPerPath[0]);
        System.out.println("S2a - S4b   " + numItemPerPath[1]);
        System.out.println("S2b - S4a   " + numItemPerPath[2]);
        System.out.println("S2b - S4b   " + numItemPerPath[3]);





    }

    /**
     * Takes all of the times for how longs the stages in the production line have been in each state. Performs a
     * calculation to give the processing time as a percentage of all time. Hence this method requires the closing time
     * of the system.
     * @param focusStage The stage that is having its statistics stored.
     * @param finishTime The time at which the simulation ends.
     */
    public void updateStageStateTimes(Stage focusStage, double finishTime) {

        //Time. Col 1: Processing, Col 2: Starving, Col 3: Blocked.
        switch(focusStage.getName()){
            case "S0":
                stageStateTimes[0][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[0][1] = focusStage.getTimeStarving();
                stageStateTimes[0][2] = focusStage.getTimeBlocked();
                break;
            case "S1":
                stageStateTimes[1][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[1][1] = focusStage.getTimeStarving();
                stageStateTimes[1][2] = focusStage.getTimeBlocked();
                break;
            case "S2a":
                stageStateTimes[2][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[2][1] = focusStage.getTimeStarving();
                stageStateTimes[2][2] = focusStage.getTimeBlocked();
                break;
            case "S2b":
                stageStateTimes[3][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[3][1] = focusStage.getTimeStarving();
                stageStateTimes[3][2] = focusStage.getTimeBlocked();
                break;
            case "S3":
                stageStateTimes[4][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[4][1] = focusStage.getTimeStarving();
                stageStateTimes[4][2] = focusStage.getTimeBlocked();
                break;
            case "S4a":
                stageStateTimes[5][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[5][1] = focusStage.getTimeStarving();
                stageStateTimes[5][2] = focusStage.getTimeBlocked();
                break;
            case "S4b":
                stageStateTimes[6][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[6][1] = focusStage.getTimeStarving();
                stageStateTimes[6][2] = focusStage.getTimeBlocked();
                break;
            case "S5":
                stageStateTimes[7][0] = (focusStage.getTimeProducing()/finishTime)*100  ;
                stageStateTimes[7][1] = focusStage.getTimeStarving();
                stageStateTimes[7][2] = focusStage.getTimeBlocked();
                break;


        }

    }

    /**
     * Updates the statistics pertaining to the queues. The information is stored in the queues and then taken from them
     * at the conclusion of the simulation.
     * @param line
     * @param index
     */
    public void updateQueues(WaitingLine line, int index) {
        queueStatistics[index][0] = (line.getTimeItemsQueued()/line.getNumQueuedItems());
        queueStatistics[index][1] = line.getNumQueuedItems()/itemsProcessed;

    }

    /**
     * Updates the number of items that have taken one of four paths to ejection into the void at the production line's
     * end. Hence this method is invoked whenever an item is ejected from the end of the production line.
     * @param item The item that has been ejected from the production line.
     */
    public void updatePaths(Item item){
        switch(item.getPath()){
            case "S0S1S2aS3S4aS5":
                numItemPerPath[0]++;
                break;
            case "S0S1S2aS3S4bS5":
                numItemPerPath[1]++;
                break;
            case "S0S1S2bS3S4aS5":
                numItemPerPath[2]++;
                break;
            case "S0S1S2bS3S4bS5":
                numItemPerPath[3]++;
                break;
        }
    }
}

