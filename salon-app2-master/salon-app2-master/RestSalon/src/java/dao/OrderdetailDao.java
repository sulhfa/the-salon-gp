package dao;

import dto.OrderDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderdetailDao extends Dao implements IOrderdetailDao {

    public OrderdetailDao() {
        super();
    }
    
    public OrderdetailDao(Connection conn) {
        super(conn);
    }

    public OrderdetailDao(String databaseName) {
        super(databaseName);
    }
    
    @Override
    public OrderDetails getOrderDetailById(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        OrderDetails t = null;

        if(id<1)
            return t;
        try {
            con = getConnection();

            String query = "Select * from orderdetails where id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int order_id = rs.getInt("orderId");
                int service_id = rs.getInt("servicesId");
                double cost = rs.getDouble("cost");
                t = new OrderDetails(order_id, service_id, cost);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getOrderDetailById() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getOrderDetailById() method: " + e.getMessage());
            }
        }
        return t;
    }

    @Override
    public ArrayList<OrderDetails> getAllDetailsByOrderId(int order_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<OrderDetails> ts = new ArrayList<OrderDetails>();

        if(order_id<1)
            return ts;
        try {
            con = getConnection();

            String query = "Select * from orderdetails where orderId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, order_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                int t_id = rs.getInt("id");
                int service_id = rs.getInt("servicesId");
                double cost = rs.getDouble("cost");
                OrderDetails s = new OrderDetails(t_id, order_id, service_id, cost);
                ts.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllDetailsByOrderId() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getAllDetailsByOrderId() method: " + e.getMessage());
            }
        }
        return ts;
    }

    @Override
    public int addNewOrderDetail(OrderDetails od) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        if (od == null) {
            return 0;
        }
        try {
            con = getConnection();

            String query = "INSERT INTO "
                    + "orderdetails(orderId,"
                    + "servicesId,"
                    + "cost)"
                    + "VALUES (?,?,?);";

            ps = con.prepareStatement(query);
            ps.setInt(1, od.getOrderId());
            ps.setInt(2, od.getServiceId());
            ps.setDouble(3, od.getCost());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the addNewOrderDetail() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the addNewOrderDetail() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    @Override
    public int delOrderDetail(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;
        if(id<1)
            return 0;
        try {
            con = getConnection();

            String query = "DELETE FROM orderdetails WHERE id = ?;";

            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the delOrderDetail() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the delOrderDetail() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

}
