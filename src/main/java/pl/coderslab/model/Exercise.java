package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private int id;
    private String title;
    private String description;


    public Exercise() {
    }


    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }


    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO Exercises(title, description) VALUES (?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE Exercises SET title=?, description=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.setInt(3, this.id);
            preparedStatement.executeUpdate();
        }
    }


    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM Exercises WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


    public static Exercise loadExerciseById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Exercises where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Exercise loadedExercise = new Exercise();
            loadedExercise.id = resultSet.getInt("id");
            loadedExercise.title = resultSet.getString("title");
            loadedExercise.description = resultSet.getString("description");
            return loadedExercise;
        }
        return null;
    }


    public static List<Exercise> loadAllExercises(Connection conn) throws SQLException {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM Exercises";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Exercise loadedExercise = new Exercise();
            loadedExercise.setId(resultSet.getInt("id"));
            loadedExercise.setTitle(resultSet.getString("title"));
            loadedExercise.setDescription(resultSet.getString("description"));
            exercises.add(loadedExercise);
        }
        return exercises;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
