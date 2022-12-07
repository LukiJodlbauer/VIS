package at.fhooe.sail.vis.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerMain {
	public static void main(String[] _argv) {
		System.out.println("HelloWorld server");

		try (var socket = new ServerSocket(12345)) {
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

					out.write(line.toString().getBytes());
					out.flush();

					String received = line.toString().trim();
					System.out.println("received: " + received);

					if (received.equals("shutdown")) {
						doShutdown = true;
						break;
					} else if (received.equals("quit")) {
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
