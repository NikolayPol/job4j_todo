package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Task;
import model.User;
import store.HbrStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Класс IndexServlet - контроллер для index.html.
 * Работает с Ajax.
 *
 * @author Nikolay Polegaev
 * @version 2.0 23.10.2021
 */
public class IndexServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        Task task = GSON.fromJson(req.getReader(), Task.class);
        HttpSession s = req.getSession();
        User user = (User) s.getAttribute("user");
        task.setUser(user);
        HbrStore.instOf().add(task);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        HttpSession s = req.getSession();
        User user = (User) s.getAttribute("user");
        List<Task> list = HbrStore.instOf().findAll(user);
        String json = GSON.toJson(list);
        resp.getWriter().write(json);
    }
}
