package dao;

import dto.Notifications;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationDao extends Dao implements INotificationDao{

    public NotificationDao() {
        super();
    }
    
    public NotificationDao(Connection conn) {
        super(conn);
    }

    public NotificationDao(String databaseName) {
        super(databaseName);
    }
    
    @Override
    public Notifications getNotificationByOrderId(int order_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Notifications t = null;

        if(order_id<1)
            return t;
        try {
            con = getConnection();

            String query = "Select * from notification where orderid = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, order_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int t_id = rs.getInt("id");
                Date event_date = rs.getDate("eventDate");
                int event_status = rs.getInt("eventStatus");
                String event_detail = rs.getString("eventDetail");
                t = new Notifications(t_id, order_id, event_date, event_status,event_detail);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getOrderTrackingByOrderId() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getOrderTrackingByOrderId() method: " + e.getMessage());
            }
        }
        return t;
    }

    @Override
    public int addNewNotification(Notifications a) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        if (a == null)
            return 0;
        try {
            con = getConnection();

            String query = "INSERT INTO "
                    + "notification(orderId,"
                    + "eventDate,"
                    + "eventStatus,"
                    + "eventDetail)"
                    + "VALUES (?,?,?,?);";

            ps = con.prepareStatement(query);
            ps.setInt(1, a.getOrderId());
            ps.setDate(2, a.getEventDate());
            ps.setInt(3, a.getEventStatus());
            ps.setString(4, a.getEventDetails());

            rowsAffected = ps.executeUpdate();

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

 
}
