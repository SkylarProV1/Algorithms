import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr = (Item[]) new Object[1];
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue(){ }

    // is the randomized queue empty?
    public boolean isEmpty() {return N == 0; }

    // return the number of items on the randomized queue
    public int size(){ return N;}

    // add the item
    public void enqueue(Item item){
        if (item == null)
            throw new IllegalArgumentException();

        if (N == arr.length) resize(2 * arr.length);

        arr[N++]=item;
    }
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())  throw new NoSuchElementException();
        int r = StdRandom.uniform(N);
        swap(r, N - 1);
        Item item = arr[--N];
        arr[N] = null;
        if ( N>0 && N==arr.length/4 )
            resize(arr.length / 2);

        return item ;
    }

    private void swap(int i, int j){
        Item temp = arr[i];
        arr[i]=arr[j];
        arr[j]=arr[i];
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (isEmpty())  throw new NoSuchElementException();
        int r = StdRandom.uniform(N);
        return arr[r];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {return new RandomizedIterator();}

    private class RandomizedIterator implements Iterator<Item> {
        private int curr;
        private int[] index;

        public RandomizedIterator(){
            index = new int[N];
            for (int i = 0; i< N ; i++ ){
                index[i]=i;
            }
            StdRandom.shuffle(index);
            curr=N-1;
        }

        public boolean hasNext(){
            return curr >= 0;
        }

        public Item next(){
            if (curr <0)
                throw new NoSuchElementException();
            return arr[index[curr--]];
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

    }

    private void resize(int cap){
        Item[] temp = (Item[]) new Object[cap];
        int j = 0;
        for (int i = 0; i < N; i++){
            if (arr[i] != null)
                temp[j++] = arr[i];
        }
        arr = temp;
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<String> rq = new RandomizedQueue();
        rq.enqueue("Love");
        rq.enqueue("Computer");
        rq.enqueue("Program");
        rq.enqueue("Algorithm");
        rq.enqueue("Data Structure");

        for (String s : rq)
        {
            StdOut.println(s);
        }
        StdOut.println();
        rq.dequeue();

        for (String s : rq)
        {
            StdOut.println(s);
        }
        StdOut.println();
        rq.dequeue();

        for (String s : rq)
        {
            StdOut.println(s);
        }
        StdOut.println();
    }

}