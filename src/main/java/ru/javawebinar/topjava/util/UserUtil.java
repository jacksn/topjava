package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.Comparator;

public class UserUtil {
    public static Comparator<User> SORT_BY_NAME_EMAIL = (o2, o1) -> {
        int dif = o2.getName().compareTo(o1.getName());
        if (dif != 0) {
            return dif;
        } else {
            return o2.getEmail().compareTo(o1.getEmail());
        }
    };
}
