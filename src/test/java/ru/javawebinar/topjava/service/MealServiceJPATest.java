package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.REPO_JPA)
public class MealServiceJPATest extends AbstractMealServiceTest {
}
