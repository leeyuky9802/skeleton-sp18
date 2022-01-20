package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    int n;
    boolean[][] grid;
    WeightedQuickUnionUF disjointSets;
    boolean isPorous;
    int countOpen;
    public Percolation(int N){
        disjointSets = new WeightedQuickUnionUF(N*N+1+1);
        countOpen = 0;
        n=N;
        grid= new boolean[N][N];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                grid[i][j]=false;
            }
        }
        for(int i=0;i<n;i++){
            disjointSets.union(i,N*N);
            disjointSets.union(n*n-i-1,N*N+1);
        }
        isPorous=false;
    }             // create N-by-N grid, with all sites initially blocked


    public void open(int row, int col){
        check(row);
        check(col);
        if(grid[row][col]==true) return;
        countOpen++;
        grid[row][col]= true;
        try{
            connect(row,col,row-1,col);
        } catch (Exception e){}
        try{
            connect(row,col,row+1,col);
        } catch (Exception e){}
        try{
            connect(row,col,row,col-1);
        } catch (Exception e){}
        try{
            connect(row,col,row,col+1);
        } catch (Exception e){}

        if(disjointSets.connected(rowColToInt(row,col),n*n+1))isPorous=true;

    }       // open the site (row, col) if it is not open already


    public boolean isOpen(int row, int col){
        return grid[row][col];
    }  // is the site (row, col) open?


    public boolean isFull(int row, int col){
        check(row);
        check(col);
        if(!grid[row][col]) return false;
        if(disjointSets.connected(rowColToInt(row,col),n*n)) return true;
        else return false;
    }  // is the site (row, col) full?
    public int numberOfOpenSites(){
        return countOpen;
    }           // number of open sites
    public boolean percolates(){
        return isPorous;
    }              // does the system percolate?
    private int rowColToInt(int row,int col){
        return row*n+col;
    }
    private void check(int i){
        if(i<0) throw new java.lang.IllegalArgumentException();
        if(i>n-1) throw new java.lang.IndexOutOfBoundsException();
    }
    private void connect(int row1,int col1,int row2,int col2){
        check(row2);
        check(col2);
        if(grid[row2][col2]==false) return;
        disjointSets.union(rowColToInt(row1,col1),rowColToInt(row2,col2));
    }


 /*   public static void main(String[] args){

    }*/
}
