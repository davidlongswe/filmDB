package database_elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Film;

import java.sql.*;

public class DatabaseHandler {

    private Connection dbConnection;
    private DatabaseAccount databaseAccount;
    private Statement statement;

    public DatabaseHandler(){
        try {
            Class.forName("org.mariadb.jdbc.Driver").getDeclaredConstructor().newInstance();
            this.databaseAccount = new DatabaseAccount();
        } catch (Exception e) {
            System.err.println("JDBC Drivers were not found: " + e.getMessage());
        }
    }

    public void connect(){
        try {
            String DB_URL = "jdbc:mariadb://atlantis.informatik.umu.se/svph1910_db_ht2020";
            dbConnection = DriverManager.getConnection(
                    DB_URL,
                    databaseAccount.getUser(),
                    databaseAccount.getPassword());
        }catch (SQLException e) {
            System.err.println("Database couldn't connect with error-code: " + e.getMessage());
        }
    }

    public void disconnect(){
        try {
            if(dbConnection != null){
                dbConnection.close();
            }
        } catch (SQLException e) {
            System.err.println("Database couldn't disconnect with error-code: " + e.getMessage());
        }
    }

    public ObservableList<Film> getFilms(){
        connect();
        ObservableList<Film> films = FXCollections.observableArrayList();
        String query = "SELECT * FROM Films";
        try{
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            Film film;
            while(resultSet.next()){
                film = new Film(resultSet.getInt("ID"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("year"),
                        resultSet.getInt("rating"));
                films.add(film);
            }
            statement.close();
            resultSet.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        disconnect();
        return films;

    }

    public void insertRecord(int id, String title, String genre, int year, int rating){
        String query = "INSERT INTO Films VALUES (" + id + ",'" + title + "','" + genre + "',"
                + year + "," + rating + ")";
        executeQuery(query);
    }

    public void updateRecord(int id, String title, String genre, int year, int rating){
        String query = "UPDATE  Films SET title  = '" + title + "', genre = '" + genre + "', year = " +
                year + ", rating = " + rating + " WHERE ID = " + id + "";
        executeQuery(query);
    }

    public void deleteRecord(int id){
        String query = "DELETE FROM Films WHERE ID =" + id + "";
        executeQuery(query);
    }

    public void executeQuery(String query){
        connect();
        try {
            statement = dbConnection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

}
