package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(userMeal1.getId(), UserTestData.USER_ID);
        MATCHER.assertEquals(userMeal1, meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(userMeal1.getId(), UserTestData.USER_ID);
        Collection<Meal> actual = service.getAll(UserTestData.USER_ID);
        Collection<Meal> expected = Arrays.asList(userMeal6, userMeal5, userMeal4, userMeal3, userMeal2);
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<Meal> expected = Arrays.asList(userMeal3, userMeal2, userMeal1);
        Collection<Meal> actual = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> actual = service.getAll(UserTestData.USER_ID);
        Collection<Meal> expected = Arrays.asList(userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = new Meal(userMeal1.getId(), LocalDateTime.now(), "Updated description", userMeal1.getCalories() + 500);
        service.update(meal, UserTestData.USER_ID);
        MATCHER.assertEquals(meal, service.get(userMeal1.getId(), UserTestData.USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal meal = service.save(new Meal(LocalDateTime.now(), "Newly added", 620), UserTestData.USER_ID);
        Collection<Meal> actual = service.getAll(UserTestData.USER_ID);
        Collection<Meal> expected = Arrays.asList(meal, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void testGetBetweenDatesWithWrongUserId() throws Exception {
        Collection<Meal> expected = Arrays.asList(userMeal3, userMeal2, userMeal1);
        Collection<Meal> actual = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), UserTestData.ADMIN_ID);
        MATCHER.assertCollectionEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithWrongUserId() throws Exception {
        service.get(userMeal1.getId(), UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWithWrongUserId() throws Exception {
        service.delete(userMeal1.getId(), UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWithWrongUserId() throws Exception {
        service.update(userMeal1, UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteAbsent() throws Exception {
        service.delete(10, UserTestData.USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetAbsent() throws Exception {
        service.get(10, UserTestData.USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateAbsent() throws Exception {
        Meal meal = new Meal(10, LocalDateTime.now(), "Updated description", userMeal1.getCalories() + 500);
        service.update(meal, UserTestData.USER_ID);
        MATCHER.assertEquals(meal, service.get(userMeal1.getId(), UserTestData.USER_ID));
    }
}