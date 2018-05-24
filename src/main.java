/**
 * Created by tom on 21/05/2018.
 */
import java.util.*;
import java.io.*;
public class main {
    public static void main(String[] args){
        Random r = new Random();
        double rNum = r.nextDouble();
        double mean = 10;
        double range = 100;
        double time = mean + (range*(rNum-0.5));

        System.out.println(time);
        System.out.println(rNum);
    }


}
