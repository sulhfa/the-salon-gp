package dao;

import dto.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import util.SecHash;

public class AccountDaoWithMockingTest {

    public AccountDaoWithMockingTest() {
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
     * Test of testAccounts method, of class AccountDao.
     */
    @Test
    public void TestcheckUserByEmailPasswordWithMoch() throws SQLException {
        Account a2 = new Account(2, SecHash.getSecurePassword("7890123", "email2@email.com"), "email2@email.com",
                "UserName", 1, 1, "SEC ANSWER", "address", "00000000", "location", "notes...");


        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //1............................................
        when(dbConn.prepareStatement("Select userid, email from users where email = ? AND password = ?")).thenReturn(ps);
        ps.setString(1, a2.getEmail());
        ps.setString(2, a2.getPassword());

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getString("email")).thenReturn(a2.getEmail());
        when(rs.getInt("userid")).thenReturn(a2.getUser_id());

        AccountDao AcDao = new AccountDao(dbConn);
        int result = AcDao.checkUserByEmailPassword(a2);
        int expect_id = 2;
        assertEquals("Existing user not located", expect_id, result);
    }

    @Test
    public void TestUpdatePasswordWithMoch() throws SQLException {
        Account a1 = new Account(1, SecHash.getSecurePassword("123456", "email1@email.com"), "email1@email.com",
                "UserName", 1, 1, "SEC ANSWER", "address", "00000000", "location", "notes...");

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //2............................................
        when(dbConn.prepareStatement("UPDATE users SET password = ? WHERE userid = ? and email = ?")).thenReturn(ps);
        String pass = SecHash.getSecurePassword("123456789", a1.getEmail());
        ps.setString(1, pass);
        ps.setInt(2, a1.getUser_id());
        ps.setString(3, a1.getEmail());

        int n = 1;
        when(ps.executeUpdate()).thenReturn(n);

        AccountDao AcDao2 = new AccountDao(dbConn);
        int result = AcDao2.updatePassword("email1@email.com", "123456", "123456789");
        int expect_id = 1;
        assertEquals("Update password not success", expect_id, result);
    }
}
