/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.ConexaoDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static java.util.Collections.list;
import java.util.List;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Home
 */
public class ConexaoController {
    public void SalvarConfiguracoes(ConexaoDao conexao){
                
                
               
                JSONObject objetojson = new JSONObject();
                FileWriter writeFile = null;
                System.out.println("aqui: "+conexao.getBanco());
                objetojson.put("banco",conexao.getBanco());
                objetojson.put("nomebanco",conexao.getNomebanco());
                objetojson.put("driver",conexao.getDriver());
                objetojson.put("porta",conexao.getPorta());
                objetojson.put("local",conexao.getLocal());
                objetojson.put("usuario",conexao.getUsuario());
                objetojson.put("senha",conexao.getSenha());

                
        try {
            writeFile = new FileWriter("conf.json");
        } catch (IOException ex) {
            Logger.getLogger(ConexaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.println(objetojson.toJSONString());
            writeFile.write(objetojson.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(ConexaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
                JOptionPane.showMessageDialog(null,"Configurações salvas com sucesso!!!!");
    }
    
        public JSONObject lerarquivo(){
        JSONObject resultado=null;
        
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("conf.json"));
            resultado=(JSONObject)obj;
        } 
        catch (FileNotFoundException ex) {ex.printStackTrace(); }
        catch (IOException ex) {ex.printStackTrace(); }
        catch (ParseException ex) {ex.printStackTrace(); }
        catch (Exception ex) {ex.printStackTrace(); }
        
        
        return resultado;
    }
}
