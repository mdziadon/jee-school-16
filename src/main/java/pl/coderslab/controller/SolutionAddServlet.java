package pl.coderslab.controller;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/addSolution")
public class SolutionAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String exerciseId = request.getParameter("exerciseId");
        String userId = request.getParameter("userId");
        String description = request.getParameter("description");

        int exerciseIdInt = Integer.parseInt(exerciseId);
        int userIdInt = Integer.parseInt(userId);

        try (Connection connection = DbUtil.getConn()) {
            Solution solution = createSolution(description, exerciseIdInt, userIdInt);

            solution.saveToDB(connection);

            response.sendRedirect("/");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = DbUtil.getConn()) {
            List<User> users = User.loadAllUsers(connection);
            List<Exercise> exercises = Exercise.loadAllExercises(connection);
            request.setAttribute("users", users);
            request.setAttribute("exercises", exercises);

            getServletContext().getRequestDispatcher("/solutionAdd.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Solution createSolution(String description, int exerciseIdInt, int userIdInt) {
        Exercise exercise = new Exercise();
        exercise.setId(exerciseIdInt);

        User user = new User();
        user.setId(userIdInt);

        Solution solution = new Solution();
        solution.setDescription(description);
        solution.setExercise(exercise);
        solution.setUser(user);
        return solution;
    }
}
