public class LinkedListDeque<Item> {
    private class Node{
        Item item;
        Node next;
        Node previous;
    }
    private Node sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new Node();
        sentinel.next=sentinel;
        sentinel.previous=sentinel;
        size=0;
    }

    public void addFirst(Item item){
        Node temp = new Node();
        temp.item=item;
        temp.next=sentinel.next;
        temp.previous=sentinel;
        sentinel.next.previous=temp;
        sentinel.next=temp;
        size++;
    }
    public void addLast(Item item){
        Node temp = new Node();
        temp.item=item;
        temp.next=sentinel;
        temp.previous=sentinel.previous;
        sentinel.previous.next=temp;
        sentinel.previous=temp;
        size++;
    }
    public boolean isEmpty(){
        if(size==0) return true;
        else return false;
    }
    public int size() { return size;}
    public void printDeque(){
        Node p = sentinel;
        if(p.next==sentinel) return;
        p=p.next;
        while(p!=sentinel){
            System.out.print(p.item+" ");
            p=p.next;
        }
    }
    public Item removeLast(){
        Node ret=sentinel.previous;
        sentinel.previous=sentinel.previous.previous;
        sentinel.previous.next=sentinel;
        size--;
        return ret.item;
    }
    public Item removeFirst(){
        Node ret=sentinel.next;
        sentinel.next=sentinel.next.next;
        sentinel.next.previous=sentinel;
        size--;
        return ret.item;
    }

    public Item get(int Index){
        if(Index>=size) return null;
        Node p=sentinel;
        for(int i=0;i<=Index;i++){
            p=p.next;
        }
        return p.item;
    }

    public Item getRecursive(int Index){
        return getRecursiveHelper(Index,sentinel);
    }

    public Item getRecursiveHelper(int Index,Node node){
        if(Index==-1) return node.item;
        else{
            return getRecursiveHelper(Index-1,node.next);
        }
    }
}
