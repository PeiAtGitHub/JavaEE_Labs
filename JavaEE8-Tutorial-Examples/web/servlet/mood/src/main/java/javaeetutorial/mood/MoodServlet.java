package javaeetutorial.mood;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/report")
public class MoodServlet extends HttpServlet {
    
    public static final String MOOD = "mood";
    
    public static final String THOUGHTFUL = "thoughtful";
    public static final String LETHARGIC = "lethargic";
    public static final String HUNGRY = "hungry";
    public static final String ALERT = "alert";
    public static final String SLEEPY = "sleepy";
    
    public static final long serialVersionUID = 18925377774889413L;

    /**
     * Handles the HTTP <code>GET</code> method.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"en\">");
            out.println("<head><title>Servlet MoodServlet</title></head>");
            out.println("<body>");
            out.println(String.format("<h2>Hour of day: %d</h2>", TimeOfDayFilter.hourOfDay));
            out.println(String.format("<h2>Server's mood at this hour: %s</h2>", (String) request.getAttribute(MOOD)));
            out.println("</body></html>");
        }
    }

    
    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "This servlet responds server's mood.";
    }
}
