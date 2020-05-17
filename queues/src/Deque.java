import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {


    private Node first;
    private Node last;
    private int N;

    private class Node{
        private Item item;
        private Node prev;
        private Node next;
    }

    // construct an empty deque
    public Deque() { }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size(){ return N; }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null)
            throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item=item;
        first.next=oldfirst;
        if (isEmpty()) {last = first;}
        else            {oldfirst.prev=first;}
        N++;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null)
            throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        if (isEmpty()) {first = last;}
        else               {oldlast.next = last;}
       N++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty())
                 {first = last;}
        else        {first.prev = null;}
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        N--;
        if (isEmpty())
                {first = last;}
        else        {last.next = null;}
        return item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node curr = first;

        public boolean hasNext() {
            return curr != null;
        }

        public Item next() {
            if (null == curr)
                throw new NoSuchElementException();

            Item item = curr.item;
            curr = curr.next;
            return item;
        }

        public void remove() { throw new UnsupportedOperationException(); }

    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<String> dq = new Deque<String>();
        StdOut.println(dq.isEmpty());
        StdOut.println(dq.size());

        dq.addLast("Love");
        dq.addLast("Computer");
        dq.addLast("Program");
        StdOut.println(dq.size());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeFirst());

        dq.addFirst("Love");
        dq.addFirst("Computer");
        dq.addFirst("Program");
        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.size());

    }

}
