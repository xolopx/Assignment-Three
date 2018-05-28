/**
 * Created by tom on 21/05/2018.
 */



public class PA3 {

    public static void main(String[] args){

        //Read these in with a Scanner later.
        double M = 1000;        //mean, for time calculation.
        double N = 1000;        //range, for time calculation.
        double productionTime = 10000;
        int queueLength = 5;
        int numStages = 1;

        Factory one = new Factory(productionTime,M,N,queueLength, numStages);

       //Start running the factory.
        one.run();


        one.getStatistics().printStats();
    }

    //Generates a random time based on the mean (M) and range (N) given as parameters. This is used to generate the time
    // for an item.


}
