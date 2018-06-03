
public class PA3 {

    public static void main(String[] args){

        double M = Double.parseDouble(args[0]);        //mean, for time calculation.
        double N = Double.parseDouble(args[1]);        //range, for time calculation.
        int queueLength = Integer.parseInt(args[2]);   //length of queues.
        double productionTime = 10000000;

        Factory one = new Factory(productionTime, M, N, queueLength);
        one.run();
    }

}
