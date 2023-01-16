package at.fhooe.sail.vis.jaxb.bruteforce.main;

public class WGS84 {
    private double mLatitude;
    private double mLongitude;

    public WGS84(String xml) {
        mLatitude = Double.parseDouble(getContentOfTag(xml, "latitude"));
        mLongitude = Double.parseDouble(getContentOfTag(xml, "longitude"));
    }

    private String getContentOfTag(String xml, String tag) {
        int start = xml.indexOf(String.format("<%s>", tag)) + String.format("<%s>", tag).length();
        int end = xml.indexOf(String.format("</%s>", tag));
        return xml.substring(start, end);
    }

    @Override
    public String toString() {
        return "WGS84{" +
                "mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                '}';
    }
}
