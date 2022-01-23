package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import org.w3c.dom.Node;

import java.util.*;
import java.util.function.Consumer;

public class Solver {
    MinPQ<Node> minPQ;
    int moves;
    Node lastNode;
    Set<WorldState> checked;
    protected class Node implements Comparable<Node>,Iterable<WorldState>{
        WorldState w;
        int moves;
        Node previous;
        public Node(WorldState ww,int mmoves, Node pre){
            w=ww;
            moves=mmoves;
            previous=pre;
        }
        private int priority(){
            return moves+this.w.estimatedDistanceToGoal();
        }
        @Override
        public int compareTo(Node o) {
            return this.priority()-o.priority();
        }

        @Override
        public Iterator<WorldState> iterator() {
            return new WorldStateIterator(this);
        }
    }


    public Solver(WorldState initial){
        checked = new HashSet<>();
        Node init = new Node(initial,0,null);
        minPQ = new MinPQ<Node>();
        minPQ.insert(init);
        while (minPQ.size()!=0){
            Node temp = minPQ.delMin();
            checked.add(temp.w);
            if(temp.w.isGoal()){
                end(temp);
                return;
            }
            int tempMoves;
            Iterable<WorldState> delNei= temp.w.neighbors();
            tempMoves= temp.moves+1;
            for(WorldState w:delNei){
                if(checked.contains(w)) continue;
                Node t = new Node(w,tempMoves,temp);
                minPQ.insert(t);
            }
        }
    }

    private class WorldStateIterator implements Iterator<WorldState>{
        ArrayDeque<WorldState> l;

        public WorldStateIterator(Node last){
            l = new ArrayDeque<>();
            while(last.previous!=null){
                l.add(last.w);
                last=last.previous;
            }
            l.add(last.w);
        }
        @Override
        public boolean hasNext() {
            return !l.isEmpty();
        }

        @Override
        public WorldState next() {
            return l.removeLast();
        }
    }

    private void end(Node last){
        this.moves = last.moves;
        lastNode = last;
        return;
    }
    public int moves(){
        return moves;
    }
    public Iterable<WorldState> solution(){
        return lastNode;
    }
}
