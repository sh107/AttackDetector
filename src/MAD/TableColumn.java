/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MAD;

/**
 *
 * @author Sijin
 */
public class TableColumn {

    //more can be added from here
    public final static int VMStatistics = 20;
    public final static int volume = 22;
    public final static int ippool = 8;
    public final static int vms = 22;
    public final static int logins = 8;
    public final static int privateippool = 4;
    public final static int users = 21;

    public static int getEntry(String name) {

        int b = 10;

        if (name.equalsIgnoreCase("`reddragon`.`vmstatistics`")) {

            b = VMStatistics;

        }

        if (name.equalsIgnoreCase("`reddragon`.`volume`")) {

            b = volume;

        }

        if (name.equalsIgnoreCase("`reddragon`.`ippool`")) {

            b = ippool;

        }

        if (name.equalsIgnoreCase("`reddragon`.`vms`")) {

            b = vms;

        }

        if (name.equalsIgnoreCase("`reddragon`.`logins`")) {

            b = logins;

        }
        if (name.equalsIgnoreCase("`reddragon`.`privateippool`")) {

            b = privateippool;

        }

        if (name.equalsIgnoreCase("`reddragon`.`users`")) {

            b = users;

        }

        return b;


    }
}
