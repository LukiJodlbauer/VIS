package at.fhooe.sail.vis.soap.dynamic.hellosoap.main;

public class DummyData {
    private String mName;
    private String mValue;

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
