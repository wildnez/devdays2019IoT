package com.hazelcast.demo.iot.data;


import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;


public final class EarthTools
{
    private static final double M_TO_KM = (1.0 / 1000.0);

    /**
     * Computes the distance between two points on the Earth's surface in
     * kilometers, taking the curvature of the Earth into account.
     *
     * @param long1 the longitude of the first point
     * @param lat1 the latitude of the first point
     * @param long2 the longitude of the second point
     * @param lat2 the latitude of the second point
     *
     * @return the distance between the first and second points in kilometers
     */
    public static double distanceBetween(double long1, double lat1,
            double long2, double lat2)
    {
        final GeodeticCalculator CALCULATOR =
                new GeodeticCalculator(DefaultGeographicCRS.WGS84);

        CALCULATOR.setStartingGeographicPoint(long1, lat1);
        CALCULATOR.setDestinationGeographicPoint(long2, lat2);

        return M_TO_KM * CALCULATOR.getOrthodromicDistance( );
    }

    /**
     * Computes the distance between two points on the Earth's surface in
     * kilometers, taking the curvature of the Earth into account.
     *
     * @param p1 the first point on the Earth's surface
     * @param p2 the second point on the Earth's surface
     *
     * @return the distance between the first and second points in kilometers
     */
    public static double distanceBetween(Location p1, Location p2)
    {
        return distanceBetween(p1.getLongitude(), p1.getLatitude( ),
                p2.getLongitude( ), p2.getLatitude( ));
    }

    private EarthTools( )
    {
        // EarthTools aren't instantiable
    }
}
