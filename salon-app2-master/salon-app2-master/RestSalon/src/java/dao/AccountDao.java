package dao;

import dto.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.SecHash;

public class AccountDao extends Dao implements IAccountDao {

    public AccountDao(Connection conn) {
        super(conn);
    }

    public AccountDao(String databaseName) {
        super(databaseName);
    }

    public AccountDao() {
        super();
    }

    @Override
    public int checkUserByEmailPassword(Account a) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int user_id = 0;

        if (a == null || !a.IsValid()) {
            return user_id;
        }

        try {
            con = getConnection();

            String query = "select userid, email from users where email = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, a.getEmail());
            String pass = SecHash.getSecurePassword(a.getPassword(), a.getEmail());
            ps.setString(2, pass);
            rs = ps.executeQuery();

            if (rs.next()) {
                user_id = rs.getInt("userid");
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the checkUserByUsernamePassword() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the checkUserByUsernamePassword() method: " + e.getMessage());
            }
        }
        return user_id;
    }

    @Override
    public int updatePassword(String username, String oldPassword, String newPassword) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String query = "UPDATE users SET password = ? WHERE password = ? and email = ?";
            String pass2 = SecHash.getSecurePassword(newPassword, username);
            String pass1 = SecHash.getSecurePassword(oldPassword, username);
            ps = con.prepareStatement(query);
            ps.setString(1, pass2);
            ps.setString(2, pass1);
            ps.setString(3, username);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the updatePassword() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the updatePassword() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    @Override
    public int createNewAccount(Account a) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        if (!a.IsValid()) {
            return 0;
        }

        try {
            con = getConnection();

            String query = "INSERT INTO users(password,email,fullname,userType,securityQuestionId,"
                    + "securityAnswer,phone) VALUES (?,?,?,?,?,?,?);";

            String pass = SecHash.getSecurePassword(a.getPassword(), a.getEmail());
            ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, pass);
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getFullname());
            ps.setInt(4, a.getUserType());
            ps.setInt(5, a.getSecurityQuestionId());
            String answ = SecHash.getSecurePassword(a.getSecurityAnswer(), a.getEmail());

            ps.setString(6, answ);
            ps.setString(7, a.getPhone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rowsAffected = rs.getInt(1);
                //System.out.println("new Key:"+rowsAffected);
            }
            if (rowsAffected != 0) {
                if (a.getUserType() == 2) {
                    query = "INSERT INTO owners (ownerId,salonname) values (?,?);";
                    ps = null;
                    ps = con.prepareStatement(query);
                    ps.setInt(1, rowsAffected);
                    ps.setString(2, a.getSalonName());
                    ps.executeUpdate();

                } else if (a.getUserType() == 1) {
                    query = "INSERT INTO customers (custId) values(?);";
                    ps = null;
                    ps = con.prepareStatement(query);
                    ps.setInt(1, rowsAffected);
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the createNewAccount() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the addUser() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    @Override 
    public Account getUserByServiceId(int serviceId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account t = null;

        if (serviceId < 1) {
            return t;
        }
        try {
            con = getConnection();

            String query = "Select u.* from users u, services s where u.userid = s.ownerId and s.id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, serviceId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int t_id = rs.getInt("userid");
                String email = rs.getString("email");
                String fullname = rs.getString("fullname");
                int userType = rs.getInt("userType");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String location = rs.getString("location");
                String notes = rs.getString("notes");
                int status = rs.getInt("status");
                int sec_question = rs.getInt("securityQuestionId");
                String sec_answer = rs.getString("securityAnswer");
                t = new Account(t_id, email, fullname, userType, status, sec_question,
                        sec_answer, address, phone, location, notes);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getUserById() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getUserById() method: " + e.getMessage());
            }
        }
        return t;
    }
      
    @Override
    public Account getUserById(int user_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account t = null;

        if (user_id < 1) {
            return t;
        }
        try {
            con = getConnection();

            String query = "Select * from users where userid = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int t_id = rs.getInt("userid");
                String email = rs.getString("email");
                String fullname = rs.getString("fullname");
                int userType = rs.getInt("userType");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String location = rs.getString("location");
                String notes = rs.getString("notes");
                int status = rs.getInt("status");
                int sec_question = rs.getInt("securityQuestionId");
                String sec_answer = rs.getString("securityAnswer");
                t = new Account(t_id, email, fullname, userType, status, sec_question,
                        sec_answer, address, phone, location, notes);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getUserById() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getUserById() method: " + e.getMessage());
            }
        }
        return t;
    }

    @Override
    public boolean isEmailUnique(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        int counts = 0;
        ResultSet rs = null;
        try {
            con = getConnection();

            String query = "select count(*) as counts from users WHERE  email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                counts = rs.getInt("counts");
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the isEmailUnique() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the isEmailUnique() method");
                e.getMessage();
            }
        }

        return counts == 0;
    }

    @Override
    public int getUserStatus(int user_id) {
        Connection con = null;
        PreparedStatement ps = null;
        int status = 0;
        ResultSet rs = null;
        try {
            con = getConnection();

            String query = "select status from users WHERE userid = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, user_id);

            rs = ps.executeQuery();

            if (rs.next()) {
                status = rs.getInt("status");
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getUserStatus() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the getUserStatus() method");
                e.getMessage();
            }
        }

        return status;
    }

    @Override
    public ArrayList<Account> getAllusers() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Account> ts = new ArrayList<Account>();

        try {
            con = getConnection();

            String query = "select * from users where userType!=1;";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int t_id = rs.getInt("userid");
                String email = rs.getString("email");
                String fullname = rs.getString("fullname");
                int userType = rs.getInt("userType");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String location = rs.getString("location");
                String notes = rs.getString("notes");
                int status = rs.getInt("status");
                int sec_question = rs.getInt("securityQuestionId");
                String sec_answer = rs.getString("securityAnswer");
                Account t = new Account(t_id, email, fullname, userType, status, sec_question,
                        sec_answer, address, phone, location, notes);
                ts.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllusers() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllusers() method: " + e.getMessage());
            }
        }
        return ts;
    }

    @Override
    public int updateUserStatus(int user_id, int new_status) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String query = "UPDATE users SET status = ? WHERE userid = ? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, new_status);
            ps.setInt(2, user_id);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the updateUserStatus() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the updateUserStatus() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    @Override
    public int updateUser(Account a) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String query = "UPDATE users SET fullname = ?, phone=?, address=?,location=? WHERE email = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, a.getFullname());
            ps.setString(2, a.getPhone());
            ps.setString(3, a.getAddress());
            ps.setString(4, a.getLocation());
            ps.setString(5, a.getEmail());
            rowsAffected = ps.executeUpdate();
            if (a.getUserType() == 2) {
                query = "UPDATE owners SET salonname=?;";
                ps = con.prepareStatement(query);
                ps.setString(1, a.getSalonName());
                rowsAffected += ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the updateUser() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the updateUser() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

}
