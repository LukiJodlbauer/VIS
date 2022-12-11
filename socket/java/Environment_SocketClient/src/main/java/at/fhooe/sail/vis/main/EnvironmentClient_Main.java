package at.fhooe.sail.vis.main;

import at.fhooe.sail.vis.socket.Environment_SocketClient;
import at.fhooe.sail.vis.test.EnvData;
import at.fhooe.sail.vis.test.IEnvService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class EnvironmentClient_Main {
	public static void main(String[] _argv) {
		IEnvService service = new Environment_SocketClient();
//		while (true) {
			String[] sensors = service.requestEnvironmentDataTypes();
			for (String sensor : sensors) {
				EnvData dataO = service.requestEnvironmentData(sensor);
				System.out.print(dataO);
				System.out.println();
				System.out.println("*****************************");
			}
			System.out.println();
			System.out.println();
			EnvData[] dataOs = service.requestAll();
			for (EnvData dataO : dataOs) {
				System.out.println(dataO);
			}
			try {
				Thread.sleep(1000);
			} catch (Exception _e) {
				_e.printStackTrace();
			}
//		}
	}
}
