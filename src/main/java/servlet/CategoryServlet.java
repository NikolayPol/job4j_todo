package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Category;
import store.HbrStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Класс CategoryServlet отображает категорию задачи.
 *
 * @author Nikolay Polegaev
 * @version 1.0 09.11.2021
 */
public class CategoryServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        List<Category> list = HbrStore.instOf().showCategories();
        String json = GSON.toJson(list);
        resp.getWriter().write(json);
    }
}
