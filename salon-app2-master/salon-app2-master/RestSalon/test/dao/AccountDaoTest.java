package dao;

import dto.Account;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountDaoTest {
    
    public AccountDaoTest() {
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
     * Test of checkUserByUsernamePassword method, of class AccountDao.
     */
    @Test
    public void testCheckUserByUsernamePassword() {
        Account a = new Account(1, "email@email.com",
                "UserName", 1,1, 1,"SEC ANSWER", "address", "00000000", "location", "notes...");
        AccountDao instance = new AccountDao();
        int expResult = 0;
        int result = instance.checkUserByEmailPassword(a);
        assertEquals("None recording user login",expResult, result);
    }

    /**
     * Test of updatePassword method, of class AccountDao.
     */
    @Test
    public void testUpdatePassword() {
        Account a = new Account(1, "email@email.com",
                "UserName", 1,1,1,"SEC ANSWER", "address", "00000000", "location", "notes...");
        AccountDao instance = new AccountDao();
        String newPassword = "newpasss";
        int expResult = 0;
        int result = instance.updatePassword("email@email.com", "" ,newPassword);
        assertEquals("None recording user update password",expResult, result);
    }

    /**
     * Test of createNewAccount method, of class AccountDao.
     */
    @Test
    public void testCreateNewAccount() {
        AccountDao instance = new AccountDao();
        
        Account a = new Account(0, "email@email.com",
            "UserName", 1,1,1,"SEC ANSWER", "address", "00000000", "location", "notes...");
        int expResult = 0;
        int result = instance.createNewAccount(a);
        assertEquals("User Account with invalid Id created",expResult, result);
        
        Account b = new Account("password", "emailemail.com",
            "UserName", 1,1,"SEC ANSWER", "address", "00000000", "location", "notes...");

        expResult = 0;
        result = instance.createNewAccount(b);
        assertEquals("User Account with invalid email created",expResult, result);
        
        Account c = new Account("pp", "email@email.com",
            "UserName", 1,1,"SEC ANSWER", "address", "00000000", "location", "notes...");

        expResult = 0;
        result = instance.createNewAccount(c);
        assertEquals("User Account with invalid password created",expResult, result);
        
    }

    /**
     * Test of getUserById method, of class AccountDao.
     */
    @Test
    public void testGetUserById() {
        int user_id = 0;
        AccountDao instance = new AccountDao();
        Account expResult = null;
        Account result = instance.getUserById(user_id);
        assertEquals("User with invalid Id existing", expResult, result);
    }

    /**
     * Test of checkUserByEmailPassword method, of class AccountDao.
     */
    @Test
    public void testCheckUserByEmailPassword() {
        Account a = null;
        AccountDao instance = new AccountDao();
        int expResult = 0;
        int result = instance.checkUserByEmailPassword(a);
        assertEquals("Null user pass checking password",expResult, result);
        
        Account b = new Account(1, "email@email.com",
            "UserName", 1,1,1,"SEC ANSWER", "address", "00000000", "location", "notes...");
         expResult = 0;
         result = instance.checkUserByEmailPassword(a);
        assertEquals("Account wiht invalid password pass checking",expResult, result);
    }
    
}
