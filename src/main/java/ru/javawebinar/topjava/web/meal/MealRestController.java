package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Collection<Meal> getAll() {
        log.info("getAll for User {}", authUserId());
        return service.getAll(authUserId());
    }

    public void delete(int id) {
        log.info("delete {} for User {}", id, authUserId());
        service.delete(id, authUserId());
    }

    public Meal get(int id) throws NotFoundException {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {} for User {}", meal, authUserId());
        checkNew(meal);
        return service.save(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={} for User {}", meal, id, authUserId());
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for User {}", startDate, endDate, startTime, endTime, userId);
        return MealsUtil.getFilteredTos(
                service.getBetween(userId, startDate, endDate),
                authUserCaloriesPerDay(),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX);
    }

}
