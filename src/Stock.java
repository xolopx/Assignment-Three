import java.util.Random;

/**
 * Created by Tom on 01-Jun-18.
 * The infinite supply of items that feeds the production line. Whenever an item is taken from stock it is immediately
 * replenished. All items start their life here.
 */
public class Stock{

    private Item stock;          //the item that is stored in the stock.
    private Random r;            //Determines the random value of all items created.

    /**
     * Initially the stock is filled with a new item. And the random object created.
     */
    Stock(){
        r = new Random();
        stock = new Item(r.nextDouble());
    }

    /**
     * When the first stage of the production line needs stock it is taken from here. The method gives up its stock but
     * immediately fills its place in turn, with a new item.
     * @return The item that was in stock.
     */
    public Item takeStock(){

        Item temp = stock;
        stock = new Item(r.nextDouble());
        return temp;
    }
}
