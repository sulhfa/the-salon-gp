package util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class SecHashTest {
    
    public SecHashTest() {
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
     * Test of getSecurePassword method, of class SecHash.
     */
    @Test
    public void testGetSecurePassword() {
        System.out.println("getSecurePassword");
        String passwordToHash = "";
        String salt = "";
        String expResult = "";
        String result = SecHash.getSecurePassword(passwordToHash, salt);
        assertNotSame(expResult, result);
    }
    
}
