/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Random;

public class NormalQueries {
    private static int random;

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String ip = null;
    private String username = null;
    private String password = null;

    public NormalQueries() {
        //select * from mysql.slow_log;
        ip = "146.169.35.102";
        username = "user";
        password = "123";

    }

    //interval in seconds
    public String readDatabaseQuery1() throws Exception {

        String json = "";

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://" + ip + "/RedDragon?"
                    + "user=" + username + "&password=" + password);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from VMs");
           
        } catch (Exception e) {
            throw e;
        } finally {
            close();
            return json;
        }

    }


    //interval in seconds
    public String readDatabaseQuery2() throws Exception {

        String json = "";

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://" + ip + "/RedDragon?"
                    + "user=" + username + "&password=" + password);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from ippool");

        } catch (Exception e) {
            throw e;
        } finally {
            close();
            return json;
        }

    }

    //interval in seconds
    public String readDatabaseQuery3() throws Exception {

        String json = "";

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://" + ip + "/RedDragon?"
                    + "user=" + username + "&password=" + password);

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("select * from users");

        } catch (Exception e) {
            throw e;
        } finally {
            close();
            return json;
        }

    }

    // You need to close the resultSet

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws Exception {

        NormalQueries dao = new NormalQueries();
        System.out.println("normal starts...");

        while(true){

            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(10);

            if(randomInt%3 == 1){
                dao.readDatabaseQuery1();
            } else if (randomInt%3 == 2){
                dao.readDatabaseQuery2();
            } else {
                dao.readDatabaseQuery3();
            }

            Thread.sleep(randomInt *1000);
        }
      

    }


}
