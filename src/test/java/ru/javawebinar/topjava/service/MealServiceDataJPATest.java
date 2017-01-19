package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER;

@Profile("repository-datajpa")
@ActiveProfiles(Profiles.REPO_DATA_JPA)
public class MealServiceDataJPATest extends AbstractMealServiceTest {

    @Test
    public void testGetMealWithUser() throws Exception {
        Meal meal = service.getWithUser(MEAL1_ID);
        ru.javawebinar.topjava.MealTestData.MATCHER.assertEquals(MEAL1, meal);
        MATCHER.assertEquals(meal.getUser(), USER);
    }
}
