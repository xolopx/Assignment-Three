/**
 * Created by tom on 21/05/2018.
 */



public class PA3 {

    public static void main(String[] args){

        //Read these in with a Scanner later.
        double M = 1000;        //mean, for time calculation.
        double N = 1000;        //range, for time calculation.
        double productionTime = 10000000;
        int queueLength = 5;


        Factory one = new Factory(productionTime, M, N, queueLength);
        one.run();



    }

}
