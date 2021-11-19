package FONTS.src.domini.drivers;

import FONTS.src.domini.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest extends User {

    @Test
    public void testGetUserID() {
        User user = new User(2);
        int ID = user.getUserID();
        assertEquals(2,ID,0);
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