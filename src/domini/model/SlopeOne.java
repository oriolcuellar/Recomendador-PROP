package src.domini.model;
import src.domini.model.*;

import java.util.ArrayList;
import java.util.Map;

public class SlopeOne {
    private User user;
    private Map<Integer,ArrayList<User>> users;

    protected static ArrayList<User> intersection(ArrayList<User> l1, ArrayList<User> l2) {
        ArrayList<User> l3 = new ArrayList<User>();
        for(int i = 0; i < l1.size(); ++i) {
            for(int j = 0; j < l2.size(); ++j) {
                if(l1.get(i).getUserID() == l2.get(j).getUserID()) {
                    l3.add(l1.get(i));
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

    public ArrayList<User> getIntersaction(int IDitemI, int IDitemJ) {
        ArrayList<User> usersI = users.get(IDitemI);
        ArrayList<User> usersJ = users.get(IDitemJ);

       return intersection(usersI, usersJ);
    }

    public void slopeOneAlgorithm() {
        Item ui = new Item(1);
        Item uj = new Item(1);

        int IDitemI = ui.getID();
        int IDitemJ = uj.getID();

        ArrayList<User> usersIJ = getIntersaction(IDitemI,IDitemJ);
        float devIJ = calculateDesviation(IDitemI,IDitemJ, usersIJ);
    }

    public SlopeOne() {}
}
