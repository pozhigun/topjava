package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save meal {}, user id {}", meal, meal.getUserId());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }

        if (mealIsNotNullAndTrueUserId(meal.getId(), userId)) {
            meal.setUserId(userId);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            throw new NotFoundException("Not found id");
        }

    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal{}, userid{}", repository.get(id), userId);
        return mealIsNotNullAndTrueUserId(id, userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("meal get{}", repository.get(id));
        return mealIsNotNullAndTrueUserId(id, userId) ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getBetween(userId, LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public Collection<Meal> getBetween(int userId, LocalDate startTime, LocalDate endTime) {
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startTime, endTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private boolean mealIsNotNullAndTrueUserId(int id, int userId) {
        Meal meal = repository.get(id);
        return (meal != null && meal.getUserId() == userId);
    }

}
