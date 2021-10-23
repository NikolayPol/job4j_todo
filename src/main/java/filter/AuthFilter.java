package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Класс AuthFilter фильтрует запросы и обеспечивает безопасность.
 * Через метод doFilter() будут проходить запросы к сервлетам.
 * Если запрос идет на сервлет авторизации, то проверку делать не будем.
 * Для всех остальных запросов мы проверяем текущего пользователя. Если его нет,
 * то отправляем на страницу авторизации.
 *
 * @author Nikolay Polegaev
 * @version 2.0 23.10.2021
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (uri.endsWith("auth.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (uri.endsWith("reg.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth.do");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {
    }
}
