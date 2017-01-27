package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;

@Controller
@RequestMapping(value = "/meals")
public class MealUIController extends AbstractMealController {

    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createMeal(Model model) {
        model.addAttribute("meal", new Meal());
        return "meal";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editMeal(@PathVariable("id") int id, Model model) {
        model.addAttribute("meal", super.get(id));
        return "meal";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveMeal(Meal meal) {
        if (meal.getId() == null) {
            super.create(meal);
        } else {
            super.update(meal, meal.getId());
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteMeal(@PathVariable("id") int id) {
        super.delete(id);
        return "redirect:/meals";
    }
}
