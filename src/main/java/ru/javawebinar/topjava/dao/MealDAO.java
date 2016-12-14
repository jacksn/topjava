package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    int add(Meal meal);
    Meal update(Meal meal);
    void remove(int id);
    List<Meal> getAll();
    Meal get(int id);
}
