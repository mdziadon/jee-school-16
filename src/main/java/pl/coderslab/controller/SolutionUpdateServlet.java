package pl.coderslab.controller;

import pl.coderslab.model.Solution;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/updateSolution")
public class SolutionUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        int solutionId = Integer.parseInt(id);
        String description = request.getParameter("description");

        try (Connection connection = DbUtil.getConn()) {
            Solution solution = Solution.loadSolutionById(connection, solutionId);
            solution.setId(solutionId);
            solution.setDescription(description);
            solution.saveToDB(connection);
            response.sendRedirect("/");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        int solutionId = Integer.parseInt(id);

        try (Connection connection = DbUtil.getConn()) {
            Solution solution = Solution.loadSolutionById(connection, solutionId);
            request.setAttribute("solution", solution);

            getServletContext().getRequestDispatcher("/solutionUpdate.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
