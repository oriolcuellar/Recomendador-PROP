package src.domini.model;

import src.domini.model.*;
import java.util.ArrayList;
import static java.lang.Integer.max;
import static java.lang.Integer.min;

// @Author Marc Camarillas, Roberto Amat
public class Cluster {
    // Atributes
    private User centroid;
    /* Contine los usuarios que pertenecen al clúster */
    private ArrayList<User> cluster;
    /* Contiene la suma de distancias de un usuario con los demás */
    private ArrayList<Float> sumDistances;

    // Constructora
    public Cluster() {
        centroid = new User();
        cluster = new ArrayList<User>();
        sumDistances = new ArrayList<Float>();
    }

    // Getters
    public User getcentroid() {
        return centroid;
    }
    public ArrayList<User> getCluster() {
        return cluster;
    }

    // Setters
    public void setCentroid(User centroid) {
        this.centroid = centroid;
    }

    public void setCluster(ArrayList<User> cluster) {
        this.cluster = cluster;
    }

    // Modifiers
    /*  
       - Añades usuario
       - Calculas la distancia de este usuario con los demás
       - Sumas la distancia a cada usuario y al que añades
    */
    public void addUser(User user) {
        cluster.add(user);
        float f = 0;
        sumDistances.add(f);
        float d = 0;
        for(int i = 0; i < cluster.size(); ++i) {
            float actualDistance = user.calculateDistances(cluster.get(i));
            d += actualDistance;
            float d1 = sumDistances.get(i) + actualDistance;
            sumDistances.set(i,d1);
        }
        sumDistances.set(cluster.size() - 1, d);
    }

    /*
       - Calculas la distancia de este usuario con los demás
       - Restas la distancia a cada usuario
       - Eliminas el usuario
    */
    public void deleteUser(User user) {
        for(int i = 0; i < cluster.size(); ++i) {
            float actualDistance = user.calculateDistances(cluster.get(i));
            float d1 = sumDistances.get(i) - actualDistance;
            sumDistances.set(i,d1);
        }
        int indexUserRemoved = cluster.indexOf(user);
        cluster.remove(user);
        sumDistances.remove(indexUserRemoved);
        user.setNumCluster(-1);
    }

    /*
        Dentro de cada clúster el centroide nuevo será aquel cuya suma de distancias
        con otros usuarios sea menor
     */
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

    // Print
    public void printCluster() {
        System.out.println("CENTROID: " + centroid.getUserID());
        for(int i = 0; i < cluster.size(); ++i) {
            System.out.println("USER: " + cluster.get(i).getUserID());
        }
    }
}

