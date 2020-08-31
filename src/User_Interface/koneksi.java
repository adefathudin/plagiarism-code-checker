/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User_Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class koneksi { // koneksi ke database
    private static Connection mysqlkonek;
    public static Connection koneksiDB() throws SQLException {
        if(mysqlkonek==null){
            try {
                String DB="jdbc:mysql://localhost:3306/adefathudin_arif";
                String user="adefathudin";
                String pass="assu";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                mysqlkonek = (Connection) DriverManager.getConnection(DB,user,pass);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"gagal koneksi");
            }
        }
        return mysqlkonek;
    }
}