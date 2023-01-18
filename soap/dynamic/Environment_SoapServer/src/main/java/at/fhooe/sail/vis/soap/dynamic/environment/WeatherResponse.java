package at.fhooe.sail.vis.soap.dynamic.environment;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import org.eclipse.persistence.oxm.annotations.XmlPath;

/**
 * Model for getting weather data from API
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WeatherResponse {
    /**
     * holds air temperature of city
     */
    @XmlPath("main/temp/text()")
    private double mTemperature;
    /**
     * holds air pressure of city
     */
    @XmlPath("main/pressure/text()")
    private int mPressure;
    /**
     * holds air humidity of city
     */
    @XmlPath("main/humidity/text()")
    private int mHumidity;

    /**
     * Empty constructor
     */
    public WeatherResponse() {}

    /**
     * Constructor with all class-variables
     * @param mTemperature air temperature of city
     * @param mPressure air pressure of city
     * @param mHumidity air humidity of city
     */
    public WeatherResponse(double mTemperature, int mPressure, int mHumidity) {
        this.mTemperature = mTemperature;
        this.mPressure = mPressure;
        this.mHumidity = mHumidity;
    }

    /**
     * Basic toString with all class-variables
     * @return all variables as String
     */
    @Override
    public String toString() {
        return "WeatherResponse{" +
                "mTemperature=" + mTemperature +
                ", mPressure=" + mPressure +
                ", mHumidity=" + mHumidity +
                '}';
    }

    /**
     * Temperature getter
     * @return air temperature
     */
    public double getTemperature() {
        return mTemperature;
    }

    /**
     * Pressure getter
     * @return air pressure
     */
    public int getPressure() {
        return mPressure;
    }

    /**
     * Humidity getter
     * @return air humidity
     */
    public int getHumidity() {
        return mHumidity;
    }
}
