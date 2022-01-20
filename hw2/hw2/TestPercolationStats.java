package hw2;

import org.junit.Test;

public class TestPercolationStats {
    @Test
    public void testDoEx(){
        PercolationStats s = new PercolationStats(20,10,new PercolationFactory());
        System.out.println(s.confidenceHigh);
    }
}
