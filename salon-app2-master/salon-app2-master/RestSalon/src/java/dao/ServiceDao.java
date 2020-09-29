package dao;

import dto.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDao extends Dao implements IServiceDao {

    public ServiceDao() {
        super();
    }

    public ServiceDao(Connection conn) {
        super(conn);
    }

    public ServiceDao(String databaseName) {
        super(databaseName);
    }

    @Override
    public Service getServiceById(int service_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Service t = null;
        if(service_id<1)
            return t;
        try {
            con = getConnection();

            String query = "select * from services where id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, service_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int t_id = rs.getInt("id");
                String title = rs.getString("title");
                int ownerId = rs.getInt("ownerId");
                String details = rs.getString("details");
                double cost = rs.getDouble("cost");
                int status = rs.getInt("status");
                t =new Service(t_id, title, ownerId, details, cost,status);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getServiceById() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getServiceById() method: " + e.getMessage());
            }
        }
        return t;
    }

    @Override
    public ArrayList<Service> getAllServiceByOwnerId(int owner_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Service> ts = new ArrayList<Service>();
        if(owner_id<1)
            return ts;
        try {
            con = getConnection();

            String query = "select s.*, o.salonName, IFNULL(avg(r.rate),0) as srate, count(d.id) as pcount " +
                            "from services s " +
                            "join owners o on s.ownerid=o.ownerid " +
                            "left join servicedetails d on d.serviceId=s.id " +
                            "left join reviewers r on r.serviceId=s.id "+
                            "where s.ownerId = ? "+
                            "group by s.id ;";
            
            ps = con.prepareStatement(query);
            ps.setInt(1, owner_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                int t_id = rs.getInt("id");
                String title = rs.getString("title");
                int ownerId = rs.getInt("ownerId");
                String details = rs.getString("details");
                double cost = rs.getDouble("cost");
                int status = rs.getInt("status");
                Service s = new Service(t_id, title, ownerId, details, cost,status);
                s.setSalonName(rs.getString("salonname"));
                s.setAvgRate(rs.getDouble("srate"));
                s.setPicCount(rs.getInt("pcount"));
                if(s.getPicCount()>0){
                    String query2 = "select d.picturepath " +
                            "from servicedetails d where d.serviceId = ? ;";
                    PreparedStatement ps2 = con.prepareStatement(query2);
                    ps2.setInt(1, s.getId());
                    ResultSet rs2 = ps2.executeQuery();
                    ArrayList<String> imageList= new ArrayList<String>();
                    while (rs2.next()) {
                        imageList.add(rs2.getString("picturepath"));
                    }
                    s.setImageList(imageList);
                }
                ts.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllServiceByUserId() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllServiceByUserId() method: " + e.getMessage());
            }
        }
        return ts;
    }

    @Override
    public ArrayList<Service> getAllService(boolean forpublic) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Service> ts = new ArrayList<Service>();

        try {
            con = getConnection();

            String query = "select s.*, o.salonName, IFNULL(avg(r.rate),0) as srate, count(d.id) as pcount " +
                            "from services s " +
                            "join owners o on s.ownerid=o.ownerid " +
                            "left join servicedetails d on d.serviceId=s.id " +
                            "left join reviewers r on r.serviceId=s.id ";
            if(forpublic)
                query+="where s.status=1 ";
            
            query+="group by s.id ;";
            
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int t_id = rs.getInt("id");
                String title = rs.getString("title");
                int ownerId = rs.getInt("ownerId");
                String details = rs.getString("details");
                double cost = rs.getDouble("cost");
                int status = rs.getInt("status");
                Service s = new Service(t_id, title, ownerId, details, cost,status);
                s.setSalonName(rs.getString("salonname"));
                s.setAvgRate(rs.getDouble("srate"));
                s.setPicCount(rs.getInt("pcount"));
                if(s.getPicCount()>0){
                    String query2 = "select d.picturepath " +
                            "from servicedetails d where d.serviceId = ? ;";
                    PreparedStatement ps2 = con.prepareStatement(query2);
                    ps2.setInt(1, s.getId());
                    ResultSet rs2 = ps2.executeQuery();
                    ArrayList<String> imageList= new ArrayList<String>();
                    while (rs2.next()) {
                        imageList.add(rs2.getString("picturepath"));
                    }
                    s.setImageList(imageList);
                }
                ts.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllServiceByUserId() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllServiceByUserId() method: " + e.getMessage());
            }
        }
        return ts;
    }

    @Override
    public int updateService(Service s) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;
        if (s == null) {
            return 0;
        }
        try {
            con = getConnection();

            String query = "UPDATE services SET title = ?, details =?, cost=? WHERE id = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, s.getTitle());
            ps.setString(2, s.getDetails());
            ps.setDouble(3, s.getCost());
            ps.setInt(4,s.getId());
            
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the upddateSerivce() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the upddateSerivce() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    
    @Override
    public int addNewService(Service s) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;
        if (s == null) {
            return 0;
        }
        try {
            con = getConnection();

            String query = "INSERT INTO "
                    + "services(title,"
                    + "ownerId,"
                    + "details,"
                    + "cost )"
                    + "VALUES (?,?,?,?);";

            ps = con.prepareStatement(query);
            ps.setString(1, s.getTitle());
            ps.setInt(2, s.getOwnerId());
            ps.setString(3, s.getDetails());
            ps.setDouble(4, s.getCost());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the addNewSerivce() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the addNewSerivce() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    @Override
    public int delService(int service_id) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;
        if(service_id<1)
            return 0;
        try {
            con = getConnection();

            String query = "DELETE FROM services WHERE id = ?;";

            ps = con.prepareStatement(query);
            ps.setInt(1, service_id);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the delService() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the delService() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    @Override
    public int updateServiceStatus(int service_id, int new_status) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            String query = "UPDATE services SET status = ? WHERE id = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, new_status);
            ps.setInt(2, service_id);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception occured in the updateServiceStatus() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the updateServiceStatus() method");
                e.getMessage();
            }
        }

        return rowsAffected;
    }

    

}
