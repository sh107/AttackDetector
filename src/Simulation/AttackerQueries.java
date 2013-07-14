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

public class AttackerQueries {

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String ip = null;
    private String username = null;
    private String password = null;

    public AttackerQueries() {
        //select * from mysql.slow_log;
        ip = "146.169.35.102";
        username = "user";
        password = "123";

    }

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
            resultSet = statement.executeQuery("select * from ipusage");

        } catch (Exception e) {
            throw e;
        } finally {
            close();
            return json;
        }

    }

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
            resultSet = statement.executeQuery("select * from logins");

        } catch (Exception e) {
            throw e;
        } finally {
            close();
            return json;
        }

    }

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
            resultSet = statement.executeQuery("select * from VMStatistics");

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

        AttackerQueries dao = new AttackerQueries();

        System.out.println("attacker starts...");
        while(true){

            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(5);

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
