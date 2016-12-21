package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;
import sun.text.resources.cldr.mr.FormatData_mr;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ProfileRestController profileRestController;
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = applicationContext.getBean(MealRestController.class);
        profileRestController = applicationContext.getBean(ProfileRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        applicationContext.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        if (id == null) {
            String dateFromS = request.getParameter("dateFrom");
            String dateToS = request.getParameter("dateTo");
            String timeFromS = request.getParameter("timeFrom");
            String timeToS = request.getParameter("timeTo");

            if (dateFromS == null || dateFromS.isEmpty()) {
                AuthorizedUser.setDateFrom(LocalDate.MIN);
            } else {
                AuthorizedUser.setDateFrom(LocalDate.parse(dateFromS, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            if (dateToS == null || dateToS.isEmpty()) {
                AuthorizedUser.setDateTo(LocalDate.MAX);
            } else {
                AuthorizedUser.setDateTo(LocalDate.parse(dateToS, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            if (timeFromS == null || timeFromS.isEmpty()) {
                AuthorizedUser.setTimeFrom(LocalTime.MIN);
            } else {
                AuthorizedUser.setTimeFrom(LocalTime.parse(timeFromS));
            }

            if (timeToS == null || timeToS.isEmpty()) {
                AuthorizedUser.setTimeTo(LocalTime.MAX);
            } else {
                AuthorizedUser.setTimeTo(LocalTime.parse(timeToS));
            }
        } else {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    AuthorizedUser.id(), LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories"))
            );

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.update(meal);
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setAttribute("user", profileRestController.get());
        LocalDate dateFrom = AuthorizedUser.getDateFrom();
        LocalDate dateTo = AuthorizedUser.getDateTo();
        LocalTime timeFrom = AuthorizedUser.getTimeFrom();
        LocalTime timeTo = AuthorizedUser.getTimeTo();
        if (dateFrom != LocalDate.MIN) request.setAttribute("dateFrom", dateFrom);
        if (dateTo != LocalDate.MAX) request.setAttribute("dateTo", dateTo);
        if (timeFrom != LocalTime.MIN) request.setAttribute("timeFrom", timeFrom);
        if (timeTo != LocalTime.MAX) request.setAttribute("timeTo", timeTo);

        if (action == null) {
            LOG.info("Get meal list");
            request.setAttribute("meals", mealRestController.getAll());
            request.getRequestDispatcher("meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            try {
                mealRestController.delete(id);
            } catch (NotFoundException e) {
                LOG.error("Delete meal: " + e.getMessage() + " for user with id=" + AuthorizedUser.id());
            }
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal;
            try {
                meal = action.equals("create") ?
                        new Meal(AuthorizedUser.id(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
            } catch (NotFoundException e) {
                LOG.error("Get meal: " + e.getMessage() + " for user with id=" + AuthorizedUser.id());
                response.sendRedirect("meals");
                return;
            }
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
