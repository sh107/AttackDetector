/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import Detection.Detection;
import Detection.NetworkDetection;
import java.sql.Timestamp;
import java.util.Date;
import Profiling.NetworkProfile;
import Profiling.Profile;
import communication.AccessDatabase;
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
public class RunNetworkDetection {

    /**
     * @param args the command line arguments
     */
    public static void run() throws Exception {
        // TODO code application logic here
        int timeForMonitoring = 60;

        while (true) {

            try {
                // Create file
                FileWriter fstream = new FileWriter("network.csv", true);
                BufferedWriter out = new BufferedWriter(fstream);

                Date date = new java.util.Date();
                Timestamp now = new Timestamp(date.getTime());
                System.out.println("Time: " + now.toString());
                out.write(now.toString());

                String source = "146.169.6.76";

                Timestamp end = covertDateToTimestamp("2013-05-21 15:08:28");
                Timestamp begin = covertDateToTimestamp("2013-05-21 15:07:15");//new Timestamp(end.getTime() - 10 * 60 * 1000);

                Profile gp = new NetworkProfile(begin, end, source);

                gp.SetupProfile();

                List<String> srclist = new ArrayList<String>();

                AccessDatabase ad = new AccessDatabase();

                srclist = ad.getSourcelist();

           //     for (int i = 0; i < srclist.size(); i++) {
                    date = new java.util.Date();
//            end = covertDateToTimestamp("2013-04-18 21:28:23");
//            begin = covertDateToTimestamp("2013-04-18 16:18:14");//new Timestamp(end.getTime() - 10 * 60 * 1000);

                    String newsource = source;//srclist.get(i);
                    out.write(", ");
                    out.write(newsource);
                    end = new Timestamp(date.getTime());
                    begin = new Timestamp(end.getTime() - 5 * timeForMonitoring * 1000);

                    Profile sp = new NetworkProfile(begin, end, newsource);
                    sp.SetupProfile();

                    Detection test = new NetworkDetection(gp, sp);

                    System.out.print(newsource);
                    test.examine(out);

                    System.out.println(" ");
     //           }

                out.newLine();
                out.close();
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }

            Thread.sleep(timeForMonitoring * 1000);

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
