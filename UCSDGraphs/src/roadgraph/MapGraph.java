/**
 * @author UCSD MOOC development team and YOU
 * <p>
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between
 */
package roadgraph;


import geography.GeographicPoint;
import util.GraphLoader;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author UCSD MOOC development team and David C Peterson
 * <p>
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between
 */
public class MapGraph {
    enum PathCriteria {
        SHORTEST_PATH, SHORTEST_DURATION
    }

    private final HashMap<GeographicPoint, MapNode> vertices;
    private final HashSet<MapEdge> edges;
    private PathCriteria pathCriteria;

    /**
     * Create a new empty MapGraph
     */
    public MapGraph() {
        vertices = new HashMap<>();
        edges = new HashSet<>();
        pathCriteria = PathCriteria.SHORTEST_DURATION;
    }


    private void printGraph() {
        System.out.println("\nnum vertices " + vertices.size());
        System.out.println("num edges: " + edges.size());
        System.out.println("vertices: " + vertices.values());
    }


    /**
     * Get the number of vertices (road intersections) in the graph
     *
     * @return The number of vertices in the graph.
     */
    public int getNumVertices() {
        return vertices.values().size();
    }

    /**
     * Return the intersections, which are the vertices in this graph.
     *
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<GeographicPoint> getVertices() {
        return new HashSet<>(vertices.keySet());
    }

    /**
     * Get the number of road segments in the graph
     *
     * @return The number of edges in the graph.
     */
    public int getNumEdges() {
        return edges.size();
    }


    /**
     * Add a node corresponding to an intersection at a Geographic Point
     * If the location is already in the graph or null, this method does
     * not change the graph.
     *
     * @param location The location of the intersection
     * @return true if a node was added, false if it was not (the node
     * was already in the graph, or the parameter is null).
     */
    public boolean addVertex(GeographicPoint location) {
        if (location == null || vertices.get(location) != null) return false;
        vertices.put(location, new MapNode(location));
        return true;
    }

    /**
     * Adds a directed edge to the graph from pt1 to pt2.
     * Precondition: Both GeographicPoints have already been added to the graph
     *
     * @param from     The starting point of the edge
     * @param to       The ending point of the edge
     * @param roadName The name of the road
     * @param roadType The type of the road
     * @param length   The length of the road, in km
     * @throws IllegalArgumentException If the points have not already been
     *                                  added as nodes to the graph, if any of the arguments is null,
     *                                  or if the length is less than 0.
     */
    public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) throws IllegalArgumentException {

        MapNode start = vertices.get(from);
        MapNode end = vertices.get(to);
        if (from == null || to == null || roadName == null || roadType == null || start == null || end == null || length < 0)
            throw new IllegalArgumentException();
        MapEdge startEdge = new MapEdge(start, end, roadName, roadType, length);

        edges.add(startEdge);
        start.addEdge(startEdge);
    }


    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest (unweighted)
     * path from start to goal (including both start and goal).   If no path exists, return null
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return bfs(start, goal, temp);
    }

    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest (unweighted)
     * path from start to goal (including both start and goal).  If no path exists, return null
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {

        // Initialize everything
        MapNode startNode = vertices.get(start);
        MapNode goalNode = vertices.get(goal);

        if (start == null || goal == null || goalNode == null || startNode == null) {
            System.out.println("Start and/or goal node is null!  No path exists.");
            return null;
        }

        HashMap<MapNode, MapNode> parentMap = new HashMap<>();

        if (!bfsSearch(startNode, goalNode, parentMap, nodeSearched)) {
            System.out.println("BFS Search, no path exists");
            return null;
        }

        return constructPath(startNode, goalNode, parentMap);

    }

    /**
     * Use BFS to search for a path from MapNode start to MapNode goal.
     *
     * @param start        The MapNode to start the search for a path to goal
     * @param goal         The MapNode to find the shortest path to.
     * @param parentMap    The Map<MapNode, MapNode></MapNode,> where key is the next node and value is the parent (previous) node.
     * @param nodeSearched Consumer<GeographicPoint></GeographicPoint> used for BFS visualization.
     * @return true if the goal is found and false otherwise (includes start/goal being null).
     */
    private boolean bfsSearch(MapNode start, MapNode goal, HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched) {


        HashSet<MapNode> visited = new HashSet<>();
        Queue<MapNode> toExplore = new LinkedList<>();
        toExplore.add(start);

        while (!toExplore.isEmpty()) {
            MapNode curr = toExplore.remove();
            if (curr.equals(goal)) {
                return true;
            }
            // Hook for visualization.  See writeup.
            nodeSearched.accept(curr.getLocation());

            Set<MapNode> neighbors = curr.getNeighbors();
            for (MapNode next : neighbors) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    parentMap.put(next, curr);
                    toExplore.add(next);

                }
            }
        }
        return false;
    }

    /**
     * Reconstruct the shortest path of MapNodes from MapNode start to MapNode goal that BFS search found.
     * This method is not called unless a path was found so return should always be non-null.
     *
     * @param start     The node to start the path construction from
     * @param goal      The node to end (i.e. goal) that ends the path construction.
     * @param parentMap Map of MapNodes where key is a node in the graph and value is its neighbor parent
     * @return return a List<GeographicPoint> that identify the shortest path between start and goal.
     */
    private List<GeographicPoint> constructPath(MapNode start, MapNode goal, HashMap<MapNode, MapNode> parentMap) {
        // reconstruct the path
        LinkedList<GeographicPoint> path = new LinkedList<>();
        MapNode curr = goal;
        while (!curr.equals(start)) {
            path.addFirst(curr.getLocation());
            curr = parentMap.get(curr);
        }
        path.addFirst(start.getLocation());
        return path;
    }

    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        // You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return dijkstra(start, goal, temp);
    }

    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 4
        return getGeographicPointsPath(start, goal, true, nodeSearched);
    }

    private List<GeographicPoint> getGeographicPointsPath(GeographicPoint start, GeographicPoint goal, boolean isDijkstra, Consumer<GeographicPoint> nodeSearched) {
        // Initialize everything
        MapNode startNode = vertices.get(start);
        MapNode goalNode = vertices.get(goal);
        String algo;
        if (isDijkstra) algo = "Dijkstra: ";
        else algo = "AStar:  ";

        if (start == null || goal == null || goalNode == null || startNode == null) {
            System.out.println(algo + "Start and/or goal node is null!  No path exists.");
            return null;
        }

        HashMap<MapNode, MapNode> parentMap = new HashMap<>();

        if (pathSearchFound(startNode, goalNode, parentMap, isDijkstra, nodeSearched)) {
            System.out.println("Travel Time (hours): " + goalNode.getTravelTimeFromStartNode());
            return constructPath(startNode, goalNode, parentMap);
        }
        System.out.println(algo + "No path exists from " + startNode + " to " + goalNode);
        return null;
    }


    /**
     * Use Dijkstra/AStar algorithm to search for a path from MapNode start to MapNode goal.  The shortest path (or travel time)
     * is determined by exploring the path of nodes closest to the start node prioritized.
     * g(n) the distance or travel-time (cost) from start vertex to vertex n
     * h(n) the heuristic estimated cost from vertex n to goal vertex
     * fn = g(n) + h(n)
     * Guaranteed to find the shortest path (or travel time).  IFF estimate is never an overestimate
     * Dijkstra is the special case where h(n) is 0.
     *
     * @param start        The MapNode to start the search for a path to goal
     * @param goal         The MapNode to find the shortest path to by distance or travel time.
     * @param parentMap    The Map<MapNode, MapNode></MapNode,> where key is the next node and value is the parent (previous) node.
     * @param nodeSearched Consumer<GeographicPoint></GeographicPoint> used for nodes searched visualization.
     * @return true if the goal is found and false otherwise (includes start/goal being null).
     */
    private boolean pathSearchFound(MapNode start, MapNode goal, HashMap<MapNode, MapNode> parentMap, boolean isDijkstra, Consumer<GeographicPoint> nodeSearched) {
        HashSet<MapNode> visited = new HashSet<>();
        PriorityQueue<MapNode> toExplore = new PriorityQueue<>();
        String algo;
        if (isDijkstra) algo = "Dijkstra ";
        else algo = "AStar ";

        initVertices(isDijkstra, goal);
        start.setLenFromStartNode(0);
        start.setTravelTimeFromStartNode(0);

        toExplore.add(start);

        while (!toExplore.isEmpty()) {
            MapNode curr = toExplore.remove();

            if (!visited.contains(curr)) {
                visited.add(curr);
                if (curr.equals(goal)) {
                    System.out.println(algo + " nodes visited:  " + visited.size());
                    System.out.println("visited nodes\t" + visited);
                    return true;
                }
                // Hook for visualization.  See writeup.
                nodeSearched.accept(curr.getLocation());

                Set<MapNode> neighbors = curr.getNeighbors();
                for (MapNode next : neighbors) {
                    if (!visited.contains(next)) {
                        double nextPathLen = curr.getLenFromStartNode() + curr.getEdge(next).getLength();
                        double nextPathTravelTime = curr.getTravelTimeFromStartNode() + curr.getEdge(next).getTravelTime();
                        if ((pathCriteria == PathCriteria.SHORTEST_PATH && nextPathLen < next.getLenFromStartNode())
                                || (pathCriteria == PathCriteria.SHORTEST_DURATION && nextPathTravelTime < next.getTravelTimeFromStartNode())) {
                            next.setLenFromStartNode(nextPathLen);
                            next.setTravelTimeFromStartNode(nextPathTravelTime);
                            parentMap.put(next, curr);
                            toExplore.add(next);
                        }
                    }
                }
            }
        }
        System.out.println(algo + " visited:  " + visited.size() + " nodes: " + visited + " but not found");
        return false;
    }

    /**
     * Initialize the length from start/goal nodes for Dijkstra and A* search algorithms
     *
     * @param isDijkstra boolean to tell if this is being called as part of a Dijkstra algorithm or from AStar
     * @param goalNode   the route destination goal node needs to have its distance from this node to goal node
     *                   for AStar algorithm.  For Dijkstra, just init it to 0.
     *                   f(n) = g(n) + h(n) where
     *                   f(n) is the shortest route.
     *                   g(no) is the distance from start node to this node (in kilometers).
     *                   h(n) is the distance from this node to the goal node (in kilometers).  For Dijkstra set to 0
     */
    private void initVertices(boolean isDijkstra, MapNode goalNode) {
        for (MapNode vertex : vertices.values()) {
            vertex.setLenFromStartNode(Double.MAX_VALUE);
            vertex.setTravelTimeFromStartNode(Double.MAX_VALUE);
            vertex.setPathCriteria(pathCriteria);
            if (isDijkstra) vertex.setLenFromGoalNode(0);
            else {
                vertex.setLenFromGoalNode(GeoDistance.distance(vertex.getLocation(), goalNode.getLocation()));
                //vertex.setLenFromGoalNode(straightLineDistance(vertex.getLocation(), goalNode.getLocation()));
            }
        }
    }

    /**
     * The distance between two points formula derived from the Pythagorean Theorem.
     *
     * @param n1 are the Geographic points of node 1
     * @param n2 are the Geographic points of node 2
     * @return the pythagorean distance (straight line) between 2 geographic points.
     * AStar search requires that the distance be accurate IFF the estimate is never an overestimate.
     * A straight line estimate between the 2 GeographicPoints should be less than the
     * sphere estimate in GeoDistance class.  This method was a debugging test method and is no longer
     * needed as the GeoDistance estimate is correct (so long as you use consistent measure of the
     * distance-from-start-node which is in kilometers).
     */
    private double straightLineDistance(GeographicPoint n1, GeographicPoint n2) {
        return Math.hypot(Math.abs(n2.getY() - n1.getY()), Math.abs(n2.getX() - n1.getX()));
        //return Math.sqrt((n2.getX() - n1.getX()) * (n2.getX() - n1.getX()) + (n2.getY() - n1.getY()) * (n2.getY() - n1.getY()));
    }


    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return aStarSearch(start, goal, temp);
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 4
        // Initialize everything
        return getGeographicPointsPath(start, goal, false, nodeSearched);
    }


    public static void main(String[] args) {
        // Week 3 test.
        // testBFS();

        // You can use this method for testing Dijkstra and AStar.


        /* Here are some test cases you should try before you attempt
         * the Week 4 End of Week Quiz, EVEN IF you score 100% on the
         * programming assignment.
         */
        //testDijkstraAStar(MapNode.PathCriteria.SHORTEST_PATH);
        //testDijkstraAStar(MapNode.PathCriteria.SHORTEST_DURATION);

        testFastestPath(PathCriteria.SHORTEST_PATH);
        testFastestPath(PathCriteria.SHORTEST_DURATION);



        /* Use this code in Week 4 End of Week Quiz */
        //quiz();


    }

    private static void testFastestPath(PathCriteria pathCriteria) {
        System.out.println("testAStarFastestPath by " + pathCriteria);
        MapGraph fastestMap = new MapGraph();

        GraphLoader.loadRoadMap("data/testdata/fastest.map", fastestMap);
        fastestMap.pathCriteria = pathCriteria;

        GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
        GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

        System.out.println("Test 1 using fastest.map: ");
        List<GeographicPoint> testroute = fastestMap.dijkstra(testStart, testEnd);
        MapNode endNode = fastestMap.vertices.get(testEnd);
        double routeTravelTime = endNode.getTravelTimeFromStartNode();
        List<GeographicPoint> testroute2 = fastestMap.aStarSearch(testStart, testEnd);
        double routeTravelTime2 = endNode.getTravelTimeFromStartNode();

        System.out.println("Dijkstra route: \t" + testroute);
        System.out.println(pathCriteria + " Dijkstra Path travel time estimate:  " + routeTravelTime);
        System.out.println("AStar route: \t\t" + testroute2);
        System.out.println(pathCriteria + " AStar Path travel time estimate:  " + routeTravelTime2);
        System.out.println(" Dijkstra and AStar routes are equal?  " + equalRoutes(testroute, testroute2));
    }

    /**
     * Test the BFS algorithm in week 3
     */
    private static void testBFS() {
        System.out.print("Making a new map...");
        MapGraph firstMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
        firstMap.printGraph();

        // Test a few BFS searches on the simpletest map
        GeographicPoint s = new GeographicPoint(1.0, 1.0);
        GeographicPoint g = new GeographicPoint(8.0, -1);
        List<GeographicPoint> path = firstMap.bfs(s, g);
        System.out.println("path " + "start " + s + " goal " + g + " " + path);

        s.setLocation(4.0, -1);
        g.setLocation(7.0, 3.0);
        path = firstMap.bfs(s, g);
        System.out.println("path " + "start " + s + " goal " + g + " " + path);

        s.setLocation(4.0, 2);
        g.setLocation(8, -1);
        path = firstMap.bfs(s, g);
        System.out.println("path " + "start " + s + " goal " + g + " " + path);

        System.out.println("DONE.");
    }

    private static boolean equalRoutes(List<GeographicPoint> route1, List<GeographicPoint> route2) {
        return route1.size() == route2.size() && route1.containsAll(route2);
    }


    private static void testDijkstraAStar() {
        MapGraph simpleTestMap = new MapGraph();
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);


        GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
        GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

        System.out.println("Test 1 using simpletest: Nodes visited Dijkstra should be 9 and AStar should be 5");
        List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart, testEnd);
        List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart, testEnd);
        System.out.println("Dijkstra route: \t" + testroute);
        System.out.println("AStar route: \t\t" + testroute2);
        System.out.println(" Dijkstra and AStar routes are equal?  " + equalRoutes(testroute, testroute2));
        MapNode endNode = simpleTestMap.vertices.get(testEnd);
        System.out.println(simpleTestMap.pathCriteria + " Path travel time estimate:  " + endNode.getTravelTimeFromStartNode());


        MapGraph utcGraph = new MapGraph();
        GraphLoader.loadRoadMap("data/maps/utc.map", utcGraph);
        System.out.println("utcGraph \nvertices: " + utcGraph.vertices.size() + " \nedges " + utcGraph.edges.size());

        // A very simple test using real data
        testStart = new GeographicPoint(32.869423, -117.220917);
        testEnd = new GeographicPoint(32.869255, -117.216927);
        System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
        testroute = utcGraph.dijkstra(testStart, testEnd);
        testroute2 = utcGraph.aStarSearch(testStart, testEnd);
        System.out.println("Dijkstra route: \t" + testroute);
        System.out.println("AStar route: \t\t" + testroute2);
        System.out.println(" Dijkstra and AStar routes are equal?  " + equalRoutes(testroute, testroute2));
        endNode = utcGraph.vertices.get(testEnd);
        System.out.println(utcGraph.pathCriteria + " Path travel time estimate:  " + endNode.getTravelTimeFromStartNode());


        // A slightly more complex test using real data
        testStart = new GeographicPoint(32.8674388, -117.2190213);
        testEnd = new GeographicPoint(32.8697828, -117.2244506);
        System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
        testroute = utcGraph.dijkstra(testStart, testEnd);
        testroute2 = utcGraph.aStarSearch(testStart, testEnd);
        System.out.println("Dijkstra route: \t" + testroute);
        System.out.println("AStar route: \t\t" + testroute2);
        System.out.println(" Dijkstra and AStar routes are equal?  " + equalRoutes(testroute, testroute2));
        endNode = utcGraph.vertices.get(testEnd);
        System.out.println(utcGraph.pathCriteria + " Path travel time estimate:  " + endNode.getTravelTimeFromStartNode());
    }

    private static void quiz() {
        MapGraph theMap = new MapGraph();
        System.out.print("DONE. \nLoading the utc.map...");
        GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
        System.out.println("DONE.");

        GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
        GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);


        List<GeographicPoint> route = theMap.dijkstra(start, end);
        List<GeographicPoint> route2 = theMap.aStarSearch(start, end);
        System.out.println("Quiz Dijkstra route: \t" + route);
        System.out.println("Quiz AStar route: \t\t" + route2);
        System.out.println(" Dijkstra and AStar routes are equal?  " + equalRoutes(route, route2));
    }

}
