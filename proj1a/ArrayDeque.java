public class ArrayDeque<Item> {
    Item[] items;
    int length;
    int size;
    int nextLast;
    int nextFirst;
    public ArrayDeque(){
        items =(Item[]) new Object[8];
        size=0;
        nextFirst=3;
        nextLast=4;
        length=8;
    }
    public void addFirst(Item item){
        items[nextFirst]=item;
        nextFirst--;
        nextFirst+=length;
        nextFirst%=length;
        size++;
        check();

    }
    public void addLast(Item item){
        items[nextLast]=item;
        nextLast++;
        nextLast+=length;
        nextLast%=length;
        size++;
        check();

    }
    public boolean isEmpty(){ if(size==0) return true;else return false;}
    public int size(){return size;}
    public void printDeque(){
        int p=nextFirst;
        p++;
        p%=length;
        for(int i=0;i<size;i++){
            System.out.print(items[p]+" ");
            p++;
            p%=length;

        }
    }
    public Item removeFirst(){
        Item temp;
        nextFirst++;
        nextFirst+=length;
        nextFirst%=length;
        size--;
        temp=items[nextFirst];
        check();
        return temp;
    }
    public Item removeLast(){
        Item temp;
        nextLast--;
        nextLast+=length;
        nextLast%=length;
        size--;
        temp=items[nextLast];
        check();
        return temp;
    }
    public Item get(int Index){
        int i=nextFirst+Index+1;
        i%=length;
        return items[i];
    }
    public void check(){
        if(nextLast==nextFirst) rearrange(length*2);
        if(length>=16&&4*size<length) rearrange(length/2);
    }
    public void rearrange(int newSize){
        int l=length;
        length=newSize;
        Item[] temp = (Item[]) new Object[newSize];
        nextFirst++;
        nextFirst%=l;
        for(int i=0;i<size;i++){
            temp[i]=items[nextFirst];
            nextFirst++;
            nextFirst%=l;
        }
        nextFirst=length-1;
        nextLast=size;
        items=temp;
    }
}
