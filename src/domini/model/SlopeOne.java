package src.domini.model;
import src.domini.model.*;

import java.util.ArrayList;
import java.util.Map;

public class SlopeOne {
    private User user;
    private Map<Integer,ArrayList<User>> itemValorateBy;

    protected static ArrayList<User> intersection(ArrayList<User> l1, ArrayList<User> l2, int numCluster) {
        ArrayList<User> l3 = new ArrayList<User>();
        for(int i = 0; i < l1.size(); ++i) {
            if(l1.get(i).getNumCluster() == numCluster) {
                for (int j = 0; j < l2.size(); ++j) {
                    if (l1.get(i).getUserID() == l2.get(j).getUserID() && l2.get(j).getNumCluster() == numCluster) {
                        l3.add(l1.get(i));
                    }
                }
            }
        }
        return l3;
    }

    protected static float calculateDesviation(int IDitemI, int IDitemJ, ArrayList<User> usersIJ) {
        float sumTotal = 0;
        for(int i = 0; i < usersIJ.size(); ++i) {
            float valorationUserI = usersIJ.get(i).searchUsedItem(IDitemI).getValoracio();
            float valorationUserJ = usersIJ.get(i).searchUsedItem(IDitemJ).getValoracio();
            sumTotal += valorationUserJ - valorationUserI;
        }
        return sumTotal/usersIJ.size();
    }

    private float calculateValorationMean(User user) {
        ArrayList<ItemUsat> usedItems = user.getItemsUsats();
        float sum = 0;
        for(ItemUsat u: usedItems) sum += u.getValoracio();
        return sum/usedItems.size();
    }

    private float calculatDesviationMean(User user) {
        ArrayList<ItemUsat> usedItems = user.getItemsUsats();
        float sum = 0;
        for(int i = 0; i < usedItems.size(); ++i) {
            int IDitemI = usedItems.get(i).getItem().getID();
            for(int j = i + 1; j < usedItems.size(); ++j) {
                int IDitemJ = usedItems.get(j).getItem().getID();
                ArrayList<User> usersIJ = getIntersaction(IDitemI,IDitemJ);
                sum += calculateDesviation(IDitemI,IDitemJ,usersIJ);
            }
        }
        return sum/usedItems.size();
    }

    private ArrayList<User> getIntersaction(int IDitemI, int IDitemJ) {
        ArrayList<User> usersI = itemValorateBy.get(IDitemI);
        ArrayList<User> usersJ = itemValorateBy.get(IDitemJ);

       return intersection(usersI, usersJ, user.getNumCluster());
    }



    public void slopeOneAlgorithm(User user, Map<Integer,ArrayList<User>> itemValorateBy) {
        this.itemValorateBy = itemValorateBy;
        this.user = user;
        Item ui = new Item(1);
        Item uj = new Item(1);

        int IDitemI = ui.getID();
        int IDitemJ = uj.getID();

        ArrayList<User> usersIJ = getIntersaction(IDitemI,IDitemJ);
        float devIJ = calculateDesviation(IDitemI,IDitemJ, usersIJ);
    }
    public SlopeOne() {}
}
