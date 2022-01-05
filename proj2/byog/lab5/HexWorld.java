package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static void addHexagon(TETile[][] tiles,int size,int x,int y){

        int dSize=2;
        int bound =y-size;
        //check(tiles.length,tiles[0].length,size,x,y);
        for(;y>bound;y--){
            for(int j=x;j<x+dSize;j++){
                tiles[j][y]= Tileset.WALL;
            }
            dSize+=2;
            x--;
        }
        bound-=size;
        dSize-=2;
        x+=1;
        for(;y>bound;y--){
            for(int j=x;j<x+dSize;j++){
                tiles[j][y]= Tileset.WALL;
            }
            dSize-=2;
            x++;
        }
    }

    public static void main(String args[]){
        TERenderer ter= new TERenderer();
        TETile[][] tiles = new TETile[10][10];
        ter.initialize(10,10);
        for (int x = 0; x < 10; x += 1) {
            for (int y = 0; y <10; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }


        ter.renderFrame(tiles);
    }

    
}
