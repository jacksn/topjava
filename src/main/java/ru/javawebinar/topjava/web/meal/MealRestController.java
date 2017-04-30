package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController {

    static final String REST_URL = "/rest/profile/meals";

    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    @GetMapping("/{id}")
    public Meal get(@PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }

    @GetMapping
    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Meal meal, @PathVariable("id") int id) {
        checkIdConsistent(meal, id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody Meal meal) {
        checkNew(meal);
        int userId = AuthorizedUser.id();
        LOG.info("create {} for User {}", meal, userId);

        Meal created = service.save(meal, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/between")
    public List<MealWithExceed> filter(
            @RequestParam(value = "startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam(value = "endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        return getBetween(startDateTime.toLocalDate(), startDateTime.toLocalTime(), endDateTime.toLocalDate(), endDateTime.toLocalTime());
    }

    @GetMapping(value = "/filter")
    public List<MealWithExceed> filter(
            @RequestParam(value = "startDate", required = false) LocalDate startDate, @RequestParam(value = "startTime", required = false) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) LocalDate endDate, @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return getBetween(startDate, startTime, endDate, endTime);
    }

    private List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, userId);

        return MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX, AuthorizedUser.getCaloriesPerDay());
    }
}
