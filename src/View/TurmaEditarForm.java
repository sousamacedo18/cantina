/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Conexao.ConexaoBD;
import Controller.TurmaController;
import Controller.CursoController;
import Dao.CursoDao;
import Dao.TurmaDao;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Home
 */
public class TurmaEditarForm extends javax.swing.JDialog {
    ConexaoBD conexao = new ConexaoBD();
    TurmaDao turmasdao = new TurmaDao();
    TurmaController turmas = new TurmaController();
    PrincipalForm principal;
    ArrayList<Integer> codigosCurso= new ArrayList<>();;
    private Integer idcurso=0;
    private Integer retorno=null;
    ArrayList<Integer> arr = new ArrayList<>();

    public Integer getRetorno() {
        return retorno;
    }
    /**
     * Creates new form CursoEditarForm
     */
    public TurmaEditarForm(java.awt.Frame parent, boolean modal) throws InterruptedException, IOException {
        super(parent, modal);
        try {
            this.principal = new PrincipalForm();
        } catch (SQLException ex) {
            Logger.getLogger(TurmaEditarForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
    }
         public ArrayList<Integer> carregarCurso(){
                                jcbxCurso.removeAllItems();
                                 CursoDao dao = new CursoDao();
                                  
                                  int count=0;
                                 for (CursoController u : dao.CarregarCursoTodos()) {
                                         jcbxCurso.addItem(u);
                                         arr.add(u.getIdcurso());
                                         codigosCurso.add(u.getIdcurso());
                                         if(idcurso==u.getIdcurso()){
                                             jcbxCurso.setSelectedIndex(count);
                                             
                                         }
                                         jcbxCurso.requestFocus();
                                      count++;
                                 }
      return arr;                                 
     }
     
     public Integer pegarIdCurso(){
         int id=0;
         System.out.println(id);
         id=codigosCurso.get(jcbxCurso.getSelectedIndex());
         
         return id;
     } 
     public Integer pegarIndexCurso(int id){
         int index=0;
               //id=codigosCurso.get(jcbxCurso.getSelectedIndex());
         return index;
     }

         public void SalvarTurma(){
       turmas.setIdturma(Integer.parseInt(txtId.getText()));
       turmas.setNometurma(txtNome.getText().toUpperCase().trim());
       turmas.setCursoid(pegarIdCurso());
       turmasdao.AtualizarTurma(turmas);
        }
    public void editarCampos(Integer id){
        turmas.setIdturma(id);
       TurmaDao dao = new TurmaDao();
      for (TurmaController u : dao.CarregarTurmaId(id)) {
        txtId.setText(Integer.toString(u.getIdturma()));
        txtNome.setText(u.getNometurma());
        idcurso=u.getCursoid();
        //jcbxCurso.setSelectedIndex(u.getCursoid());

    }
    
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

        JP_PRINCIPAL = new javax.swing.JPanel();
        lbNomeOriginal1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lbNomeOriginal2 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lbNomeOriginal3 = new javax.swing.JLabel();
        jcbxCurso = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        lbTitulo1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        JP_PRINCIPAL.setBackground(new java.awt.Color(255, 255, 255));
        JP_PRINCIPAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 4));

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

        lbNomeOriginal3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        lbNomeOriginal3.setForeground(new java.awt.Color(51, 51, 51));
        lbNomeOriginal3.setText("Curso");

        jcbxCurso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbxCurso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcbxCursoKeyPressed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        lbTitulo1.setBackground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 24)); // NOI18N
        lbTitulo1.setForeground(new java.awt.Color(255, 255, 255));
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTitulo1.setText("Alterar Cadastro de Turma");

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
                .addGap(139, 139, 139)
                .addComponent(lbTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
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

        javax.swing.GroupLayout JP_PRINCIPALLayout = new javax.swing.GroupLayout(JP_PRINCIPAL);
        JP_PRINCIPAL.setLayout(JP_PRINCIPALLayout);
        JP_PRINCIPALLayout.setHorizontalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lbNomeOriginal3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbxCurso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        JP_PRINCIPALLayout.setVerticalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeOriginal3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbxCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha o campo nome do curso");
        }else{
            SalvarTurma();
            retorno=1;
            this.dispose();

        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jcbxCursoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcbxCursoKeyPressed
        // TODO add your handling code here:
               if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtNome.requestFocus();
               }
    }//GEN-LAST:event_jcbxCursoKeyPressed

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
            SalvarTurma();
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
            java.util.logging.Logger.getLogger(TurmaEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TurmaEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TurmaEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TurmaEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TurmaEditarForm dialog = null;
                try {
                    dialog = new TurmaEditarForm(new javax.swing.JFrame(), true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TurmaEditarForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TurmaEditarForm.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JPanel JP_PRINCIPAL;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JComboBox<Object> jcbxCurso;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JLabel lbNomeOriginal1;
    private javax.swing.JLabel lbNomeOriginal2;
    private javax.swing.JLabel lbNomeOriginal3;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
