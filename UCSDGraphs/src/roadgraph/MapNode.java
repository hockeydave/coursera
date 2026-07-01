package roadgraph;

import geography.GeographicPoint;

import java.util.*;

import static roadgraph.RoadTypeSpeed.*;

/**
 * @author David C Peterson
 * <p>
 * A class which represents a Vertex (Node) representing an intersection on a geographic map (usually).
 * It has a GeographicPoint (Lat/Lon) to represent its location.
 */
public class MapNode implements Comparable<MapNode> {

    // The geographic lat/lon location of this vertex.
    private final GeographicPoint location;
    // Edges connected to this vertex
    private final HashSet<MapEdge> edges;

    // Len from this node to the start/goal nodes are required for Dijkstra/AStar algorithms.
    private double lenFromStartNode;
    private double lenFromGoalNode;
    private double travelTimeFromStartNode;

    private MapGraph.PathCriteria pathCriteria;



    /**
     * Constructor for a MapNode
     *
     * @param location GeographicPoint Lat/Lon location on the map for this vertex (node)
     */
    MapNode(GeographicPoint location) {
        this.location = location;
        this.edges = new HashSet<>();
        lenFromStartNode = 0;
        pathCriteria = MapGraph.PathCriteria.SHORTEST_PATH;
    }

    public void setPathCriteria(MapGraph.PathCriteria pathCriteria) {
        this.pathCriteria = pathCriteria;
    }

    /**
     * @return Return the GeographicPoint (Lat/Lon) where this vertex (node) sits on a geographic map.
     */
    GeographicPoint getLocation() {
        return location;
    }

    /**
     * Get the length from the start node to this node (in km)
     *
     * @return length from the start node to this node (in km)
     */
    public double getLenFromStartNode() {
        return lenFromStartNode;
    }

    /**
     * Set the length of the start node to this node (in km)
     *
     * @param lenFromStartNode the length from this node to the start node.
     */
    public void setLenFromStartNode(double lenFromStartNode) {
        this.lenFromStartNode = lenFromStartNode;
    }

    /**
     * Set the length of this node from the goal node in miles (important for A* search)
     *
     * @param lenFromGoalNode km distance from this node to the goal node
     */
    public void setLenFromGoalNode(double lenFromGoalNode) {
        this.lenFromGoalNode = lenFromGoalNode;
    }

    /**
     * Get the length of this node from goal node in miles.
     *
     * @return the length in km from this node to goal node.
     */
    public double getLenFromGoalNode() {
        return lenFromGoalNode;
    }

    /**
     * Get the Traversal time from the Start node (in the path traversal to this node).
     * @return the traversal time (hours) from the Start node in path to this node.
     */
    public double getTravelTimeFromStartNode() {
        return travelTimeFromStartNode;
    }

    /**
     * Set the traversal time.
     * @param travelTimeFromStartNode Traversal time (hours) from the Start node in path to this node.
     */
    public void setTravelTimeFromStartNode(double travelTimeFromStartNode) {
        this.travelTimeFromStartNode = travelTimeFromStartNode;
    }


    /**
     * @return the neighbors of this MapNode. Useful for searching for paths in a MapGraph via algorithms.
     */
    Set<MapNode> getNeighbors() {
        Set<MapNode> neighbors = new HashSet<>();
        for (MapEdge edge : edges) {
            neighbors.add(edge.getOtherNode(this));
        }
        return neighbors;
    }

    /**
     * Add a directed edge to this MapNode (vertex).
     *
     * @param edge MapEdge between this node and a neighbor node.
     */
    void addEdge(MapEdge edge) {
        edges.add(edge);
    }

    MapEdge getEdge(MapNode endNode) {
        for (MapEdge edge : edges) {
            if (edge.getOtherNode(this).equals(endNode)) return edge;
        }
        return null;
    }

    /**
     * Returns whether two nodes are equal.
     * Nodes are considered equal if their locations are the same,
     * even if their street list is different.
     *
     * @param o the node to compare to
     * @return true if these nodes are at the same location, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MapNode)) {
            return false;
        }
        MapNode node = (MapNode) o;
        return node.location.equals(this.location);
    }

    /**
     * Because we compare nodes using their location, we also
     * may use their location for HashCode.
     *
     * @return The HashCode for this node, which is the HashCode for the
     * underlying point
     */
    @Override
    public int hashCode() {
        return location.hashCode();
    }

    /**
     * Override the default Object String to return a more meaningful representation of this MapNode
     *
     * @return String representation of this MapNode
     */
    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder("[NODE at location (" + location + ")");
        toReturn.append(" priority: ").append(getPriority());
        toReturn.append(" intersects streets: ");
        for (MapEdge e : edges) {
            toReturn.append(e.getStreetName()).append(", ");
        }
        toReturn.append("]");
        return toReturn.toString();
    }

    // For debugging, output roadNames as a String.
    public String roadNamesAsString() {
        StringBuilder toReturnBuilder = new StringBuilder("(");
        for (MapEdge e : edges) {
            toReturnBuilder.append(e.getStreetName()).append(", ");
        }
        toReturnBuilder.append(")");
        return toReturnBuilder.toString();
    }

    /**
     * Get the Priority for this node in the PriorityQueue depending upon whether we are using the shortest path
     * or shortest travel duration
     * @return double to indicate the Priority for this node in the PriorityQueue depending upon whether we are
     * using the shortest path or the least travel duration
     */
    private double getPriority() {
        if(pathCriteria == MapGraph.PathCriteria.SHORTEST_PATH)
            return lenFromStartNode + lenFromGoalNode;
        else {
            // Assume "as the bird flies speed to be the slowest street type speed since we need underestimate
            return travelTimeFromStartNode + (lenFromGoalNode/ getKPHbyRoadType(MAX_US_SPEED));
        }
    }



    /**
     * Implement Comparable method compareTo so that this class object can go into a PriorityQueue
     * Comparable Compares this object with the specified object for order using Dijkstra's shortest
     * distance from start node priority.
     *
     * @param o MapNode object to compare to
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.  In the case of MapNode we are using the lenFromStartNode to determine priority where
     * lower numbers have higher priority since we're trying to find the shortest path.
     */
    @Override
    public int compareTo(MapNode o) {
        if (o == null) throw new NullPointerException();
        return Double.compare(this.getPriority(), o.getPriority());
    }
}
