package FONTS.src.domini.model;
import FONTS.src.domini.model.*;

import java.util.ArrayList;

/** \brief Clase que implementa el cluster.
 *  @author Roberto Amat
 */
public class Cluster {

    /** Usuario que representa el centroide del clúster, siendo este
     * el usuario con distancias más pequeñas con los demás. */
    private User centroid;

    /** ArrayList que contine los usuarios que pertenecen al clúster
    *  @see User */
    private ArrayList<User> cluster;

    /** ArrayList que contiene la suma de distancias de un usuario con los demás.
     * Su longitud es el número de usuarios que forman parte del clúster. */
    private ArrayList<Float> sumDistances;

    /** Constructora de la clase. Inicializa los dos ArrayList vacios y el usuario
     * como uno por defecto. */
    public Cluster() {
        centroid = new User();
        cluster = new ArrayList<User>();
        sumDistances = new ArrayList<Float>();
    }

    /** Getter que retorna el centroide del clúster. */
    public User getcentroid() {
        return centroid;
    }

    /** Getter que retorna el el ArrayList de usuarios que
     * forman parte del clúster. */
    public ArrayList<User> getCluster() {
        return cluster;
    }

    /** Función que establece un usuario como centroide del clúster.
     * @param  centroid Usuario que va a pasar a ser el nuevo centroide del clúster. */
    public void setCentroid(User centroid) {
        if(cluster.contains(centroid)) {
            this.centroid = centroid;
        }
        else System.out.println("No se puede asignar " + centroid.getUserID() + " como centroide" +
                " dado que no forma parte del cluster.");
    }

    /** Función que añade un usuario al clúster.
     * @param user Usuario añadido al clúster.
     *  Una vez añadido, se añaden en las distancias de los demás su distancia respecto de éste,
     *  y viceversa. */
    public void addUser(User user) {
        if(cluster.contains(user)) System.out.println("El usuario " + user.getUserID() + " ya forma parte del cluster.");
        else {
            cluster.add(user);
            float f = 0;
            sumDistances.add(f);
            float d = 0;
            for(int i = 0; i < cluster.size(); ++i) {
                if(user != null) {
                    float actualDistance = user.calculateSimilarity(cluster.get(i));
                    d += actualDistance;
                    float d1 = sumDistances.get(i) + actualDistance;
                    sumDistances.set(i, d1);
                }
            }
            sumDistances.set(cluster.size() - 1, d);
        }
    }

     /** Borra un usuario del clúster.
     * @param user Usuario a borrar.
     * Se modifican todas las distancias borrando las que involucren al borrado.*/
    public void deleteUser(User user) {
        if(cluster.contains(user)) {
            for (int i = 0; i < cluster.size(); ++i) {
                float actualDistance = user.calculateSimilarity(cluster.get(i));
                float d1 = sumDistances.get(i) - actualDistance;
                sumDistances.set(i, d1);
            }
            int indexUserRemoved = cluster.indexOf(user);
            cluster.remove(user);
            sumDistances.remove(indexUserRemoved);
            user.setNumCluster(-1);
        }
        else System.out.println("El usuario: " + user.getUserID() + " no existe en el cluster.");
    }

    /** Función que recalcula el centroide del cluster, buscando aquel cuyas distancias con los
     * demás sean más pequeñas. */
    public void recalculateCentroid() {
        float sumMin = 100000;
        int iMin = 0;
        for(int i = 0; i < sumDistances.size(); ++i) {
            if(sumDistances.get(i) < sumMin) {
                sumMin = sumDistances.get(i);
                iMin = i;
            }
        }
        centroid = cluster.get(iMin);
    }

    /**Función que imprime todos los usuarios del clúster, especifiando
     * además cual de ellos es el centroide. */
    public void printCluster() {
        System.out.println("Centroide: " + centroid.getUserID());
        System.out.print("Usuarios:");
        boolean first = true;
        for (User user : cluster) {
            if(first) first = false;
            else System.out.print(", ");
            System.out.print(user.getUserID());
        }
        System.out.println("\n");
    }
}

