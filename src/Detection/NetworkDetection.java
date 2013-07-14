/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Detection;

import Profiling.NetworkProfile;
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
public class NetworkDetection extends Detection {

    NetworkProfile GeneralProfile;
    NetworkProfile SuspectProfile;

    public NetworkDetection(Profile gp, Profile sp) {
        GeneralProfile = (NetworkProfile) gp;
        SuspectProfile = (NetworkProfile) sp;
    }

    @Override
    public String examine(BufferedWriter out) {

        String text = "=============================================";


        boolean time = compareTimeInterval();
        boolean hitrate = compareHitRate();

        System.out.print("[");
        System.out.print("Time interval: ");

        if (time) {
            try {
                out.write(", ");
                out.write("true");
                System.out.print("True");
            } catch (IOException ex) {
                Logger.getLogger(NetworkDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                out.write(", ");
                out.write("false");
                System.out.print("False");
            } catch (IOException ex) {
                Logger.getLogger(NetworkDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.print(", Data Input: ");
        if (hitrate) {
            try {
                out.write(", ");
                out.write("true");
                System.out.print("True");
            } catch (IOException ex) {
                Logger.getLogger(NetworkDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                out.write(", ");
                out.write("false");
                System.out.print("False");
            } catch (IOException ex) {
                Logger.getLogger(NetworkDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        System.out.print("]");
        return text;
    }

    @Override
    public boolean tTest(String name) {
        double[] methodA = null;
        double[] methodB = null;

        boolean result = false;

        if (name.equalsIgnoreCase("timeinterval")) {

            if (this.SuspectProfile.getTimeIntervalList().size() < 2 || this.GeneralProfile.getTimeIntervalList().size() < 2) {
                return result;
            }

            methodA = covertToArray1(this.GeneralProfile, name);
            methodB = covertToArray1(this.SuspectProfile, name);


        }


        if (name.equalsIgnoreCase("hitrate")) {

            if (this.SuspectProfile.getDataInputList().size() < 2  || this.GeneralProfile.getDataInputList().size() < 2) {
                return result;
            }

            methodA = covertToArray2(this.GeneralProfile, name);
            methodB = covertToArray2(this.SuspectProfile, name);


//            System.out.println("A");
//            for (int i = 0; i < methodA.length; i++) {
//                System.out.println(methodA[i]);
//            }
//
//            System.out.println("B");
//            for (int i = 0; i < methodB.length; i++) {
//                System.out.println(methodB[i]);
//            }
        }

        double alpha = 0.1;

        TTest t = new TTest();
        result = t.tTest(methodA, methodB, alpha);
        return result;
    }

    private double[] covertToArray1(NetworkProfile profile, String type) {
        double[] values = new double[profile.getTimeIntervalList().size()];

        if (type.equalsIgnoreCase("timeinterval")) {
            for (int i = 0; i < profile.getTimeIntervalList().size(); i++) {
                values[i] = (double) profile.getTimeIntervalList().get(i);
            }
        }

        return values;

    }

    private double[] covertToArray2(NetworkProfile GeneralProfile, String name) {
        double[] values = new double[GeneralProfile.getDataInputList().size()];

        if (name.equalsIgnoreCase("hitrate")) {
            for (int i = 0; i < GeneralProfile.getDataInputList().size(); i++) {
                values[i] = GeneralProfile.getDataInputList().get(i);
            }
        }


        return values;
    }
}
