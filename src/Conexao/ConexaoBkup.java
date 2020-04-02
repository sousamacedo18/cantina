/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Home
 */
public class ConexaoBkup {
    static Connection con = null;
    public static Connection conectar(){
        try {
            
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/refeitorioifto","root","");
        }catch(SQLException e){
            System.out.println(e.getMessage()+"aqui!!!");
                  
        }
        return con;
}
}
