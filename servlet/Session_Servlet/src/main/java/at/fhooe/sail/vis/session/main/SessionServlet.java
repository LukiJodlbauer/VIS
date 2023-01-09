package at.fhooe.sail.vis.session.main;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

/**
 * Session-Servlet that keeps track of the sessions and shows based on
 * that different magic-numbers.
 */
@WebServlet(
		name = "SessionServlet",
		urlPatterns = {"/session"}
)
public class SessionServlet extends HttpServlet {

	/**
	 * Checks if session is already existing. If so it gets the last magic-number
	 * and displays it. It always generates a new one and stores the last one in
	 * the session storage.
	 *
	 * @param _request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 * @param _response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 *
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest _request, HttpServletResponse _response)
			throws IOException {
		boolean didAlreadyVisit = true;
		int lastMagicNumber = 0;

		Object attribute = _request.getSession().getAttribute("magicNumber");
		if (attribute != null) {
			lastMagicNumber = (int)attribute;
		} else {
			didAlreadyVisit = false;
		}

		int newMagicNumber = Integer.parseInt(_request.getParameter("magicNumber"));
		_request.getSession().setAttribute("magicNumber", newMagicNumber);

		var userAgentParts = _request.getHeader("User-Agent").split(" ");
		var browser = userAgentParts[userAgentParts.length - 1].split("/")[0];

		_response.setContentType("text/html");
		PrintWriter out = _response.getWriter();

		out.println("<html>");
		out.println("</head><title>Hello World</title></head>");
		out.println("<body>");
		out.println(String.format("<p>You are currently using %s (magic number: %d).<br>", browser, newMagicNumber));
		if (didAlreadyVisit) {
			out.println(String.format("Last time you visited, your magic number was %d!</p>", lastMagicNumber));
		}
		out.println("</body>");
		out.println("</html>");
	}
}
