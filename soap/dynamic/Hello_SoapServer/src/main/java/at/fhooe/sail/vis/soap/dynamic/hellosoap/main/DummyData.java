package at.fhooe.sail.vis.soap.dynamic.hellosoap.main;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * DummyData object or testing.
 */
@XmlRootElement
public class DummyData {
    /**
     * String holding dummy-name.
     */
    @XmlElement
    private String mName;
    /**
     * String holding dummy-value.
     */
    @XmlElement
    private String mValue;

    /**
     * Empty constructor
     */
    public DummyData() {}

    /**
     * Basic constructor
     * @param _name used for name of dummy object
     * @param _value used for value of dummy object
     */
    public DummyData(String _name, String _value) {
        this.mName = _name;
        this.mValue = _value;
    }

    /**
     * Basic toString-Method
     * @return String of DummyData-object with variables
     */
    @Override
    public String toString() {
        return "DummyData{" +
                "mName='" + mName + '\'' +
                ", mValue='" + mValue + '\'' +
                '}';
    }
}
