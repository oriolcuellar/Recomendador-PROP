package src.domini.drivers;

import src.domini.model.*;

import java.util.*;

public class SlopeOneDriver{
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
        ArrayList<User> users = new ArrayList<User>();

        User user0 = new User(0,"1234", null,1);
        users.add(user0);
        User user1 = new User(1,"1234", null,1);
        users.add(user1);
        User user2 = new User(2, "1234", null,2);
        users.add(user2);
        User user3 = new User(3,"1234", null,2);
        users.add(user3);
        User user4 = new User(4,"1234", null,1);
        users.add(user4);
        User user5 = new User(5,"1234", null,1);
        users.add(user5);
        User user6 = new User(6,"1234", null,2);
        users.add(user6);
        User user7 = new User(7,"1234", null,1);
        users.add(user7);

        usersItem1.add(user0);
        usersItem1.add(user1);
        usersItem1.add(user4);
        usersItem1.add(user7);

        user0.addItemUsat(1, 2);
        user1.addItemUsat(1, 5);
        user4.addItemUsat(1, (float)1.5);
        user7.addItemUsat(1, 2);

        usersItem2.add(user3);
        usersItem2.add(user2);
        usersItem2.add(user4);
        usersItem2.add(user6);

        user3.addItemUsat(2, 3);
        user2.addItemUsat(2, (float)4.5);
        user4.addItemUsat(2, (float)0.5);
        user6.addItemUsat(2, 5);

        usersItem3.add(user5);
        usersItem3.add(user1);
        usersItem3.add(user6);
        usersItem3.add(user2);

        user5.addItemUsat(3, 1);
        user1.addItemUsat(3, (float)2.5);
        user6.addItemUsat(3, (float)0.5);
        user2.addItemUsat(3, 4);

        usersItem4.add(user3);
        usersItem4.add(user1);
        usersItem4.add(user0);
        usersItem4.add(user7);

        user3.addItemUsat(4, 3);
        user1.addItemUsat(4, 0);
        user0.addItemUsat(4, (float)2.5);
        user7.addItemUsat(4, 3);

        Map<Integer,ArrayList<User>> items = new HashMap<Integer,ArrayList<User>>();
        items.put(1, usersItem1);
        items.put(2, usersItem2);
        items.put(3, usersItem3);
        items.put(4, usersItem4);

        SlopeOne So = new SlopeOne(items);
        for(int i = 0; i < users.size(); ++i) {
            System.out.println("USER: " + users.get(i).getUserID());
            So.getPredictions(users.get(i));
            System.out.println();
            users.get(i).printUsedItems();
            System.out.println();
            So.printResults();
            System.out.println();
        }

    }
}
