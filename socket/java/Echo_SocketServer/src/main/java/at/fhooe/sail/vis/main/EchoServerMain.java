package at.fhooe.sail.vis.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Basic HttpServer-Class for testing Client and Server Communication.
 */
public class EchoServerMain {
	/**
	 * Entry point of application.
	 * @param _argv contains startup parameters
	 */
	public static void main(String[] _argv) {
		System.out.println("starting server ...");

		int port = Integer.parseInt(_argv[0]);

		try (var socket = new ServerSocket(port)) {
			boolean doShutdown = false;

			while (true) {
				System.out.println("waiting for clients " +
						"(msgs are terminated with \\n) ...");

				Socket sock = socket.accept();
				System.out.println("client connected ...");

				InputStream in = sock.getInputStream();
				OutputStream out = sock.getOutputStream();

				int data = -1;
				var line = new StringBuilder();

				while (true) {
					while ((data = in.read()) != -1) {
						line.append((char)data);
						if (((char)data) == '\n') {
							break;
						}
					}

					if (data == -1) {
						break;
					}

					out.write(line.toString().getBytes());
					out.flush();

					String received = line.toString().trim();
					System.out.println("received: " + received);

					if (received.equals("shutdown")) {
						doShutdown = true;
						sock.close();
						break;
					} else if (received.equals("drop")) {
						sock.close();
						break;
					}

					line.setLength(0);
				}

				out.close();
				in.close();

				if (doShutdown) {
					break;
				}
			}
		} catch (IOException _e) {
			_e.printStackTrace();
		}
	}
}
