package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 28, 9, 0), "Завтрак", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 28, 10, 0), "Перекус", 250),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 28, 11, 0), "Обед", 800),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 28, 22, 0), "Ужин", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 29, 8, 30), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 29, 11, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 29, 20, 0), "Ужин", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> listUserMealExcess = new ArrayList<>();
        Map<LocalDate, Integer> countedCaloriesPerDay =
                calculationCaloriesPerDay(meals);

        meals.forEach(userMeal -> {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                listUserMealExcess.add(new UserMealWithExcess(userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        countedCaloriesPerDay.get(userMeal.getDateTime().toLocalDate()) <= caloriesPerDay));
            }
        });

        return listUserMealExcess;
    }

    private static Map<LocalDate, Integer> calculationCaloriesPerDay(List<UserMeal> userMealList) {
        Map<LocalDate, Integer> countedCaloriesPerDay = new HashMap<>();
        userMealList.forEach(userMeal -> countedCaloriesPerDay
                .merge(userMeal.getDateTime().toLocalDate(),
                        userMeal.getCalories(),
                        Integer::sum));
        return countedCaloriesPerDay;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }

}
