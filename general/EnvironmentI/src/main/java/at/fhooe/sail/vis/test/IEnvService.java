package at.fhooe.sail.vis.test;

public interface IEnvService {
	public String[]  requestEnvironmentDataTypes();
	public EnvData   requestEnvironmentData(String _type);
	public EnvData[] requestAll ();
}
