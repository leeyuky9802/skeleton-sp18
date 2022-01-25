public class AStarNode implements Comparable<AStarNode> {
    long id;
    AStarNode previous;
    double distance;
    double priority;

    @Override
    public int compareTo(AStarNode o) {
        return Double.compare(this.priority,o.priority);
    }
}
