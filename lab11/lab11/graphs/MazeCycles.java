package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycleFound = false;
    private Maze maze;
    int sizeN;

    private void clear(){
        for(int i=0;i<sizeN*sizeN-1;i++){
            distTo[i]=Integer.MAX_VALUE;
            edgeTo[i]=Integer.MAX_VALUE;
            marked[i]= false;
        }
    }


    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        sizeN = m.N();
    }

    private boolean dfs(int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if(marked[w]&&edgeTo[v]!=w){
                cycleFound=true;
                edgeTo[w] = v;
                drawCycle(w,v);
                return true;
            }else if (!marked[w]) {
                edgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
                return dfs(w);
            }
        }
        return false;
    }

    private void drawCycle(int found,int current){
        announce();
        return;
    }



    @Override
    public void solve() {
        int i=0;
        while(!cycleFound){
            distTo[i] =0;
            edgeTo[i] =i;
            dfs(i);
            clear();
            i++;
        }
        return;
    }
}

