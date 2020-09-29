package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {

    private String databaseName;
    // Included to allow for dependency injection
    private Connection con;

    public Dao() {
        this.databaseName = "salondb";
    }

    public Dao(Connection con) {
        this.con = con;
    }

    public Dao(String databaseName) {
        this.databaseName = databaseName;
    }

    public Connection getConnection() {
        // If there was no connection provided, make one
        if (con == null) {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url    = "jdbc:mysql://localhost:3306/" + this.databaseName;
            String username = "root";
            String password = "test.com";
            Connection tmp_con = null;
            try {
                Class.forName(driver);
                tmp_con = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException ex1) {
                System.out.println("Failed to find driver class " + ex1.getMessage());
                System.exit(1);
            } catch (SQLException ex2) {
                System.out.println("Connection failed " + ex2.getMessage());
                System.exit(2);
            }

            return tmp_con;
        }
        // If there was a connection provided, use that one
        return con;
    }

    public void freeConnection() {
        try {
            if (this.con != null) {
                this.con.close();
                this.con = null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public void freeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public boolean isFreeConnection(){
        return con==null;
    }

}
