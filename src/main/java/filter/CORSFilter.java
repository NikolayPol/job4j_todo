package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Класс CORSFilter реализует Cross-Origin Resource Sharing для
 * совместного использования ресурсов между источниками.
 * CORS - это механизм, который позволяет JavaScript на веб-странице
 * отправлять запросы Ajax в другой домен, отличный от домена, из которого он был создан.
 * По умолчанию такие веб-запросы запрещены в браузерах, и они приведут к ошибкам политики
 * безопасности одного и того же источника.
 *
 * @author Nikolay Polegaev
 * @version 1.0 05.10.2021
 */
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods", "GET, "
                + "OPTIONS, HEAD, PUT, POST");

        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        chain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}