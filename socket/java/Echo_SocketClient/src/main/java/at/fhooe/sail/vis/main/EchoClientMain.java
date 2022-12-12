package at.fhooe.sail.vis.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Basic EchoClient-Class for testing Client and Server Communication.
 */
public class EchoClientMain {
	/**
	 * Entry point of application.
	 * @param _argv contains startup parameters
	 */
	public static void main(String[] _argv) {
		System.out.println("starting client ...");

		int port  = Integer.parseInt(_argv[0]);
		String ip = _argv[1];

		try {
			Socket sock = new Socket(ip, port);
			System.out.println("connected to server");

			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();

			var scanner = new Scanner(System.in);

			int data = -1;
			var line = new StringBuilder();

			while (true) {
				System.out.print("Enter message: ");
				String message = scanner.nextLine() + "\n";

				out.write(message.getBytes());
				out.flush();

				while ((data = in.read()) != -1) {
					line.append((char)data);
					if (((char)data) == '\n') {
						break;
					}
				}

				if (data == -1) {
					break;
				}

				System.out.print("ECHO: " + line);

				message = message.trim();

				if (message.equals("quit")) {
					break;
				} else if (message.equals("shutdown")) {
					break;
				}

				line.setLength(0);
			}

			out.close();
			in.close();
			sock.close();
		} catch (IOException _e) {
			_e.printStackTrace();
		}
	}
}
