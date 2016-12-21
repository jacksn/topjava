package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "Test user", "user@topjava.ru", "pass", Role.ROLE_USER),
            new User(null, "Test admin", "admin@topjava.ru", "pass", Role.ROLE_ADMIN)
    );
}
