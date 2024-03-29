package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    /* в ресурсах каждого метода надо гарантированно закрывать помимо стейтмента еще и коннекшн - для разделения используй ;

    - sout перенеси в сервис - бизнес логика там

    - удали комментированный код*/

    public void createUsersTable() {

        String sql = "CREATE TABLE myschemas.users\n"
                + "(\n" + "    user_id       int auto_increment,\n"
                + "    user_name     varchar(30) null,\n"
                + "    user_lastname varchar(30) null,\n"
                + "    user_age      int         null,\n"
                + "    constraint users_pk\n"
                + "        primary key (user_id)\n" + ");";

        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            if (!e.getMessage().equals("Table 'users' already exists")) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dropUsersTable() {

        String sql = "drop table myschemas.users;";

        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            if (!e.getMessage().equals("Unknown table 'myschemas.users'")) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO myschemas.users (user_name, user_lastname, user_age)\n" +
                " VALUES (?, ?, ?); ";

        try (Connection conn = Util.getMySQLConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {

        String sql = "DELETE FROM myschemas.users WHERE user_id = ?; ";

        try (Connection conn = Util.getMySQLConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM myschemas.users ;";

        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String userName = rs.getString("user_name");
                String userLastname = rs.getString("user_lastname");
                Byte userAge = rs.getByte("user_age");

                users.add(new User(userName, userLastname, userAge));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {

        String sql = "DELETE FROM myschemas.users;";

        try (Connection conn = Util.getMySQLConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
