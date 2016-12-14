package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealMemDAOImpl implements MealDAO {
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private static AtomicInteger lastId = new AtomicInteger(0);
    private static final Logger LOG = getLogger(MealMemDAOImpl.class);

    {
        MealsUtil.MEALS.forEach(this::add);
    }

    @Override
    public int add(Meal meal) {
        int id = lastId.getAndIncrement();
        meal.setId(id);
        update(meal);
        return id;
    }

    @Override
    public Meal update(Meal meal) {
        Meal prevMeal = meals.put(meal.getId(), meal);
        if (prevMeal == null) {
            LOG.debug("Add: added: " + meal);
        } else {
            LOG.debug("Update: " + prevMeal + " was replaced with " + meal);
        }
        return meal;
    }

    @Override
    public void remove(int id) {
        Meal meal = meals.remove(id);
        if (meal == null) {
            LOG.debug("Remove: error removing meal with id=" + id + ". Not found in database.");
        } else {
            LOG.debug("Remove: successfully removed: " + meal);
        }
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> mealList = new ArrayList<>(meals.values());
        mealList.sort(Comparator.comparing(Meal::getDateTime));
        return mealList;
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }
}
