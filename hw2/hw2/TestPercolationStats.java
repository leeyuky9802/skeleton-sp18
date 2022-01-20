package hw2;

import org.junit.Test;

public class TestPercolationStats {
    @Test
    public void testDoEx(){
        PercolationStats s = new PercolationStats(20,100,new PercolationFactory());
        System.out.println(s.mean);
    }
}
