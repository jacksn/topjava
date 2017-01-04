package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final Meal userMeal1 = new Meal(100100, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(100101, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(100102, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(100103, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal5 = new Meal(100104, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal6 = new Meal(100105, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal adminMeal1 = new Meal(100106, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак админа", 1000);
    public static final Meal adminMeal2 = new Meal(100107, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед админа", 500);
    public static final Meal adminMeal3 = new Meal(100108, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин админа", 510);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();
}
