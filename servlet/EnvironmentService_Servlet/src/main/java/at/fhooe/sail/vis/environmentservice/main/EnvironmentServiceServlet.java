package at.fhooe.sail.vis.environmentservice.main;

import at.fhooe.sail.vis.environmentservice.rmi.RmiClient;
import at.fhooe.sail.vis.environmentservice.socket.Environment_SocketClient;
import at.fhooe.sail.vis.main.EnvData;
import at.fhooe.sail.vis.soap.dynamic.environment.Environment_SoapClient;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Displays the data of the different servers in a table on a web page.
 * Every attached server is displayed through an own table, which contains
 * the current environment values of the server.
 */
@WebServlet(
		name = "EnvironmentServiceServlet",
		urlPatterns = {"/environment"}
)
public class EnvironmentServiceServlet extends HttpServlet {

	/**
	 * Prints basic table which shows the C++ and RMI Environment-Server data.
	 *
	 * @param _request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 * @param _response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 *
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws IOException {
		_response.setContentType("text/html");
		PrintWriter out = _response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"refresh\" content=\"5\">");
		out.println("<title>Environment Service</title>");
		out.println("<style>table, th, td {border: 1px solid black;border-collapse: collapse;}td{padding-left:8px;padding-right:8px;}</style>");
		out.println("</head>");
		out.println("<body>");

		out.println("<h1>C++ Server Environment Data</h1>");
		try {
			var service = new Environment_SocketClient(4949, "127.0.0.1");
			EnvData[] cppData = service.requestAll();

			printTable(out, cppData);
		} catch (IOException _e) {
			out.println("<p>Server offline!</p>");
		}

		out.println("<h1>RMI Server Environment Data</h1>");
		try {
			var rmiData = RmiClient.getEnvData();

			printTable(out, rmiData);
		} catch (Exception _e) {
			out.println("<p>Server offline!</p>");
		}

		out.println("<h1>SOAP Server Environment Data</h1>");
		try {
			var soapClient = new Environment_SoapClient();
			var soapData = soapClient.mSOAP.requestAll();

			printTable(out, soapData);
		} catch (Exception _e) {
			out.println("<p>Server offline!</p>");
		}

		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * Prints table that shows environment-data.
	 *
	 * @param out
	 * @param list
	 */
	private static void printTable(PrintWriter out, EnvData[] list) {
		out.println("<table>");
		out.println("<thead>");
		out.println("<tr>");
		out.println("<th>Timestamp</th>");
		out.println("<th>Sensor</th>");
		out.println("<th>Value</th>");
		out.println("</tr>");
		out.println("</thead>");

		out.println("<tbody>");
		for (EnvData item : list) {
			out.println("<tr>");
			out.println(String.format("<td>%d</td>", item.getTimestamp()));
			out.println(String.format("<td>%s</td>", item.getSensorName()));

			String values = Arrays.stream(item.getValues())
					.mapToObj(String::valueOf)
					.collect(Collectors.joining(";"));
			out.println(String.format("<td>%s</td>", values));
			out.println("</tr>");
		}
		out.println("</tbody>");
		out.println("</table>");
	}
}
