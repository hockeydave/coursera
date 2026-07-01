package roadgraph;

import java.util.HashMap;

public class RoadTypeSpeed {

    static final double MILES_TO_KMS = 1.609334;
    static final String RESIDENTIAL_STREET = "residential";
    static final String CITY_STREET = "city street";
    static final String CONNECTOR_STREET = "connector";
    static final String MOTORWAY_LINK = "motorway_link";
    static final String MOTORWAY = "motorway";
    static final String UNCLASSIFIED = "unclassified";
    static final String PRIMARY = "primary";
    static final String PRIMARY_LINK = "primary_link";
    static final String SECONDARY = "secondary";
    static final String SECONDARY_LINK = "secondary_link";
    static final String TERTIARY = "tertiary";
    static final String TERTIARY_LINK = "tertiary_link";
    static final String MAX_US_SPEED = "super highway";
    static final String AVG_US_SPEED = "normal road";


    static final String LIVING_STREET = "living_street";


    // Speed in KPH
    private static HashMap<String, Double> roadTypeToSpeed;

    /**
     * Assume that
     * residential streets are 25 MPH
     * city streets are 35 MPH
     * connector streets are 40 MPH
     */
    static {
        roadTypeToSpeed = new HashMap<String, Double>(10);
        roadTypeToSpeed.put(RESIDENTIAL_STREET, 25 * MILES_TO_KMS);
        roadTypeToSpeed.put(LIVING_STREET, 25 * MILES_TO_KMS);
        roadTypeToSpeed.put(CITY_STREET, 35 * MILES_TO_KMS);
        roadTypeToSpeed.put(CONNECTOR_STREET, 40 * MILES_TO_KMS);
        roadTypeToSpeed.put(PRIMARY, 65 * MILES_TO_KMS);
        roadTypeToSpeed.put(PRIMARY_LINK, 40 * MILES_TO_KMS);
        roadTypeToSpeed.put(SECONDARY, 55 * MILES_TO_KMS);
        roadTypeToSpeed.put(SECONDARY_LINK, 40 * MILES_TO_KMS);
        roadTypeToSpeed.put(TERTIARY, 50 * MILES_TO_KMS);
        roadTypeToSpeed.put(TERTIARY_LINK, 40 * MILES_TO_KMS);
        roadTypeToSpeed.put(UNCLASSIFIED, 60 * MILES_TO_KMS);
        roadTypeToSpeed.put(MOTORWAY, 65 * MILES_TO_KMS);
        roadTypeToSpeed.put(MOTORWAY_LINK, 40 * MILES_TO_KMS);
        roadTypeToSpeed.put(MAX_US_SPEED, 80 * MILES_TO_KMS);
        roadTypeToSpeed.put(AVG_US_SPEED, 40 * MILES_TO_KMS);
    }

    public static Double getKPHbyRoadType(String roadType) {
        Double kph = roadTypeToSpeed.get(roadType);
        if(kph == null) throw new IllegalStateException("roadType is not valid " + roadType);
        return kph;
    }
}
