package dao;

import dto.Service;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceDaoWithMochingTest {

    public ServiceDaoWithMochingTest() {
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

    @Test
    public void TestGetServiceByIdWithMock() throws SQLException {
        // Create expected results
        Service a1 = new Service(1, "AA", 1, "A A A", 10.50,1);
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //1............................................
        when(dbConn.prepareStatement("Select * from services where id = ?")).thenReturn(ps);
        ps.setInt(1, a1.getId());

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(a1.getId());
        when(rs.getInt("ownerId")).thenReturn(a1.getOwnerId());
        when(rs.getString("title")).thenReturn(a1.getTitle());
        when(rs.getString("details")).thenReturn(a1.getDetails());
        when(rs.getDouble("cost")).thenReturn(a1.getCost());

        ServiceDao sDao = new ServiceDao(dbConn);
        Service result = sDao.getServiceById(a1.getId());
        Service expect_id = a1;
        assertEquals("Service cannot returned successfully", expect_id, result);
    }

    @Test
    public void TestGetServiceByOwnerIdWithMock() throws SQLException {
        // Create expected results

        Service a1 = new Service(1, "AA", 1, "A A A", 10.50,1);
        Service a2 = new Service(2, "BB", 1, "B B B", 21.25,1);
        Service a3 = new Service(3, "CC", 1, "C C C", 25.75,1);

        ArrayList<Service> expectedResults = new ArrayList();
        expectedResults.add(a1);
        expectedResults.add(a2);
        expectedResults.add(a3);

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //2............................................
        when(dbConn.prepareStatement("Select * from services where ownerId = ?")).thenReturn(ps);
        ps.setInt(1, a1.getOwnerId());

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, true, false);
        when(rs.getInt("id")).thenReturn(a1.getId(), a2.getId(), a3.getId());
        when(rs.getInt("ownerId")).thenReturn(a1.getOwnerId(), a2.getOwnerId(), a3.getOwnerId());
        when(rs.getString("title")).thenReturn(a1.getTitle(), a2.getTitle(), a3.getTitle());
        when(rs.getString("details")).thenReturn(a1.getDetails(), a2.getDetails(), a3.getDetails());
        when(rs.getDouble("cost")).thenReturn(a1.getCost(), a2.getCost(), a3.getCost());

        ServiceDao sDao = new ServiceDao(dbConn);
        ArrayList<Service> result2 = sDao.getAllServiceByOwnerId(a1.getOwnerId());
        assertEquals("Services by owner cannot returned successfully", expectedResults, result2);
    }

    public void TestDeleteServiceWithMock() throws SQLException {
        Service a2 = new Service(2, "BB", 1, "B B B", 21.25,1);
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //3............................................
        when(dbConn.prepareStatement("DELETE FROM services WHERE id = ?;")).thenReturn(ps);
        ps.setInt(1, a2.getId());

        int n = 1;
        when(ps.executeUpdate()).thenReturn(n);

        ServiceDao sDao = new ServiceDao(dbConn);
        int result2 = sDao.delService(a2.getId());
        assertEquals("Delete Services error", n, result2);
    }

    public void TestAddServiceWithMock() throws SQLException {
        // Create expected results

        Service s = new Service(1, "AA", 1, "A A A", 10.50,1);

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //4............................................
        when(dbConn.prepareStatement("insert into services(title,"
                + "ownerId,"
                + "details,"
                + "cost )"
                + "VALUES (?,?,?,?);")).thenReturn(ps);
        ps.setString(1, s.getTitle());
        ps.setInt(2, s.getOwnerId());
        ps.setString(3, s.getDetails());
        ps.setDouble(4, s.getCost());

        int n = 1;
        when(ps.executeUpdate()).thenReturn(n);

        ServiceDao sDao = new ServiceDao(dbConn);
        int result2 = sDao.addNewService(s);
        assertEquals("Add Services error", n, result2);
    }
}
