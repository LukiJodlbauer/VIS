package at.fhooe.sail.vis.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClientMain {
	public static void main(String[] _argv) {
		System.out.println("starting server ...");

		try {
			Socket sock = new Socket("127.0.0.1", 12345);
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
				System.out.print("ECHO: " + line);

				message = message.trim();

				if (message.equals("quit")) {
					break;
				} else if (message.equals("shutdown")) {
					break;
				}
			}

			out.close();
			in.close();
			sock.close();
		} catch (IOException _e) {
			_e.printStackTrace();
		}
	}
}
