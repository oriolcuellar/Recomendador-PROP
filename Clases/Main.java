import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
       Usuarios u = new Usuarios();
       LectorCSV lectorCSV = new LectorCSV(u);
       lectorCSV.Lector("Entradas_CSV/ratings.db.csv", "Ratings");

       ArrayList<User> us = u.getUsuarios();
       /*
       for(int i = 0; i < us.size(); ++i) {
           System.out.println("Usuario " + us.get(i).getUserID() + " " + us.get(i).getItemsUsats().size());
           for(int j = 0; j < us.get(i).getItemsUsats().size(); ++j) {
               System.out.println(us.get(i).getItemsUsats().get(j).getItem().getID() + " " +us.get(i).getItemsUsats().get(j).getValoracio());
           }
       }
       */
       Kmeans k = new Kmeans(6, us);
       k.printAllClusters();
    }
}