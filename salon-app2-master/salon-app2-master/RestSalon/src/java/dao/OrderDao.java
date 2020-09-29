package dao;

import dto.Order;
import dto.OrderDetails;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDao extends Dao implements IOrderDao {

    public OrderDao() {
        super();
    }
    
    public OrderDao(Connection conn) {
        super(conn);
    }

    public OrderDao(String databaseName) {
        super(databaseName);
    }
    
    @Override
    public Order getOderById(int order_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order t = null;

        if(order_id<1)
            return t;
        try {
            con = getConnection();

            String query = "Select * from orders where id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, order_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                //int t_id = rs.getInt("id");
                int userId = rs.getInt("custId");
                Date orderDate = rs.getDate("orderDate");
                int orderStatus = rs.getInt("orderStatus");
                int paymentType = rs.getInt("paymentType");
                double totalCost = rs.getDouble("total");
                t = new Order(order_id, userId, orderDate, orderStatus, paymentType, totalCost, null);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getOderById() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getOderById() method: " + e.getMessage());
            }
        }
        return t;
    }

    @Override
    public int addNewOrder(Order a) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        if (a == null) {
            return 0;
        }
        try {
            con = getConnection();

            String query = "INSERT INTO "
                    + "orders (custId,"
                    + "orderdate,"
                    + "orderstatus,"
                    + "paymenttype, total) "
                    + " VALUES (?,?,?,?,?);";

            ps = con.prepareStatement(query,  PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, a.getUserId());
            ps.setDate(2, a.getOrderDate());
            ps.setInt(3, a.getOrderStatus());
            ps.setInt(4, a.getPaymentType());
            ps.setDouble(5, a.getTotalCost());

            rowsAffected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                a.setId(rs.getInt(1));
                //System.out.println("new Key:"+rowsAffected);
            }

            if (a.getOrderDetails() != null) {
                IOrderdetailDao db = new OrderdetailDao();
                for (OrderDetails d : a.getOrderDetails()) {
                    d.setOrderId(a.getId());
                    rowsAffected += db.addNewOrderDetail(d);
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the addNewOrder() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the addNewOrder() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    @Override
    public int delOrder(int order_id) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        if(order_id<1)
            return 0;
        try {
            con = getConnection();

            String query = "DELETE FROM orders WHERE id = ?;";

            ps = con.prepareStatement(query);
            ps.setInt(1, order_id);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the delOrder() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the delOrder() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    @Override
    public ArrayList<Order> getAllOdersByCustId(int cust_id, boolean active) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Order> ts = null;

        if(cust_id<1)
            return ts;
        try {
            con = getConnection();

            String query = "Select o.* , count(*) as pcount " +
                            "from orders o left join orderdetails d on o.id=d.orderId " +
                            "where o.custId = ?  ";
            if (active){
                query+=" and o.orderstatus<3 group by o.id ;";
            }else{
                query+=" and o.orderstatus>2 group by o.id ;";
            }
            ps = con.prepareStatement(query);
            ps.setInt(1, cust_id);
            rs = ps.executeQuery();
            ts = new ArrayList<Order>();
            while (rs.next()) {
                Order t = new Order();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("custId"));
                t.setOrderDate(rs.getDate("orderdate"));
                t.setOrderStatus(rs.getInt("orderstatus"));
                t.setPaymentType(rs.getInt("paymentType"));
                t.setTotalCost(rs.getDouble("total"));
                t.setServiceCount(rs.getInt("pcount"));
                if(t.getServiceCount()>0){
                    String query2 = "select d.*, s.title " +
                            "from orderdetails d , services s where d.servicesId=s.id and d.orderId = ? ;";
                    PreparedStatement ps2 = con.prepareStatement(query2);
                    ps2.setInt(1,t.getId());
                    ResultSet rs2 = ps2.executeQuery();
                    ArrayList<OrderDetails> servList= new ArrayList<OrderDetails>();
                    while (rs2.next()) {
                        OrderDetails od = new OrderDetails();
                        od.setId(rs2.getInt("id"));
                        od.setOrderId(rs2.getInt("orderId"));
                        od.setCost(rs2.getDouble("cost"));
                        od.setServiceId(rs2.getInt("servicesId"));
                        od.setServiceTitle(rs2.getString("title"));
                        servList.add(od); 
                    }
                    t.setOrderDetails(servList);
                }
                ts.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllOdersByUserId() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllOdersByUserId() method: " + e.getMessage());
            }
        }
        return ts;
    }

    @Override
    public ArrayList<Order> getAllOdersByOwner(int owner_id,  boolean active) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Order> ts = null;

        if(owner_id<1)
            return ts;
        try {
            con = getConnection();

            String query = "Select o.* , count(*) as pcount " +
                            "from orders o left join orderdetails d on o.id=d.orderId , services s " +
                            "where s.id=d.servicesId and s.ownerId = ? ";

            if (active){
                query+=" and o.orderstatus<3 group by o.id ;";
            }else{
                query+=" and o.orderstatus>2 group by o.id ;";
            }
            
            ps = con.prepareStatement(query);
            ps.setInt(1, owner_id);
            rs = ps.executeQuery();
            ts = new ArrayList<Order>();
            while (rs.next()) {
                Order t = new Order();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("custId"));
                t.setOrderDate(rs.getDate("orderdate"));
                t.setOrderStatus(rs.getInt("orderstatus"));
                t.setPaymentType(rs.getInt("paymentType"));
                t.setTotalCost(rs.getDouble("total"));
                t.setServiceCount(rs.getInt("pcount"));
                if(t.getServiceCount()>0){
                    String query2 = "select d.*, s.title " +
                            "from orderdetails d , services s where d.servicesId=s.id and d.orderId = ? ;";
                    PreparedStatement ps2 = con.prepareStatement(query2);
                    ps2.setInt(1,t.getId());
                    ResultSet rs2 = ps2.executeQuery();
                    ArrayList<OrderDetails> servList= new ArrayList<OrderDetails>();
                    while (rs2.next()) {
                        OrderDetails od = new OrderDetails();
                        od.setId(rs2.getInt("id"));
                        od.setOrderId(rs2.getInt("orderId"));
                        od.setCost(rs2.getDouble("cost"));
                        od.setServiceId(rs2.getInt("servicesId"));
                        od.setServiceTitle(rs2.getString("title"));
                        servList.add(od); 
                    }
                    t.setOrderDetails(servList);
                }
                ts.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllOdersByUserId() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllOdersByUserId() method: " + e.getMessage());
            }
        }
        return ts;
    }

    @Override
    public ArrayList<Order> getAllOdersReported() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Order> ts = null;

        try {
            con = getConnection();

            String query = "Select o.* , count(*) as pcount " +
                            "from orders o left join orderdetails d on o.id=d.orderId , services s " +
                            "where s.id=d.servicesId and (o.orderstatus = 7 or o.orderstatus = 8) group by o.id ;";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            ts = new ArrayList<Order>();
            while (rs.next()) {
                Order t = new Order();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("custId"));
                t.setOrderDate(rs.getDate("orderdate"));
                t.setOrderStatus(rs.getInt("orderstatus"));
                t.setPaymentType(rs.getInt("paymentType"));
                t.setTotalCost(rs.getDouble("total"));
                t.setServiceCount(rs.getInt("pcount"));
                if(t.getServiceCount()>0){
                    String query2 = "select d.*, s.title " +
                            "from orderdetails d , services s where d.servicesId=s.id and d.orderId = ? ;";
                    PreparedStatement ps2 = con.prepareStatement(query2);
                    ps2.setInt(1,t.getId());
                    ResultSet rs2 = ps2.executeQuery();
                    ArrayList<OrderDetails> servList= new ArrayList<OrderDetails>();
                    while (rs2.next()) {
                        OrderDetails od = new OrderDetails();
                        od.setId(rs2.getInt("id"));
                        od.setOrderId(rs2.getInt("orderId"));
                        od.setCost(rs2.getDouble("cost"));
                        od.setServiceId(rs2.getInt("servicesId"));
                        od.setServiceTitle(rs2.getString("title"));
                        servList.add(od); 
                    }
                    t.setOrderDetails(servList);
                }
                ts.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllOdersByUserId() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllOdersByUserId() method: " + e.getMessage());
            }
        }
        return ts;
    }


    @Override
    public int updateOrderStatus(int order_id, int new_status) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String query = "UPDATE orders SET orderstatus = ? WHERE id = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, new_status);
            ps.setInt(2, order_id);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the updateOrderStatus() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the updateOrderStatus() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

}
