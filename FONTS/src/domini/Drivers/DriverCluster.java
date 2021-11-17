package FONTS.src.domini.drivers;

import FONTS.src.domini.model.Cluster;
import FONTS.src.domini.model.User;

/** Driver de la Clase Cluster.
 *  @author Roberto Amat
 */
public class DriverCluster {



    /** Main del driver.
     *
     */
    public static void main(String[] args) {
        Cluster c = new Cluster();


        User u = new User(1);
        c.deleteUser(u);
    }

}
