package at.fhooe.sail.vis.jaxb.parser.main;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

/**
 * Wind class for parsing testing xml jaxb parsing
 */
public class Wind {
    /**
     * Wind speed
     */
    private double mSpeed;
    /**
     * Wind direction
     */
    private int mDeg;

    /**
     * Constructor for extracting xml string to Wind object
     * @param xml XMl String which should be used
     */
    public Wind(String xml) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xml));

            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (reader.getLocalName().equals("speed")) {
                        mSpeed = Double.parseDouble(reader.getElementText());
                    } else if (reader.getLocalName().equals("deg")) {
                        mDeg = Integer.parseInt(reader.getElementText());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *  Converts wind object to visual string representation
     * @return String representation of wind object
     */
    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + mSpeed +
                ", deg=" + mDeg +
                '}';
    }
}
