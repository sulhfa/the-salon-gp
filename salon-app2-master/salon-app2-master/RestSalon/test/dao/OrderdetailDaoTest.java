package dao;

import dto.OrderDetails;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderdetailDaoTest {
    
    public OrderdetailDaoTest() {
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
     * Test of getOrderDetailById method, of class OrderdetailDao.
     */
    @Test
    public void testGetOrderDetailById() {
        System.out.println("getOrderDetailById");
        int id = 0;
        OrderdetailDao instance = new OrderdetailDao();
        OrderDetails expResult = null;
        OrderDetails result = instance.getOrderDetailById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllDetailsByOrderId method, of class OrderdetailDao.
     */
    @Test
    public void testGetAllDetailsByOrderId() {
        System.out.println("getAllDetailsByOrderId");
        int order_id = 0;
        OrderdetailDao instance = new OrderdetailDao();
        ArrayList<OrderDetails> expResult = new ArrayList<OrderDetails>();
        ArrayList<OrderDetails> result = instance.getAllDetailsByOrderId(order_id);
        assertEquals(expResult, result);
    }

    /**
     * Test of addNewOrderDetail method, of class OrderdetailDao.
     */
    @Test
    public void testAddNewOrderDetail() {
        System.out.println("addNewOrderDetail");
        OrderDetails od = null;
        OrderdetailDao instance = new OrderdetailDao();
        int expResult = 0;
        int result = instance.addNewOrderDetail(od);
        assertEquals(expResult, result);
    }

    /**
     * Test of delOrderDetail method, of class OrderdetailDao.
     */
    @Test
    public void testDelOrderDetail() {
        System.out.println("delOrderDetail");
        int id = 0;
        OrderdetailDao instance = new OrderdetailDao();
        int expResult = 0;
        int result = instance.delOrderDetail(id);
        assertEquals(expResult, result);
    }
    
}
