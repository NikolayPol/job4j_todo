package servlet;

import com.google.gson.Gson;
import model.Task;
import model.User;
import store.HbrStore;
import store.HbrStoreWrapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Класс FilterServlet получает список невыполненных заданий.
 *
 * @author Nikolay Polegaev
 * @version 2.0 23.10.2021
 */
public class FilterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        HttpSession s = req.getSession();
        User user = (User) s.getAttribute("user");
        List<Task> list = HbrStore.instOf().showFilterItems(user);
        String json = new Gson().toJson(list);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
