package src.domini.model;
import src.domini.controladores.CtrlDomini;
import src.domini.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.lang.Math.max;

public class SlopeOne {
    private User user;
    private Map<Integer,ArrayList<User>> itemValoratedBy;
    private ArrayList<myPair> predictions;

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
            float valorationUserI = user.searchUsedItem(IDitemI).getValoracio();
            float valorationUserJ = user.searchUsedItem(IDitemJ).getValoracio();
            sumTotal += (valorationUserJ - valorationUserI);
        }
        return sumTotal/usersIJ.size();
    }

    private float calculateValorationMean(User user) {
        ArrayList<ItemUsat> usedItems = user.getItemsUsats();
        float sum = 0;
        for(ItemUsat u: usedItems) sum += u.getValoracio();
        return sum/usedItems.size();
    }

    // suma media de las desviaciones de todos los items I respecto del item J, ambos puntuados por el usuario user
    private float calculateDesviationMean(User user, int IDitemJ) {
        ArrayList<ItemUsat> usedItems = user.getItemsUsats();
        float num = 0;
        int count = 0;
        for(ItemUsat itemI : usedItems) {
            int IDitemI =  itemI.getItem().getID();
            if(IDitemI != IDitemJ) {
                ArrayList<User> usersIJ = getIntersaction(IDitemI,IDitemJ);
                if(usersIJ.size() != 0) {
                    num += calculateDesviation(IDitemI, IDitemJ, usersIJ);
                    count++;
                }
            }
        }
        if(count != 0) return num/count;
        return -1;
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
        for(Map.Entry<Integer, ArrayList<User>> item : itemValoratedBy.entrySet()) {
            //si el item no esta valorado por el usuario ejecutar predicción
            if(!item.getValue().contains(user)) {
                float valoration = meanValoration + calculateDesviationMean(user, item.getKey());

                /* si tuvieramos el item
                ItemUsat iu = new ItemUsat(item, user, valoration);
                user.addItemUsat(iu);
                 */
                predictions.add(new myPair(item.getKey(), valoration));
            }
        }
    }

    public void printResults() {
        for(int i = 0; i < predictions.size(); ++i) {
            System.out.println("Valoracion estimada para el item " + predictions.get(i).getItemID() +
                    ": " + predictions.get(i).getValoration());
        }
    }

    public SlopeOne(Map<Integer,ArrayList<User>> itemValoratedBy) {
        this.itemValoratedBy = itemValoratedBy;
    }

    //retorna las predicciones para el usuario u
    public ArrayList<myPair> getPredictions(User user){
        this.user = user;
        slopeOneAlgorithm(user);
        return predictions;
    }
}
