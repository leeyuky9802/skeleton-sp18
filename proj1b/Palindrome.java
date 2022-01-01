public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> rt= new ArrayDeque<Character>();
        for(char a : word.toCharArray()){
            rt.addLast(a);
        }
        return rt;
    }
    public boolean isPalindrome(String word){
        Deque<Character> palin = wordToDeque(word);
        while(palin.size()>1){
            if(palin.removeFirst()==palin.removeLast()) continue;
            else return false;
        }
        return true;
    }
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> palin = wordToDeque(word);
        while(palin.size()>1){
            if(cc.equalChars(palin.removeFirst(),palin.removeLast())) continue;
            else return false;
        }
        return true;
    }
}
