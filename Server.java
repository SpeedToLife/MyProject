/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Лена
 */
public class Server implements Serialisable {
private static String url = "jdbc:mysql://localhost:3306/vklady";
    private static String user = "root";
    private static String password = "123456";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    static private ServerSocket server;
    static private Socket connection;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;
  
    public static void main(String[] args) {
        String query = "select count(*) from vklady";
 
        try {
            con = DriverManager.getConnection(url, user, password);
 
            stmt = con.createStatement();
 
            rs = stmt.executeQuery(query);
 
            while (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Total number of vklady in the table : " + count);
            }
 
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        
//        int port = 3000;
//        try {
//            ServerSocket ss = new ServerSocket(port); 
//         System.out.println("Waiting for a client...");
//
//         Socket socket = ss.accept(); 
//         System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
//         System.out.println();
//
//         InputStream sin = socket.getInputStream();
//         OutputStream sout = socket.getOutputStream();
//
//         DataInputStream in = new DataInputStream(sin);
//         DataOutputStream out = new DataOutputStream(sout);
//
//         String line = null;
//         while(true) {
//           line = in.readUTF(); 
//           System.out.println("The dumb client just sent me this line : " + line);
//           System.out.println("I'm sending it back...");
//           out.writeUTF(line); 
//           out.flush(); 
//           System.out.println("Waiting for the next line...");
//           System.out.println();
//         }
//      } catch(Exception x) { x.printStackTrace(); }
    } 
    public void run() {
        try {
            server = new ServerSocket(8000, 10);
            while(true){
                connection = server.accept();
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                output.writeObject("Incoming: "+(String)input.readObject());
                }
                } catch (ClassNotFoundException e) {
                } catch (IOException e) {
                }
        
            }
}
