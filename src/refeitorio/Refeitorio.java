/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refeitorio;
import View.ConfConexaoForm;
import View.Login;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Home
 */
public class Refeitorio {
   public Boolean verConexao(){
       File f= new File("conf.json");
       if(f.exists()){
           return true;
       }else{
   
           return false;
       }

   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        // TODO code application logic here
      
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                    } catch (ClassNotFoundException ex) {
            Logger.getLogger(Refeitorio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Refeitorio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Refeitorio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Refeitorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        Refeitorio r = new Refeitorio();
        if(r.verConexao()){
                    Login log = new Login();
                    log.setVisible(true);            
        }else{
          ConfConexaoForm conf = new ConfConexaoForm(null,true);
         conf.setVisible(true);           
        }

       
        
    }
    
}
