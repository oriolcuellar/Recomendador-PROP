package src.domini.drivers;

import src.domini.model.*;

import java.util.*;

public class SlopeOneDriver extends SlopeOne {
    /*private static void printUsers(ArrayList<User> users) {
        for (User user : users) {
            System.out.print(user.getUserID() + " ");
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

    }*/

    public static void main(String[] args) {
        ArrayList<User> usersItem1 = new ArrayList<User>();
        ArrayList<User> usersItem2 = new ArrayList<User>();
        ArrayList<User> usersItem3 = new ArrayList<User>();
        ArrayList<User> usersItem4 = new ArrayList<User>();

        User user0 = new User(0,"1234", null,1);
        User user1 = new User(1,"1234", null,1);
        User user2 = new User(2, "1234", null,2);
        User user3 = new User(3,"1234", null,2);
        User user4 = new User(4,"1234", null,1);
        User user5 = new User(5,"1234", null,1);
        User user6 = new User(6,"1234", null,2);
        User user7 = new User(7,"1234", null,1);


        usersItem1.add(user0);
        usersItem1.add(user1);
        usersItem1.add(user4);
        usersItem1.add(user7);

        user0.addItemUsat(1, 2.);
        user1.addItemUsat(1, 2.);
        user4.addItemUsat(1, 2.);
        user7.addItemUsat(1, 2.);



        usersItem2.add(user3);
        usersItem2.add(user2);
        usersItem2.add(user4);
        usersItem2.add(user6);

        usersItem3.add(user5);
        usersItem3.add(user1);
        usersItem3.add(user6);
        usersItem3.add(user2);

        usersItem4.add(user3);
        usersItem4.add(user1);
        usersItem4.add(user0);
        usersItem4.add(user7);

        Map<Integer,ArrayList<User>> items = new HashMap<Integer,ArrayList<User>>();
        items.put(1, usersItem1);
        items.put(2, usersItem2);
        items.put(3, usersItem3);
        items.put(4, usersItem4);





    }
}
