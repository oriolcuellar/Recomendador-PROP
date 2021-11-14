package src.domini.model;
import src.domini.model.*;
import java.util.ArrayList;
import java.util.Map;

public class SlopeOne {
    private User user;
    private Map<Integer,ArrayList<User>> itemValoratedBy;

    protected static ArrayList<User> intersection(ArrayList<User> l1, ArrayList<User> l2, int numCluster) {
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

    protected static float calculateDesviation(int IDitemI, int IDitemJ, ArrayList<User> usersIJ) {
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
        for(ItemUsat itemI : usedItems) {
            int IDitemI =  itemI.getItem().getID();
            if(IDitemI != IDitemJ) {
                ArrayList<User> usersIJ = getIntersaction(IDitemI,IDitemJ);
                num += calculateDesviation(IDitemI,IDitemJ,usersIJ);
            }
        }
        return num/usedItems.size();
    }

    private ArrayList<User> getIntersaction(int IDitemI, int IDitemJ) {
        ArrayList<User> usersI = itemValoratedBy.get(IDitemI);
        ArrayList<User> usersJ = itemValoratedBy.get(IDitemJ);

       return intersection(usersI, usersJ, user.getNumCluster());
    }



    public void slopeOneAlgorithm(User user, Map<Integer,ArrayList<User>> itemValorateBy) {
        this.itemValoratedBy = itemValorateBy;
        this.user = user;

        float meanValoration = calculateValorationMean(user);
        ArrayList<Integer> predictionsID = new ArrayList<>();
        ArrayList<Float> predictionsValoration = new ArrayList<>();
        //predecir todos los que no tiene valoracion
        for(Map.Entry<Integer, ArrayList<User>> item : itemValoratedBy.entrySet()) {
            //si el item no esta valorado por el usuario ejecutar predicción
            if(!item.getValue().contains(user)) {
                float valoration = meanValoration + calculateDesviationMean(user, item.getKey());

                /* si tuvieramos el tiem
                ItemUsat iu = new ItemUsat(item, user, valoration);
                user.addItemUsat(iu);
                 */
                predictionsID.add(item.getKey());
                predictionsValoration.add(valoration);
            }
        }
    }
    public SlopeOne(User u) {

    }
}
