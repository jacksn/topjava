package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;

@Controller
public class MealUIController extends AbstractMealController {

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        LOG.info("getAll");
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(value = "/meals/create", method = RequestMethod.GET)
    public String createMeal(Model model) {
        LOG.info("Create new meal");
        model.addAttribute("meal", new Meal());
        return "meal";
    }

    @RequestMapping(value = "/meals/edit/{id}", method = RequestMethod.GET)
    public String editMeal(@PathVariable("id") int id, Model model) {
        LOG.info("Edit meal with id " + id);
        model.addAttribute("meal", super.get(id));
        return "meal";
    }

    @RequestMapping(value = "/meals/save", method = RequestMethod.POST)
    public String saveMeal(Meal meal) {
        if (meal.getId() == null) {
            LOG.info("Save new meal");
            super.create(meal);
        } else {
            LOG.info("Update meal with id " + meal.getId());
            super.update(meal, meal.getId());
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/delete/{id}", method = RequestMethod.GET)
    public String deleteMeal(@PathVariable("id") int id) {
        LOG.info("Delete meal with id " + id);
        super.delete(id);
        return "redirect:/meals";
    }
}
