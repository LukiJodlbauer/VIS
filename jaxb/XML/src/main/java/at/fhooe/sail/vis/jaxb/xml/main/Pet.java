package at.fhooe.sail.vis.jaxb.xml.main;

import jakarta.xml.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

@XmlRootElement
@XmlType(propOrder = {"mName", "mType", "mID", "mBirthday", "mVaccinations"})
public class Pet {
    @XmlAttribute(name ="nickname")
    private String mNickName;
    @XmlElement(name = "name")
    private String mName;
    @XmlElement(name = "type", namespace = "http://www.example.com/type")
    private Type mType;
    @XmlElement(name = "id")
    private String mID;
    @XmlElement(name = "date")
    private Date mBirthday;
    @XmlElementWrapper(name = "vaccinations")
    @XmlElement(name = "vaccination")
    private String[] mVaccinations;

    public Pet() { }

    public Pet(String _name, String _nickName, Date _birthday, Type _type,
               String[] _vaccinations, String _ID) {
        this.mName = _name;
        this.mNickName = _nickName;
        this.mBirthday = _birthday;
        this.mType = _type;
        this.mVaccinations = _vaccinations;
        this.mID = _ID;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "mNickName='" + mNickName + '\'' +
                ", mName='" + mName + '\'' +
                ", mType=" + mType +
                ", mID='" + mID + '\'' +
                ", mBirthday=" + mBirthday +
                ", mVaccinations=" + Arrays.toString(mVaccinations) +
                '}';
    }
}
