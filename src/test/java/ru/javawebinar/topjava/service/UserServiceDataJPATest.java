package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER;

@ActiveProfiles(Profiles.REPO_DATA_JPA)
public class UserServiceDataJPATest extends AbstractUserServiceTest {

    @Test
    public void testGetMealWithUser() throws Exception {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        ru.javawebinar.topjava.MealTestData.MATCHER.assertCollectionEquals(
                Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1),
                user.getMeals());
    }

}
