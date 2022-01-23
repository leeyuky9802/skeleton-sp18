package hw4.puzzle;

import java.util.*;

public class Board implements Iterable<WorldState>,WorldState {
    int[][] tiles;
    int sizeN;
    int zeroX;
    int zeroY;
    int estmation;
    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    public Board(int[][] tiles){
        this.sizeN= tiles.length;
        int[][] newTiles = new int[sizeN][sizeN];
        for(int row=0;row<sizeN;row++){
            for(int col=0;col<sizeN;col++){
                newTiles[row][col] = tiles[row][col];
            }
        }
        this.tiles= newTiles;

        int count=0;
        for(int row=0;row<sizeN;row++){
            for(int col=0;col<sizeN;col++){
                if(tileAt(row,col)==0){
                    zeroX=row;
                    zeroY=col;
                    estmation=manhattan();
                    return;
                }
            }
        }
    }
    public int tileAt(int i, int j){
        if(i<0||i>=sizeN||j<0||j>=sizeN) throw new IndexOutOfBoundsException();
        return tiles[i][j];
    }
    public int size(){return sizeN;}
    public Iterable<WorldState> neighbors(){
        return this;
    }
    public int hamming(){
        int count=0;
        for(int row=0;row<sizeN;row++){
            for(int col=0;col<sizeN;col++){
                if(tileAt(row,col)!=corropadingInt(row,col))  count++;
            }
        }
        return count;
    }

    private int corropadingInt(int row,int column){
        return row*sizeN+column+1;
    }
    public int manhattan(){
        int count=0;
        for(int row=0;row<sizeN;row++){
            for(int col=0;col<sizeN;col++){
                int temp = tileAt(row,col);
                if(temp == 0) continue;
                temp--;
                count+=Math.abs(row-temp/sizeN);
                count+=Math.abs(col-temp%sizeN);
            }
        }
        return count;
    }
    public int estimatedDistanceToGoal(){
        return estmation;
    }
    public boolean equals(Object y){
        Board that = (Board) y;
        if(that.sizeN!= this.sizeN) return false;
        for(int row=0;row<sizeN;row++){
            for(int col=0;col<sizeN;col++){
                if(this.tileAt(row,col)!=that.tileAt(row,col))return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<WorldState> iterator() {
        return new BoardIter(this);
    }

    public Deque<WorldState> neis(){
        Deque<WorldState> rt = new ArrayDeque<>();
        try {
            int[][] newTiles = new int[sizeN][sizeN];
            for(int row=0;row<sizeN;row++){
                for(int col=0;col<sizeN;col++){
                    newTiles[row][col] = tileAt(row,col);
                }
            }
            newTiles[zeroX+1][zeroY] = 0;
            newTiles[zeroX][zeroY] = tileAt(zeroX+1,zeroY);
            rt.add(new Board(newTiles));
        }catch (Exception e){}
        try {
            int[][] newTiles = new int[sizeN][sizeN];
            for(int row=0;row<sizeN;row++){
                for(int col=0;col<sizeN;col++){
                    newTiles[row][col] = tileAt(row,col);
                }
            }
            newTiles[zeroX-1][zeroY] = 0;
            newTiles[zeroX][zeroY] = tileAt(zeroX-1,zeroY);
            rt.add(new Board(newTiles));
        }catch (Exception e){}
        try {
            int[][] newTiles = new int[sizeN][sizeN];
            for(int row=0;row<sizeN;row++){
                for(int col=0;col<sizeN;col++){
                    newTiles[row][col] = tileAt(row,col);
                }
            }
            newTiles[zeroX][zeroY+1] = 0;
            newTiles[zeroX][zeroY] = tileAt(zeroX,zeroY+1);
            rt.add(new Board(newTiles));
        }catch (Exception e){}
        try {
            int[][] newTiles = new int[sizeN][sizeN];
            for(int row=0;row<sizeN;row++){
                for(int col=0;col<sizeN;col++){
                    newTiles[row][col] = tileAt(row,col);
                }
            }
            newTiles[zeroX][zeroY-1] = 0;
            newTiles[zeroX][zeroY] = tileAt(zeroX,zeroY-1);
            rt.add(new Board(newTiles));
        }catch (Exception e){}

        return rt;
    }

    @Override
    public int hashCode(){
        int hash=0;
        for(int row=0;row<sizeN;row++){
            for(int col=0;col<sizeN;col++){
                hash+=tileAt(row,col);
                hash*= col;
            }
        }
        return hash;
    }

    private class BoardIter implements Iterator<WorldState>{
        Deque<WorldState> deque;

        BoardIter(Board board){
            deque=board.neis();
        }

        @Override
        public boolean hasNext() {
            return !deque.isEmpty();
        }

        @Override
        public WorldState next() {
            return deque.removeLast();
        }
    }
}
