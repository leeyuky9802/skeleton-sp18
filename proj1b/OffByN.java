public class OffByN implements CharacterComparator {
    private static int n;

    @Override
    public boolean equalChars(char x, char y) {
        int diff='x'-'y';
        diff=Math.abs(diff);
        if(diff==n) return true;
        else return false;
    }

    public OffByN(int nn){
        this.n=nn;
    }
}
