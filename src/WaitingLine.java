import java.util.ArrayList;

/**
 * Created by tom on 30/05/2018.
 * WaitingLines are a queue that connects stages. They hold items when stages proceeding them are already full. They
 * have a capacity. Stages will take items from them or pass their items onto them.
 */
public class WaitingLine{

    protected ArrayList<Item> theContents;
    protected int state;                            //Empty(0), Has Room(1) or Full(2).
    private int initialCapacity;                    //Warning: arrayLists can still expand.
    private double numQueuedItems = 0;
    private double timeItemsQueued = 0;

    /**
     * Initially a WaitingLine empty.
     * @param capacity The maximum number of items that the WaitingLine can house.
     */
    WaitingLine(int capacity){
        initialCapacity = capacity;
        theContents = new ArrayList<>(capacity);
        state = 0;                                      //initially state is empty.

    }

    /**
     * Check if the contents are empty. If so a stage may starve.
     * @return True - the line is empty. False the line is not empty.s
     */
    public boolean isEmpty(){
        if(getSize() == 0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Check if the line is full. If so a stage may be blocked.
     * @return True - if the stage is full. False - if the stage is empty.
     */
    public boolean isFull(){
        if(getSize() == initialCapacity){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * When an item is passed from a stage it goes into a queue. If the queue is not full it will take the item and
     * append it to the end of its contents. The item entering will store when it entered the queue for statistics
     * purposes.
     * @param newItem The item to be stored in the queue.
     * @param theTime The time at which the item was stored in the queue. s
     */
    public void addItem(Item newItem, double theTime){

        if(!isFull()){
            theContents.add(newItem);
            updateState();
            newItem.setTimeEntering(theTime);
            numQueuedItems+= this.getSize();
        }
        else System.out.println("Added an item to a full queue.");
    }

    /**
     * When a stage requests an item from a queue then the item must be removed from its contents. A check is performed
     * to see that the contents are not empty and if this is true the item is removed. State is updated and queue
     * statistics are updated.
     */
    public void removeItem(){
        if(!isEmpty()){
            updateQueuedTime(theContents.get(0));
            theContents.remove(0);
            updateState();
        }
        else{
            System.out.println("you tried to take an item out of an empty queue.");
        }
    }

    /**
     * If an item just needs to be looked at this method returns the item stored at a specific position in the contents.
     * @return The item at the index specified. The onl;y item that should ever be looked at is the head of the
     *         contents. The reason being that items are only ever taken from the head of a queue.
     */
    public Item getItem(){
        return theContents.get(0);
    }

    /**
     * If a stage wants an item for itself it must take it from its previous queue. This method returns the item the
     * stage wants and removes the item from itself and updates the time at which the item left.
     * @param theTime The current time when the item was removed.
     * @return The item that was removed from the queue.
     */
    public Item takeItem(double theTime){

        Item temp = getItem();
        temp.setTimeLeaving(theTime);

        removeItem();
        return temp;
    }

    public int getSize() {
        return theContents.size();
    }
    public double getNumQueuedItems() {
        return numQueuedItems;
    }
    public double getTimeItemsQueued() {
        return timeItemsQueued;
    }

    /**
     * When an item is added or removed from the queue the state of the queue must be updated to reflect the change.
     */
    private void updateState(){

        if(isEmpty()){
            state = 0;
        }else if(isFull()){
            state = 2;
        }
        else {state = 1;}

    }

    /**
     * When an item leaves the queue the time it was here is recorded for later.
     * @param item The item whose statistics must be stored.
     */
    private void updateQueuedTime(Item item){
        timeItemsQueued +=  item.getTimeLeaving()- item.getTimeEntering();
    }
}
