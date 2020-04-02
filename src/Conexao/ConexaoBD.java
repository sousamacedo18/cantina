/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import View.Arquivo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Home
 */

public class ConexaoBD {
     public Statement stmt;
    public ResultSet rs;
   private String driver = "com.mysql.jdbc.Driver";
   //private String caminho = "jdbc:mysql://10.1.1.251:3306/refeitorioifto";
  //private String usuario = "root";
 //private String senha = "1234";
 public String caminho;
 private String usuario;
 private String porta;
 private String  nomebanco;
 private String senha;
 private String local;
 
 
    public Connection connection;
    
      public void lerJson(){
        JSONObject resultado=null;
        
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("conf.json"));
            
            resultado=(JSONObject)obj;
                    nomebanco = (String)resultado.get("nomebanco");
                    porta     = (String)resultado.get("porta");
                    usuario       = (String)resultado.get("usuario");
                    senha  = (String)resultado.get("senha");
                    local     = (String)resultado.get("local");
                                      
        } 
        catch (FileNotFoundException ex) {ex.printStackTrace(); }
        catch (IOException ex) {ex.printStackTrace(); }
        catch (Exception ex) {ex.printStackTrace(); }
        caminho="jdbc:mysql://"+local+":"+porta+"/"+nomebanco;
          //System.out.println(caminho+usuario+senha);

   }  

    public void Conectar(){        
        lerJson();
        try {

            System.setProperty("jdbc.Drivers", driver);

           connection = DriverManager.getConnection(caminho, usuario, senha);
           
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "Erro de conexão!\nErro: " + ex.getMessage());
        }
    }

       
    public void ExecutarSQL(String sql){
        
        try {
            //JOptionPane.showMessageDialog(null, sql);
            
            stmt = connection.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY); // Statement para diferenciação de maiúsculo de menúsculo e percorrer tabela do início ao fim e vice-versa.
            rs = stmt.executeQuery(sql);
            
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "Erro ExecutarSQL!\nErro: " + ex.getMessage());
            
        }
        
    }
    
    public void Desconectar(){
        
        try {
            
            connection.close();
            
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "Erro ao desconectar!\nErro: " + ex.getMessage());
            
        }
        
    }   
}
