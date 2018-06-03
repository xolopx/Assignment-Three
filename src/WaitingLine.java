import java.util.ArrayList;

/**
 * Created by tom on 30/05/2018.
 */
public class WaitingLine{


    protected ArrayList<Item> theContents;
    protected int state;                            //Empty(0), Has Room(1) or Full(2).
    private int initialCapacity;                    //Warning: arrayLists can still expand.
    private double numQueuedItems = 0;
    private double timeItemsQueued = 0;



    WaitingLine(){
        theContents = new ArrayList<Item>(1);
        state = 0;
    }
    WaitingLine(int capacity){
        initialCapacity = capacity;
        theContents = new ArrayList<>(capacity);
        state = 0;                                      //initially state is empty.

    }

    public boolean isEmpty(){
        if(state == 0){return true;}
        else {return false;}
    }
    public boolean isFull(){
        if(state == 2){return true;}
        else {return false;}
    }
    public void addItem(Item newItem, double theTime){
        //If there's room.
        if(!isFull()){
            theContents.add(newItem);
            newItem.setTimeEntering(theTime);
            numQueuedItems+= this.getSize();
        }

        updateState();

    }
    public void removeItem(int index){

        if(!isEmpty()){

            theContents.remove(index);

        }
        else{
            System.out.println("you tried to take an item out of an empty queue.");
            state = 0;
        }
    }
    public Item getItem(int index){
        return theContents.get(index);
    }
    public Item takeItem(int index,double theTime){

        Item temp = getItem(index);
        temp.setTimeLeaving(theTime);
        updateQueuedTime(temp);
        removeItem(index);
        updateState();

        return temp;
    }
    public int getSize() {
        return theContents.size();
    }
    private void updateState(){

        if(getSize()== initialCapacity ){
            state = 2;
        }else if(getSize() == 0){
            state = 0;
        }
        else {state = 1;}

    }
    private void updateQueuedTime(Item item){
        timeItemsQueued +=  item.getTimeLeaving()- item.getTimeEntering();
    }
    public double getNumQueuedItems() {
        return numQueuedItems;
    }
    public double getTimeItemsQueued() {
        return timeItemsQueued;
    }
}
