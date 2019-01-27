package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        UserMealWithExceed userMealWithExceed;
        List<UserMealWithExceed> filteredWithExceeded = new <UserMealWithExceed>ArrayList();
        LocalTime localTime;

        for (UserMeal k : mealList
        ) {

            if (TimeUtil.isBetween(k.getDateTime().toLocalTime(), startTime, endTime)) {
            } else continue;

            if (k.getCalories() > caloriesPerDay) {
                userMealWithExceed = new UserMealWithExceed(k.getDateTime(), k.getDescription(), k.getCalories(), true);
                filteredWithExceeded.add(userMealWithExceed);
            } else {
                userMealWithExceed = new UserMealWithExceed(k.getDateTime(), k.getDescription(), k.getCalories(), false);
                filteredWithExceeded.add(userMealWithExceed);
            }
        }
        return filteredWithExceeded;
    }
}
