/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Profiling;

import attacktype.NetworkProperties;
import communication.AccessDatabase;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijin
 */
public class NetworkProfile extends Profile {

    private Timestamp end;
    private Timestamp begin;
    private List<NetworkProperties> drn;
    private List<Long> timeIntervalList;
    private List<Double> dataInputList;
    private String source;
    private static final String[] COUNTRIES = new String[]{
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
        "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
        "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
        "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
        "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
        "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
        "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
        "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",
        "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
        "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
        "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
        "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
        "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
        "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
        "Former Yugoslav Republic of Macedonia", "France", "French Guiana", "French Polynesia",
        "French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar",
        "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
        "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras", "Hong Kong", "Hungary",
        "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
        "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
        "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
        "Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
        "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova",
        "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
        "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand",
        "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Marianas",
        "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru",
        "Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
        "Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe", "Saint Helena",
        "Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon",
        "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal",
        "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
        "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Korea",
        "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden",
        "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Bahamas",
        "The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
        "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
        "Ukraine", "United Arab Emirates", "United Kingdom",
        "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
        "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara",
        "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"
    };

    public static List<String> locationList() {

        List<String> list = new ArrayList();

        for (int i = 0; i < COUNTRIES.length; i++) {
            list.add(COUNTRIES[i].toLowerCase());

        }

        return list;
    }

    public List<Double> getDataInputList() {
        return dataInputList;
    }

    public void setDataInputList(List<Double> dataInputList) {
        this.dataInputList = dataInputList;
    }

    public List<NetworkProperties> getDrn() {
        return drn;
    }

    public void setDrn(List<NetworkProperties> drn) {
        this.drn = drn;
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

    public NetworkProfile(Timestamp begin, Timestamp end, String source) {
        this.begin = begin;
        this.end = end;
        this.source = source;
    }

    @Override
    public void SetupProfile() {
        try {
            AccessDatabase ad = new AccessDatabase();
            drn = ad.readNetworkTable(begin, end, source);

            generateNormalDistribution();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateNormalDistribution() {


        this.timeIntervalList = evaluateTimeIntervalList();
        this.dataInputList = evaluateHitRateList();


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
        List<Long> list = new ArrayList<Long>();

        for (NetworkProperties rp : drn) {

            Long a = rp.getTimeInterval();
            list.add(a);


        }

        return list;
    }

    private List<Double> evaluateHitRateList() {
        List<Double> dlist = new ArrayList<Double>();

        List<Double> fullList = getHitRate();

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

    private List<Double> getHitRate() {
        List<Double> list = new ArrayList<Double>();
        int period = 10; //seconds

        Timestamp start = null;

        List<NetworkProperties> alist = ProcessRequest(drn);

        //System.out.println("aa");
        for (int i = 0; i < alist.size(); i++) {

            Double hitrate = 0.0;

            List<NetworkProperties> templist = new ArrayList<NetworkProperties>();

            start = alist.get(i).getTime();

            while ((start.getTime() + period * 1000) > drn.get(i).getTime().getTime()) {
                templist.add(alist.get(i));
                i++;

                if (i == alist.size()) {
                    break;
                }
            }


            //  System.out.println("hit " + getHit(templist));
            // System.out.println("total " + getTotalRequest(templist));
            if (getTotalRequest(templist) == 0) {
                hitrate = 0.0;
            } else {
                hitrate = getHit(templist) / (double) getTotalRequest(templist);

            }
         //  System.out.println(hitrate);
            list.add(hitrate);
        }

        return list;
    }

    private int getTotalRequest(List<NetworkProperties> templist) {

        int count = 0;

        for (int i = 0; i < templist.size(); i++) {
            try {
                String url = templist.get(i).getUri();

                URL u = new URL("http://www.abc.com" + url);
                String query = u.getQuery();

                if (query == null) {
                    continue;
                }

                Map<String, String> map = getQueryMap(query);
                Set<String> keys = map.keySet();

                for (String key : keys) {
                    if (key.equalsIgnoreCase("term")) {
                        count++;
                    }
                }


            } catch (MalformedURLException ex) {
                Logger.getLogger(NetworkProfile.class.getName()).log(Level.SEVERE, null, ex);
            }


        }

        return count;

    }

    private double getHit(List<NetworkProperties> templist) {

        int hit = 0;

        for (int i = 0; i < templist.size(); i++) {
            try {
                String url = templist.get(i).getUri();
                URL u = new URL("http://www.abc.com" + url);
                String query = u.getQuery();

                if (query == null) {
                    continue;
                }

                Map<String, String> map = getQueryMap(query);
                Set<String> keys = map.keySet();

                //System.out.println("ss");
                for (String key : keys) {
                    if (key.equalsIgnoreCase("term")) {
                        //  System.out.println(map.get(key));
                        List<String> blist = NetworkProfile.locationList();

                        for (int j = 0; j < blist.size(); j++) {

                            //              System.out.println(blist.get(j));
                            //               System.out.println(map.get(key).toLowerCase());
                            if (blist.get(j).startsWith(map.get(key).toLowerCase())) {
                                hit++;
                                break;
                            }

                        }
                    }
                }


            } catch (MalformedURLException ex) {
                Logger.getLogger(NetworkProfile.class.getName()).log(Level.SEVERE, null, ex);
            }


        }

        return (double) hit;
    }

    public static Map<String, String> getQueryMap(String query) {


        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();

        for (String param : params) {
            String name = param.split("=")[0];

            String value = null;
            if (param.split("=").length > 1) {
                value = param.split("=")[1];

            } else {
                value = "";
            }

          //  System.out.println(value);
            map.put(name, value);
        }
        return map;
    }

    private List<NetworkProperties> ProcessRequest(List<NetworkProperties> drn) {
        List<NetworkProperties> list = new ArrayList<NetworkProperties>();

        for (int i = 0; i < drn.size(); i++) {
            try {
                String url = drn.get(i).getUri();
                //    System.out.println(url);
                URL u = new URL("http://www.abc.com" + url);
                String query = u.getQuery();

                if (query == null) {
                    continue;
                }

                Map<String, String> map = getQueryMap(query);
                Set<String> keys = map.keySet();

                for (String key : keys) {
                    if (key.equalsIgnoreCase("term")) {
                        //  System.out.println(drn.get(i).getUri());
                        list.add(drn.get(i));
                    }
                }


            } catch (MalformedURLException ex) {
                Logger.getLogger(NetworkProfile.class.getName()).log(Level.SEVERE, null, ex);
            }


        }

        return list;
    }
}
