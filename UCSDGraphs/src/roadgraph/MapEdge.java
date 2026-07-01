package roadgraph;

import geography.GeographicPoint;

/**
 * @author David C Peterson
 * <p>
 * A class which represents an edge between geographic locations (usually on a map).
 */
public class MapEdge {


    private final MapNode start;
    private final MapNode end;
    private final String streetName;
    private final String roadType;
    // length of road segment in km
    private final double length;
    static final double DEFAULT_LENGTH = 0.01;





    /**
     * Construct a MapEdge object (this is a part of a directed map so this is a one way edge).
     *
     * @param start      GeographicPoint position to start the edge at.
     * @param end        GeographicPoint position to end the edge at.
     * @param streetName String name of the street the edge represents
     * @param roadType   String representation of the road type.  These are free form.
     * @param length     The length of the edge between the start/end Geographic points
     */
    MapEdge(MapNode start, MapNode end, String streetName, String roadType, double length) {
        this.start = start;
        this.end = end;
        this.streetName = streetName;
        this.roadType = roadType;
        this.length = length;
    }

    /**
     * Construct a MapEdge object (this is a part of a directed map so this is a one way edge).
     *
     * @param start      GeographicPoint position to start the edge at.
     * @param end        GeographicPoint position to end the edge at.
     * @param streetName String name of the street the edge represents
     * @param roadType   String representation of the road type.  These are free form.
     */
    MapEdge(MapNode start, MapNode end, String streetName, String roadType) {
        this(start, end, streetName, roadType, DEFAULT_LENGTH);
    }

    /**
     * Construct a MapEdge object (this is a part of a directed map so this is a one way edge).
     *
     * @param start      GeographicPoint position to start the edge at.
     * @param end        GeographicPoint position to end the edge at.
     * @param streetName String name of the street the edge represents
     */
    MapEdge(MapNode start, MapNode end, String streetName) {
        this(start, end, streetName, "", DEFAULT_LENGTH);
    }

    /**
     * Get the duration (travel time) that it would take to traverse this road segment.
     * @return the travel time in hours that it would take to traverse this edge given the speed limit
     * determined by the road type.
     */
    public double getTravelTime() {
        return length / RoadTypeSpeed.getKPHbyRoadType(roadType);
    }
    /**
     * @return The start node for this edge's start point.
     */
    MapNode getStart() {
        return start;
    }

    /**
     * @return end MapNode for this edge's end point.
     */
    MapNode getEnd() {
        return end;
    }

    /**
     * @return String street's name represented by this edge.
     */
    String getStreetName() {
        return streetName;
    }

    /**
     * @return double length of the road in km represented by this edge.
     */
    double getLength() {
        return length;
    }

    /**
     * Given one of the nodes involved in this edge, get the other one
     *
     * @param node The node on one side of this edge
     * @return the other node involved in this edge
     */
    MapNode getOtherNode(MapNode node) {
        if (node.equals(start)) return end;
        else if (node.equals(end)) return start;
        throw new IllegalArgumentException("Looking for " + "a point that is not in the edge");
    }

    /**
     * Return the location of the start point
     *
     * @return The location of the start point as a GeographicPoint
     */
    GeographicPoint getStartPoint() {
        return start.getLocation();
    }

    /**
     * Return the location of the end point
     *
     * @return The location of the end point as a GeographicPoint
     */
    GeographicPoint getEndPoint() {
        return end.getLocation();
    }

    /**
     * Override the toString default object representation to return a string more representative of the edge for
     * debugging.
     *
     * @return String representation of the edge
     */
    @Override
    public String toString() {
        String toReturn = "[EDGE between ";
        toReturn += "\n\t" + start.getLocation();
        toReturn += "\n\t" + end.getLocation();
        toReturn += "\nRoad name: " + streetName + " Road type: " + roadType + " Segment length: " + String.format("%.3g", length) + "km";

        return toReturn;
    }
}
