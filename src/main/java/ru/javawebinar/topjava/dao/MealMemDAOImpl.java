package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealMemDAOImpl implements MealDAO {
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private static AtomicInteger lastid = new AtomicInteger(0);
    private static final Logger LOG = getLogger(MealMemDAOImpl.class);

    public MealMemDAOImpl() {
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public int add(Meal meal) {
        int id = lastid.getAndIncrement();
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
        }
        else {
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

    @Override
    public int getCount() {
        return meals.size();
    }
}
