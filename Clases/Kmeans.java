import java.util.ArrayList;
import java.util.Random;

public class Kmeans {
    private ArrayList<Cluster> clusters;
    private ArrayList<User> users;
    private int k;

    // Auxiliares

    // Asignar k centroids random sin que se repitan
    private void assignKCentroids(){
        Random myRandom = new Random();
        ArrayList<User> nuevoscentroids = new ArrayList<User>();
        int indiceNuevocentroid = 0;

        for(int i = 0; i < k; ++i) {
            User aux;
            Cluster c = new Cluster();
            do{
                indiceNuevocentroid = myRandom.nextInt(users.size() - 1);
                aux = users.get(indiceNuevocentroid);
            }while (nuevoscentroids.contains(aux));
            c.setCentroid(aux);
            c.addUser(aux);
            clusters.add(c);
            nuevoscentroids.add(aux);
        }
    }

    private void asignaUsuarioACluster(User usuario) {
        float dMin = Float.POSITIVE_INFINITY;
        int iMin = 0;
        for(int i = 0; i < k; ++i) {
            User centroidActual = clusters.get(i).getcentroid();
            float distanciaActual = usuario.calculaDistancias(centroidActual);
            if(distanciaActual < dMin) {
                dMin = distanciaActual;
                iMin = i;
            }
        }
        usuario.setNumCluster(iMin);
        clusters.get(iMin).addUser(usuario);
        clusters.get(iMin).recalculateCentroid();
    }

    private void deleteUserFromCluster(User user) {
        int indexCluster = user.getNumCluster();
        clusters.get(indexCluster).deleteUser(user);
        clusters.get(indexCluster).recalculateCentroid();

    }

    // Constructora
    public Kmeans(int k) {
        this.k = k;
        clusters = new ArrayList<Cluster>();
        Usuarios u = new Usuarios();
        users = u.getUsuarios();
        // Asignar k centroids random sin que se repitan
        assignKCentroids();
        // Assignas el resto de usuarios a los clusters correspondientes y recalculamos el centroid
        for(int i  = 0; i < users.size(); ++i) {
            asignaUsuarioACluster(users.get(i));
        }
    }

    // Functions
    public void newValoration(User user) {
        deleteUserFromCluster(user);
        asignaUsuarioACluster(user);
    }
}
