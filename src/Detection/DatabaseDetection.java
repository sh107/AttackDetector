/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Detection;

import MAD.MAD;
import MAD.QueryExtraction;
import Profiling.DatabaseProfile;
import Profiling.Profile;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Sijin
 */
public class DatabaseDetection extends Detection {

    DatabaseProfile GeneralProfile;
    DatabaseProfile SuspectProfile;

    public DatabaseProfile getGeneralProfile() {
        return GeneralProfile;
    }

    public void setGeneralProfile(DatabaseProfile GeneralProfile) {
        this.GeneralProfile = GeneralProfile;
    }

    public DatabaseProfile getSuspectProfile() {
        return SuspectProfile;
    }

    public void setSuspectProfile(DatabaseProfile SuspectProfile) {
        this.SuspectProfile = SuspectProfile;
    }

    public DatabaseDetection(Profile gp, Profile sp) {
        GeneralProfile = (DatabaseProfile) gp;
        SuspectProfile = (DatabaseProfile) sp;
    }

    public String examine(BufferedWriter out) {
        boolean attack = false;
        String text = "=============================================";

        boolean data = compareDataSize();
        boolean response = compareResponseTime();
        boolean time = compareTimeInterval();
        boolean query = newnewCompareQueryPattern();
//        boolean dbcpu = compareDatabaseCPU();
//        boolean dbmem = compareDatabaseMemory();
//        boolean cpu = compareCPU();
//        boolean mem = compareMemory();

        if (data) {
            try {
                out.write(", ");
                out.write("true");
                System.out.print("Data size: True ");
            } catch (IOException ex) {
                Logger.getLogger(DatabaseDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                out.write(", ");
                out.write("false");
                System.out.print("Data size: False ");
            } catch (IOException ex) {
                Logger.getLogger(DatabaseDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (response) {
            try {
                out.write(", ");
                out.write("true");
                System.out.print("Response Time: True ");
            } catch (IOException ex) {
                Logger.getLogger(DatabaseDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                out.write(", ");
                out.write("false");
                System.out.print("Response Time: False ");
            } catch (IOException ex) {
                Logger.getLogger(DatabaseDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (time) {
            try {
                out.write(", ");
                out.write("true");
                System.out.print("Time interval: True ");
            } catch (IOException ex) {
                Logger.getLogger(DatabaseDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                out.write(", ");
                out.write("false");
                System.out.print("Time interval: False ");
            } catch (IOException ex) {
                Logger.getLogger(DatabaseDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (query) {
            try {
                out.write(", ");
                out.write("true");
                System.out.print("Query Pattern: True ");
            } catch (IOException ex) {
                Logger.getLogger(DatabaseDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                out.write(", ");
                out.write("false");
                System.out.print("Query Pattern: False ");
            } catch (IOException ex) {
                Logger.getLogger(DatabaseDetection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("]");
//        if (dbcpu) {
//            System.out.println("Possible attack (Database CPU)");
//        } else {
//            System.out.println("No attack (Database CPU)");
//        }
//
//        if (dbmem) {
//            System.out.println("Possible attack (Database Memory)");
//        } else {
//            System.out.println("No attack (Database Memory)");
//        }
//
//
//        if (cpu) {
//            System.out.println("Possible attack (CPU)");
//        } else {
//            System.out.println("No attack (CPU)");
//        }
//
//
//        if (mem) {
//            System.out.println("Possible attack (Memory)");
//        } else {
//            System.out.println("No attack (Memory)");
//        }
        return text;
    }

    public boolean tTest(String name) {

        double[] methodA = null;
        double[] methodB = null;

        boolean result = false;

        if (name.equalsIgnoreCase("datasize")) {

            if (this.SuspectProfile.getDataSizeList().size() < 2 || this.GeneralProfile.getDataSizeList().size() < 2) {
                return result;
            }
//            System.out.println(this.GeneralProfile.getDataSizeList().size());
//            System.out.println(this.SuspectProfile.getDataSizeList().size());
            methodA = covertToArray1(this.GeneralProfile, name);
            methodB = covertToArray1(this.SuspectProfile, name);
        }

        if (name.equalsIgnoreCase("responsetime")) {

            if (this.SuspectProfile.getResponseTimeList().size() < 2 || this.GeneralProfile.getResponseTimeList().size() < 2) {
                return result;
            }


            methodA = covertToArray1(this.GeneralProfile, name);
            methodB = covertToArray1(this.SuspectProfile, name);

        }

        if (name.equalsIgnoreCase("timeinterval")) {

            if (this.SuspectProfile.getTimeIntervalList().size() < 2 || this.GeneralProfile.getTimeIntervalList().size() < 2) {
                return result;
            }

            methodA = covertToArray1(this.GeneralProfile, name);
            methodB = covertToArray1(this.SuspectProfile, name);
        }

        if (name.equalsIgnoreCase("databasecpu")) {

            if (this.SuspectProfile.getDbCPUList().size() < 2) {
                return result;
            }

            methodA = covertToArray(this.GeneralProfile, name);
            methodB = covertToArray(this.SuspectProfile, name);
        }

        if (name.equalsIgnoreCase("databasememory")) {

            if (this.SuspectProfile.getDbMemoryList().size() < 2) {
                return result;
            }

            methodA = covertToArray(this.GeneralProfile, name);
            methodB = covertToArray(this.SuspectProfile, name);
        }

        if (name.equalsIgnoreCase("machinecpu")) {

            if (this.SuspectProfile.getMachineCPUList().size() < 2) {
                return result;
            }

            methodA = covertToArray(this.GeneralProfile, name);
            methodB = covertToArray(this.SuspectProfile, name);
        }

        if (name.equalsIgnoreCase("machinememory")) {

            if (this.SuspectProfile.getMachineMemoryList().size() < 2) {
                return result;
            }

            methodA = covertToArray(this.GeneralProfile, name);
            methodB = covertToArray(this.SuspectProfile, name);
        }

        double alpha = 0.10;

        TTest t = new TTest();
        result = t.tTest(methodA, methodB, alpha);
        return result;
    }

    private double[] covertToArray1(DatabaseProfile profile, String type) {
        double[] values = new double[profile.getDataSizeList().size()];

        if (type.equalsIgnoreCase("datasize")) {
            for (int i = 0; i < profile.getDataSizeList().size(); i++) {
                values[i] = (double) profile.getDataSizeList().get(i);
            }
        }

        if (type.equalsIgnoreCase("responsetime")) {
            for (int i = 0; i < profile.getResponseTimeList().size(); i++) {
                values[i] = (double) profile.getResponseTimeList().get(i);
            }
        }

        if (type.equalsIgnoreCase("timeinterval")) {
            for (int i = 0; i < profile.getTimeIntervalList().size(); i++) {
                values[i] = (double) profile.getTimeIntervalList().get(i);
            }
        }


        return values;

    }

    private double[] covertToArray(DatabaseProfile profile, String type) {

        double[] values = new double[profile.numberOfData];

        if (type.equalsIgnoreCase("datasize")) {
            for (int i = 0; i < profile.getDataSizeList().size(); i++) {
                values[i] = (double) profile.getDataSizeList().get(i);
            }
        }

        if (type.equalsIgnoreCase("responsetime")) {
            for (int i = 0; i < profile.numberOfData; i++) {
                values[i] = (double) profile.getResponseTimeList().get(i);
            }
        }
        if (type.equalsIgnoreCase("timeinterval")) {
            for (int i = 0; i < profile.numberOfData; i++) {
                values[i] = (double) profile.getTimeIntervalList().get(i);
            }
        }
        if (type.equalsIgnoreCase("databasecpu")) {
            for (int i = 0; i < profile.numberOfData; i++) {
                values[i] = (double) profile.getDbCPUList().get(i);
            }
        }

        if (type.equalsIgnoreCase("databasememory")) {
            for (int i = 0; i < profile.numberOfData; i++) {
                values[i] = (double) profile.getDbMemoryList().get(i);
            }
        }
        if (type.equalsIgnoreCase("machinecpu")) {
            for (int i = 0; i < profile.numberOfData; i++) {
                values[i] = (double) profile.getMachineCPUList().get(i);
            }
        }
        if (type.equalsIgnoreCase("machinememory")) {
            for (int i = 0; i < profile.numberOfData; i++) {
                values[i] = (double) profile.getMachineMemoryList().get(i);
            }
        }


        return values;
    }

    private boolean compareQueryPatternStrict() {

        boolean answer = false;

        Set<String> comparelist = new HashSet<String>();

        for (int i = 0; i < this.GeneralProfile.getQueryPatternList().size(); i++) {

            comparelist.add(this.GeneralProfile.getQueryPatternList().get(i));
        }

        List<Double> simList = new ArrayList<Double>();

        for (int i = 0; i < this.SuspectProfile.getQueryPatternList().size(); i++) {


            if (!comparelist.contains(this.SuspectProfile.getQueryPatternList().get(i))) {
                return true;
            }

        }

        return answer;
    }

    private boolean newCompareQueryPattern() {
        boolean answer = false;

        if (this.GeneralProfile.getQueryPatternList().size() < 2 || this.SuspectProfile.getQueryPatternList().size() < 2) {
            return false;
        }


        Set<String> setlistgeneral = new HashSet<String>();

        for (int i = 0; i < this.GeneralProfile.getQueryPatternList().size(); i++) {

            setlistgeneral.add(this.GeneralProfile.getQueryPatternList().get(i));

        }
        //    System.out.println("a " + this.SuspectProfile.getQueryPatternList().size());

        Set<String> setlissuspect = new HashSet<String>();

        for (int i = 0; i < this.SuspectProfile.getQueryPatternList().size(); i++) {

            setlissuspect.add(this.SuspectProfile.getQueryPatternList().get(i));
            // System.out.println(this.SuspectProfile.getQueryPatternList().get(i));
        }

        List<String> generallist = new ArrayList<String>();
        List<String> suspectlist = new ArrayList<String>();

        Iterator<String> iterator = setlistgeneral.iterator();


        while (iterator.hasNext()) {

            String a = iterator.next();

            generallist.add(a);

        }

        Iterator<String> iterator1 = setlissuspect.iterator();


        while (iterator1.hasNext()) {

            String a = iterator1.next();

            suspectlist.add(a);

        }

        // System.out.println("aaaaaa");
        double[] alist = compareStringList(generallist, generallist);
        double[] blist = compareStringList(suspectlist, generallist);

//        System.out.println("a " + alist.length);
//        System.out.println("b " + blist.length);
//        System.out.println(" ");
//        for (int i = 0; i < alist.length; i++) {
//            System.out.print("a " + alist[i]);
//        }
//        System.out.println(" ");
//        for (int i = 0; i < blist.length; i++) {
//            System.out.print("b " + blist[i]);
//
//        }



        double alpha = 0.10;

        TTest t = new TTest();
        answer = t.tTest(alist, blist, alpha);

//        double a = getMin(alist);
//        System.out.println("a " + a);
//        double b = getMin(blist);
//        System.out.println("b " + b);
//        if(a > b){
//            answer = true;
//        }

        return answer;

    }

    private double compare(String a, String b) {
        double similarity = 0.0;

        String[] doc1 = a.split(" ");
        String[] doc2 = b.split(" ");

        for (int i = 0; i < doc1.length - 1; i++) {
            if (!doc1[i].equalsIgnoreCase(doc1[i + 1])) {
                String unique = doc1[i];
                double count = 0.0;
                for (int j = 0; j < doc2.length; j++) {
                    if (unique.equalsIgnoreCase(doc2[j])) {
                        count++;
                        similarity += count / doc2.length;
                    }
                }
            }
        }
        return similarity;
    }

    private double findMin(List<Double> simList) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (double d : simList) {
            if (d > max) {
                max = d;
            }
            if (d < min) {
                min = d;
            }

        }


        return min;
    }

    private double[] compareStringList(List<String> profile1, List<String> profile2) {

        List<Double> simList = new ArrayList<Double>();
        // System.out.println(profile1.getQueryPatternList().size());

        for (int i = 0; i < profile1.size(); i++) {

            double similarity = 0.0;

            for (int j = 0; j < profile2.size(); j++) {
                //              similarity += compare(profile1.getQueryPatternList().get(i), profile2.getQueryPatternList().get(j));
                if (similarity < compare(profile1.get(i), profile2.get(j))) {
                    similarity = compare(profile1.get(i), profile2.get(j));

                }
            }

            //  similarity = similarity / (double) profile2.getQueryPatternList().size();
            simList.add(similarity);
        }

        double[] sim = convertToDouble(simList);

        return sim;
    }

    private boolean compareQueryPattern() {

        boolean answer = false;


        Set<String> comparelist = new HashSet<String>();

        for (int i = 0; i < this.GeneralProfile.getQueryPatternList().size(); i++) {

            comparelist.add(this.GeneralProfile.getQueryPatternList().get(i));
        }

        List<Double> simList = new ArrayList<Double>();

        for (int i = 0; i < this.SuspectProfile.getQueryPatternList().size(); i++) {

//
//            if (!comparelist.contains(this.SuspectProfile.getQueryPatternList().get(i))) {
//                return true;
//            }

            String b = this.SuspectProfile.getQueryPatternList().get(i);

            Iterator<String> iterator = comparelist.iterator();
            double similarity = 0.0;

            //  System.out.println("s " + b);

            while (iterator.hasNext()) {

                String a = iterator.next();
                //if (similarity <= compare(a, b)) {
                //   System.out.println(compare(a, b));
                similarity += compare(a, b);
                //  }
                //  System.out.println("s " + b);
                // System.out.println("g " + a);

            }
            //  System.out.println("size " + comparelist.size());
            similarity = similarity / (double) comparelist.size();
            // System.out.println("sim " + similarity);
            simList.add(similarity);

        }

        double min = findMin(simList);
//        for(int i =0; i < simList.size(); i++){
//            System.out.print(simList.get(i) + " ");
//        }
        //     System.out.println(" ");
        if (min < 0.425) {
            answer = true; //This value can be adjusted by running the same data set.
        }

        return answer;

    }

    private double[] convertToDouble(List<Double> simList) {

        double[] values = new double[simList.size()];


        for (int i = 0; i < simList.size(); i++) {
            values[i] = (double) simList.get(i);
        }

        return values;


    }

    private double getMin(double[] alist) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (double d : alist) {
            if (d > max) {
                max = d;
            }
            if (d < min) {
                min = d;
            }

        }


        return min;
    }

    private boolean newCompareQueryPatternWithMAD() {

        return buildMad(this.GeneralProfile.getQueryPatternList(), this.SuspectProfile.getQueryPatternList());
    }

    private boolean buildMad(List<String> queryPatternList, List<String> queryPatternList0) {


        List<String> normalquerylist = queryPatternList;

        List<String> susquerylist = queryPatternList0;

        for (int i = 0; i < susquerylist.size(); i++) {

            if (MAD(normalquerylist, susquerylist.get(i))) {
                return true;
            }

        }

        return false;
    }

    private boolean MAD(List<String> normalquerylist, String get) {

        boolean value = false;
        List<Vector> normalvectorlist = new ArrayList<Vector>();

        for (int i = 0; i < normalquerylist.size(); i++) {
            String query = normalquerylist.get(i);

            query = query.toLowerCase();

            if (query.startsWith("select")) {
                Vector a = covertQueryToVector(query);
                normalvectorlist.add(a);

            }

        }

        Vector sus = covertQueryToVector(get);



        return value;

    }

    private Vector covertQueryToVector(String get) {

        Vector a = new Vector();


        System.out.println(a);

        return a;

    }

    public static void main(String[] str) {

        String a = "select * from table where a = bbc";



    }

    private boolean newnewCompareQueryPattern() {

        boolean result = false;

//        for (int i = 0; i < this.GeneralProfile.getQueryPatternList().size(); i++) {
//            System.out.println(this.GeneralProfile.getQueryPatternList().get(i));
//
//        }
//
//        System.out.println("aaa");

        MAD m = new MAD();


        double medium = m.getMedian(GeneralProfile);
//System.out.println("aaa");
        double mad = m.generateMAD(GeneralProfile);

        int counter = 0;
//System.out.println("aaa");
        for (int i = 0; i < SuspectProfile.getQueryPatternList().size(); i++) {

            double value = MAD.convertUnity(QueryExtraction.extract(SuspectProfile.getQueryPatternList().get(i)));
            

            result = m.calculate(value, SuspectProfile, medium, mad);
            if(result){
                counter ++;
            } else {
                counter --;
            }
        }

        if(counter >0){
            result = true;
        }

        return result;
    }
}
