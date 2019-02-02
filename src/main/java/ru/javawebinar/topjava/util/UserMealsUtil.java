package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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

        /*
        Map<LocalDate, Integer> summCaloriesInOneDay = new HashMap();
        List<UserMealWithExceed> mealWithOutExceeded = new <UserMealWithExceed>ArrayList();
        List<UserMealWithExceed> filteredWithExceeded = new <UserMealWithExceed>ArrayList();


        for (UserMeal k1 : mealList
        ) {
            summCaloriesInOneDay.put(k1.getDateTime().toLocalDate(), summCaloriesInOneDay.getOrDefault(k1.getDateTime().toLocalDate(), 0) + k1.getCalories());
        }

        for (UserMeal k2 : mealList
        ) {
            if (summCaloriesInOneDay.getOrDefault(k2.getDateTime().toLocalDate(), 0) <= caloriesPerDay) {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(k2.getDateTime(), k2.getDescription(), k2.getCalories(), false);
                mealWithOutExceeded.add(userMealWithExceed);
                if (TimeUtil.isBetween(k2.getDateTime().toLocalTime(), startTime, endTime))
                    filteredWithExceeded.add(userMealWithExceed);
            } else {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(k2.getDateTime(), k2.getDescription(), k2.getCalories(), true);
                mealWithOutExceeded.add(userMealWithExceed);
                if (TimeUtil.isBetween(k2.getDateTime().toLocalTime(), startTime, endTime))
                    filteredWithExceeded.add(userMealWithExceed);
            }
        }

        return filteredWithExceeded;
        */

        List<UserMealWithExceed> filteredWithExceededStream = new <UserMealWithExceed>ArrayList();

        Map<LocalDate, Integer> sumOfCalloriesByDay = mealList.stream()
                .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime) == true)
                .map(meal -> {
                            UserMealWithExceed userMealWithExceed;
                            if (sumOfCalloriesByDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) <= caloriesPerDay) {
                                userMealWithExceed = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true);
                                filteredWithExceededStream.add(userMealWithExceed);
                            } else {
                                userMealWithExceed = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false);
                                filteredWithExceededStream.add(userMealWithExceed);
                            }
                            meal.getDateTime();
                            return meal;
                        }
                )
                .collect(Collectors.toList());

        return filteredWithExceededStream;

    }
}
