/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import Detection.DatabaseDetection;
import Detection.Detection;
import java.sql.Timestamp;
import java.util.Date;
import Profiling.DatabaseProfile;
import Profiling.Profile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sijin
 */
public class RunDatabaseDetection {

    /**
     * @param args the command line arguments
     */
    public static void run() throws Exception {
        // TODO code application logic here
        int timeForMonitoring = 60;

        List<String> userList = new ArrayList<String>();

        for (int i = 0; i < 3; i++) {
            userList.add("user" + (i + 1));

        }

        while (true) {

            try {
                // Create file
                FileWriter fstream = new FileWriter("database.csv", true);
                BufferedWriter out = new BufferedWriter(fstream);
                
                //Close the output stream

                Date date = new java.util.Date();
                Timestamp end = new Timestamp(date.getTime());
                System.out.println("Time: " + end.toString());
                out.write(end.toString());
                for (int i = 0; i < userList.size(); i++) {

                    date = new java.util.Date();
                    end = covertDateToTimestamp("2013-04-17 22:49:33");
                    Timestamp begin = covertDateToTimestamp("2013-04-17 19:21:46");//new Timestamp(end.getTime() - 10 * 60 * 1000);

                    String dbuser = userList.get(i);
                    Profile gp = new DatabaseProfile(begin, end, "user1");

                    gp.SetupProfile();

                end = covertDateToTimestamp("2013-04-17 20:44:27");
                begin = covertDateToTimestamp("2013-04-17 16:20:02");
//                    date = new java.util.Date();
  //                  end = new Timestamp(date.getTime());
    //                begin = new Timestamp(end.getTime() - 5 * timeForMonitoring * 1000);


                    Profile sp = new DatabaseProfile(begin, end, dbuser);
                    sp.SetupProfile();

                    //print user
                    out.write(", ");
                    out.write(dbuser);
                    System.out.print("username: " + dbuser + " [ ");
                    Detection test = new DatabaseDetection(gp, sp);

                    // System.out.println(test.examine());
                    test.examine(out);


                }
                out.newLine();
                out.close();
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }

            Thread.sleep(timeForMonitoring * 1000);

            System.out.println("==========================================");

        }

    }

    private static Timestamp covertDateToTimestamp(String string) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string);


        } catch (ParseException e) {
            System.out.println("Exception :" + e);
        }

        return new Timestamp(date.getTime());

    }
}
