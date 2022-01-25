import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    double[] lonDPP;
    double[] lon;
    double[] lat;
    private double ullon,ullat,lrlon,lrlat;
    int ulx,uly,lrx,lry;
    private double rasterUllon,rasterUllat,rasterLrlon,rasterLrlat;
    int depth;

    private int getDepth(double dpp){
        for(int i=0;i<8;i++){
            if(lonDPP[i]<dpp) return i;
        }
        return 7;
    }

    private void calcResult(){
        ulx= xAxis(ullon);
        uly= yAxis(ullat);
        lrx= xAxis(lrlon);
        lry= yAxis(lrlat);
        rasterUllon = calcRasterX(ulx) + ROOT_ULLON;
        rasterUllat = -calcRasterY(uly) + ROOT_ULLAT;
        rasterLrlon = calcRasterX(lrx) + ROOT_ULLON+lon[depth];
        rasterLrlat = -calcRasterY(lry) + ROOT_ULLAT-lat[depth];
    }

    private double calcRasterX(int i){
        double rt = 0;
        for(int j=i;j>0;j--){
            rt+=lon[depth];
        }
        return rt;
    }

    private double calcRasterY(int i){
        double rt = 0;
        for(int j=i;j>0;j--){
            rt+=lat[depth];
        }
        return rt;
    }

    private int xAxis(double offset){
        double rootLength = Math.abs(ROOT_ULLON-ROOT_LRLON);
        double target = Math.abs(offset-ROOT_ULLON);
        int mult= (int) Math.pow(2,depth);
        double ratio = target/rootLength;
        double rt = ratio *mult;
        return (int) rt;
    }

    private int yAxis(double offset){
        double rootLength = Math.abs(ROOT_ULLAT-ROOT_LRLAT);
        double target = Math.abs(offset-ROOT_ULLAT);
        int mult= (int) Math.pow(2,depth);
        double ratio = target/rootLength;
        double rt = ratio *mult;
        return (int) rt;
    }

    //take tha map and set the values
    private void setValues(Map<String,Double> map){
        ullon= map.get("ullon");
        ullat= map.get("ullat");
        lrlon= map.get("lrlon");
        lrlat= map.get("lrlat");
        depth = getDepth(Math.abs(ullon-lrlon)/map.get("w"));
    }

    public Rasterer() {
        lonDPP = new double[8];
        lat = new double[8];
        lon = new double[8];
        lonDPP[0] = Math.abs(ROOT_ULLON-ROOT_LRLON)/256;
        lon[0] = Math.abs(ROOT_ULLON-ROOT_LRLON);
        lat[0] = Math.abs(ROOT_ULLAT-ROOT_LRLAT);
        for(int i=1;i<8;i++){
            lonDPP[i] = lonDPP[i-1]/2;
            lon[i] = lon[i-1]/2;
            lat[i] = lat[i-1]/2;
        }
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        setValues(params);
        calcResult();
        results.put("render_grid",RasterHelper.addImages(ulx,uly,lrx,lry,depth));
        results.put("raster_ul_lon",rasterUllon);
        results.put("raster_ul_lat",rasterUllat);
        results.put("raster_lr_lon",rasterLrlon);
        results.put("raster_lr_lat",rasterLrlat);
        results.put("depth",depth);
        results.put("query_success",true);
        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "+ "your browser.");
        System.out.println(results);
        return results;
    }

}
