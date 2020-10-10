package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.RepositoryMeal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceMeal implements RepositoryMeal {
    AtomicInteger countID = new AtomicInteger();
    Map<Integer, Meal> mapMeal = new ConcurrentHashMap<>();

    @Override
    public void createMeal(Meal meal) {
        countID.getAndIncrement();
        meal.setId(countID.get());
        mapMeal.put(countID.get(), meal);
    }

    @Override
    public List<MealTo> getAllMealTo() {
        return filteredMealToByList();
    }

    @Override
    public void deleteMeal(int id) {
        mapMeal.remove(id);
    }

    @Override
    public Meal getMealByID(int id) {
        return mapMeal.get(id);
    }

    @Override
    public void updateMeal(int id, LocalDateTime localDateTime, String description, int calories) {
        mapMeal.get(id).setDateTime(localDateTime);
        mapMeal.get(id).setDescription(description);
        mapMeal.get(id).setCalories(calories);
    }

    private List<MealTo> filteredMealToByList() {
        List<Meal> listMeal = Collections.synchronizedList(new ArrayList<>());

        for (Map.Entry<Integer, Meal> e : mapMeal.entrySet()) {
            listMeal.add(e.getValue());
        }

        return MealsUtil.filteredByStreams(listMeal, LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
