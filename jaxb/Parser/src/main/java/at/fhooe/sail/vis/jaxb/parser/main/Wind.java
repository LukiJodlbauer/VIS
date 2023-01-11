package at.fhooe.sail.vis.jaxb.parser.main;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

public class Wind {
    private double mSpeed;
    private int mDeg;

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

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + mSpeed +
                ", deg=" + mDeg +
                '}';
    }
}
