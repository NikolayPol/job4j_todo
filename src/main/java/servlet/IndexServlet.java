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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс IndexServlet - контроллер для index.html.
 * Работает с Ajax через Json.
 *
 * @author Nikolay Polegaev
 * @version 2.1 09.11.2021
 */
public class IndexServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        Map<String, String> map = new HashMap<>();
        map = (Map<String, String>) GSON.fromJson(req.getReader(), map.getClass());
        Task task = new Task(
                map.get("description"),
                map.get("categoryId"),
                map.get("categoryName"));
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
