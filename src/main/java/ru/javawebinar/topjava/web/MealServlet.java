package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealMemDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static final String ADD_OR_EDIT = "mealEdit.jsp";
    private static final String LIST = "meals.jsp";
    private MealDAO mealDAO;

    public MealServlet() {
        super();
        mealDAO = new MealMemDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");

        if (action != null) {
            switch (action.toLowerCase()) {
                case "add":
                    LOG.debug("Adding new meal");
                    forward = ADD_OR_EDIT;
                    break;
                case "remove":
                    LOG.debug("Removing meal");
                    try {
                        int id = Integer.valueOf(req.getParameter("id"));
                        mealDAO.remove(id);
                        LOG.debug("Remove meal with id = " + id);
                    } catch (Exception e) {
                        LOG.error("Error removing meal " + e.toString());
                    }
                    resp.sendRedirect("meals");
                    return;
                case "edit":
                    try {
                        int id = Integer.valueOf(req.getParameter("id"));
                        Meal meal = mealDAO.get(id);
                        LOG.debug("Edit meal " + meal);
                        req.setAttribute("meal", meal);
                        forward = ADD_OR_EDIT;
                    } catch (Exception e) {
                        LOG.error("Error when editing meal " + e.toString());
                        resp.sendRedirect("meals");
                        return;
                    }
                    break;
            }
        } else {
            forward = LIST;
        }
        if (forward.equals(LIST)) {
            req.setAttribute("mealList",
                    MealsUtil.getFilteredWithExceeded(mealDAO.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(forward);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dateTime"));
            String description = req.getParameter("description");
            int calories = Integer.valueOf(req.getParameter("calories"));

            Meal meal = new Meal(localDateTime, description, calories);

            String id = req.getParameter("id");

            if (id.isEmpty()) {
                mealDAO.add(meal);
            } else {
                meal.setId(Integer.valueOf(id));
                mealDAO.update(meal);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        resp.sendRedirect("meals");
    }
}
