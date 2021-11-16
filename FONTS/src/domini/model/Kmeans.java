package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


// @Author Roberto Amat, Marc Camarillas
public class Kmeans {
    // Atributes
    /* Lista de todos los clústers*/
    private ArrayList<Cluster> clusters;
    /* Lista de todos los usuarios */
    private Map<Integer, User> users;
    private int k;

    // Auxiliares

    /*
    Asignar k centroids random sin que se repitan
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
            c.setCentroid(aux);
            c.addUser(aux);
            clusters.add(c);
            nuevoscentroids.add(aux);
            System.out.println("CENTROIDE INICIAL: " + clusters.get(i).getcentroid().getUserID());
        }
    }
    /*
        Asigna un usuario al centroide de menos distancia
     */
    private void asignUserToCluster(User user) {
        float dMin = Float.POSITIVE_INFINITY;
        int iMin = 0;
        for(int i = 0; i < k; ++i) {
            User centroidActual = clusters.get(i).getcentroid();
            float distanciaActual = user.calculateDistances(centroidActual);
            if(distanciaActual < dMin) {
                dMin = distanciaActual;
                iMin = i;
            }
        }
        user.setNumCluster(iMin);
        clusters.get(iMin).addUser(user);
        clusters.get(iMin).recalculateCentroid();
    }

    /*
        Elimina un usuario del cluster al que pertenece
     */
    private void deleteUserFromCluster(User user) {
        int indexCluster = user.getNumCluster();
        clusters.get(indexCluster).deleteUser(user);
        clusters.get(indexCluster).recalculateCentroid();

    }

    // Constructora
    /*
        Crea un conjunto con k clústers
     */
    public Kmeans(int k, Map <Integer, User> u) {
        this.k = k;
        clusters = new ArrayList<Cluster>();
        users = u;
        // Asignar k centroids random sin que se repitan
        assignKCentroids();
        // Assignas el resto de usuarios a los clusters correspondientes y recalculamos el centroid
        for(Map.Entry<Integer, User> entry : users.entrySet()) {
            asignUserToCluster(entry.getValue());
        }
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }

    // Functions

    public void newValoration(User user) {
        deleteUserFromCluster(user);
        asignUserToCluster(user);
    }

    // PRINTS

    public void printAllClusters() {
        for(int i = 0; i < clusters.size(); ++i) {
            clusters.get(i).printCluster();
        }
    }
}