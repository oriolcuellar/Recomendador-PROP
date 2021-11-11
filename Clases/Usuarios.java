import java.util.ArrayList;

public class Usuarios {
    private static ArrayList<User> usuarios;

    // Constructora
    public Usuarios() {
        usuarios = new ArrayList<User>();
    }

    // Getter
    public User getUsuario(int indice) {
        return usuarios.get(indice);
    }

    public ArrayList<User> getUsuarios() {
        return usuarios;
    }

    // Functions
    public void addUsuario(User usuario) {
        usuarios.add(usuario);
    }


}
