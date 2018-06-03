import java.util.ArrayList;

/**
 * Created by tom on 30/05/2018.
 */
public class WaitingLine<T>{


    protected ArrayList<T> theContents;
    protected int state;                            //Empty(0), Has Room(1) or Full(2).
    private int initialCapacity;                    //Warning: arrayLists can still expand.


    WaitingLine(){
        theContents = new ArrayList<T>(1);
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
    public void addItem(T newItem){
        //If there's room.
        if(!isFull()){
            theContents.add(newItem);
        }

        updateState();

    }
    public void removeItem(int index){

        if(!isEmpty()){
            theContents.remove(index);
        }
        else{
            state = 0;
        }
    }
    public T getItem(int index){
        return theContents.get(index);
    }
    public T takeItem(int index){

        T temp = getItem(index);
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
}
