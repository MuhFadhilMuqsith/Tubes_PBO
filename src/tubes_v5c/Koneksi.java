/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes_v5c;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author _MFMq_PC
 */
public class Koneksi {
    private static Connection conn;
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "db_logistik";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/" + DB_NAME;
    private static final String DB_UNAME = "logistik";
    private static final String DB_PASS = "logistik123";
    
    public static Connection bukaKoneksi() {
        if (conn == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_UNAME, DB_PASS);
            }
            catch (ClassNotFoundException e){
                System.err.format("Class Not Found !!!");
            }
            catch (SQLException e){
                 System.err.format("SQL State : %s\n%s", e.getSQLState(), e.getMessage());
            }
            catch (Exception e){
                 e.printStackTrace();
            }
        }
        return conn;
    }
    
    
}
