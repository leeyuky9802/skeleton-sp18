package synthesizer;

import java.awt.event.ItemEvent;

public abstract class AbstractBoundedQueue<Item> implements BoundedQueue<Item> {
    protected int fillCount;
    protected int capacity;
    @Override
    public int capacity(){
        return capacity;
    }
    @Override
    public int fillCount(){
        return fillCount;
    }

}
