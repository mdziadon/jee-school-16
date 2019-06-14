package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private UserGroup userGroup;


    public User() {
    }

    public User(String username, String email, String password, UserGroup userGroup) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userGroup = userGroup;
    }

    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO Users(username, email, password, user_group_id) VALUES (?, ?, ?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.setInt(4, this.userGroup.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE Users SET username=?, email=?, password=?, user_group_id = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.setInt(4, this.userGroup.getId());
            preparedStatement.setInt(5, this.id);
            preparedStatement.executeUpdate();
        }
    }


    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM Users WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


    public static User loadUserById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Users where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getUserFromResultSet(conn, resultSet);
        }
        return null;
    }


    public static List<User> loadAllUsers(Connection conn) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            users.add(getUserFromResultSet(conn, resultSet));
        }
        return users;
    }


    public static List<User> loadAllUsersByGroupId(Connection conn, int userGroupId) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users where user_group_id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, userGroupId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            users.add(getUserFromResultSet(conn, resultSet));
        }
        return users;
    }


    private static User getUserFromResultSet(Connection conn, ResultSet resultSet) throws SQLException {
        User loadedUser = new User();
        loadedUser.setId(resultSet.getInt("id"));
        loadedUser.setUsername(resultSet.getString("username"));
        loadedUser.setPassword(resultSet.getString("password"));
        loadedUser.setEmail(resultSet.getString("email"));
        int userGroupId = resultSet.getInt("user_group_id");
        UserGroup userGroup = UserGroup.loadUserGroupById(conn, userGroupId);
        loadedUser.setUserGroup(userGroup);
        return loadedUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }


}
