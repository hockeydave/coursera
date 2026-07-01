package roadgraph;

import geography.GeographicPoint;

public class GeoDistance {
    enum GeoUnits {
        MILES, NAUTICAL_MILES, KILOMETERS
    }
    public static double distance(GeographicPoint n1, GeographicPoint n2) {
        return distance(n1, n2, GeoUnits.KILOMETERS);
    }

    /**
     * Determine the approximate geo distance from GeographicPoint 1 to GeographicPoint 2
     *
     * @param n1   GeographicPoint starting point
     * @param n2   GeographicPoint ending point
     * @param unit GeoDistance unit for MILE, NAUTICAL_MILE, or KILOMETERS.  default MILES
     * @return distance in GeoDistance units from n1 to n2
     */
    public static double distance(GeographicPoint n1, GeographicPoint n2, GeoUnits unit) {
        double lat1 = n1.getX();
        double lon1 = n1.getY();
        double lat2 = n2.getX();
        double lon2 = n2.getY();
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515; // default GeoUnits.MILES
            if (unit.equals(GeoUnits.KILOMETERS)) {
                dist = dist * 1.609344;
            } else if (unit.equals(GeoUnits.NAUTICAL_MILES)) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
