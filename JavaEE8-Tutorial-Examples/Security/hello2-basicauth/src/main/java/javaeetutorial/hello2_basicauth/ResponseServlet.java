package javaeetutorial.hello2_basicauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

@WebServlet(name="ResponseServlet", urlPatterns={"/response"})
public class ResponseServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        if (StringUtils.isNoneEmpty(username)) {
            response.getWriter().println("<h2>Hello, " + username + "!</h2>");
        }
    }

    @Override
    public String getServletInfo() {
        return "The Response servlet says hello.";
    }
    
}
