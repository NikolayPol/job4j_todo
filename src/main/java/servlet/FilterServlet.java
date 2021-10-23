package servlet;

import com.google.gson.Gson;
import model.Task;
import store.HbrStoreWrapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Класс FilterServlet получает список невыполненных заданий.
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
        List<Task> list = HbrStoreWrapper.instOf().showFilterItems();
        String json = new Gson().toJson(list);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
