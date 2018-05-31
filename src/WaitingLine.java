import java.util.ArrayList;

/**
 * Created by tom on 30/05/2018.
 */
public class WaitingLine<T>{

    //This is what the WaitingLine will actually store.
    protected ArrayList<T> theContents;
    //This is flag indicating the state of the WaitingLine. Empty(0), Has Room(1) or Full(2).
    protected int state;

    WaitingLine(int capacity){

        theContents = new ArrayList<>(capacity);
        state = 0;                                      //initially state is empty.

    }
    //returns the state of the queue.
    public boolean isEmpty(){
        if(state == 0){return true;}
        else {return false;}
    }
    //returns the state of the queue.
    public boolean isFull(){
        if(state == 2){return true;}
        else {return false;}
    }
    //adds an item to the wait line.
    public void addItem(T newItem){
        //If there's room.
        if(!isFull()){
            theContents.add(newItem);
        }
    }
    //Removes an item at a specific location
    public void removeItem(int index){
        //If the list is not empty.
        if(!isEmpty()){theContents.remove(index);}
    }
    //returns an item
    public T getItem(int index){
        return theContents.get(index);
    }
    public T takeItem(int index){
        //store temp for returning.
        T temp = getItem(index);
        //remove the item from the list.
        removeItem(index);
        //return the item.
        return temp;

    }
}
