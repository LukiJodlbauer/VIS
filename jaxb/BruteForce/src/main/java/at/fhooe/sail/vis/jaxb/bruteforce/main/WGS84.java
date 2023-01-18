package at.fhooe.sail.vis.jaxb.bruteforce.main;

/**
 * Test class for brute force to extract information from the following XML
 */
public class WGS84 {
    /**
     * latitude of object
     */
    private double mLatitude;
    /**
     * longitude of object
     */
    private double mLongitude;

    /**
     * Constructor for parsing xml string
     * @param xml xml string which should be parsed
     */
    public WGS84(String xml) {
        mLatitude = Double.parseDouble(getContentOfTag(xml, "latitude"));
        mLongitude = Double.parseDouble(getContentOfTag(xml, "longitude"));
    }

    /**
     * Method to extract value for given tag
     * @param xml xml string which should be examined
     * @param tag tag which should be looked for
     * @return value of the tag
     */
    private String getContentOfTag(String xml, String tag) {
        int start = xml.indexOf(String.format("<%s>", tag)) + String.format("<%s>", tag).length();
        int end = xml.indexOf(String.format("</%s>", tag));
        return xml.substring(start, end);
    }

    /**
     * Method to get visible representation of object
     * @return String representation for object
     */
    @Override
    public String toString() {
        return "WGS84{" +
                "mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                '}';
    }
}
