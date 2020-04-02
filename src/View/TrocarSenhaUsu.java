/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Dao.UsuarioDao;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class TrocarSenhaUsu extends javax.swing.JDialog {
private int retorno = 0;
private String senhaatual;
private String senhanova;

    public int getRetorno() {
        return retorno;
    }

    public String getSenhaatual() {
        return senhaatual;
    }

    public String getSenhanova() {
        return senhanova;
    }

    /**
     * Creates new form TrocarSenha
     */
    public TrocarSenhaUsu(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtSenhaAtual.requestFocus(true);
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
   public void confirmaralteracao() throws UnsupportedEncodingException{
       if(txtNovaSenha.getText().equals("")|| txtSenhaAtual.getText().equals("")|| txtNovaSenha1.getText().equals("")){
           JOptionPane.showMessageDialog(this, "Preencha todas os campos!!!");
       }else{
       senhanova=criptografia(txtNovaSenha.getText());
       if(senhanova.equals(senhaatual)|| senhanova.equals("")){
           JOptionPane.showMessageDialog(null, "Escolha outra senha diferente da atual");
           txtNovaSenha1.requestFocus(true);
       }
       else{
           JOptionPane.showMessageDialog(null, "Senha alterada, lembre-se de usar sua nova senha");
           retorno=1;
           senhanova=txtNovaSenha.getText();
           this.dispose();
       }
       }
   }
   public void setarSenhaAtual(int id) throws SQLException{
       UsuarioDao dao = new UsuarioDao();
      senhaatual=dao.pegarsenha(id); 
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JP_PRINCIPAL = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSenhaAtual = new javax.swing.JPasswordField();
        txtNovaSenha1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNovaSenha = new javax.swing.JPasswordField();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        JP_PRINCIPAL.setBackground(new java.awt.Color(60, 77, 87));

        jPanel1.setBackground(new java.awt.Color(50, 168, 82));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mudar senha de acesso");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(64, 64, 64)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Senha Atual");

        txtSenhaAtual.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSenhaAtual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaAtualKeyPressed(evt);
            }
        });

        txtNovaSenha1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNovaSenha1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNovaSenha1KeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Senha");

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Repetir Senha");

        txtNovaSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNovaSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNovaSenhaKeyPressed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 51, 0));
        btnSalvar.setText("Salvar Alterações");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JP_PRINCIPALLayout = new javax.swing.GroupLayout(JP_PRINCIPAL);
        JP_PRINCIPAL.setLayout(JP_PRINCIPALLayout);
        JP_PRINCIPALLayout.setHorizontalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel2))
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(18, 18, 18)
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNovaSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(btnSalvar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JP_PRINCIPALLayout.setVerticalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNovaSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
    try {
        // TODO add your handling code here:
        confirmaralteracao();
    } catch (UnsupportedEncodingException ex) {
        Logger.getLogger(TrocarSenhaUsu.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtSenhaAtualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaAtualKeyPressed
        // TODO add your handling code here:
               String senhaencrypt="";
                        try {
                            senhaencrypt=criptografia(txtSenhaAtual.getText().trim());
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(TrocarSenhaUsu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if(senhaencrypt.equals(senhaatual)){
                  txtNovaSenha1.requestFocus();  
                }else{
                    JOptionPane.showMessageDialog(this, "Forneça sua senha ou procure o administrador do sistema!!!");
                }
                
                }
    }//GEN-LAST:event_txtSenhaAtualKeyPressed

    private void txtNovaSenha1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNovaSenha1KeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(txtSenhaAtual.equals("")){
                 JOptionPane.showMessageDialog(this, "Forneça sua senha ou procure o administrador do sistema!!!");
                txtSenhaAtual.requestFocus(true);
            }
            else{
                txtNovaSenha.requestFocus(true);
            }
        }
    }//GEN-LAST:event_txtNovaSenha1KeyPressed

    private void txtNovaSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNovaSenhaKeyPressed
        // TODO add your handling code here:
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(txtSenhaAtual.equals("")){
                 JOptionPane.showMessageDialog(this, "Forneça sua senha ou procure o administrador do sistema!!!");
                txtSenhaAtual.requestFocus(true);
            }
            else{
                if(txtNovaSenha.equals(txtNovaSenha1)){
                   btnSalvar.requestFocus(true); 
                }else{
                    JOptionPane.showMessageDialog(this, "A nova senha e a confirmação da mesma deve ser igual");
                   txtNovaSenha1.requestFocus(true); 
               }
                
            }
        }
    }//GEN-LAST:event_txtNovaSenhaKeyPressed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        //        System.exit(0);
               // TODO add your handling code here:
        this.senhanova="";
        this.retorno=0;
        this.dispose();
    }//GEN-LAST:event_lbFecharMouseClicked

    private void lbFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseEntered
        //        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.yellow);
        //        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.yellow);
    }//GEN-LAST:event_lbFecharMouseEntered

    private void lbFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseExited
        //        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
        //        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.white);
    }//GEN-LAST:event_lbFecharMouseExited

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
            java.util.logging.Logger.getLogger(TrocarSenhaUsu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrocarSenhaUsu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrocarSenhaUsu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrocarSenhaUsu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TrocarSenhaUsu dialog = new TrocarSenhaUsu(new javax.swing.JDialog(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_PRINCIPAL;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JPasswordField txtNovaSenha;
    private javax.swing.JPasswordField txtNovaSenha1;
    private javax.swing.JPasswordField txtSenhaAtual;
    // End of variables declaration//GEN-END:variables
}
