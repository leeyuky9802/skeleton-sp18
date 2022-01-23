package lab11.graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    Deque<Integer> fringe;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        fringe = new ArrayDeque();
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe.add(s);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        while (!targetFound){
            int now = fringe.removeFirst();
            if(marked[now]) continue;
            marked[now] = true;
            announce();
            if(now == t){
                this.targetFound=true;
                return;
            }
            Iterable<Integer> neighbours = maze.adj(now);
            int tempDis = distTo[now];
            tempDis++;
            for(int nei:neighbours){
                if(!marked[nei]){
                    fringe.add(nei);
                    distTo[nei] = tempDis;
                    edgeTo[nei] = now;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

