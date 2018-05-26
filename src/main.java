/**
 * Created by tom on 21/05/2018.
 */
import java.util.*;
import java.io.*;
import java.util.PriorityQueue; //This is the WaitList class.
public class main {

    public static void main(String[] args){

        //Read these in with a Scanner later.
        double M = 1000;        //mean, for time calculation.
        double N = 1000;        //range, for time calculation.

        timeGenerator(M,N);


    }

    public static void timeGenerator(double M, double N){
        Random r = new Random();
        double rNum = r.nextDouble();
        double mean = 10;
        double range = 100;
        double time = mean + (range*(rNum-0.5));

        System.out.println(time);
        System.out.println(rNum);
    }

}
