/**
 * Created by tom on 23/04/2018.
 * This class extends MyPolygons and adds the functionality to sort a MyPolygons list.
 * "extends" = "inherits"
 * SortedPolygons has all of the functionality of MyPolygons plus some more that I am going to add.
 * This class is the same as MyPolygons however it has additional functionality.
 */
public class SortedList<T extends Comparable<T>> extends LinkedList<T>{

    //Constructor, must be redone for the subclass as constructor in superclass is not a "member method".
    SortedList() {

        //All signs point to sentinel.
        head_ptr_ = sentinel;
        tail_ptr_ = sentinel;
        //Tell that sentinel where to go.
        sentinel.set_next(head_ptr_);
        sentinel.set_previous(tail_ptr_);
        //currently we are pointed at the sentinel as current because it's the only goddamn node.
        current_ptr_ = sentinel;
        //We will call the list length zero because the sentinel has no data in it.
        list_length = 0;
    }

    //Moves current to a desired position.
    //Mode 0 = forward, Mode 1 = back.
    //Position is where to go to.
    private void moveCurrent(int mode, int toPosition)
    {
        reset();

        if(mode==1){
            for(int i = 0;i<toPosition;i++){
                back();
            }
        }
        else if(mode == 0)
        {
            for(int i = 0;i<toPosition;i++){
              forward();
            }
        }
        else{
            System.out.println("You did not use this method correctly it is mode 1 or 0 m8.");
        }

    }

    //This function sorts the list implementing ComparePolygons interface. Isn't really amy insertion involved but I guess
    //if you can add a polygon and then just sort the list again.
    public void sort(){

        //Counter for how many polygons must be sorted.
        int leftToSort = list_length-1;
        //This is the position of the last polygon in the "sorted section" (aka SS) of the list .
        int sortedPosition = 1;
        //polyUS = unsorted section polygon.
        //polySS = sorted section polygon.
        T polyUS,polySS;

        if(list_length!=0||list_length!=1){
            //leftToSort will be decremented as each polygon is processed. This is the main processing loop.
            while(leftToSort !=0){
                //Move the current to the beginning of the unsorted list.
                moveCurrent(0,sortedPosition+1);
               //Grab polygon first in unsorted list.
                polyUS = getObject();
                //Compare to all of the polygons in the "sorted part of the list" using for-loop.
                //This is the "sorted section" comparison loop.
                for(int i=0;i<sortedPosition;i++){

                    //This will move to the correct polygon in the "sorted section" (aka SS).
                    moveCurrent(0,sortedPosition-i);
                    polySS = getObject();
                    //If the "unsorted section" (aka US) polygon is smaller than the SS polygon they must swap positions.
                    //To do this the US polygon is deleted and reinserted in the correct position, pushing items to it's
                    //right (lower in the list) down.
                    if(polySS.compareTo(polyUS)==1){

                        //This is the position that the polyUS is at.
                        moveCurrent(0,sortedPosition+1-i);
                        //remove the US polygon.
                        remove();
                        //current pts position.
                        int currentPos = getCurrentPosition();
                        //if you removed tail.
                        if(currentPos==0){
                            //move back from sentinel one spot.
                            moveCurrent(1,1);
                            currentPos=getCurrentPosition()+1;
                        }



                        //reinsert US polygon in the correct position. The position will be before polyUS.
                        insert(currentPos-1, polyUS);

                    }
                    //Else polyUS was bigger and is in the right position.
                }
                //Each time a polygon is sorted this goes down.
                leftToSort --;
                //And this goes up.
                sortedPosition++;
            }
        }

    }

    //returns the current position.
    public int getCurrentPosition(){
        int thePosition = 0;

        while(current_ptr_!=sentinel){
            thePosition++;
            back();
        }
        return thePosition;
    }

    //Sorts inserted polygons into the list.
    public void insertInOrder(T object){
        //Chuck her in.
        add_to_head(object);
        //Sort it out.
        sort();
    }

}




