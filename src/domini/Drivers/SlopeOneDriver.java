package src.domini.drivers;

import src.domini.model.*;

import java.util.*;

public class SlopeOneDriver extends SlopeOne {

    private static ArrayList<User> usersItem1;
    private static ArrayList<User> usersItem2;
    private static ArrayList<User> intersectionUsers;


    private static void printUsers(ArrayList<User> users) {
        for(int i = 0; i < users.size(); ++i) {
            System.out.print(users.get(i).getUserID() + " ");
        }
    }

    private static void intersectionTest() {
        usersItem1 = new ArrayList<User>();
        for(int i = 0; i < 10; ++ i) {
            User user = new User(i,"1234", null);
            usersItem1.add(user);
        }
        usersItem2 = new ArrayList<User>();
        for(int i = 1; i < 20; i += 2) {
            User user = new User(i,"1234", null);
            usersItem2.add(user);
        }

        intersectionUsers = intersection(usersItem1, usersItem2,0);
        System.out.println("USERS1: ");
        printUsers(usersItem1);
        System.out.println("\n" + "USERS2: ");
        printUsers(usersItem2);
        System.out.println("\n" + "INTERSECTION: ");
        printUsers(intersectionUsers);
    }

    private static void calculateDesviationTest() {

    }

    public static void main(String[] args) {
        intersectionTest();
    }
}
