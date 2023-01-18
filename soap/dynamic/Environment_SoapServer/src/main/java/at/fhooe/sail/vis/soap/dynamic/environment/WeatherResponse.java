package at.fhooe.sail.vis.soap.dynamic.environment;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlAccessorType(XmlAccessType.FIELD)
public class WeatherResponse {
    @XmlPath("main/temp/text()")
    private double mTemperature;
    @XmlPath("main/pressure/text()")
    private int mPressure;
    @XmlPath("main/humidity/text()")
    private int mHumidity;

    public WeatherResponse() {}

    public WeatherResponse(double mTemperature, int mPressure, int mHumidity) {
        this.mTemperature = mTemperature;
        this.mPressure = mPressure;
        this.mHumidity = mHumidity;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "mTemperature=" + mTemperature +
                ", mPressure=" + mPressure +
                ", mHumidity=" + mHumidity +
                '}';
    }

    public double getTemperature() {
        return mTemperature;
    }

    public int getPressure() {
        return mPressure;
    }

    public int getHumidity() {
        return mHumidity;
    }
}
