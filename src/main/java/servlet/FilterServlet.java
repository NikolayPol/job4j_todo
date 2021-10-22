package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Task;
import store.HbrStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

/**
 * Класс
 * Класс с тестами
 *
 * @author Nikolay Polegaev
 * @version 1.0 11.10.2021
 */
public class FilterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        List<Task> list = HbrStore.instOf().showFilterItems();
        String json = new Gson().toJson(list);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
