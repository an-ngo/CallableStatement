package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO  implements IUserDAO{
    private static String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private static String username = "root";
    private static String password = "123456a@A";

    private static final String INSERT_USER_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
    private static final String FIND_BY_COUNTRY = "select * from users where country = ?;";
    private static final String FIND_ALL_USERS = "call showAllUserList()";
    private static final String UPDATE_USERS_PROCEDURE = "call updateUser(?,?,?,?)";

    public UserDAO(){};
    protected static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }

    @Override
    public List<User> findByCountry(String country) throws SQLException {
        System.out.println(FIND_BY_COUNTRY);
        List<User> userList = new ArrayList<>();
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_COUNTRY);){
            preparedStatement.setString(1,country);
            ResultSet resultSet = preparedStatement.executeQuery();
            while ((resultSet.next())){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                userList.add(new User(id,name,email,country));

            }



        }
        return userList;
    }

    //    public static void main(String[]sout args) {
//        if(UserDAO.getConnection()==null){
//            System.out.println("khong thanh cong");
//        }
//        else System.out.println("thanh cong");
//    }

    @Override
    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USER_SQL);
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
        ){
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getCountry());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        ) {
            preparedStatement.setInt(1,id);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                user = new User(id,name,email,country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection con = getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_USERS);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id,name,email,country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        Connection connection = getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);){
            preparedStatement.setInt(1,id);
            rowDeleted= preparedStatement.executeUpdate()>0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL);
        ){
            preparedStatement.setInt(4,user.getId());
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getCountry());

            rowUpdated = preparedStatement.executeUpdate()>0;
        }
        return rowUpdated;
    }

    @Override
    public List<User> findAllUsers() {
        Connection con = getConnection();
        List<User> list = new ArrayList<>();
        try (CallableStatement statement = con.prepareCall(FIND_ALL_USERS)) {
            ResultSet resultSet= statement.executeQuery();


        }catch (SQLException e){

        }
        return null;
    }

    @Override
    public boolean updateUserP(User user) throws SQLException {
        Connection con = getConnection();
        CallableStatement statement = con.prepareCall(UPDATE_USERS_PROCEDURE);
        statement.setInt(1,user.getId());
        statement.setString(2,user.getName());
        statement.setString(3,user.getEmail());
        statement.setString(4,user.getCountry());
        return statement.executeUpdate()>0;
    }

    @Override
    public boolean deleteUserP(int id) throws SQLException {
        return false;
    }
}
