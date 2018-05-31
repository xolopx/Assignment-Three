/**
 * Created by tom on 16/04/2018.
 */
//Import iterator libraries and the
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T>{

    //"protected" maintains encapsulation but allows subclasses access to member variables.
    protected Node<T> head_ptr_ = new Node();
    protected Node<T> tail_ptr_ = new Node();
    protected Node<T> current_ptr_ = new Node();
    protected Node<T> sentinel = new Node();
    //the length of the list.
    protected int list_length = 0;
    //the modification count of the list.
    protected int modCount = 0;

    LinkedList() {

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

    //Returns polygon at current. Does NOT return a node.
    public T getObject()
    {
        //This returns the polygon in the current node.
        return current_ptr_.get_data();
    }
    //This adds a new node and piece of data to the head of the list. Uses add() method.
    public void add_to_head(T data) {
        //return current to sentinel.
        reset();
        add(data);
    }
    //This adds a new node and piece of data to the tail of the list. uses add() method.
    public void add_to_tail(T data) {
        //Set current to the previous to sentinel. Point to sentinel as add() pushed the list down.
        current_ptr_ = sentinel;
        //pop that badboi in the list yo.
        add(data);
    }
    //This is a generic method used for both add to head and tail. It creates a new node and places the data in it then
    //places the node in a position that is dictated by where current_ptr_ points.
    public void add(T data)
    {
        //1. create new node on heap
        Node<T> freshNode = new Node(data);
        //set the next pointer of new node to the next of the sentinel.
        freshNode.set_next(current_ptr_.get_next());
        //set the previous node of the new node to the sentinel. It is now positioned where the head would be. Good.
        freshNode.set_previous(current_ptr_);
        //Set the sentinels next's previous to be the new Node.
        current_ptr_.get_next().set_previous(freshNode);
        //Set the sentinel's next to be the new node.
        current_ptr_.set_next(freshNode);
        //set current to the new node.
        current_ptr_= freshNode;
        //increment the ol' listo.
        list_length++;
        //increment the modification count
        modCount++;

//        //inserts polygon before location of current.
//        Node newNode = new Node(data);
//        newNode.prev().next(newNode);
//        newNode.next().prev(newNode);
//        length++;

    }
    //Moves the current ptr forward.
    public void forward() {
    current_ptr_ = current_ptr_.get_next();
}
    //Moves the current ptr back.
    public void back() {
    current_ptr_ = current_ptr_.get_previous();
}
    //Resets the current pointer to the sentinel.
    public void reset() {
    current_ptr_= sentinel;
}
    //inserts a node after position given.
    public void insert(int position, T data)
    {
        //reset the position of the current pointer to have a starting point.
        reset();
        //cycle through the linked list until the position is reached.
        for(int i = 1;i<position;i++)
        {
            //will cycle the current pointer forward to the position.
            forward();
        }
        //Position is now reached so add the new node.
        add(data);
    }
    //Takes the head off the list and returns the data inside it.
    public T pop()
    {
        //make the current the next of the sentinel (aka the head).
        current_ptr_ = sentinel.get_next();
        //Create a pointer to store the retrieved head in.
        Node<T> theHead;
        //retrieve the head.
        theHead = current_ptr_;
        //delete the head.
        deleteHead();

        return theHead.get_data();
    }
    //Simply removes the head of the list.
    public void deleteHead()
    {
        //set the current to the sentinel.
        reset();
        //Set the current to the head.
        forward();
        //remove that badboi.
        remove();

    }
    //Returns the size of the list.
    public int getSize() {
    return list_length;
}
    //Removes the node at the current ptr.
    public void remove() {

    //Set the heads previous' next to the heads next. So splice out the previous' life.
    current_ptr_.get_previous().set_next(current_ptr_.get_next());
    //Set the head's next to it's previous. So splice it out of the next's life.
    current_ptr_.get_next().set_previous(current_ptr_.get_previous());
    //Make the next node the current.
    forward();
    //decrement length.
    list_length--;
    //increment mod count.
    modCount++;

    }
    //Returns the data stored in the head node.
    public T getHead() {
        //This is the head polygon.
        T theHead = sentinel.get_next().get_data();

        return theHead;
        //Sure thing babe.
    }
    //Returns the data stored in the tail node.
    public T getTail(){
        //This is the tail polygon.
        T theTail = sentinel.get_previous().get_data();

        return theTail;

    }

    public T getCurrent(){
        return current_ptr_.get_data();
    }
    //Returns an instance of the iterator as defined in the class "Listerator".
    @Override
    public Iterator<T> iterator(){
        return new Listerator(sentinel.get_next());
    }

    private class Listerator implements Iterator<T>{

        //when list's iterator is created it receives the head node of the list and the modification count of the list.
        private Node<T> currentNode;
        private int countedModCount;

        //Constructor is private so that it can be created the by the Linked List.
        private Listerator(Node<T> tempNode){
            currentNode = tempNode;
            countedModCount = modCount;
        }

        public int getModCount(){
            return modCount;
        }
        //Overriding the hasNext function of Iterable.
        //For a doubly linked linked list checks that the current equals the sentinel.
        @Override
        public boolean hasNext(){
           return currentNode != sentinel;
        }

        //Overridden method from the Iterable interface that will return the generic type of the linked list.
        //The method will return the current node and "iterate" (bazinga) the current to the next node in the list.
        @Override
        public T next(){
            //If the modification count of the iterator doesn't match the list.
            if(getModCount() != countedModCount){
               throw(new ConcurrentModificationException("You made modifications without using the iterator yo'."));
            }
            //If the list doesn't have a next item.
            if(!hasNext()){
                throw(new NoSuchElementException("There ain't nothing out here brother."));
            }

            T object = currentNode.get_data();
            currentNode = currentNode.get_next();
            return object;
        }

        //Overrides the function for remove from iterable.
        //Removes the object stored in current. New current is node that was before the original current.
        @Override
        public void remove(){
            currentNode.get_previous().set_next(currentNode.get_next());
            currentNode.get_next().set_previous(currentNode.get_previous());
            currentNode = currentNode.get_previous();
        }
    }

}
