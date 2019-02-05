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


    // Collection WithDuplicatesPass
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> sumCaloriesInOneDay = new HashMap<>();
        List<UserMealWithExceed> filteredWithExceeded = new ArrayList<>();

        for (UserMeal meal : mealList) {
            sumCaloriesInOneDay.put(meal.getDateTime().toLocalDate(),
                    sumCaloriesInOneDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) + meal.getCalories());
        }

        for (UserMeal mealElements : mealList) {
            if (TimeUtil.isBetween(mealElements.getDateTime().toLocalTime(), startTime, endTime))
                filteredWithExceeded.add(new UserMealWithExceed(
                        mealElements.getDateTime(),
                        mealElements.getDescription(),
                        mealElements.getCalories(),
                        sumCaloriesInOneDay.get(mealElements.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        return filteredWithExceeded;

    }


/*
    // Collection WithoutDuplicatePass
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {


        Map<LocalDate, Integer> sumCaloriesInOneDay = new HashMap<>();
        List<UserMealWithExceed> filteredWithExceeded = new ArrayList<>();
        Map<LocalDate, UserMeal> mealFilter = new HashMap<>();

        for (UserMeal element : mealList) {
            sumCaloriesInOneDay.put(element.getDateTime().toLocalDate(),
                    sumCaloriesInOneDay.getOrDefault(element.getDateTime().toLocalDate(), 0) + element.getCalories());
            if (TimeUtil.isBetween(element.getDateTime().toLocalTime(), startTime, endTime))
                mealFilter.put(element.getDateTime().toLocalDate(), element);
        }

            for (Map.Entry<LocalDate, UserMeal> entry : mealFilter.entrySet())
                filteredWithExceeded.add(new UserMealWithExceed(
                        entry.getValue().getDateTime(),
                        entry.getValue().getDescription(),
                        entry.getValue().getCalories(),
                        (sumCaloriesInOneDay.get(entry.getKey()) > caloriesPerDay)
                ));

        return filteredWithExceeded;

    }
*/

/*
    //Stream API
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> meal = mealList.stream().collect(
                Collectors.groupingBy(
                        p -> p.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(p -> TimeUtil.isBetween(p.getDateTime().toLocalTime(), startTime, endTime))
                .map(p -> new UserMealWithExceed(
                                p.getDateTime(),
                                p.getDescription(),
                                p.getCalories(),
                                meal.get(p.getDateTime().toLocalDate()) > caloriesPerDay)
                )
                .collect(Collectors.toList());

    }
*/

}
