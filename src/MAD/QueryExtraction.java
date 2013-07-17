/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MAD;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Sijin
 */
public class QueryExtraction {

    public static int splitProjection1(String a) {

      //  a = a.trim();
        String[] ss = a.split("from");

 //       System.out.println(a);
   //     System.out.println(ss[1]);
        return QueryExtraction.splitIntoProjection1(ss[0], ss[1].trim());


    }

    public static int splitProjection2(String a) {

        String[] ss = a.split("from");

        // System.out.println(ss[1]);
        return QueryExtraction.splitIntoProjection2(ss[1]);

    }

    public static int splitIntoProjection1(String a, String b) {
        String[] ss = a.split(" ");

        //  System.out.println(b);
        if (ss[1].endsWith("*")) {

            return splitIntoProjection3(b);


        }

        //   System.out.println(ss.length - 1);
        return ss.length - 1;

    }

    public static int splitIntoProjection2(String a) {
        String[] ss = a.split(" ");

        return ss.length - 1;

    }

    public static int splitIntoSelection2(String a) {
        String[] ss = a.split(" ");

        return ss.length;

    }

    public static int splitSelection(String a) {

        a = a.trim();
        String[] ss = a.split("between");

        return splitIntoSelection2(ss[0]);

    }

    public static List<Integer> extract(String query) {
        String q = query;

        List<Integer> al = new ArrayList<Integer>();
        if (q.startsWith("select")) {
            if (q.toLowerCase().contains("where".toLowerCase())) {
                String[] ss = q.split("where");

                //  System.out.println(QueryExtraction.splitProjection1(ss[0]));
                al.add(QueryExtraction.splitProjection1(ss[0]));
                al.add(QueryExtraction.splitProjection2(ss[0]));
                al.add(QueryExtraction.splitSelection(ss[1]));
                al.add(QueryExtraction.splitSelection(ss[1]));
            } else if (q.toLowerCase().contains("join".toLowerCase())) {
                String[] ss = q.split("join");

                //  System.out.println(QueryExtraction.splitProjection1(ss[0]));
                al.add(QueryExtraction.splitProjection1(ss[0]));
                al.add(QueryExtraction.splitProjection2(ss[0]));
                al.add(QueryExtraction.splitSelection(ss[1]));
                al.add(QueryExtraction.splitSelection(ss[1]));
            } else {
               

                //  System.out.println(QueryExtraction.splitProjection1(ss[0]));
                al.add(QueryExtraction.splitProjection1(q));
                al.add(QueryExtraction.splitProjection2(q));
                al.add(0);
                al.add(0);
            }


//            for (int i = 0; i < al.size(); i++) {
//                System.out.println(al.get(i));
//
//            }

        }

        return al;
    }

    public static void main(String[] args) {

        System.out.println(MAD.convertUnity(QueryExtraction.extract("select * from `reddragon`.`users`")));

    }

    private static int splitIntoProjection3(String b) {
        String[] ss = b.split(" ");

        return TableColumn.getEntry(ss[0]);

    }
}
