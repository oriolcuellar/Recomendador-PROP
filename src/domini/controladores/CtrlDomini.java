
//@Author Oriol Cuellar

package src.domini.controladores;

import src.domini.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CtrlDomini {

// Atributes

    private static CtrlDomini   dominiSingelton = null;

    private User actualUser;
    private Item selectedItem;
    private static Map <Integer, User> usersList;
    private static ArrayList <ItemUsat> ratesList;
    private static ArrayList<Item> itemList;
//CtrlDomini control= CtrlDomini.getInstance();
//control.getAllUsers();
//constructor
    public static CtrlDomini getInstance(){
        if (dominiSingelton == null)
        {
            dominiSingelton = new CtrlDomini() {};
        }
        return dominiSingelton;
    }

    private void iniCtrlDomini(){
        usersList = new HashMap<>();
        actualUser = null;
        ratesList = new ArrayList<>();
        itemList = new ArrayList<>();
    }

    private CtrlDomini(){
        iniCtrlDomini();
    }

//Profile controller

    public void register(Integer userId, String password){
        //pre: Usuari actiu null, userId not exists, userId and password not null
        //post: es crea un usuari i es posa d'usuari actiu.
        if (usersList.containsKey(userId) || actualUser!=null || userId==null || password==null || password==""){
            System.out.println("\n error al registrar \n");
        }
        else{
            TipusRol rol=TipusRol.Usuari;
            actualUser= new User(userId, password, rol);
            usersList.put(userId, actualUser);
        }
    }
    public void login(int userId, String password){
        //pre: Usuari actiu null, User amb userId i password existeix
        //post: es crea un usuari i es posa d'usuari actiu.
        if (actualUser!=null) {
            System.out.println("\n tanca sessio primer \n");
        }
        else if (usersList.containsKey(userId)) {
            actualUser=usersList.get(userId);
            if (actualUser.getPassword()==password) {//logged
                System.out.println("\n Sessió iniciada \n");
            }
            else {
                System.out.println("\n error usuari o contrasenya \n");
            }
        }
    }
    public  void logout(){
        //pre: usuari actiu es null
        if (actualUser==null) System.out.println("\n No hi ha usuari loggejat \n");
        else{
            actualUser=null;
            System.out.println("\n Logged Out \n");
        }

    }
    public static void editProfile(){}
    public static void deleteProfile(){}
    public static void showRecommendedItems(int k){// to do------------------
        //kmeans
        //Usuarios u = new Usuarios();
        //LectorCSV lectorCSV = new LectorCSV(u);
        //lectorCSV.Lector("Entradas_CSV/ratings.db.csv", "Ratings");


        Kmeans kmeans = new Kmeans(k, usersList);
        kmeans.printAllClusters();

        //slope one

            //map id item, users que lo tienen valorado
        //k-neighbours


    }
    public void rateRecommendation(){}//to do----------------------
    public void selectItem(){}
    public void rateItem(){}
    public void showAllItems(){}
    public void ShowRatedItems(){}
    public void save(){}
    public void exit(){}
    public void createItem(){}
    public void deleteItem(){}
    public void modifyItem(){}
    public void loadItems(){

    }//to do------------------------------------
    public void loadUsers(){

    }//to do------------------------------------
    public void loadRates(){
        LectorCSV2 reader= new LectorCSV2(usersList, itemList, ratesList);
        reader.Lector("Entradas_CSV/ratings.db.csv", "Ratings");
    }//to do------------------------------------
    public void deleteUser(){}
    public void createUser(){}

}
//driver stubs por cada clase
//juegos de prueba
//excepciones

