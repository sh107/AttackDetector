/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package support;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijin
 */
public class Main {

  public static void main(String[] args) {

      if(args[0].equalsIgnoreCase("database")){
            try {
                RunDatabaseDetection.run();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

      if(args[0].equalsIgnoreCase("network")){
            try {
                RunNetworkDetection.run();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

      if(args[0].equalsIgnoreCase("cache")){
            try {
                RunCacheDetection.run();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

  }

}
