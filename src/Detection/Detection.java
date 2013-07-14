/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Detection;

import Profiling.DatabaseProfile;
import Profiling.Profile;
import java.io.BufferedWriter;

/**
 *
 * @author Sijin
 */
public abstract class Detection {

//    Profile GeneralProfile;
//    Profile SuspectProfile;
//
//    public Detection(Profile gp, Profile sp) {
//        this.GeneralProfile = gp;
//        this.SuspectProfile = sp;
//    }
//
//    public Profile getGeneralProfile() {
//        return GeneralProfile;
//    }
//
//    public void setGeneralProfile(Profile GeneralProfile) {
//        this.GeneralProfile = GeneralProfile;
//    }
//
//    public Profile getSuspectProfile() {
//        return SuspectProfile;
//    }
//
//    public void setSuspectProfile(Profile SuspectProfile) {
//        this.SuspectProfile = SuspectProfile;
//    }
    abstract public String examine(BufferedWriter out);

    abstract public boolean tTest(String name);

    boolean compareDuration() {
        return tTest("duration");
    }

    boolean compareTimeInterval() {
        return tTest("timeinterval");
    }

    boolean compareDataSize() {

        return tTest("datasize");

    }

    boolean compareResponseTime() {
        return tTest("responsetime");
    }

    boolean compareDatabaseCPU() {
        return tTest("databasecpu");
    }

    boolean compareDatabaseMemory() {
        return tTest("databasememory");
    }

    boolean compareCPU() {
        return tTest("machinecpu");
    }

    boolean compareMemory() {
        return tTest("machinememory");
    }

    boolean compareHitRate() {
         return tTest("hitrate");
    }


}
