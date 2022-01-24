
package javaserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DbHelper {
    
    static String username = "root";
    static String password = "root";
    static String dbUrl = "jdbc:mysql://localhost:3306/kullanicilar";
    
    public Connection getConnection() throws SQLException {
        return  (Connection) DriverManager.getConnection(dbUrl, username, password);
    }
    
    public void ShowError(SQLException exception) {
        System.out.println("Error : " + exception.getMessage());
        System.out.println("Error Code : " + exception.getErrorCode());
    }
    
   
}
