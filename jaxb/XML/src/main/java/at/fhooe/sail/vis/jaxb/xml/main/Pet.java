package at.fhooe.sail.vis.jaxb.xml.main;

import jakarta.xml.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * Pet class used in jaxb
 */
@XmlRootElement
@XmlType(propOrder = {"mName", "mType", "mID", "mBirthday", "mVaccinations"})
public class Pet {
    /**
     * Nickname of pet
     */
    @XmlAttribute(name ="nickname")
    private String mNickName;
    /**
     * Name of pet
     */
    @XmlElement(name = "name")
    private String mName;
    /**
     * Type of pet
     */
    @XmlElement(name = "type", namespace = "http://www.example.com/type")
    private Type mType;
    /**
     * Id of pet
     */
    @XmlElement(name = "id")
    private String mID;
    /**
     * Birthdate of pet
     */
    @XmlElement(name = "date")
    private Date mBirthday;
    /**
     * List of given vaccinations
     */
    @XmlElementWrapper(name = "vaccinations")
    @XmlElement(name = "vaccination")
    private String[] mVaccinations;

    /**
     * Empty constructor for marshalling
     */
    public Pet() { }

    /**
     * Constructor for creating pet objects
     * @param _name
     * @param _nickName
     * @param _birthday
     * @param _type
     * @param _vaccinations
     * @param _ID
     */
    public Pet(String _name, String _nickName, Date _birthday, Type _type,
               String[] _vaccinations, String _ID) {
        this.mName = _name;
        this.mNickName = _nickName;
        this.mBirthday = _birthday;
        this.mType = _type;
        this.mVaccinations = _vaccinations;
        this.mID = _ID;
    }

    /**
     *  Converts pet to visual string representation
     * @return String representation of pet
     */
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
