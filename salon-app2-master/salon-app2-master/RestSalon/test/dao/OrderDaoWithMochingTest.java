package dao;

import dto.Order;
import java.sql.Connection;
import java.sql.Date;
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

public class OrderDaoWithMochingTest {

    public OrderDaoWithMochingTest() {
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
    public void TestGetOrderByIdWithMock() throws SQLException {
        // Create expected results
        String str = "2015-03-31";
        Date d = Date.valueOf(str);
        Order a1 = new Order(1, 1, d, 1, 1, 1000.50, null);
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //1............................................
        when(dbConn.prepareStatement("Select * from orders where id = ?")).thenReturn(ps);
        ps.setInt(1, a1.getId());

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("Id")).thenReturn(a1.getId());
        when(rs.getInt("custId")).thenReturn(a1.getUserId());
        when(rs.getInt("orderStatus")).thenReturn(a1.getOrderStatus());
        when(rs.getDate("orderDate")).thenReturn(a1.getOrderDate());
        when(rs.getInt("paymentType")).thenReturn(a1.getPaymentType());
        when(rs.getDouble("total")).thenReturn(a1.getTotalCost());

        OrderDao sDao = new OrderDao(dbConn);
        Order result = sDao.getOderById(a1.getId());
        Order expect_id = a1;
        assertEquals("Order cannot returned successfully", expect_id, result);
    }

    @Test
    public void TestGetOrderByCustIdWithMock() throws SQLException {
        // Create expected results
        String str = "2015-03-31";
        Date d = Date.valueOf(str);
        Order a1 = new Order(1, 1, d, 1, 1, 1000.50, null);
        Order a2 = new Order(2, 1, d, 1, 1, 2000.50, null);
        Order a3 = new Order(3, 1, d, 1, 1, 3000.50, null);

        ArrayList<Order> expectedResults = new ArrayList();
        expectedResults.add(a1);
        expectedResults.add(a2);
        expectedResults.add(a3);

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //2............................................
        when(dbConn.prepareStatement("Select * from orders where custId = ?;")).thenReturn(ps);
        ps.setInt(1, a1.getUserId());

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, true, false);
        when(rs.getInt("id")).thenReturn(a1.getId(), a2.getId(), a3.getId());
        when(rs.getInt("custId")).thenReturn(a1.getUserId(), a2.getUserId(), a3.getUserId());
        when(rs.getInt("orderStatus")).thenReturn(a1.getOrderStatus(), a2.getOrderStatus(), a3.getOrderStatus());
        when(rs.getDate("orderDate")).thenReturn(a1.getOrderDate(), a2.getOrderDate(), a3.getOrderDate());
        when(rs.getInt("paymentType")).thenReturn(a1.getPaymentType(), a2.getPaymentType(), a3.getPaymentType());
        when(rs.getDouble("total")).thenReturn(a1.getTotalCost(), a2.getTotalCost(), a3.getTotalCost());

        OrderDao sDao = new OrderDao(dbConn);
        ArrayList<Order> result2 = sDao.getAllOdersByCustId(a1.getUserId(),true);
        assertEquals("Order by customer cannot returned successfully", expectedResults, result2);
    }

    public void TestDeleteOrderWithMock() throws SQLException {
        String str = "2015-03-31";
        Date d = Date.valueOf(str);
        Order a1 = new Order(1, 1, d, 1, 1, 1000.50, null);
        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //3............................................
        when(dbConn.prepareStatement("DELETE FROM orders WHERE id = ?;")).thenReturn(ps);
        ps.setInt(1, a1.getId());

        int n = 1;
        when(ps.executeUpdate()).thenReturn(n);

        OrderDao sDao = new OrderDao(dbConn);
        int result2 = sDao.delOrder(a1.getId());
        assertEquals("Delete Order error", n, result2);
    }

    public void TestAddOrderWithMock() throws SQLException {
        // Create expected results
        String str = "2015-03-31";
        Date d = Date.valueOf(str);
        Order a = new Order(1, 1, d, 1, 1, 1000.50, null);

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        //4............................................
        when(dbConn.prepareStatement("INSERT INTO "
                + "order(custId,"
                + "orderdate,"
                + "orderstatus,"
                + "paytmenttype, total )"
                + "VALUES (?,?,?,?,?);")).thenReturn(ps);

        ps.setInt(1, a.getUserId());
        ps.setDate(2, a.getOrderDate());
        ps.setInt(3, a.getOrderStatus());
        ps.setInt(4, a.getPaymentType());
        ps.setDouble(5, a.getTotalCost());

        int n = 1;
        when(ps.executeUpdate()).thenReturn(n);

        OrderDao sDao = new OrderDao(dbConn);
        int result2 = sDao.addNewOrder(a);
        assertEquals("Add Order error", n, result2);
    }
}
