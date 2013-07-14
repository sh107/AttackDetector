/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Profiling;

import attacktype.CacheProperties;
import communication.AccessDatabase;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijin
 */
public class CacheProfile extends Profile {

    private Timestamp end;
    private Timestamp begin;
    private List<CacheProperties> drn;
    private List<Integer> durationList;

    public List<CacheProperties> getDrn() {
        return drn;
    }

    public void setDrn(List<CacheProperties> drn) {
        this.drn = drn;
    }

    public List<Integer> getDurationList() {
        return durationList;
    }

    public void setDurationList(List<Integer> durationList) {
        this.durationList = durationList;
    }

    public CacheProfile(Timestamp begin, Timestamp end) {
        this.begin = begin;
        this.end = end;

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

    @Override
    public void SetupProfile() {
        try {
            AccessDatabase ad = new AccessDatabase();
            drn = ad.readCacheTable(begin, end);

            generateNormalDistribution();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateNormalDistribution() {
        this.durationList = evaluateDurationList();
    }

    private List<Integer> evaluateDurationList() {
        List<Integer> dlist = new ArrayList<Integer>();

        List<Integer> fullList = getDuration();

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

    private List<Integer> getDuration() {
        List<Integer> list = new ArrayList<Integer>();

        for (CacheProperties rp : drn) {

            int a = rp.getDuration();
            list.add(a);

        }

        return list;
    }



}
