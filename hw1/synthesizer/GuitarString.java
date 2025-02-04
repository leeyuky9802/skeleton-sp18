// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;

//Make sure this class is public
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayRingBuffer<Double>((int)Math.round(SR/frequency));
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
         int size=buffer.fillCount();
         for(int i=0;i<size;i++){
             buffer.dequeue();
         }

         for(int i=0;i<buffer.capacity();i++){
             buffer.enqueue(randomNum());
         }

        // TODO: Dequeue everything in the buffer, and replace it with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each other.
    }
    private double randomNum(){
        return Math.random()-0.5;
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        Double d;
        d=buffer.dequeue();
        if(d==null) return;
        double one=d;
        double two=buffer.peek();
        one+=two;
        one*=DECAY;
        one/=2;
        buffer.enqueue(-one);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        if(buffer.peek()==null) return 0;
        return buffer.peek();
    }
}
