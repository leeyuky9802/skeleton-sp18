import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

public class RasterHelper {
    public static String xyToS(int x,int y,int depth){
        StringBuilder rt = new StringBuilder();
        rt.append('d');
        rt.append(depth);
        rt.append('_');
        rt.append('x');
        rt.append(x);
        rt.append('_');
        rt.append('y');
        rt.append(y);
        rt.append(".png");
        return rt.toString();
    }
    public static String[][] addImages(int ulx,int uly,int lrx,int lry,int depth){
        String[][] rt = new String[lry-uly+1][lrx-ulx+1];
        for(int j=uly;j<=lry;j++){
            for(int i=ulx;i<=lrx;i++){
                rt[j-uly][i-ulx] = xyToS(i,j,depth);
            }
        }
        return rt;
    }
}
/*
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
             *                    forget to set this to true on success! <br>*/
