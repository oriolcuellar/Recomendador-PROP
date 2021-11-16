package FONTS.src.domini.model;

import FONTS.src.domini.model.*;
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

    /*
        Busca si hay un usuario con ID user Id si no está lo crea
     */
    public User getUsuarioById(int userID) {
        for(int i = 0; i < usuarios.size(); ++i) {
            if(userID == usuarios.get(i).getUserID()) return  usuarios.get(i);
        }
        TipusRol t = TipusRol.Usuari;
        User u = new User(userID,"1234",t);
        usuarios.add(u);
        return u;
    }

    public ArrayList<User> getUsuarios() {
        return usuarios;
    }

    // Functions
    public void addUsuario(User usuario) {
        usuarios.add(usuario);
    }

}