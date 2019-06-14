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

@WebServlet("/deleteSolution")
public class SolutionDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        int solutionId = Integer.parseInt(id);

        try (Connection connection = DbUtil.getConn()) {
            Solution solution = new Solution();
            solution.setId(solutionId);
            solution.delete(connection);
            response.sendRedirect("/");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
