package filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class LocalizationFilter implements Filter {
    private String locale;
    private String bundle;
    private static final Logger logger = Logger.getLogger(LocalizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        locale = filterConfig.getInitParameter("locale");
        bundle = filterConfig.getInitParameter("bundle");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String receivedLocale = request.getParameter("locale");
        if (receivedLocale != null) {
            this.locale = receivedLocale;
            logger.info("Changed locale to " + receivedLocale);
        } else {
            receivedLocale = (String) request.getSession().getAttribute("locale");
            if (receivedLocale != null)
                this.locale = receivedLocale;
        }
        String receivedBundle = request.getParameter("bundle");
        if (receivedBundle != null) {
            this.bundle = receivedBundle;
            logger.info("Changed bundle to " + receivedBundle);
        } else {
            receivedBundle = (String) request.getSession().getAttribute("bundle");
            if (receivedBundle != null)
                this.bundle = receivedBundle;
        }

        request.getSession().setAttribute("locale", this.locale);
        request.getSession().setAttribute("bundle", this.bundle);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
