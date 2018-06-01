import java.util.ArrayList;

/**
 * Created by Tom on 01-Jun-18.
 * This is a specific type of waitingList that is of length and always has at least one item in it.
 * It is specifically for Item type objects.
 */
public class Stock extends WaitingLine<Item>{

    //Want the stock to know the mean and range so it can produce its own Items to refill with.
    private double mean;
    private double range;

    //It's default constructor is a just a WaitingLine with capacity one and one item in it.
    Stock(double M, double R){
        mean = M;
        range = R;
        Item newItem = new Item(mean,range);
        theContents = new ArrayList<>(1);
        theContents.add(newItem);
    }
    //append Items to the stock by the integer value specified. This should only be called when an item is removed from
    //the stock.
    private void stockUp(){
        //declare a new item
        Item newItem = new Item(mean,range);
        //Put the new item in.
        addItem(newItem);
    }
    //Overriding takeItem function from WaitingLine so that the function returns an item and refills the stock.
    @Override
    public Item takeItem(int index){
        //store temp for returning.
        Item temp = getItem(index);
        //remove the item from the list.
        removeItem(index);
        //restock the stock.
        stockUp();
        //return the item.
        return temp;

    }
}
