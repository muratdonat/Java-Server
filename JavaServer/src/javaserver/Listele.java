
package javaserver;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Listele {
   
    static Connection connection = null;
    static DbHelper db = new DbHelper();
    static PreparedStatement statement = null;
    static Statement st = null;
    static String isim, mail, sifre;
    static List<String> mailList = new ArrayList<>();
    static List<String> sifreList = new ArrayList<>();
 /*       
    public static void Show() {
        try {
            connection = (Connection) db.getConnection();
            String sql = "select * from kullanicilar.bilgiler";
            statement = connection.prepareStatement(sql);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String isim = resultSet.getString("isim");
                String email = resultSet.getString("email");
                mailList.add(email);
                String sifre = resultSet.getString("sifre");
                sifreList.add(sifre);
                //System.out.println("İsim -> " + isim + " Mail -> " + email + " Sifre -> " + sifre);
  
                
            }
            
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Listele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  */  
    public static void theQuery(String query) throws SQLException {    
        connection = (Connection) db.getConnection();
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String mail = resultSet.getString("email");
            mailList.add(mail);
            String sifre = resultSet.getString("sifre");
            sifreList.add(sifre);
        }
        statement.close();
        connection.close();
    }
    
}
