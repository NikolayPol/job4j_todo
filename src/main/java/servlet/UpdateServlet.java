package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Task;
import store.HbrStoreWrapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Класс UpdateServlet обновляет задачи после изменения checkbox.
 *
 * @author Nikolay Polegaev
 * @version 1.0 22.10.2021
 */
public class UpdateServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String id = req.getParameter("id");
        String done = req.getParameter("done");
        Task task = new Task();
        task.setId(Integer.parseInt(id));
        task.setDone(Boolean.parseBoolean(done));
        HbrStoreWrapper.instOf().update(task);
    }
}
