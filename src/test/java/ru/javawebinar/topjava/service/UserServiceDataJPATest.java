package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.REPO_DATA_JPA)
public class UserServiceDataJPATest extends AbstractUserServiceTest {

    @Test
    public void testGetUserWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        ru.javawebinar.topjava.UserTestData.MATCHER.assertEquals(USER, user);
        ru.javawebinar.topjava.MealTestData.MATCHER.assertCollectionEquals(
                Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1),
                user.getMeals());
    }

    @Test
    public void testGetUserWithNoMeals() throws Exception {
        User user = service.getWithMeals(USER_NO_MEALS_ID);
        ru.javawebinar.topjava.UserTestData.MATCHER.assertEquals(USER_NO_MEALS, user);
        assertEquals(0, user.getMeals().size());
    }
}
