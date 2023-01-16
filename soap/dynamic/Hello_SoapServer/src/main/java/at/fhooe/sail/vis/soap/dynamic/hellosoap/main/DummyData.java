package at.fhooe.sail.vis.soap.dynamic.hellosoap.main;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DummyData {
    @XmlElement
    private String mName;
    @XmlElement
    private String mValue;

    public DummyData() {}

    public DummyData(String _name, String _value) {
        this.mName = _name;
        this.mValue = _value;
    }

    @Override
    public String toString() {
        return "DummyData{" +
                "mName='" + mName + '\'' +
                ", mValue='" + mValue + '\'' +
                '}';
    }
}
