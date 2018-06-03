import java.util.ArrayList;

/**
 * Created by Tom on 01-Jun-18.
 * Permanently has 1 item in it. Whenever stock is taken it is immediately replenished.
 */
public class Stock{

    private Item stock;

    Stock(){
        stock = new Item();
    }

    private void stockUp(){

        Item newItem = new Item();
        stock = newItem;
    }

    //Whenever stock is taken new stock is automatically created.
    public Item takeStock(){

        Item temp = stock;
        stock = new Item();
        return temp;
    }
}
