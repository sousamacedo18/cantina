/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Conexao.ConexaoBD;
import Controller.CursoController;
import Dao.CursoDao;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Home
 */
public class CursoEditarForm extends javax.swing.JDialog {
    ConexaoBD conexao = new ConexaoBD();
    CursoDao cursosdao = new CursoDao();
    CursoController cursos = new CursoController();
    PrincipalForm principal;
    private Integer retorno=null;

    public Integer getRetorno() {
        return retorno;
    }
    /**
     * Creates new form CursoEditarForm
     */
    public CursoEditarForm(java.awt.Frame parent, boolean modal) throws SQLException, InterruptedException, IOException {
        super(parent, modal);
        this.principal = new PrincipalForm();
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
         public void SalvarCurso(){
       cursos.setIdcurso(Integer.parseInt(txtId.getText()));
       cursos.setNomecurso(txtNome.getText().toUpperCase());
       cursosdao.AtualizarCurso(cursos);
        }
    public void editarCampos(Integer id){
        cursos.setIdcurso(id);
       
       CursoDao dao = new CursoDao();
      for (CursoController u : dao.CarregarCursoId(id)) {
        txtId.setText(Integer.toString(u.getIdcurso()));
        txtNome.setText(u.getNomecurso());

    }
    txtNome.requestFocus();
    }
            public boolean verificarCarmpos(){
        
        if(txtNome.getText().equals("")){
            
           return true;
        }
       return false; 
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
        lbNomeOriginal1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lbNomeOriginal2 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lbTitulo1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 4));

        lbNomeOriginal1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        lbNomeOriginal1.setForeground(new java.awt.Color(51, 51, 51));
        lbNomeOriginal1.setText("Nome");

        txtId.setEditable(false);
        txtId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbNomeOriginal2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        lbNomeOriginal2.setForeground(new java.awt.Color(51, 51, 51));
        lbNomeOriginal2.setText("Código");

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        lbTitulo1.setBackground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 24)); // NOI18N
        lbTitulo1.setForeground(new java.awt.Color(255, 255, 255));
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTitulo1.setText("Alterar Cadastro de Curso");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(lbTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitulo1)
                    .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnSalvar.setBackground(new java.awt.Color(235, 114, 21));
        btnSalvar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        btnSalvar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalvarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNome))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha o campo nome do curso");
        }else{
            SalvarCurso();
            retorno=1;
            this.dispose();

        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        // TODO add your handling code here:
                       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnSalvar.requestFocus();
               }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        // TODO add your handling code here:
                       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                           if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha o campo nome do curso");
        }else{
            SalvarCurso();
            retorno=1;
            this.dispose();

        }
               }
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
         txtNome.setText("");
        retorno=0;
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
            java.util.logging.Logger.getLogger(CursoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CursoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CursoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CursoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CursoEditarForm dialog = null;
                try {
                    dialog = new CursoEditarForm(new javax.swing.JFrame(), true);
                } catch (SQLException ex) {
                    Logger.getLogger(CursoEditarForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CursoEditarForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CursoEditarForm.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    private javax.swing.JButton btnSalvar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JLabel lbNomeOriginal1;
    private javax.swing.JLabel lbNomeOriginal2;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
