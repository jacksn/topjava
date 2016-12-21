package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        LOG.info("Get all meals");
        List<Meal> meals = service.getAll(AuthorizedUser.id(), AuthorizedUser.getDateFrom(), AuthorizedUser.getDateTo());
        return MealsUtil.getFilteredWithExceeded(meals, AuthorizedUser.getTimeFrom(), AuthorizedUser.getTimeTo(), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        LOG.info("Get " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        LOG.info("Create " + meal);
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("Delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        service.save(meal);
    }

}
