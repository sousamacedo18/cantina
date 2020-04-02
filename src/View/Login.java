/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.UsuarioController;
import Dao.AcessoDao;
import Dao.UsuarioDao;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Home
 */
public class Login extends javax.swing.JFrame {
        public String caminho;
        private String usuario;
        private String porta;
        private String  nomebanco;
        private String senha;
        private String local;
        
     private Point point = new Point();
    ConexaoBD conexao = new ConexaoBD();
    UsuarioDao usuariosdao = new UsuarioDao();
    UsuarioController usuarios = new UsuarioController();
    
    private String emailBanco="";
    private String senhaBanco=null;
    private String senhaCriptografada="";
    private Integer idusuario;
    private int bloqueado;
    /**
     * Creates new form Login
     */
    public Login() throws IOException {
        initComponents();

        URL caminhoIcone = getClass().getResource("/ico/if_Food_572824.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        lerLogin();
        lerJson();
        GerarBackup();

        
    }
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
    public void GerarBackup () throws IOException{
            LocalDateTime dataAgora = LocalDateTime.now();
            DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

            String nome = dataAgora.format(dataFormato);

                 Runtime bck = Runtime.getRuntime();
                    bck.exec("C:/xampp/mysql/bin/mysqldump.exe -v -v -v --host="+local+" --user="+usuario+" --password="+senha+" --port="+porta+" --opt --routines --triggers --protocol=tcp --force --allow-keywords --compress --add-drop-table --default-character-set=latin1 --hex-blob --result-file=backup/"+ nome +".sql --databases "+nomebanco);

    } 
      public void diaExtensoHoraEntrada() throws IOException{
        int segundos;
        int minutos;
        int horas;
        int segonds = 0;  
        int ontem;
        Date d  = new Date();
        String[] semanaExtenso = {"Domingo","Segunda-Feira","Terça-Feira","Quarta-Feira","Quinta-Feira","Sexta-Feira","Sabado"};   
        Calendar data;
        
                data = Calendar.getInstance();
                int nu = data.get(data.DAY_OF_WEEK);
                ontem = data.get(Calendar.DAY_OF_MONTH);
                
                data = Calendar.getInstance();
                horas = data.get(Calendar.HOUR_OF_DAY);
                minutos = data.get(Calendar.MINUTE);
                segundos = data.get(Calendar.SECOND); 
                //LbHoraEntrada.setText(horas+":"+minutos);
                 segonds=(horas*60+minutos)*60+segundos;
                Date datasistema = new Date();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                
                        System.out.println("passou aqui");
        
  }
  public static String[]readArray(String file){
      int ctr=0;
      String[] words = null;
      try{
         Scanner s1 = new Scanner(new File(file)); 
         while (s1.hasNextLine()){
          ctr= ctr+1;
          s1.next();
      }
         words = new  String[ctr];
         
         
      }catch(FileNotFoundException e){
          
      }
        
        return words;
  }
   public void guardarLogin(){
       String arq = "logar.txt";
       if(!new File(arq).exists()) {
           JOptionPane.showMessageDialog(null, "O arquivo que guarda o login, não foi encontrado");
       }else{
       
       String texto="";
       texto=txtEmail.getText().trim();
       if(Arquivo.Write(arq, texto)){
           
       }else{
           JOptionPane.showMessageDialog(null, "Arquivo de Entrada Criado!!!!");
       }
       }
   }
  public static void filelist() throws IOException
    {
        String path = new File("./backup").getCanonicalPath();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

    for (File file : listOfFiles)
       
    {
        if (file.isFile())
        {
            String[] filename = file.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
           // if(filename[0].equalsIgnoreCase("a")) //matching defined filename
                System.out.println("File exist: "+filename[1]+"."+filename[1]); // match occures.Apply any condition what you need
               // System.out.println("File exist: "+filename[0]+"."+filename[1]); // match occures.Apply any condition what you need
               file.delete();
        }
     }
}
   public void lerLogin(){
       String arq = "logar.txt";
       if(!new File(arq).exists()) {
           JOptionPane.showMessageDialog(null, "O arquivo que guarda o login, não foi encontrado");
       }else{
       String texto=Arquivo.Read(arq);
       if(texto.isEmpty()){
           JOptionPane.showMessageDialog(null, "Não foi possível ler o login guardado");
       }else{
           txtEmail.setText(texto);
           if(txtEmail.getText().equals("")){
               txtEmail.requestFocus();
           }
           else{
              txtSenha.requestFocus();
           }
       }
       }
   }
   
   public static String criptografia(String original) throws UnsupportedEncodingException, UnsupportedEncodingException
	{
		String senha = null;
		MessageDigest algoritmo;
		byte messageDigest[];
		StringBuilder hexString;
		try {
			//algoritmo =MessageDigest.getInstance("SHA-256");// 64 letras
			algoritmo = MessageDigest.getInstance("MD5");  // 32 letras
			messageDigest = algoritmo.digest(original.getBytes("UTF-8"));
			hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			senha = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	       //System.out.println("Senha normal: "+original+" - Senha criptografada: "+senha);
		return senha;
	}
   public void logarSistema() throws SQLException, InterruptedException, IOException{
       PrincipalForm principal = new PrincipalForm();
              UsuarioDao dao = new UsuarioDao();
              idusuario=0;
              bloqueado=0;
             btnEntrar.setEnabled(true);

              
        		
              if(txtEmail.getText().equals("")|| txtSenha.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!!");
               } else{ 
                  try {
                     senhaCriptografada=criptografia(txtSenha.getText().trim());
                 } catch (UnsupportedEncodingException ex) {
                     Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                 }                 
                 if(usuariosdao.Logar(txtEmail.getText().toLowerCase(), senhaCriptografada)==false){
                        JOptionPane.showMessageDialog(null, "Senha incorreta!");
                        txtSenha.requestFocus();
                   }else{

//                  System.out.println("passou aqui");

		    for (UsuarioController u : dao.LogarUsuario(txtEmail.getText().toLowerCase(), senhaCriptografada)) {
                    emailBanco=u.getEmailusuario();
                    senhaBanco=u.getSenhausuario();
                    idusuario=u.getIdusuario();
                    bloqueado=u.getBloquear();
//                            System.out.println(emailBanco);
//                            System.out.println(senhaBanco);
                  }
                   
                    if(emailBanco.equals(txtEmail.getText().trim()) && senhaBanco.equals(senhaCriptografada)){
//                    System.out.println("passou aqui");
                    if(bloqueado==1){
                        JOptionPane.showMessageDialog(null, "Desculpe, Procure o Adm do Sistema, Você está sem Permissão para Acessar o Sistema!");
                    }else{
                   
                   AcessoDao Adao = new AcessoDao();
                   
                   principal.listaAcesso(idusuario);
                
                   principal.setVisible(true);
                   guardarLogin();
                   this.dispose();
                   btnEntrar.setEnabled(false);
                   }
                 
              }
                else{
//                    System.out.println("passou aqui tambem");
               JOptionPane.showMessageDialog(null, "Email ou Senha incorretos!!");
        
                }                    
                 }
              }
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        btnEntrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbFechar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 102));
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(50, 168, 82), 2, true));
        jPanel1.setForeground(new java.awt.Color(204, 255, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/email-5-32.png"))); // NOI18N

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });

        txtSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenhaFocusGained(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/padlock-12-32.png"))); // NOI18N

        btnEntrar.setBackground(new java.awt.Color(0, 84, 140));
        btnEntrar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setText("Entrar");
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(50, 168, 82));

        lbFechar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbFechar.setForeground(new java.awt.Color(255, 255, 255));
        lbFechar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFechar.setText("X");
        lbFechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbFecharMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbFecharMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbFecharMouseExited(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 28)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Login");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbFechar))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .addComponent(txtEmail))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseExited
//        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
//        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.white);
    }//GEN-LAST:event_lbFecharMouseExited

    private void lbFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseEntered
//        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
//        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.yellow);
    }//GEN-LAST:event_lbFecharMouseEntered

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lbFecharMouseClicked

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        try {
            try {
                // TODO add your handling code here:
                logarSistema();
                //            String[] words = readArray("conf.txt");
                //            for(int i=0;i<words.length;i=i++){
                //                System.out.println(words[i]);
            } catch (InterruptedException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

    }//GEN-LAST:event_btnEntrarActionPerformed

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                logarSistema();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void txtSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSenhaFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        // TODO add your handling code here:
        usuariosdao.usuarioParaLogar(txtEmail.getText().trim());
        //       txtEmail.requestFocus();
    }//GEN-LAST:event_txtEmailFocusLost

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        
  
    }//GEN-LAST:event_formMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
             point.x = evt.getX();
             point.y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        Point p = this.getLocation();
        this.setLocation(p.x +evt.getX()- point.y,p.y +evt.getY()- point.y);
    }//GEN-LAST:event_formMouseDragged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Login().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
