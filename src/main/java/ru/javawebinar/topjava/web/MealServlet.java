package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.ServiceMeal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final String LIST_MEAL = "meals.jsp";
    private static final String INSERT_OR_EDIT = "create_edit_meal.jsp";

    ServiceMeal serviceMeal = new ServiceMeal();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.debug("redirect to meal");

        String forword = "";
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("listMeal")) {
            forword = LIST_MEAL;
            req.setAttribute("meals", serviceMeal.getAllMealTo());
        } else if (action.equalsIgnoreCase("delete")) {
            int mealID = Integer.parseInt(req.getParameter("mealID"));
            serviceMeal.deleteMeal(mealID);
            forword = LIST_MEAL;
            req.setAttribute("meals", serviceMeal.getAllMealTo());
        } else if (action.equalsIgnoreCase("edit")) {
            forword = INSERT_OR_EDIT;
            int mealID = Integer.parseInt(req.getParameter("mealID"));
            Meal meal = serviceMeal.getMealByID(mealID);
            req.setAttribute("meal", meal);
        } else {
            forword = INSERT_OR_EDIT;
        }

        RequestDispatcher view = req.getRequestDispatcher(forword);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LocalDateTime date = LocalDateTime.parse(req.getParameter("date"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        String mealID = req.getParameter("mealid");
        if (mealID == null || mealID.isEmpty()) {
            Meal meal = new Meal(date, description, calories);
            serviceMeal.createMeal(meal);
        } else {
            serviceMeal.updateMeal(Integer.parseInt(mealID), date, description, calories);
        }

        RequestDispatcher view = req.getRequestDispatcher(LIST_MEAL);
        req.setAttribute("meals", serviceMeal.getAllMealTo());
        view.forward(req, resp);
    }

}
