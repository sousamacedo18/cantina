/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Dao.AlunoDao;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class TrocarSenha extends javax.swing.JDialog {
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
    public TrocarSenha(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
   public void confirmaralteracao(){
       senhanova=txtNovaSenha.getText();
       if(senhanova.equals(senhaatual)|| senhanova.equals("")){
           JOptionPane.showMessageDialog(null, "Escolha outra senha diferente da atual");
           retorno=0;
       }
       else{
           JOptionPane.showMessageDialog(null, "Senha alterada, lembre-se de usar sua nova senha");
           retorno=1;
           this.dispose();
       }
   }
   public void setarSenhaAtual(int id) throws SQLException{
       AlunoDao dao = new AlunoDao();
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
        btnSalvar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSenhaAtual = new javax.swing.JPasswordField();
        txtNovaSenha = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        JP_PRINCIPAL.setBackground(new java.awt.Color(255, 255, 255));
        JP_PRINCIPAL.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 4, true));

        btnSalvar.setText("Confirmar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel2.setText("Senha Atual");

        jLabel3.setText("Nova Senha");

        txtSenhaAtual.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtNovaSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Trocar Senha ");

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
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JP_PRINCIPALLayout = new javax.swing.GroupLayout(JP_PRINCIPAL);
        JP_PRINCIPAL.setLayout(JP_PRINCIPALLayout);
        JP_PRINCIPALLayout.setHorizontalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSalvar)
                    .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(txtSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        JP_PRINCIPALLayout.setVerticalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSalvar)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        confirmaralteracao();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked

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
            java.util.logging.Logger.getLogger(TrocarSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrocarSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrocarSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrocarSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TrocarSenha dialog = new TrocarSenha(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JPasswordField txtNovaSenha;
    private javax.swing.JPasswordField txtSenhaAtual;
    // End of variables declaration//GEN-END:variables
}
