import java.util.ArrayList;
import static java.lang.Integer.max;
import static java.lang.Integer.min;

// @Author Marc Camarillas, Roberto Amat
public class Cluster {

    private User centroid;
    private ArrayList<User> cluster;
    private ArrayList<Float> sumDistances;

    // Auxiliares

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

    // Functions
    /*  
    *  Añades usuario
    *  Calculas la distancia de este usuario con los demás
    *  Sumas la distancia a cada usuario y al que añades
    * */
    public void addUser(User usuario) {
        cluster.add(usuario);
        float d = 0;
        for(int i = 0; i < cluster.size(); ++i) {
            float distanciaActual = usuario.calculaDistancias(cluster.get(i));
            d += distanciaActual;
            float d1 = sumDistances.get(i) + distanciaActual;
            sumDistances.set(i,d1);
        }
        sumDistances.add(d);
    }

    // Delete distancias
    public void deleteUser(User user) {
        for(int i = 0; i < cluster.size(); ++i) {
            float distanciaActual = user.calculaDistancias(cluster.get(i));
            float d1 = sumDistances.get(i) - distanciaActual;
            sumDistances.set(i,d1);
        }
        int indexUserRemoved = cluster.indexOf(user);
        cluster.remove(user);
        sumDistances.remove(indexUserRemoved);
        user.setNumCluster(-1);
    }

    public void recalculateCentroid() {
        float sumaMin = Float.POSITIVE_INFINITY;
        int iMin = 0;
        for(int i = 0; i < sumDistances.size(); ++i) {
            if(sumDistances.get(i) < sumaMin) {
                sumaMin = sumDistances.get(i);
                iMin = i;
            }
        }
        centroid = cluster.get(iMin);
    }
}

