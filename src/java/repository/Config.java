package repository;

import java.sql.*;
import java.util.logging.Logger;

/**
 *
 * @author erickbaron
 */
public class Config {
    
          private static Connection conn = null;
    
    public static Connection getConnection(){
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";
        
        if (conn == null){
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException | ClassNotFoundException e) {
                 Logger.getLogger(e.getMessage());
            }   
        }
        return conn;
    }
    
    public static void closeConnection(){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(e.getMessage());
            }
        }
    }
    
        public static void closeStatement(Statement sttmt){
        if (sttmt != null){
            try {
                sttmt.close();
            } catch (SQLException e) {
                 Logger.getLogger(e.getMessage());
            }
        }
    }
    
    public static void closeResultSet(ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                Logger.getLogger(e.getMessage());
            }
        }
    }
    
   
}
