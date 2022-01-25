import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class SPHelper {
    HashSet<Long> vistedNodes = new HashSet<>();
    AStarNode rt;
    Map<Long,Node> graph;
    long dest;
    double destLon,destLat;
    GraphDB g;
    PriorityQueue<AStarNode> PQ = new PriorityQueue<>();

    public SPHelper(GraphDB g, long id, long dest){
        this.g= g;
        this.graph = g.graph;
        this.dest = dest;
        this.destLon= graph.get(id).lon;
        this.destLat= graph.get(id).lat;
        AStarNode startNd = new AStarNode();
        startNd.id = id;
        startNd.distance = 0;
        startNd.priority= heuristic(id);
        PQ.add(startNd);

        while(true){
            AStarNode temp = PQ.remove();
            if(temp.id==dest){
                rt=temp;
                break;
            }
            shortestHelper(temp);
        }
    }

    private double heuristic(long id){
        return GraphDB.distance(destLon,destLat,graph.get(id).lon,graph.get(id).lat);
    }

    public void shortestHelper(AStarNode currentNode){
        vistedNodes.add(currentNode.id);
        for(Way way:graph.get(currentNode.id).ways){
            if(!vistedNodes.contains(way.theOtherEnd.id)){
                AStarNode t = new AStarNode();
                t.priority = currentNode.distance + way.distance+heuristic(way.theOtherEnd.id);
                t.distance = currentNode.distance + way.distance;
                t.previous = currentNode;
                t.id = way.theOtherEnd.id;
                PQ.add(t);
            }
        }
    }
}
