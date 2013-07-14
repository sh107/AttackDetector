/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Profiling;

import attacktype.DatabaseProperties;
import attacktype.ResourceProperties;
import communication.AccessDatabase;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijin
 */
public class DatabaseProfile extends Profile {

    public static int numberOfData = 30;
    private Timestamp end;
    private Timestamp begin;
    private String dbuser;
    private List<Double> dbCPUList;
    private List<Long> dbMemoryList;
    private List<Double> machineCPUList;
    private List<Long> machineMemoryList;
    private List<Double> responseTimeList;
    private List<Integer> dataSizeList;
    private List<Long> timeIntervalList;
    private List<String> queryPatternList;
    private List<DatabaseProperties> dpl;
    private List<ResourceProperties> drl;


    public DatabaseProfile(Timestamp begin, Timestamp end, String dbuser) {
        this.begin = begin;
        this.end = end;
        this.dbuser = dbuser;
    }



    public void SetupProfile() {
        try {
            AccessDatabase ad = new AccessDatabase();
            dpl = ad.readDatabaseTable(begin, end, dbuser);
            drl = ad.readResourceTable(begin, end);
            generateNormalDistribution();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<DatabaseProperties> getDpl() {
        return dpl;
    }

    public void setDpl(List<DatabaseProperties> dpl) {
        this.dpl = dpl;
    }

    public List<ResourceProperties> getDrl() {
        return drl;
    }

    public void setDrl(List<ResourceProperties> drl) {
        this.drl = drl;
    }

    public static int getNumberOfData() {
        return numberOfData;
    }

    public static void setNumberOfData(int numberOfData) {
        DatabaseProfile.numberOfData = numberOfData;
    }

    public List<String> getQueryPatternList() {
        return queryPatternList;
    }

    public void setQueryPatternList(List<String> queryPatternList) {
        this.queryPatternList = queryPatternList;
    }

    public List<Integer> getDataSizeList() {
        return dataSizeList;
    }

    public void setDataSizeList(List<Integer> dataSizeList) {
        this.dataSizeList = dataSizeList;
    }

    public List<Double> getResponseTimeList() {
        return responseTimeList;
    }

    public void setResponseTimeList(List<Double> responseTimeList) {
        this.responseTimeList = responseTimeList;
    }

    public List<Long> getTimeIntervalList() {
        return timeIntervalList;
    }

    public void setTimeIntervalList(List<Long> timeIntervalList) {
        this.timeIntervalList = timeIntervalList;
    }

    public Timestamp getBegin() {
        return begin;
    }

    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public List<Double> getDbCPUList() {
        return dbCPUList;
    }

    public void setDbCPUList(List<Double> dbCPUList) {
        this.dbCPUList = dbCPUList;
    }

    public List<Long> getDbMemoryList() {
        return dbMemoryList;
    }

    public void setDbMemoryList(List<Long> dbMemoryList) {
        this.dbMemoryList = dbMemoryList;
    }

    public List<Double> getMachineCPUList() {
        return machineCPUList;
    }

    public void setMachineCPUList(List<Double> machineCPUList) {
        this.machineCPUList = machineCPUList;
    }

    public List<Long> getMachineMemoryList() {
        return machineMemoryList;
    }

    public void setMachineMemoryList(List<Long> machineMemoryList) {
        this.machineMemoryList = machineMemoryList;
    }

    private void generateNormalDistribution() {

        this.queryPatternList = evaluateQueryPattern();
//        this.dbCPUList = evaluateDatabaseCPUList();
//        this.dbMemoryList = evaluateDatabaseMemoryList();
//        this.machineCPUList = evaluateMachineCPUList();
//        this.machineMemoryList = evaluateMachineMemoryList();
        this.dataSizeList = evaluateDataSizeList();
        this.responseTimeList = evaluateResponseTimeList();
        this.timeIntervalList = evaluateTimeIntervalList();

    }

    private List<Double> getDatabaseCPU() {

        List<Double> list = new ArrayList();

        for (ResourceProperties rp : drl) {
            Double a = rp.getDatabaseCPU();
            list.add(a);
        }

        return list;
    }

    private List<Double> selectRandomlyDouble(List<Double> fullList) {
        List<Double> list = new ArrayList();
        int numberData = 60;
        int size = fullList.size();

        for (int i = 0; i < size; i++) {
            list.add(fullList.get(i));
        }

        while (list.size() > numberData) {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(list.size());
            list.remove(randomInt);
        }

        return list;
    }

    private List<Long> selectRandomlyLong(List<Long> fullList) {
        List<Long> list = new ArrayList();
        int numberData = 60;
        int size = fullList.size();

        for (int i = 0; i < size; i++) {
            list.add(fullList.get(i));
        }

        while (list.size() > numberData) {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(list.size());
            list.remove(randomInt);
        }

        return list;
    }

    private Double averageDouble(List<Double> randomList) {
        Double a = 0.0;

        for (int j = 0; j < randomList.size(); j++) {
            a += randomList.get(j);
        }

        Double ave = (Double) a / randomList.size();

        return ave;

    }

    private Long averageLong(List<Long> randomList) {
        Long a = 0L;
        Long ave = 0L;
        for (int j = 0; j < randomList.size(); j++) {
            a += randomList.get(j);
        }

        if (randomList.size() != 0) {
            ave = a / randomList.size();
        }

        return ave;

    }

    private List<Double> evaluateDatabaseCPUList() {
        List<Double> dlist = new ArrayList<Double>();

        List<Double> fullList = getDatabaseCPU();

        for (int i = 0; i < numberOfData; i++) {

            List<Double> randomList = selectRandomlyDouble(fullList);

            Double ave = averageDouble(randomList);

            dlist.add(ave);
        }

        return dlist;
    }

    private List<Long> evaluateDatabaseMemoryList() {
        List<Long> dlist = new ArrayList<Long>();

        List<Long> fullList = getDatabaseMemory();

        for (int i = 0; i < numberOfData; i++) {

            List<Long> randomList = selectRandomlyLong(fullList);

            Long ave = averageLong(randomList);

            dlist.add(ave);
        }

        return dlist;
    }

    private List<Double> evaluateMachineCPUList() {
        List<Double> dlist = new ArrayList<Double>();

        List<Double> fullList = getMachineCPU();

        for (int i = 0; i < numberOfData; i++) {

            List<Double> randomList = selectRandomlyDouble(fullList);

            Double ave = averageDouble(randomList);

            dlist.add(ave);
        }

        return dlist;
    }

    private List<Long> evaluateMachineMemoryList() {
        List<Long> dlist = new ArrayList<Long>();

        List<Long> fullList = getMachineMemory();

        for (int i = 0; i < numberOfData; i++) {

            List<Long> randomList = selectRandomlyLong(fullList);

            Long ave = averageLong(randomList);

            dlist.add(ave);
        }

        return dlist;
    }

    private List<Double> getMachineCPU() {
        List<Double> list = new ArrayList();

        for (ResourceProperties rp : drl) {
            Double a = rp.getMachineCPU();
            list.add(a);
        }

        return list;
    }

    private List<Long> getDatabaseMemory() {
        List<Long> list = new ArrayList();

        for (ResourceProperties rp : drl) {
            Long a = rp.getDatabaseMemory();
            list.add(a);
        }

        return list;
    }

    private List<Long> getMachineMemory() {
        List<Long> list = new ArrayList();

        for (ResourceProperties rp : drl) {
            Long a = rp.getMachineUsedMemory();
            list.add(a);
        }

        return list;
    }

    private List<Integer> evaluateDataSizeList() {
        List<Integer> dlist = new ArrayList<Integer>();

        List<Integer> fullList = getDataSize();

        dlist = fullList;
//        for (int i = 0; i < numberOfData; i++) {
//
//            List<Integer> randomList = selectRandomlyInteger(fullList);
//
//            Integer ave = averageInteger(randomList);
//
//            dlist.add(ave);
//        }
//
//

        return dlist;
    }

    private List<Double> evaluateResponseTimeList() {
        List<Double> dlist = new ArrayList<Double>();

        List<Double> fullList = getResponseTime();

        dlist = fullList;
//        for (int i = 0; i < numberOfData; i++) {
//
//            List<Double> randomList = selectRandomlyDouble(fullList);
//
//            Double ave = averageDouble(randomList);
//
//            dlist.add(ave);
//        }

        return dlist;
    }

    private List<Long> evaluateTimeIntervalList() {
        List<Long> dlist = new ArrayList<Long>();

        List<Long> fullList = getTimeInterval();

        dlist = fullList;
//        for (int i = 0; i < numberOfData; i++) {
//
//            List<Long> randomList = selectRandomlyLong(fullList);
//
//            Long ave = averageLong(randomList);
//
//            dlist.add(ave);
//        }

        return dlist;
    }

    private List<Long> getTimeInterval() {
        List<Long> list = new ArrayList();

        for (DatabaseProperties rp : dpl) {


            Long a = rp.getTimeInterval();
            list.add(a);


        }

        return list;
    }

    private List<Double> getResponseTime() {
        List<Double> list = new ArrayList();

        for (DatabaseProperties rp : dpl) {

            double a = rp.getResponseTime();


            list.add(a);


        }

        return list;
    }

    private List<Integer> getDataSize() {
        List<Integer> list = new ArrayList();

        for (DatabaseProperties rp : dpl) {


            int a = rp.getDataSize();
            list.add(a);


        }

        return list;
    }

    private List<Integer> selectRandomlyInteger(List<Integer> fullList) {
        List<Integer> list = new ArrayList();
        int numberData = 60;
        int size = fullList.size();

        for (int i = 0; i < size; i++) {
            list.add(fullList.get(i));
        }

        while (list.size() > numberData) {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(list.size());
            list.remove(randomInt);
        }

        return list;
    }

    public Integer averageInteger(List<Integer> randomList) {
        Integer a = 0;
        Integer ave = 0;

        for (int j = 0; j < randomList.size(); j++) {
            a += randomList.get(j);
        }

        if (!randomList.isEmpty()) {
            ave = a / randomList.size();
        }

        return ave;
    }

    private List<String> evaluateQueryPattern() {
        List<String> dlist = getDatabaseQuery();


        return dlist;
    }

    private List<String> getDatabaseQuery() {
        List<String> list = new ArrayList();

        for (DatabaseProperties rp : dpl) {

            String a = rp.getSql();


            list.add(a);


        }

        return list;
    }
}
