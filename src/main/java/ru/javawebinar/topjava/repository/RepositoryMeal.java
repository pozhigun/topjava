package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositoryMeal {

    void createMeal(Meal meal);

    List<MealTo> getAllMealTo();

    void deleteMeal(int id);

    Meal getMealByID(int id);

    void updateMeal(int id, LocalDateTime localDateTime, String description, int calories);

}
