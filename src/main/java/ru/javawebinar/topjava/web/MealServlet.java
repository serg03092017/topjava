package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.main;
import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");

        response.setContentType("text/html");

        List<Meal> getStaticCollectionMeals = meals;

        List<MealTo> mealsWithExcess = getWithExcessByCycle(getStaticCollectionMeals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        List<MealTo> mealForJsp = mealsWithExcess;

        request.setAttribute("listToMeal", mealForJsp);

        getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    public static List<MealTo> getWithExcessByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealTo> mealsWithExcess = new ArrayList<>();
        meals.forEach(meal -> {
            mealsWithExcess.add(createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
        });
        return mealsWithExcess;
    }

    public static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

}
