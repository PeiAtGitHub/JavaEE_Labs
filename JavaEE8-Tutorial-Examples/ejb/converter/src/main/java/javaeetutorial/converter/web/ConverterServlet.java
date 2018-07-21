package javaeetutorial.converter.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javaeetutorial.converter.ejb.ConverterBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

@WebServlet(urlPatterns="/converter")
public class ConverterServlet extends HttpServlet {
    private static final long serialVersionUID = -8312407323476917087L;

    @EJB
    ConverterBean converter;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html lang=\"en\">");
        out.println("<head><title>Servlet ConverterServlet</title></head>");
        out.println("<body><h2>Servlet ConverterServlet at '" + request.getContextPath() + "'</h2>");
        try {
            String amount = request.getParameter("amount");
            if (StringUtils.isNotEmpty(amount)) {
                BigDecimal dollar = new BigDecimal(amount);
                out.println(String.format("<p>%s dollars are %s yen, %s euro.</p>", amount, 
                        converter.dollarToYen(dollar).toPlainString(), converter.dollarToEuro(dollar).toPlainString()));
            } else {
                out.println("<p>Enter a dollar amount to convert:</p>");
                out.println("<form method=\"get\">");
                out.println("<p>$ <input title=\"Amount\" type=\"text\" name=\"amount\" size=\"25\"></p><br/>");
                out.println("<input type=\"submit\" value=\"Submit\"><input type=\"reset\" value=\"Reset\">");
                out.println("</form>");
            }
        } finally {
            out.println("</body></html>");
            out.close();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
