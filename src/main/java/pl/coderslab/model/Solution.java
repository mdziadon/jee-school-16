package pl.coderslab.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    private int id;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private Exercise exercise;
    private User user;


    public Solution() {
    }


    public Solution(String description) {
        this.description = description;
    }


    public Solution(Exercise exercise, User user) {
        this.exercise = exercise;
        this.user = user;
    }


    public Solution(String description, Exercise exercise, User user) {
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO Solutions(created, description, exercise_id,user_id) VALUES (NOW(),?,?,?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.description);
            preparedStatement.setInt(2, this.exercise.getId());
            preparedStatement.setLong(3, this.user.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE Solutions SET updated=NOW(), description=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.description);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }
    }


    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM Solutions WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


    public static Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Solutions where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getSolutionFromResultSet(conn, resultSet);
        }
        return null;
    }


    public static Solution[] loadAllSolutions(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM Solutions";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            solutions.add(getSolutionFromResultSet(conn, resultSet));
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    public static List<Solution> loadAllSolutions(Connection conn, int limit) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM Solutions order by created desc limit ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, limit);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            solutions.add(getSolutionFromResultSet(conn, resultSet));
        }
        return solutions;
    }


    public static List<Solution> loadAllSolutionsByUserId(Connection conn, int UserId) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM Solutions where user_id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            solutions.add(getSolutionFromResultSet(conn, resultSet));
        }
        return solutions;
    }


    private static Solution getSolutionFromResultSet(Connection conn, ResultSet resultSet) throws SQLException {
        Solution loadedSolution = new Solution();
        loadedSolution.setId(resultSet.getInt("id"));
        loadedSolution.setCreated(resultSet.getTimestamp("created"));
        loadedSolution.setUpdated(resultSet.getTimestamp("updated"));
        loadedSolution.setDescription(resultSet.getString("description"));
        Exercise exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));
        loadedSolution.setExercise(exercise);
        User user = User.loadUserById(conn, resultSet.getInt("user_id"));
        loadedSolution.setUser(user);
        return loadedSolution;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
