
@Author Oriol Cuellar

public class CtrlDomini {

    public CtrlDomini() {
    }
// Atributes
    private User actualUser;
    private static CtrlDomini   dominiSingelton;
    private Vector <User> usersList;

//constructor
    public static CtrlDomini getInstance(){
        if (dominiSingelton == null)
        {
            dominiSingelton = new CtrlDomini() {};
        }
        return dominiSingelton;
    }

    public static void register(){}
    public static void login(){}
    public static void editPerfil(){}
    public static void deletePerfil(){}
    public static void getRecommendedItems(//user, k){
        //algoritmo
        //imprimo k
    } //to do
    public static void getAllItems(){}
    public static void getValoredItems(){}
    public static void selectItem(){}
    public static void valoreItem(){}
    public static void save(){} /
    public static void exit(){}
    public static void createItem(){}
    public static void deleteItem(){}
    public static void modItem(){}
    public static void loadItems(){}
    public static void loadUsers(){}
    public static void loadValorations(){}
    public static void deleteUser(){}
    public static void createUser(){}
    public static void doAlgotithm(){}
// valorar recomendacion
}
//driver stubs por cada clase
//juegos de prueba
//excepciones

