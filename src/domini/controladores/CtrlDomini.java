
//@Author Oriol Cuellar

package src.domini.controladores;

import src.domini.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
        selectedItem = null;
        ratesList = new ArrayList<>();
        itemList = new ArrayList<>();
        User admin= new User(-1);
        admin.setRol(TipusRol.Administrador);
        usersList.put(-1, admin);
    }

    private CtrlDomini(){
        iniCtrlDomini();
    }

//Profile controller

    public void register(Integer userId, String password){
        //pre: Usuari actiu null, userId not exists, userId and password not null
        //post: es crea un usuari i es posa d'usuari actiu.
        if (usersList.containsKey(userId) || actualUser!=null || userId==null || password==null || password.equals("")){

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
            if (actualUser.getPassword().equals(password)) {//logged
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
    public void showAllItems(){
        for(Item i: itemList){
            System.out.println("\n" + i.getID() + "\n");
        }
    }
    public void ShowRatedItems(){

        //System.out.println(ratesList.size());
       /* for(ItemUsat i: usersList.get(actualUser).getItemsUsats()){
            System.out.println("\n" + i.getUsuari()+ " "+ i.getItem()+" "+ i.getValoracio() + "\n");
        }*/

    }
    public void save(){}
    public void exit(){}
    public void createItem(){

    }
    public void deleteItem(){}
    public void modifyItem(){}
    public void loadItems(){

    }//to do------------------------------------
    public void loadRates(){//falta añadir item usado a la lista de items usados
        //pre: actualUser es admin
        if (usersList.get(actualUser).getRol()!=TipusRol.Administrado){
            System.out.println("\n" + "usuari no es aministrador" + "\n");
        }
        else {
            ArrayList<Vector<String>> readed_ratings = new ArrayList<Vector<String>>();

            LectorCSV2 reader = new LectorCSV2();
            readed_ratings = reader.Lector_Ratings("Entradas_CSV/ratings.db.csv", "Ratings");

            TipusRol t = TipusRol.Usuari;
            for (Vector<String> vs : readed_ratings) {
                if (usersList.containsKey(Integer.valueOf(vs.get(0)))) {//existeix
                    User usuari = usersList.get(Integer.valueOf(vs.get(0)));
                    if (usuari.searchUsedItem(Integer.valueOf(vs.get(1))) == null) {//no existe el item en sus valoraciones
                        usuari.addItemUsat(Integer.valueOf(vs.get(1)), Float.valueOf(vs.get(0)));

                    }
                } else {//no existeix, es crea, afegim valoracio a la seva llista, afegim valoracio allista itemUsatList
                    User usuari = new User(Integer.valueOf(vs.get(0)));
                    usuari.addItemUsat(Integer.valueOf(vs.get(1)), Float.valueOf(vs.get(2)));
                    usersList.put(Integer.valueOf(vs.get(0)), usuari);
                }
            }
        }
    }//to do------------------------------------
    public void deleteUser(){
        //pre: actualUser admin
        if(actualUser)
    }
    public void createUser(){}

}
//driver stubs por cada clase
//juegos de prueba
//excepciones

