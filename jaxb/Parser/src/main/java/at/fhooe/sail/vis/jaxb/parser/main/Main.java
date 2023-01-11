package at.fhooe.sail.vis.jaxb.parser.main;

/**
 * HelloWorld-Project for testing multi-project functionality.
 */
public class Main {

	/**
	 * Entry point of application
	 *
	 * @param _argv contains startup parameters
	 */
	public static void main(String[] _argv) {
		System.out.println("Hello Parser");

		String xmlString = "<wind>\n" +
				"  <speed>50.25</speed>\n" +
				"  <deg>225</deg>\n" +
				"</wind>";

		System.out.println("XML-String:");
		System.out.println(xmlString);

		System.out.println("Extracting information ...");
		Wind wind = new Wind(xmlString);

		System.out.println(wind);
	}
}
