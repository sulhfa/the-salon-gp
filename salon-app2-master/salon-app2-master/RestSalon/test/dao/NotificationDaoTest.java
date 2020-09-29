package dao;

import dto.Notifications;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotificationDaoTest {
    
    public NotificationDaoTest() {
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
     * Test of getOrderTrackingByOrderId method, of class NotificationDao.
     */
    @Test
    public void testGetNotificationByOrderId() {
        System.out.println("getOrderTrackingByOrderId");
        int order_id = 0;
        NotificationDao instance = new NotificationDao();
        Notifications expResult = null;
        Notifications result = instance.getNotificationByOrderId(order_id);
        assertEquals(expResult, result);
    }

    /**
     * Test of addNewOrderTracking method, of class NotificationDao.
     */
    @Test
    public void testAddNewNotification() {
        System.out.println("addNewOrderTracking");
        Notifications a = null;
        NotificationDao instance = new NotificationDao();
        int expResult = 0;
        int result = instance.addNewNotification(a);
        assertEquals(expResult, result);
    }
    
}
