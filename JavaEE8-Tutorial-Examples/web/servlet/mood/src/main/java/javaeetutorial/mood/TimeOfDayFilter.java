package javaeetutorial.mood;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(
        filterName = "TimeOfDayFilter", 
        urlPatterns = { "/*" }, 
        initParams = {@WebInitParam(name = MoodServlet.MOOD, value = "awake") }
        )
public class TimeOfDayFilter implements Filter {

    String mood = null;
    
    static int hourOfDay;

    /**
     * is called by the container when the filter is instantiated
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mood = filterConfig.getInitParameter(MoodServlet.MOOD);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        hourOfDay = GregorianCalendar.getInstance().get(Calendar.HOUR_OF_DAY);
        switch (hourOfDay) {
        case 23:
        case 24:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            mood = MoodServlet.SLEEPY;
            break;
        case 7:
        case 13:
        case 18:
            mood = MoodServlet.HUNGRY;
            break;
        case 8:
        case 9:
        case 10:
        case 12:
        case 14:
        case 16:
        case 17:
            mood = MoodServlet.ALERT;
            break;
        case 11:
        case 15:
            mood = "in need of coffee";
            break;
        case 19:
        case 20:
        case 21:
            mood = MoodServlet.THOUGHTFUL;
            break;
        case 22:
            mood = MoodServlet.LETHARGIC;
            break;
        }
        
        req.setAttribute(MoodServlet.MOOD, mood);
        
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}
