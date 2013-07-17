/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MAD;

import Profiling.DatabaseProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Sijin
 */
public class MAD {

    public MAD() {
    }

    public double generateMAD(DatabaseProfile dp) {

        List<Double> list = new ArrayList<Double>();

        for (int i = 0; i < dp.getQueryPatternList().size(); i++) {

            double a = convertUnity(QueryExtraction.extract(dp.getQueryPatternList().get(i)));
            list.add(a);
        }

        double[] doublelist = new double[dp.getQueryPatternList().size()];

        for (int i = 0; i < list.size(); i++) {

            doublelist[i] = list.get(i);

        }

        Arrays.sort(doublelist);

        double m = MAD.median(doublelist);

        double[] newlist = new double[dp.getQueryPatternList().size()];

        for (int i = 0; i < list.size(); i++) {

            newlist[i] = Math.abs(doublelist[i] - m);

        }

        Arrays.sort(newlist);

        return MAD.median(newlist);

    }

    public static double convertUnity(List<Integer> extract) {

     //   System.out.println("uu");
        double a = 0.0;

        double b = extract.get(0);
        double c = extract.get(1);
        double d = extract.get(2);
        double f = extract.get(3);

        a = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2) + Math.pow(d, 2) + Math.pow(f, 2));

        return a;


    }

    public static double getMedian(DatabaseProfile dp) {
    //System.out.println("aaa");

        List<Double> list = new ArrayList<Double>();
//System.out.println("aaa");
        for (int i = 0; i < dp.getQueryPatternList().size(); i++) {
//System.out.println("aaa");
//Sy stem.out.println(dp.getQueryPatternList().get(i));
            double a = convertUnity(QueryExtraction.extract(dp.getQueryPatternList().get(i)));
            list.add(a);
        }
//System.out.println("aaa");
        double[] doublelist = new double[dp.getQueryPatternList().size()];

        for (int i = 0; i < list.size(); i++) {

            doublelist[i] = list.get(i);

        }
//System.out.println("aaa");
        Arrays.sort(doublelist);

        return MAD.median(doublelist);



    }

    // the array double[] m MUST BE SORTED
    public static double median(double[] m) {
        int middle = m.length / 2;
        if (m.length % 2 == 1) {
            return m[middle];
        } else {
            return (m[middle - 1] + m[middle]) / 2.0;
        }
    }

    public boolean calculate(double value, DatabaseProfile SuspectProfile, double medium, double mad) {

        boolean result = false;

        double z = (0.6745 * ( value - medium))/mad;


        if(Math.abs(z) > 1.5){
            result = true;
        }


        return result;

    }
}
