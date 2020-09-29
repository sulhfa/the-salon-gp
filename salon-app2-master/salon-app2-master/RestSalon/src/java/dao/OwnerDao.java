package dao;

import dto.Owner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerDao extends Dao implements IOwnerDao {

    @Override
    public Owner getById(int owner_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Owner t = null;

        if (owner_id < 1) {
            return t;
        }
        try {
            con = getConnection();

            String query = "Select * from owners where ownerId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, owner_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int ownerid = rs.getInt("ownerId");
                String salonanme = rs.getString("SalonName");
                t = new Owner(ownerid, salonanme);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getById() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getById() method: " + e.getMessage());
            }
        }
        return t;
    }

}
