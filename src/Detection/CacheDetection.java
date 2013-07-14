/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Detection;

import Profiling.CacheProfile;
import Profiling.Profile;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Sijin
 */
public class CacheDetection extends Detection {

    CacheProfile GeneralProfile;
    CacheProfile SuspectProfile;

    public CacheDetection(Profile gp, Profile sp) {
        GeneralProfile = (CacheProfile) gp;
        SuspectProfile = (CacheProfile) sp;
    }

    @Override
    public String examine(BufferedWriter out) {
        String text = "=============================================";

        boolean duration = compareDuration();


        if (duration) {
            try {
                out.write(", ");
                out.write("true");
                System.out.println("Possible attack (Duration)");
            } catch (IOException ex) {
                Logger.getLogger(CacheDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                out.write(", ");
                out.write("false");
                System.out.println("No attack (Duration)");
            } catch (IOException ex) {
                Logger.getLogger(CacheDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return text;
    }

    @Override
    public boolean tTest(String name) {
        double[] methodA = null;
        double[] methodB = null;

        boolean result = false;

    //    System.out.println(this.SuspectProfile.getDurationList().size());
        if (name.equalsIgnoreCase("duration")) {

            if (this.SuspectProfile.getDurationList().size() < 2) {
                return result;
            }

            methodA = covertToArray1(this.GeneralProfile, name);
            methodB = covertToArray1(this.SuspectProfile, name);
        }

//        System.out.print("A ");
//        for(int i = 0; i < methodA.length; i++){
//            System.out.print(methodA[i]);
//            System.out.print(",");
//        }
//
//        System.out.println(".");
//
//        System.out.print("B ");
//        for(int i = 0; i < methodB.length; i++){
//            System.out.print(methodB[i]);
//            System.out.print(",");
//        }
//
//        System.out.println(".");

        double alpha = 0.1;

        TTest t = new TTest();
        result = t.tTest(methodA, methodB, alpha);
        return result;
    }

    private double[] covertToArray1(CacheProfile profile, String type) {
        double[] values = new double[profile.getDurationList().size()];

        if (type.equalsIgnoreCase("duration")) {
            for (int i = 0; i < profile.getDurationList().size(); i++) {
                values[i] = (double) profile.getDurationList().get(i);
            }
        }

        return values;

    }


}
