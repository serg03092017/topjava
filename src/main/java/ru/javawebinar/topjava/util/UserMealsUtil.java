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


    // Start Collection WithDuplicatesPass
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> summCaloriesInOneDay = new HashMap<>();
        List<UserMealWithExceed> filteredWithExceeded = new ArrayList<>();

        for (UserMeal k01 : mealList
        ) {
            summCaloriesInOneDay.put(k01.getDateTime().toLocalDate(),
                    summCaloriesInOneDay.getOrDefault(k01.getDateTime().toLocalDate(), 0) + k01.getCalories());
        }

        for (UserMeal k02 : mealList
        ) {
            if (TimeUtil.isBetween(k02.getDateTime().toLocalTime(), startTime, endTime))
                filteredWithExceeded.add(new UserMealWithExceed(
                        k02.getDateTime(),
                        k02.getDescription(),
                        k02.getCalories(),
                        summCaloriesInOneDay.get(k02.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        return filteredWithExceeded;

    }
    //END Collection WithDuplicatesPass



    /*
    // Start Collection WithoutDuplicatePass
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {


        Map<LocalDate, Integer> summCaloriesInOneDay = new HashMap<>();
        List<UserMealWithExceed> filteredWithExceeded = new ArrayList<>();
        Map<LocalDate, UserMeal> mealFilter = new HashMap<>();

        for (UserMeal k1 : mealList
        ) {
            summCaloriesInOneDay.put(k1.getDateTime().toLocalDate(),
                    summCaloriesInOneDay.getOrDefault(k1.getDateTime().toLocalDate(), 0) + k1.getCalories());
            if (TimeUtil.isBetween(k1.getDateTime().toLocalTime(), startTime, endTime))
                mealFilter.put(k1.getDateTime().toLocalDate(), k1);
        }

            for (Map.Entry<LocalDate, UserMeal> entry : mealFilter.entrySet())
                filteredWithExceeded.add(new UserMealWithExceed(
                        entry.getValue().getDateTime(),
                        entry.getValue().getDescription(),
                        entry.getValue().getCalories(),
                        (summCaloriesInOneDay.get(entry.getKey()) > caloriesPerDay)
                ));

        return filteredWithExceeded;

    }
    */
    //END Collection WithoutDuplicatePass


    /*
    //Start Stream API
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> filteredWithExceededStream = new ArrayList<>();

        Map<LocalDate, Integer> meal = mealList.stream().collect(
                Collectors.groupingBy(
                        p -> p.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        mealList.stream()
                .filter(p -> TimeUtil.isBetween(p.getDateTime().toLocalTime(), startTime, endTime))
                .map(p ->
                        filteredWithExceededStream.add(new UserMealWithExceed(
                                p.getDateTime(),
                                p.getDescription(),
                                p.getCalories(),
                                meal.get(p.getDateTime().toLocalDate()) > caloriesPerDay))
                )
                .collect(Collectors.toList());

        return filteredWithExceededStream;
    }
    */
    //END Stream API


}
