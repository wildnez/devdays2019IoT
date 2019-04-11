package com.hazelcast.demo.iot.data;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;


/**
 * Represents one of the six named districts of the City of Beijing
 */
public enum District
{
    XICHENG,
    DONGCHENG,
    CHAOYANG,
    FENGTAI,
    SHIJINGSHAN,
    HAIDIAN;

    private static final Map<District, Predicate<Location>> CLASSIFIERS;

    static {
        CLASSIFIERS = new HashMap<>( );

        CLASSIFIERS.put(XICHENG,
                loc -> {
                    double lat = loc.getLatitude( );
                    double lng = loc.getLongitude( );
                    return ((lat > 39.860665 && lat <= 39.956682) &&
                            (lng > 116.352220 && lng <= 116.386787));
                });

        CLASSIFIERS.put(DONGCHENG,
                loc -> {
                    double lat = loc.getLatitude( );
                    double lng = loc.getLongitude( );
                    return ((lat > 39.860665 && lat <= 39.956682) &&
                            (lng > 116.386787 && lng <= 116.437324));
                });

        CLASSIFIERS.put(CHAOYANG,
                loc -> {
                    double lat = loc.getLatitude( );
                    double lng = loc.getLongitude( );
                    return (lat > 39.956682 && lng > 116.386787) ||
                            (lat <= 39.956682 && lng > 116.437324);
                });

        CLASSIFIERS.put(FENGTAI,
                loc -> {
                    double lat = loc.getLatitude( );
                    double lng = loc.getLongitude( );
                    return (lat <= 39.860665 && lng <= 116.437324) ||
                            ((lat > 39.860665 && lat <= 39.897831) &&
                                    (lng > 116.232224 && lng <= 116.352220));
                });

        CLASSIFIERS.put(SHIJINGSHAN,
                loc -> {
                    double lat = loc.getLatitude( );
                    double lng = loc.getLongitude( );
                    return ((lat > 39.860665 && lat <= 39.991677) &&
                            (lng <= 116.232224)) ||
                            ((lat > 39.991677 && lat <= 40.09) &&
                                    (lng <= 116.187479));
                });
    }

    /**
     * Given a location on the Earth described as a Location object, determine
     * which district of Beijing that Location resides.
     *
     * @param location a Location object representing a location in or around
     *        Beijing; if this parameter is not in or around Beijing, the
     *        results are undefined
     *
     * @return the district in which 'location' resides
     */
    public static District classifyLocation(Location location)
    {
        for (District d : District.values( )) {
            if (CLASSIFIERS.get(d) != null && CLASSIFIERS.get(d).test(location))
                return d;
        }

        return District.HAIDIAN;
    }

    @Override
    public String toString( )
    {
        String template = super.toString( );
        template = template.toLowerCase( );
        return Character.toUpperCase(template.charAt(0)) +
                template.substring(1);
    }
}
