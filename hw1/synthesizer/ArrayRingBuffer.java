package synthesizer;

import org.junit.Test;

import java.util.Iterator;

public class ArrayRingBuffer<Item> extends AbstractBoundedQueue<Item>
{
    public int first;
    public int last;
    Item[] items;

    @Override
    public void enqueue(Item item) {
        if(capacity==fillCount) throw new RuntimeException("Ring Buffer Overflow");
        items[last] = item;
        fillCount++;
        last+=1;
        last%=capacity;
    }

    @Override
    public Item dequeue() {
        if(fillCount==0) throw new RuntimeException("Ring Buffer Underflow");
        int rt = first;
        first++;
        first %= capacity;
        fillCount--;
        return items[rt];
    }

    @Override
    public Item peek() {
        return items[first];
    }

    public ArrayRingBuffer(int capacity){
        this.capacity=capacity;
        this.fillCount=0;
        items= (Item[]) new Object[capacity];
        this.first=0;
        this.last=0;
    }

    @Override
    public Iterator<Item> iterator(){
        return new ARBIterator<Item>();
    }

    private class ARBIterator<Item> implements Iterator<Item>{
        int front;
        int rear;
        int count;

        public ARBIterator(){
            front = first;
            rear = last;
            count = fillCount;
        }
        @Override
        public boolean hasNext() {
            return count>0;
        }

        @Override
        public Item next() {
            int rt = front;
            front ++;
            front %=capacity;
            count--;

            return (Item) items[rt];
        }
    }

}
