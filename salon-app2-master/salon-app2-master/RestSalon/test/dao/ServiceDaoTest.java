package dao;

import dto.Service;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServiceDaoTest {
    
    public ServiceDaoTest() {
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
     * Test of getServiceById method, of class ServiceDao.
     */
    @Test
    public void testGetServiceById() {
        System.out.println("getServiceById");
        int service_id = 0;
        ServiceDao instance = new ServiceDao();
        Service expResult = null;
        Service result = instance.getServiceById(service_id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllServiceByUserId method, of class ServiceDao.
     */
    @Test
    public void testGetAllServiceByUserId() {
        System.out.println("getAllServiceByUserId");
        int user_id = 0;
        ServiceDao instance = new ServiceDao();
        ArrayList<Service> expResult = new ArrayList<Service>();
        ArrayList<Service> result = instance.getAllServiceByOwnerId(user_id);
        assertEquals(expResult, result);
    }

    /**
     * Test of addNewSerivce method, of class ServiceDao.
     */
    @Test
    public void testAddNewSerivce() {
        System.out.println("addNewSerivce");
        Service s = null;
        ServiceDao instance = new ServiceDao();
        int expResult = 0;
        int result = instance.addNewService(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of delService method, of class ServiceDao.
     */
    @Test
    public void testDelService() {
        System.out.println("delService");
        int service_id = 0;
        ServiceDao instance = new ServiceDao();
        int expResult = 0;
        int result = instance.delService(service_id);
        assertEquals(expResult, result);
    }
    
}
