package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Task;
import store.HbrStoreWrapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Класс IndexServlet - контроллер для index.html.
 * Работает с Ajax.
 *
 * @author Nikolay Polegaev
 * @version 1.0 05.10.2021
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
        HbrStoreWrapper.instOf().add(task);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        List<Task> list = HbrStoreWrapper.instOf().findAll();
        String json = GSON.toJson(list);
        resp.getWriter().write(json);
    }
}
