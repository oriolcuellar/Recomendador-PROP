
@Author Oriol Cuellar

public class CtrlDomini {


// Atributes

    private static CtrlDomini   dominiSingelton = null;

    private User actualUser;
    private Item selectedItem;
    private Map <String, User> usersList;
    private map <String, ItemUsat> ratesList;
    private ArrayList <Item> itemList;

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
        ratesList = new HashMap<>();
        itemList = new ArrayList<>();
    }

    public CtrlDomini(){
        iniCtrlDomini();
    }

//Profile controller

    public static void register(string userId, string password){
        //pre: Usuari actiu null, userId not exists, userId and password not null
        //post: es crea un usuari i es posa d'usuari actiu.
        if (usersList.containsKey(userId) or actualUser!=null or userId==null or userId=="" or password==null or password==""){
            System.out.println("\n error al registrar \n");
        }
        else{
            TipusRol rol=TipusRol.Usuari;
            actualUser= new User(userId, password, rol);
            usersList.put(userId, actualUser);
        }
    }
    public static void login(string userId, string password){
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
    public static void logout(){
        //pre: usuari actiu es null
        if (actualUser==null) System.out.println("\n No hi ha usuari loggejat \n");
        else System.out.println("\n Logged Out \n");

    }
    public static void editProfile(){}
    public static void deleteProfile(){}
    public static void showRecommendedItems(//user, k){// to do------------------

    }
    public static void rateRecommendation(){}//to do----------------------
    public static void selectItem(){}
    public static void rateItem(){}
    public static void showAllItems(){}
    public static void ShowRatedItems(){}
    public static void save(){} /
    public static void exit(){}
    public static void createItem(){}
    public static void deleteItem(){}
    public static void modifyItem(){}
    public static void loadItems(){}//to do------------------------------------
    public static void loadUsers(){}//to do------------------------------------
    public static void loadRates(){}//to do------------------------------------
    public static void deleteUser(){}
    public static void createUser(){}

}
//driver stubs por cada clase
//juegos de prueba
//excepciones

