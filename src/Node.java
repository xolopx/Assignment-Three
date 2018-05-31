
//THOMAS COURTNEY: 3175353
public class Node<T>{

    private T data = null;
    private Node<T> next = null;
    private Node<T> previous = null;

	Node() {
    	data = null;
    	next = null;
    	previous = null;
        }

    Node(T init_data) {
        data = init_data;
        next = null;//init_next;
        previous = null; //init_previous;
        }

    public void set_data( T new_data) 			{data = new_data;}

    public void set_next(Node next_link) 			    {next = next_link;}

    public void set_previous(Node previous_link) 	    {previous = previous_link;}

    public  T get_data() 							        {return data;}

    public Node<T> get_previous() 			                {return previous;}

    public Node<T> get_next()							    {return next;}

}

