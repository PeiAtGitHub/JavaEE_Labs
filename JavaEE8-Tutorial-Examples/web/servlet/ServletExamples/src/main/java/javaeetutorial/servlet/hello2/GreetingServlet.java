package javaeetutorial.servlet.hello2;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

@WebServlet("/greeting")
public class GreetingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setBufferSize(8192);
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"en\">" 
                    + "<head><title>Servlet Hello</title></head>"
                    + "<body  bgcolor=\"#ffffff\">"
                    + "<form method=\"get\">"
                    + "<h2>Hello, What's your name?</h2>" 
                    + "<input title=\"My name is: \" type=\"text\" name=\"username\" size=\"25\"/><p/>" 
                    + "<input type=\"submit\" value=\"Submit\"/>"
                    + "<input type=\"reset\" value=\"Reset\"/>" 
                    + "</form>");

            if (StringUtils.isNotEmpty(request.getParameter("username"))) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/response");
                if (dispatcher != null) {
                    dispatcher.include(request, response);
                }
            }
            out.println("</body></html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "The Hello servlet says hello.";
    }
}
