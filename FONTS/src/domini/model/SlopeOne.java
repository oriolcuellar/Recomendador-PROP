package FONTS.src.domini.model;

import FONTS.src.domini.model.*;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;


// Hay que hacer una excepción para comprobar que existe el user
// @Author Marc Camarillas
public class SlopeOne {
    private User user;
    private float maxValue;
    private Map<Integer,ArrayList<User>> itemValoratedBy;
    private ArrayList<myPair> predictions;
    private Map<Integer,User> usersList;

    private ArrayList<User> intersection(ArrayList<User> l1, ArrayList<User> l2, int numCluster) {
        ArrayList<User> l3 = new ArrayList<User>();
        for (User user1 : l1) {
            if (user1.getNumCluster() == numCluster) {
                for (User user2 : l2) {
                    if (user1.getUserID() == user2.getUserID() && user2.getNumCluster() == numCluster) {
                        l3.add(user1);
                    }
                }
            }
        }
        return l3;
    }

    private float calculateDesviation(int IDitemI, int IDitemJ, ArrayList<User> usersIJ) {
        float sumTotal = 0;
        for (User user : usersIJ) {
            /*System.out.println("ID user " + user.getUserID());
            System.out.println("ID J " + user.searchUsedItem(IDitemJ).getItem().getID() + " " + IDitemJ +
                    " ID I " + IDitemI +  " " + user.searchUsedItem(IDitemI).getItem().getID());*/
            float valorationUserI = user.searchUsedItem(IDitemI).getValoracio();
            float valorationUserJ = user.searchUsedItem(IDitemJ).getValoracio();
            /*System.out.println("Valoración J " + valorationUserJ);
            System.out.println("Valoración I " + valorationUserI);*/

            sumTotal += (valorationUserJ - valorationUserI);
        }
        //System.out.println("SUMA: " + sumTotal + " Tamaño: " + usersIJ.size());
        return sumTotal/usersIJ.size();
    }

    private float calculateValorationMean(User user) {
        ArrayList<valoratedItem> usedItems = user.getItemsUsats();
        float sum = 0;
        for(valoratedItem u: usedItems) sum += u.getValoracio();
        return sum/usedItems.size();
    }

    // suma media de las desviaciones de todos los items I respecto del item J, ambos puntuados por el usuario user
    private float calculateDesviationMean(User user, int IDitemJ) {
        ArrayList<valoratedItem> usedItems = user.getItemsUsats();
        float num = 0;
        int count = 0;
        for(valoratedItem itemI : usedItems) {
            int IDitemI =  itemI.getItem().getID();
            if(IDitemI != IDitemJ) {
                ArrayList<User> usersIJ = getIntersaction(IDitemI,IDitemJ);
                if(usersIJ.size() != 0) {
                    num += calculateDesviation(IDitemI, IDitemJ, usersIJ);
                    count++;
                }
            }
        }
        if(count == 0) return Float.NEGATIVE_INFINITY;
        return num/count;
    }

    private ArrayList<User> getIntersaction(int IDitemI, int IDitemJ) {
        ArrayList<User> usersI = itemValoratedBy.get(IDitemI);
        ArrayList<User> usersJ = itemValoratedBy.get(IDitemJ);

       return intersection(usersI, usersJ, user.getNumCluster());
    }

    private void slopeOneAlgorithm(User user) {
        float meanValoration = calculateValorationMean(user);

        this.predictions = new ArrayList<myPair>();
        //predecir todos los que no tiene valoracion
        for (Map.Entry<Integer, ArrayList<User>> item : itemValoratedBy.entrySet()) {
            //si el item no esta valorado por el usuario ejecutar predicción
            if (!item.getValue().contains(user)) {
                float valoration = min(maxValue, meanValoration + calculateDesviationMean(user, item.getKey()));

                /* si tuvieramos el item
                valoratedItem iu = new valoratedItem(item, user, valoration);
                user.addvaloratedItem(iu);
                 */
                predictions.add(new myPair(item.getKey(), max(0, valoration)));
            }
        }
    }

    public void printResults() {
        for (myPair prediction : predictions) {
            System.out.println("Valoracion estimada para el item " + prediction.getItemID() +
                    ": " + prediction.getValoration());
        }
    }

    public SlopeOne(Map<Integer,ArrayList<User>> itemValoratedBy, Map<Integer,User> usersList, float maxValue) {
        this.maxValue = maxValue;
        this.itemValoratedBy = itemValoratedBy;
        this.usersList = usersList;
        this.predictions = new ArrayList<>();
    }

    //retorna las predicciones para el usuario u
    public ArrayList<myPair> getPredictions(User user){
        this.user = user;

        try {
            slopeOneAlgorithm(user);
            quicksort(predictions, 0, predictions.size() - 1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return predictions;
    }

    public static void quicksort(ArrayList<myPair> A, int izq, int der) {

        float pivote = A.get(izq).getValoration();
        int pivoteID = A.get(izq).getItemID();
        int i=izq;
        int j=der;
        float auxV;
        int auxID;
        while(i < j){
            while( A.get(i).getValoration() >= pivote && i < j) i++;
            while( A.get(j).getValoration() < pivote) j--;
            if (i < j) {
                auxV = A.get(i).getValoration();
                auxID = A.get(i).getItemID();
                myPair mp = new myPair(A.get(j).getItemID(),A.get(j).getValoration());
                A.set(i,mp);
                myPair mp1 = new myPair(auxID,auxV);
                A.set(j,mp1);
            }
        }
        myPair mp = new myPair(A.get(j).getItemID(),A.get(j).getValoration());
        A.set(izq,mp);
        myPair piv = new myPair(pivoteID,pivote);
        A.set(j,piv);

        if(izq < j-1)
            quicksort(A,izq,j-1);
        if(j+1 < der)
            quicksort(A,j+1,der);
    }
}
