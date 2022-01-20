package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.HashSet;
import java.util.Set;

public class PercolationStats {
    Percolation p;
    PercolationFactory f;

    double mean      ;
    double stddev     ;
    double confidenceLow;
    double confidenceHigh;

    public PercolationStats(int N, int T, PercolationFactory pf){
        if(N<0) throw new IllegalArgumentException();
        if(T<0) throw new IllegalArgumentException();
        f=pf;
        doStuff(N,T);
    }   // perform T independent experiments on an N-by-N grid
    public double mean()       {return mean;}                                    // sample mean of percolation threshold
    public double stddev()                  {return  stddev;}                       // sample standard deviation of percolation threshold
    public double confidenceLow()                 {return confidenceLow;}                 // low endpoint of 95% confidence interval
    public double confidenceHigh() {return confidenceHigh;}                                // high endpoint of 95% confidence interval
    private void doStuff(int N,int T){
        double[] results = new double[T];
        for(int i=0;i<T;i++){
            results[i]=doEx(N);
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceLow = mean - 1.96*stddev/Math.sqrt(T);
        confidenceHigh = mean + 1.96*stddev/Math.sqrt(T);
    }
    public double doEx(int N){
        Percolation p =f.make(N);
        int[] permutaion = StdRandom.permutation(N*N);
        int count=0;
        while (!p.percolates()){
            p.open(permutaion[count]/N,permutaion[count]%N);
            count++;
        }
        double c = count;
        double rt= c/(N*N);
        return rt;
    }
}
