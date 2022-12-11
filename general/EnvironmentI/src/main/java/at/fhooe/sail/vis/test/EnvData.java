package at.fhooe.sail.vis.test;

import java.util.Arrays;

public class EnvData {
	private String  mSensorName;
	private long    mTimestamp;
	private int[]   mValues;

	public EnvData() {}

	@Override
	public String toString() {
		return "EnvData{" +
				"mSensorName='" + mSensorName + '\'' +
				", mTimestamp=" + mTimestamp +
				", mValues=" + Arrays.toString(mValues) +
				'}';
	}

	public String getmSensorName() {
		return mSensorName;
	}

	public void setmSensorName(String mSensorName) {
		this.mSensorName = mSensorName;
	}

	public long getmTimestamp() {
		return mTimestamp;
	}

	public void setmTimestamp(long mTimestamp) {
		this.mTimestamp = mTimestamp;
	}

	public int[] getmValues() {
		return mValues;
	}

	public void setmValues(int[] mValues) {
		this.mValues = mValues;
	}
}
