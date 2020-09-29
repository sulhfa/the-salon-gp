package dao;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DaoTest {
    
    public DaoTest() {
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
     * Test of getConnection method, of class Dao.
     */
    @Test
    public void testGetConnection() {
        //System.out.println("getConnection");
        Dao instance = new Dao();
        Connection result = instance.getConnection();
        assertNull("Connection is null", result);
    }

    /**
     * Test of freeConnection method, of class Dao.
     */
    @Test
    public void testFreeConnection() {
        //System.out.println("freeConnection");
        Dao instance = new Dao();
        instance.freeConnection();
        assertTrue("Connection is not null",instance.isFreeConnection());
    }
    
}
