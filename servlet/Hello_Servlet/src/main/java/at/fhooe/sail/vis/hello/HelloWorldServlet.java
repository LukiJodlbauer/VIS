package at.fhooe.sail.vis.hello;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

/**
 * HelloWorld project for testing basic servlet functionality.
 */
@WebServlet(
		name = "HelloWorldServlet",
		urlPatterns = {"/foo", "/bar"}
)
public class HelloWorldServlet extends HttpServlet {
	/**
	 * Stores number of servlet calls.
	 */
	private static int mServletCalls = 0;

	/**
	 * Increases number of servlet-calls and prints basic web-page that displays the
	 * number of calls so far.
	 *
	 * @param _request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 * @param _response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 *
	 * @throws IOException
	 */
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
