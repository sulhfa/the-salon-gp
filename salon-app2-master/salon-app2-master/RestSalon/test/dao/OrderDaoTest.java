/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Order;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderDaoTest {
    
    public OrderDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getOderById method, of class OrderDao.
     */
    @Test
    public void testGetOderById() {
        System.out.println("getOderById");
        int order_id = 0;
        OrderDao instance = new OrderDao();
        Order expResult = null;
        Order result = instance.getOderById(order_id);
        assertEquals("Non recorded order found", expResult, result);
    }

    /**
     * Test of addNewOrder method, of class OrderDao.
     */
    @Test
    public void testAddNewOrder() {
        System.out.println("addNewOrder");
        Order a = null;
        OrderDao instance = new OrderDao();
        int expResult = 0;
        int result = instance.addNewOrder(a);
        assertEquals("Add order with invalid id",expResult, result);
    }

    /**
     * Test of delOrder method, of class OrderDao.
     */
    @Test
    public void testDelOrder() {
        System.out.println("delOrder");
        int order_id = 0;
        OrderDao instance = new OrderDao();
        int expResult = 0;
        int result = instance.delOrder(order_id);
        assertEquals("Delete order with invalid id",expResult, result);
    }

    /**
     * Test of getAllOdersByCustId method, of class OrderDao.
     */
    @Test
    public void testGetAllOdersByCustId() {
        System.out.println("getAllOdersByCustId");
        int cust_id = 0;
        OrderDao instance = new OrderDao();
        ArrayList<Order> expResult = null;
        ArrayList<Order> result = instance.getAllOdersByCustId(cust_id,true);
        assertEquals("Order for non customer",expResult, result);
    }
    
}
