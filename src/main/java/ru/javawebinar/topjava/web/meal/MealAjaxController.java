package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealAjaxController.REST_URL)
public class MealAjaxController extends AbstractMealController {
    static final String REST_URL = "/ajax/profile/meals";

//    @GetMapping("/{id}")
//    public Meal get(@PathVariable("id") int id) {
//        return super.get(id);
//    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @GetMapping
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
//        super.update(meal, id);
//    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @PostMapping
    public void save(@RequestParam Integer id,
                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                     @RequestParam LocalDateTime dateTime,
                     @RequestParam String description,
                     @RequestParam Integer calories) {

        Meal meal = new Meal(id, dateTime, description, calories);

        if (meal.isNew()) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<MealWithExceed> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate, @RequestParam(value = "startTime", required = false) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) LocalDate endDate, @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}