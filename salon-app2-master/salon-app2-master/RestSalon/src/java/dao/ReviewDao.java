
package dao;

import dto.Review;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReviewDao extends Dao implements IReviewDao {
    public ReviewDao() {
        super();
    }
    
    public ReviewDao(Connection conn) {
        super(conn);
    }

    public ReviewDao(String databaseName) {
        super(databaseName);
    }

    @Override
    public int addNewReview(Review a) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        if (a == null) {
            return 0;
        }
        try {
            con = getConnection();

            String query = "INSERT INTO "
                    + "reviewers (custId,"
                    + "orderId,"
                    + "serviceId,"
                    + "rate, comments) "
                    + " VALUES (?,?,?,?,?);";

            ps = con.prepareStatement(query);
            ps.setInt(1, a.getCustId());
            ps.setInt(2, a.getOrderId());
            ps.setInt(3, a.getServiceId());
            ps.setInt(4, a.getSrate());
            ps.setString(5, a.getComments());

            rowsAffected = ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Exception occured in the addNewReview() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the addNewReview() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }
    
}