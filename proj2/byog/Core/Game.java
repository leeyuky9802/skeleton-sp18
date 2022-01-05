package byog.Core;

import byog.TileEngine.*;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.time.chrono.ThaiBuddhistEra;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.zip.CheckedInputStream;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 16;
    public static final int HEIGHT = 32;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        String startString =mainMenu();
        if(startString==null) return;
        playWithInputString(startString);


        char first = solicitCharsInput();
        startString+=first;
        playWithInputString(startString);

        char second = solicitCharsInput();
        startString+=second;
        playWithInputString(startString);

        if(first==':'&&second=='q') save(startString);
        while(true){
            first=second;
            second = solicitCharsInput();
            startString+=second;
            playWithInputString(startString);
            if(first==':'&&second=='q') save(startString);
        }
    }

    private void save(String s){
        return;
    }
    private String mainMenu(){
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.white);
        String s = "New Game (N)";
        StdDraw.text(WIDTH/2,HEIGHT/2+1, s);
        StdDraw.text(WIDTH/2,HEIGHT/2,"Load Game (L)");
        StdDraw.text(WIDTH/2,HEIGHT/2-1,"Quit (Q)");
        StdDraw.show();
        char temp;
        while(true){
            temp=solicitCharsInput();
            if(temp=='n'){
                return enter();
            }
            if(temp=='l'){
                return readFromSave();
            }
            if(temp=='q') return null;
        }
    }

    private String enter(){
        String rt="";
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.text(WIDTH/2,HEIGHT/2,rt);
        StdDraw.show();
        char temp = solicitCharsInput();
        while(temp!='s'){
            rt+=temp;
            StdDraw.clear();
            StdDraw.clear(Color.black);
            StdDraw.text(WIDTH/2,HEIGHT/2,rt);
            StdDraw.show();
            temp = solicitCharsInput();
        }
        return 'n'+rt+'s';
    }

    private String readFromSave(){
        return null;
    }

    public char solicitCharsInput() {

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            return key;
        }
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        char[] startString = input.toCharArray();
        int countInput =0;
        String s = ""; s+=startString[countInput];countInput++;
        while(true){
            if(startString[countInput]!='s'){
                s+= startString[countInput];
                countInput++;
            }else break;
        }
        int cut = countInput+1;
        Random random = new Random(seedGen(s));


        int width = random.nextInt(60)+50;
        int height = random.nextInt(30)+25;
        int[] xs = new int[100];
        int[] ys = new int[100];
        int[][] map = new int[width][height];
        for(int[] i:map){
            for(int j:i){
                j=0;
            }
        }

        int countR=0;
        for(int i=0;i<15;i++) {
            int dx = random.nextInt(width-4)+2;
            int dy = random.nextInt(height-4)+2;
            if (map[dx][dy] != 0) continue;
            else {
                for (int j = 0; j < 10; j++) {
                    int w=random.nextInt(width/3);
                    int h=random.nextInt(height/3);
                    w+=2;
                    h+=2;
                    if(checkRoom(dx,dy,w,h,map)){
                        xs[countR]=dx;
                        ys[countR]=dy;
                        countR++;
                        break;
                    }
                }
            }
        }


        Set<Integer> connected = new HashSet<>();
        Set<Integer> inconnected = new HashSet<>();
        connected.add(0);
        for(int i=1;i<countR;i++) inconnected.add(i);
        while(!inconnected.isEmpty()){
            int conee1 = randomSelect(connected,random);
            int conee2 = randomSelect(inconnected,random);
            inconnected.remove(conee2);
            connected.add(conee2);
            for(int i=Math.min(xs[conee1],xs[conee2]);i<=Math.max(xs[conee1],xs[conee2]);i++){
                map[i][ys[conee1]] = 1;
            }
            for(int i=Math.min(ys[conee1],ys[conee2]);i<=Math.max(ys[conee1],ys[conee2]);i++){
                map[xs[conee2]][i] = 1;
            }
        }

        for(int i=1;i<width-1;i++){
            for(int j=1;j<height-1;j++){
                if(map[i][j]==0){
                    if(map[i-1][j]>0||map[i+1][j]>0||map[i][j-1]>0||map[i][j+1]>0||map[i-1][j-1]>0||map[i+1][j+1]>0||map[i+1][j-1]>0||map[i-1][j+1]>0) map[i][j] =-1;
                }
            }
        }

        int playerX = xs[0];
        int playerY = ys[0];
        for(;countInput<startString.length;countInput++){
            switch(startString[countInput]) {
                case 'w':
                    if (map[playerX][playerY + 1] > 0) playerY++;
                    break;
                case 'a':
                    if (map[playerX - 1][playerY] > 0) playerX--;
                    break;
                case 's':
                    if (map[playerX][playerY - 1] > 0) playerY--;
                    break;
                case 'd':
                    if (map[playerX + 1][playerY] > 0) playerX++;
                    break;
            }
        }







/*        TERenderer ter = new TERenderer();
        if(input.length()<=cut){
            ter.initialize(width,height);
        }*/
        TETile[][] world = mapToWorld(map,width,height);
        world[playerX][playerY] = Tileset.PLAYER;
       // ter.renderFrame(world);
        return world;
    }

    private int randomSelect(Set<Integer> myhashSet,Random random){
        int size = myhashSet.size();
        int item = random.nextInt(size);
        int i = 0;
        for(int obj : myhashSet)
        {
            if (i == item) return obj;
        }
        return 0;
    }

    private long seedGen(String s){
        long rt = 0;
        char[] ch= s.toCharArray();
        long base = 1;
        for (int i = 1; i < ch.length-1; i++) {
            rt += (ch[i]-48)*base;
            base*=10;
        }

        return rt;
    }

    private boolean checkRoom(int x,int y,int w,int h,int[][] map){
        if((x+w)>=map.length-3) return false;
        if((y+h)>=map[0].length-3) return false;
        for(int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                if(map[x+i][y+j]!=0) return false;
            }
        }
        for(int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                map[x+i][y+j]=1;
            }
        }
        return true;
    }

    private TETile[][] mapToWorld(int[][] map, int width, int height){
        TETile[][] rt= new TETile[width][height];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                if(map[i][j]==0){
                    rt[i][j] = Tileset.NOTHING;
                }else if(map[i][j]>0) rt[i][j] = Tileset.FLOOR;else rt[i][j]=Tileset.WALL;
            }
        }
        return rt;
    }
}
