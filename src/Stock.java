import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tom on 01-Jun-18.
 * Permanently has 1 item in it. Whenever stock is taken it is immediately replenished.
 */
public class Stock{

    private Item stock;
    private Random r = new Random();

    Stock(){

        stock = new Item(r);
    }

    //Whenever stock is taken new stock is automatically created.
    public Item takeStock(){

        Item temp = stock;
        stock = new Item(r);
        return temp;
    }
}
