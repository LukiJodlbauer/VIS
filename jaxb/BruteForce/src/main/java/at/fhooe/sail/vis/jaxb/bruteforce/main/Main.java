package at.fhooe.sail.vis.jaxb.bruteforce.main;

/**
 * HelloWorld-Project for testing multi-project functionality.
 */
public class Main {

	/**
	 * Entry point of application
	 * Try to parse xml string with brute force
	 * @param _argv contains startup parameters
	 */
	public static void main(String[] _argv) {
		System.out.println("Hello BruteForce");

		String xmlString = "<wgs84>\n" +
				"  <latitude>48.31</latitude>\n" +
				"  <longitude>14.29</longitude>\n" +
				"</wgs84>";

		System.out.println("XML-String:");
		System.out.println(xmlString);

		System.out.println("Extracting information ...");
		WGS84 wgs84 = new WGS84(xmlString);

		System.out.println(wgs84);
	}
}
