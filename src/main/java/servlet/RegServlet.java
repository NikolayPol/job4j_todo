package servlet;

import model.User;
import store.HbrStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Класс
 * Класс с тестами
 *
 * @author Nikolay Polegaev
 * @version 1.0 23.10.2021
 */
public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String username = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HbrStore.instOf().addUser(
                new User(
                        username,
                        email,
                        password
                )
        );
        resp.sendRedirect(req.getContextPath() + "/auth.do");
    }
}
