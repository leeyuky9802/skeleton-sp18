package hw2;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestPercolation {
    @Test
    public void test(){
        Percolation p = new Percolation(100);
        for(int i=0;i<100;i++){
            p.open(i,1);
        }
        System.out.println(p.numberOfOpenSites());
    }
}
