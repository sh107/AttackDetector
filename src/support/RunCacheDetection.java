/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import Detection.CacheDetection;
import Profiling.CacheProfile;
import Detection.Detection;
import java.sql.Timestamp;
import java.util.Date;
import Profiling.Profile;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Sijin
 */
public class RunCacheDetection {

    /**
     * @param args the command line arguments
     */
    public static void run() throws Exception {
        // TODO code application logic here
        int timeForMonitoring = 60;

        Date date = new java.util.Date();
        Timestamp end = covertDateToTimestamp("2013-05-21 13:31:09");
        Timestamp begin = covertDateToTimestamp("2013-05-21 13:24:41");//new Timestamp(end.getTime() - 10 * 60 * 1000);

        Profile gp = new CacheProfile(begin, end);

        gp.SetupProfile();

        while (true) {

            FileWriter fstream = new FileWriter("cache.csv", true);
            BufferedWriter out = new BufferedWriter(fstream);

            date = new java.util.Date();
            //         end = covertDateToTimestamp("2013-04-14 21:29:10");
            //         begin = covertDateToTimestamp("2013-04-14 21:29:12");//new Timestamp(end.getTime() - 10 * 60 * 1000);

            end = new Timestamp(date.getTime());
            System.out.println("Time: " + end.toString());
          //  System.out.println(end);
            begin = new Timestamp(end.getTime()- 5 * timeForMonitoring * 1000);

            Profile sp = new CacheProfile(begin, end);
            sp.SetupProfile();


            Detection test = new CacheDetection(gp, sp);

            test.examine(out);
            //   String attack = test.examine();

            //    System.out.println(attack);


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
