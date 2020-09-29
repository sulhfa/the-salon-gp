package dao;

import dto.SecurityQuestion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SecurityQuestionDao extends Dao implements ISecurityQuestion {

    @Override
    public ArrayList<SecurityQuestion> getAll() {
       Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<SecurityQuestion> ts = new ArrayList<SecurityQuestion>();

        try {
            con = getConnection();

            String query = "Select * from securityquestions;";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int question_id = rs.getInt("id");
                String question_txt = rs.getString("title");
                SecurityQuestion s = new SecurityQuestion(question_id, question_txt);
                ts.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAll() method: " + e.getMessage());
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

}
