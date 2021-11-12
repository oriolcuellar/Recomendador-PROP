package src.domini.drivers;

import src.domini.model.*;

import java.util.*;

public class SlopeOneDriver extends SlopeOne {

    private static void printUsers(ArrayList<User> users) {
        for(int i = 0; i < users.size(); ++i) {
            System.out.print(users.get(i).getUserID() + " ");
        }
    }

    private static void intersectionTest() {
        ArrayList<User> users1 = new ArrayList<User>();
        for(int i = 0; i < 10; ++ i) {
            User user = new User(i,"1234", null);
            users1.add(user);
        }
        ArrayList<User> users2 = new ArrayList<User>();
        for(int i = 1; i < 20; i += 2) {
            User user = new User(i,"1234", null);
            users2.add(user);
        }

        ArrayList<User> interseccionUsers = new ArrayList<>();
        interseccionUsers = intersection(users1, users2);
        System.out.println("USERS1: ");
        printUsers(users1);
        System.out.println("\n" + "USERS2: ");
        printUsers(users2);
        System.out.println("\n" + "INTERSECTION: ");
        printUsers(interseccionUsers);
    }

    private static void calculateDesviationTest() {

    }

    public static void main(String[] args) {
        intersectionTest();
    }
}
