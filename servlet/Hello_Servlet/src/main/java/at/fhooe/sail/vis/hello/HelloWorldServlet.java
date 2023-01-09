package at.fhooe.sail.vis.hello;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet(
		name = "HelloWorldServlet",
		urlPatterns = {"/foo", "/bar"}
)
public class HelloWorldServlet extends HttpServlet {
	private static int mServletCalls = 0;

	public void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws IOException {
		mServletCalls++;

		_response.setContentType("text/html");
		PrintWriter out = _response.getWriter();
		out.println("<html>");
		out.println("</head><title>Hello World</title></head>");
		out.println("<body>");
		out.println(String.format("<h1>Calls so far: %d</h1>", mServletCalls));
		out.println("</body>");
		out.println("</html>");
	}
}
