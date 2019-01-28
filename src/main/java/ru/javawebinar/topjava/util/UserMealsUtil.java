package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        UserMealWithExceed userMealWithExceed;
        List<UserMealWithExceed> filteredWithExceeded = new <UserMealWithExceed>ArrayList();

        List<UserMeal> select = mealList.stream().filter((e) ->
                TimeUtil.isBetween(e.getDateTime().toLocalTime(), startTime, endTime) == true)
                .collect(Collectors.toList());

        select.stream().map((n) -> {
                    if (n.getCalories() > caloriesPerDay) {
                        filteredWithExceeded.add(new UserMealWithExceed(n.getDateTime(), n.getDescription(), n.getCalories(), true));
                    } else {
                        filteredWithExceeded.add(new UserMealWithExceed(n.getDateTime(), n.getDescription(), n.getCalories(), false));
                    }
                    return filteredWithExceeded;
                })
                .collect(Collectors.toList());

        return filteredWithExceeded;


    }
}
