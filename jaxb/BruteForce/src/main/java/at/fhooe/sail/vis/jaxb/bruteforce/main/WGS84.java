package at.fhooe.sail.vis.jaxb.bruteforce.main;

public class WGS84 {
    private double mLatitude;
    private double mLongitude;

    public WGS84(String xml) {
        int latStart = xml.indexOf("<latitude>") + "<latitude>".length();
        int latEnd = xml.indexOf("</latitude>");
        String latString = xml.substring(latStart, latEnd);
        mLatitude = Double.parseDouble(latString);

        int lonStart = xml.indexOf("<longitude>") + "<longitude>".length();
        int lonEnd = xml.indexOf("</longitude>");
        String lonString = xml.substring(lonStart, lonEnd);
        mLongitude = Double.parseDouble(lonString);
    }

    @Override
    public String toString() {
        return "WGS84{" +
                "mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                '}';
    }
}
