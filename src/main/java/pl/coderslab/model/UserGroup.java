package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGroup {
    private int id;
    private String name;


    public UserGroup() {
    }


    public UserGroup(String name) {
        this.name = name;
    }


    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO User_groups(name) VALUES (?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE users SET name=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        }
    }


    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql2 = "DELETE FROM User_groups WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


    public static UserGroup loadUserGroupById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM User_groups where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            UserGroup loadedUserGroup = new UserGroup();
            loadedUserGroup.setId(resultSet.getInt("id"));
            loadedUserGroup.setName(resultSet.getString("name"));
            return loadedUserGroup;
        }
        return null;
    }


    public static List<UserGroup> loadAllUserGroups(Connection conn) throws SQLException {
        List<UserGroup> userGroups = new ArrayList<>();
        String sql = "SELECT * FROM User_groups";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserGroup loadedUserGroup = new UserGroup();
            loadedUserGroup.setId(resultSet.getInt("id"));
            loadedUserGroup.setName(resultSet.getString("name"));
            userGroups.add(loadedUserGroup);
        }
        return userGroups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
