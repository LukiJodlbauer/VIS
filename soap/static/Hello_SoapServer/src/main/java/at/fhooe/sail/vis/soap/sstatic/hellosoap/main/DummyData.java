package at.fhooe.sail.vis.soap.sstatic.hellosoap.main;

/**
 * DummyData object or testing.
 */
public class DummyData {
    /**
     * String holding dummy-name.
     */
    private String mName;
    /**
     * String holding dummy-value.
     */
    private String mValue;

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
