import java.io.*;
import java.util.Map;

// @Author Marc Camarillas, Roberto Amat
public class LectorCSV2 {

    //private Usuarios u;
    private Map<int, User> usersList;

    private void readRatings(int userID, int itemID, float valoration) {
        TipusRol t = TipusRol.Usuari;

        User user = u.getUsuarioById(userID);
        user.addItemUsat(itemID, valoration);

    }

    public LectorCSV(Map <int, User> u){
        this.usersList = u;
    }

    public void Lector(String csvFile, String dataType) {
        BufferedReader br = null;
        String line = "";
        //Se define separador ","
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            boolean first = true;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                //Imprime datos.
                for(int i = 0; i < datos.length; ++i) {
                    //System.out.print(datos[i] + ", ");
                }
                System.out.println();
                if(dataType == "Ratings" && !first) {
                    readRatings(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), Float.parseFloat(datos[2]));
                }
                first = false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}