package FONTS.src.domini.drivers;

import FONTS.src.domini.model.Item;
import FONTS.src.domini.model.User;
import FONTS.src.domini.model.valoratedItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class UserTest extends User {

    @Test
    public void testGetUserID() {
    }

    @Test
    public void testGetPassword() {
    }

    @Test
    public void testGetRol() {
    }

    @Test
    public void testGetValoratedItems() {
    }

    @Test
    public void testGetNumCluster() {
    }

    @Test
    public void testSetUserID() {
    }

    @Test
    public void testSetPassword() {
    }

    @Test
    public void testSetRol() {
    }

    @Test
    public void testSetValoratedItems() {
    }

    @Test
    public void testSetNumCluster() {
    }

    @Test
    public void testAddvaloratedItem() {
    }

    @Test
    public void testCalculateSimilarity() {
        System.out.println("Introduzca el ID del usuario 1");
        Scanner S = new Scanner(System.in);
        int userID1 = S.nextInt();
        User user1 = new User(userID1);
        System.out.println("Introduzca el numero de items que quiere puntuar: ");
        int n = S.nextInt();
        ArrayList<valoratedItem> items1 = new ArrayList<valoratedItem>();
        for(int i = 0; i < n; ++i) {
            System.out.println("Introduzca el ID del Item que quiere valorar: ");
            int ID = S.nextInt();
            System.out.println("Introduzca la valoracion de dicho Item: ");
            float val = S.nextFloat();
            Item item = new Item(ID);
            valoratedItem vItem = new valoratedItem(item,val);
            items1.add(vItem);
        }
        user1.setValoratedItems(items1);
        System.out.println("Introduzca el ID del usuario 2");
        int userID2 = S.nextInt();
        User user2 = new User(userID1);
        int n1 = S.nextInt();
        ArrayList<valoratedItem> items2 = new ArrayList<valoratedItem>();
        for(int i = 0; i < n1; ++i) {
            System.out.println("Introduzca el ID del Item que quiere valorar: ");
            int ID = S.nextInt();
            System.out.println("Introduzca la valoracion de dicho Item: ");
            float val = S.nextFloat();
            Item item = new Item(ID);
            valoratedItem vItem = new valoratedItem(item,val);
            items1.add(vItem);
        }
        user1.setValoratedItems(items2);
    }

    @Test
    public void testCalculateDistances2() {
    }

    @Test
    public void testSearchUsedItem() {
    }

    @Test
    public void testPrintUsedItems() {
    }
}