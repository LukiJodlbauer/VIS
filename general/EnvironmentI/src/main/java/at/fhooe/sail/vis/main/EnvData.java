package at.fhooe.sail.vis.main;

import java.util.Arrays;

/**
 * Simple Environment-Data-Class. Used to store dummy data
 * of our C++ Environment-Server.
 * Implements IEnvService interface.
 */
public class EnvData {
	/**
	 * Name of the Sensor.
	 */
	private String  mSensorName;

	/**
	 * Timestamp of when the Sensor sent the data.
	 */
	private long    mTimestamp;

	/**
	 * Values of the Sensor at the time of mTimestamp.
	 */
	private int[]   mValues;

	/**
	 * Empty Constructor
	 */
	public EnvData() {}

	/**
	 * Basic toString-Method.
	 *
	 * @return all variables' names and values.
	 */
	@Override
	public String toString() {
		return "EnvData{" +
				"mSensorName='" + mSensorName + '\'' +
				", mTimestamp=" + mTimestamp +
				", mValues=" + Arrays.toString(mValues) +
				'}';
	}

	/**
	 * Sets the variable mSensorName to the given value.
	 *
	 * @param _sensorName the value mSensorName should be set to
	 */
	public void setSensorName(String _sensorName) {
		this.mSensorName = _sensorName;
	}

	/**
	 * Sets the variable mTimestamp to the given value.
	 *
	 * @param _timestamp the value mTimestamp should be set to
	 */
	public void setTimestamp(long _timestamp) {
		this.mTimestamp = _timestamp;
	}

	/**
	 * Sets the variable mValues to the given value.
	 *
	 * @param _values the value mValues should be set to
	 */
	public void setValues(int[] _values) {
		this.mValues = _values;
	}
}
