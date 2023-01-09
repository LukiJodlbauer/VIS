package at.fhooe.sail.vis.info.main;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet(
		name = "InfoServlet",
		urlPatterns = {"/info"}
)
public class InfoServlet extends HttpServlet {
	public void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws IOException {
		String ip = _request.getRemoteAddr();
		String browserType = _request.getHeader("User-Agent");
		String mimeTypes = _request.getHeader("Content-Type");
		String clientProtocol = _request.getProtocol();
		int port = _request.getRemotePort();
		String serverName = _request.getServerName();

		_response.setContentType("text/html");
		PrintWriter out = _response.getWriter();
		out.println("<html>");
		out.println("</head><title>Info</title></head>");
		out.println("<body>");
		out.println("<h1>Info Servlet</h1>");
		out.println("<ul>");
		out.println(String.format("<li>IP: %s</li>", ip));
		out.println(String.format("<li>Browser type: %s</li>", browserType));
		out.println(String.format("<li>MIME types: %s</li>", mimeTypes));
		out.println(String.format("<li>Protocol: %s</li>", clientProtocol));
		out.println(String.format("<li>Port: %d</li>", port));
		out.println(String.format("<li>Server name: %s</li>", serverName));
		out.println("</ul>");
		out.println("</body>");
		out.println("</html>");
	}
}
