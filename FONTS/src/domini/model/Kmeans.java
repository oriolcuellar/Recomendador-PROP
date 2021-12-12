package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/** \brief Clase que implementa el algoritmo Kmeans.
 *  @author Roberto Amat
 */
public class Kmeans {
    /** ArrayList que contiene todos los clusters.
     * @see Cluster
     */
    private ArrayList<Cluster> clusters;
    /** Mapa con el ID de un usuario como Key y dicho usuario como value.
     * Contiene todos los usuarios sobre los que se aplica el algoritmo.
     * @see User
     */
    private Map<Integer, User> users;
    /** Parámetro que determina el número de clusters que va a generar el algoritmo.
     */
    private int k;

    /** Función que crea aleatoriamente los k clusters iniciales con los que parte
     *  el algoritmo.
     */
    private void assignKCentroids(){
        Random myRandom = new Random();
        ArrayList<User> nuevoscentroids = new ArrayList<User>();
        int indiceNuevocentroid = 0;

        for(int i = 0; i < k; ++i) {
            User aux;
            Cluster c = new Cluster();
            ArrayList<Integer> keysAsArray = new ArrayList<Integer>(users.keySet());
            do{
                indiceNuevocentroid = myRandom.nextInt(users.size());
                aux = users.get(keysAsArray.get(myRandom.nextInt(keysAsArray.size())));
            }while (nuevoscentroids.contains(aux));
            c.addUser(aux);
            c.setCentroid(aux);
            clusters.add(c);
            nuevoscentroids.add(aux);
        }
    }


    /** Función que asigna un nuevo usuario al cluster con el centroide más
     * cercano a él.
     * @param user Usuario a asignar.
     */
    private void asignUserToCluster(User user) {
        float dMin = 0;
        int iMin = 0;
        for(int i = 0; i < k; ++i) {
            User centroidActual = clusters.get(i).getcentroid();
            float distanciaActual = user.calculateSimilarity(centroidActual);
            //if(user.getUserID() == clusters.get(i).getCluster().get(0).getUserID()) distanciaActual = 5; // para que el centroide se añada en su propio cluster
            //System.out.println("Distancia entre " + user.getUserID() + " " + centroidActual.getUserID() + " es " + distanciaActual);
            if(distanciaActual > dMin) {
                dMin = distanciaActual;
                iMin = i;
            }
        }
        user.setNumCluster(iMin);
        clusters.get(iMin).addUser(user);
    }

    /** Función que ejecuta el algoritmo.
     * @param k Numero de clusters que tendra el algoritmo.
     */
    public void run(Map <Integer, User> users, int k) {
        this.k = k;
        clusters = new ArrayList<Cluster>();
        this.users = users;
        if(k > users.size() && users.size() != 0) System.out.println("El numero de clusters tiene que ser menor al numero de usuarios.");
        else if(k == 0) System.out.println("K tiene que ser un numero mayor que cero.");


            assignKCentroids();
            // Assignas el resto de usuarios a los clusters correspondientes y recalculamos el centroid
            int i = 0;
            while(i < 4) {
                for(int j = 0; j < k; ++j) {
                    clusters.get(j).getCluster().removeAll(clusters.get(j).getCluster());
                    clusters.get(j).clearSumDistances();
                }

                for (Map.Entry<Integer, User> entry : users.entrySet()) {
                    asignUserToCluster(entry.getValue());
                }
                ++i;
            printAllClusters();
            }


    }

    /** Constructora de la clase.
     */
    public Kmeans() {

    }

    /** Función que devuelve los clusters.
     * @return Clusters generados por el algoritmo.
     */
    public ArrayList<Cluster> getClusters() {
        return clusters;
    }


    /** Función que imprime todos los clusters generados por el algoritmo.
     * @see Cluster
     */
    public void printAllClusters() {
        for (int i = 0; i < clusters.size(); ++i) {
            System.out.println("Cluster "+i+", formado por " + clusters.get(i).getCluster().size() + " usuarios.\n");
            clusters.get(i).printCluster();
            System.out.println("\n============================================================================================================\n");
        }
    }
}
