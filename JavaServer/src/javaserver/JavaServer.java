package javaserver;

import java.sql.Connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static javaserver.Listele.connection;
import static javaserver.Listele.statement;

public class JavaServer {
        public static void Insert(String name, String mail, String password) throws SQLException{
               Connection connection = null;
               DbHelper db = new DbHelper();
               PreparedStatement statement = null;

               try {
                   connection = (Connection) db.getConnection();
                   String sql = "insert into kullanicilar.bilgiler (isim,email,sifre)"
                           + "values (?,?,?)";
                   statement = connection.prepareStatement(sql);
                   statement.setString(1, name);
                   statement.setString(2, mail);
                   statement.setString(3, password);

                   statement.executeUpdate();
                   statement.close();
                   connection.close();
               } catch (Exception e) {
                   System.out.println("Error : " + e.getMessage());
               } 


           }
        
        public static void theQuery(String query) throws SQLException {    
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kullanicilar", "root", "root");
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        

    }

    public static void main(String[] args) {
        int port = 3333;        
 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Server is listening on port " + port);
            String ad, soyad, mail, sifre;
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
 
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
 
 
                String gelenVeri = reader.readLine();
                String[] kayitDizi = gelenVeri.split("-");
                   if (kayitDizi.length == 2) {
                    mail = kayitDizi[0];
                    sifre = kayitDizi[1];
                    
                    System.out.println("Login |Mail : " + mail);
                    System.out.println("Login |Sifre : " + sifre);

                    
                       try {
                           String query = "select * from kullanicilar.bilgiler where email='" + mail + "'";
                           Listele.theQuery(query);
                           System.out.println(Listele.mailList);
                           System.out.println(Listele.sifreList);
                           for (String e : Listele.mailList) {
                                if (e.equals(mail)) {             
                                    for (String sif : Listele.sifreList) {
                                        if (sif.equals(sifre)) {
                                            writer.println("true");
                                            Listele.sifreList.clear();
                                            Listele.mailList.clear();
                                            break;
                                }
                            }
                        } 
                    }
                           System.out.println("1");
                       } catch (Exception e) {
                           System.out.println("hataa");
                       }
          
                } else if (kayitDizi.length == 4) {
                    ad = kayitDizi[0];
                    soyad = kayitDizi[1];
                    mail = kayitDizi[2];
                    sifre = kayitDizi[3];
                    
                    try {
                        Insert(ad, mail, sifre);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println("Ad : " + ad);
                    System.out.println("Soyad : " + soyad);
                    System.out.println("Mail : " + mail);
                    System.out.println("Sifre : " + sifre);
                    
                    writer.println("Başarıyla kaydoldunuz.");
                    
                } 
                socket.close();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        
    }
}