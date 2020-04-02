/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.LoginController;
import Controller.UsuarioController;
import Dao.UsuarioDao;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Home
 */
public class UsuarioCadastroForm extends javax.swing.JDialog {
     ConexaoBD conexao = new ConexaoBD();
    UsuarioDao usuariosdao = new UsuarioDao();
    UsuarioController usuarios = new UsuarioController();
    LoginController logado = new LoginController();
    
    private DefaultTableModel modelo = new DefaultTableModel();
    public int idusuario;
    private boolean eadm=false;
    public int retorno=0;
    /**
     * Creates new form UsuarioCadastroForm1
     */
    public UsuarioCadastroForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         LiberarCampos();
        eadm=usuariosdao.eAdm(idusuario);
        this.setLocationRelativeTo(null);
        txtNomeUsuario.requestFocus();

       
    }

    private void mudaMascaraTelefone(JFormattedTextField format) {
        try {
            format.setValue(null);
            String nome = format.getText().replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
            final MaskFormatter mask = new MaskFormatter();
            switch (nome.length()) {
                case 8:
                    mask.setMask("####-####");
                    format.setFormatterFactory(new DefaultFormatterFactory(mask));
                    break;
                case 9:
                    mask.setMask("#####-####");
                    format.setFormatterFactory(new DefaultFormatterFactory(mask));
                    break;
                case 10:
                    mask.setMask("(##)####-####");
                    format.setFormatterFactory(new DefaultFormatterFactory(mask));
                    break;
                case 11:
                    JPopupMenu pop = new JPopupMenu();
                    JMenuItem comDDD = new JMenuItem("(" + nome.substring(0, 2) + ")" + nome.substring(2, 7) + "-" + nome.substring(7, 11));
                    comDDD.addActionListener((ActionEvent ae) -> {
                        try {
                            mask.setMask("(##)#####-####");
                            format.setFormatterFactory(new DefaultFormatterFactory(mask));
                            format.setText(nome);
                        } catch (ParseException ex) {
                        }
                    });
                    JMenuItem com0800 = new JMenuItem(nome.substring(0, 4) + " " + nome.substring(4, 7) + "-" + nome.substring(7, 11));
                    com0800.addActionListener((ActionEvent ae) -> {
                        try {
                            mask.setMask("#### ###-####");
                            format.setFormatterFactory(new DefaultFormatterFactory(mask));
                            format.setText(nome);
                        } catch (ParseException ex) {
                        }
                    });
                    pop.add(comDDD);
                    pop.add(com0800);
                    pop.show(format, format.getX(), format.getY());
                    break;
                default:
                    break;
            }
            format.setText(nome);
        } catch (Exception asd) {
            System.out.println(asd);
        }
    }

    public boolean verificarCarmpos(){
        
        if(txtNomeUsuario.getText().equals("")|| txtContatoUsuario.getText().equals("") 
                || txtEmailUsuario.getText().equals("")||txtSenhaUsuario.getText().equals("") ||txtSenhaUsuarioRep.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
           return true;
        }else{
       return false; 
        }
    }
    public boolean verificarSenha(){
        String senha1=txtSenhaUsuario.getText();
        String senha2=txtSenhaUsuarioRep.getText();
                   if(!senha1.trim().equals(senha2)){
                       JOptionPane.showMessageDialog(null, "Senhas Digitadas não conferem!!!");
                        return true;
                   }else{
                   return false;
               }
                   
    }
    public void SalvarUsuario() throws UnsupportedEncodingException{
       usuarios.setNomeusuario(txtNomeUsuario.getText().toUpperCase().trim());
       usuarios.setContatousuario(txtContatoUsuario.getText());
       usuarios.setEmailusuario(txtEmailUsuario.getText().toLowerCase().trim());
       usuarios.setSenhausuario(txtSenhaUsuario.getText());
       usuarios.setAdm(0);
       usuarios.setBloquear(0);
       usuarios.setUsuid(idusuario);
       usuariosdao.SalvarUsuario(usuarios);
       retorno=1;
        }
    public void limparCampos(){
          
            txtNomeUsuario.setText("");
            txtContatoUsuario.setText("");
            txtEmailUsuario.setText("");
            txtSenhaUsuario.setText("");
    }
    public void LerCampos() {
         
         txtNomeUsuario.setEnabled(false);
         txtContatoUsuario.setEnabled(false);
         txtEmailUsuario.setEnabled(false);
         txtSenhaUsuario.setEnabled(false);
         btnSalvar.setEnabled(false);
        // btnCancelar.setEnabled(false);
         
    }
        public void LiberarCampos() {
      
        txtNomeUsuario.setEnabled(true);
        txtContatoUsuario.setEnabled(true);
        txtEmailUsuario.setEnabled(true);
        txtSenhaUsuario.setEnabled(true);
        txtNomeUsuario.requestFocus();
        btnSalvar.setEnabled(true);
        //btnCancelar.setEnabled(true);
        

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        lbNomeOriginal = new javax.swing.JLabel();
        lbNomeOriginal1 = new javax.swing.JLabel();
        txtEmailUsuario = new javax.swing.JTextField();
        txtNomeUsuario = new javax.swing.JTextField();
        lbNomeOriginal2 = new javax.swing.JLabel();
        txtSenhaUsuario = new javax.swing.JPasswordField();
        txtSenhaUsuarioRep = new javax.swing.JPasswordField();
        lbNomeOriginal4 = new javax.swing.JLabel();
        txtContatoUsuario = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        lbTitulo1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 4));

        lbNome.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        lbNome.setForeground(new java.awt.Color(51, 51, 51));
        lbNome.setText("Contato");

        lbNomeOriginal.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        lbNomeOriginal.setForeground(new java.awt.Color(51, 51, 51));
        lbNomeOriginal.setText("Email");

        lbNomeOriginal1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        lbNomeOriginal1.setForeground(new java.awt.Color(51, 51, 51));
        lbNomeOriginal1.setText("Nome");

        txtEmailUsuario.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtEmailUsuario.setForeground(new java.awt.Color(102, 102, 102));
        txtEmailUsuario.setEnabled(false);
        txtEmailUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailUsuarioKeyPressed(evt);
            }
        });

        txtNomeUsuario.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtNomeUsuario.setForeground(new java.awt.Color(102, 102, 102));
        txtNomeUsuario.setEnabled(false);
        txtNomeUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeUsuarioKeyPressed(evt);
            }
        });

        lbNomeOriginal2.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        lbNomeOriginal2.setForeground(new java.awt.Color(51, 51, 51));
        lbNomeOriginal2.setText("Senha");

        txtSenhaUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSenhaUsuario.setForeground(new java.awt.Color(102, 102, 102));
        txtSenhaUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaUsuarioKeyPressed(evt);
            }
        });

        txtSenhaUsuarioRep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSenhaUsuarioRep.setForeground(new java.awt.Color(102, 102, 102));
        txtSenhaUsuarioRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaUsuarioRepKeyPressed(evt);
            }
        });

        lbNomeOriginal4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        lbNomeOriginal4.setForeground(new java.awt.Color(51, 51, 51));
        lbNomeOriginal4.setText("Repetir Senha");

        txtContatoUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtContatoUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContatoUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContatoUsuarioFocusLost(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        lbTitulo1.setBackground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 24)); // NOI18N
        lbTitulo1.setForeground(new java.awt.Color(255, 255, 255));
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTitulo1.setText("Cadastro de Administradores do Sistema");

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
                .addGap(26, 26, 26)
                .addComponent(lbTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitulo1)
                    .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSalvar.setBackground(new java.awt.Color(235, 114, 21));
        btnSalvar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(51, 51, 51));
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNome, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbNomeOriginal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbNomeOriginal4)
                        .addGap(8, 8, 8)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEmailUsuario)
                    .addComponent(txtSenhaUsuarioRep)
                    .addComponent(txtSenhaUsuario)
                    .addComponent(txtContatoUsuario)
                    .addComponent(txtNomeUsuario)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lbNome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txtContatoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNomeOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmailUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenhaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenhaUsuarioRep, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNomeOriginal4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSenhaUsuario.requestFocus();
        }
    }//GEN-LAST:event_txtEmailUsuarioKeyPressed

    private void txtNomeUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtContatoUsuario.requestFocus();
        }
    }//GEN-LAST:event_txtNomeUsuarioKeyPressed

    private void txtSenhaUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSenhaUsuarioRep.requestFocus();
        }
    }//GEN-LAST:event_txtSenhaUsuarioKeyPressed

    private void txtSenhaUsuarioRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaUsuarioRepKeyPressed
        // TODO add your handling code here:
        String senha1=txtSenhaUsuario.getText();
        String senha2=txtSenhaUsuarioRep.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(senha1.trim().equals(senha2)){
                JOptionPane.showMessageDialog(this, "Senhas conferidas!!");
                btnSalvar.requestFocus();
            }else;{
                JOptionPane.showMessageDialog(this, "Senhas não conferem!!");
            }

        }
    }//GEN-LAST:event_txtSenhaUsuarioRepKeyPressed

    private void txtContatoUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContatoUsuarioFocusGained
        // TODO add your handling code here:
        txtContatoUsuario.setFormatterFactory(null);
    }//GEN-LAST:event_txtContatoUsuarioFocusGained

    private void txtContatoUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContatoUsuarioFocusLost
        // TODO add your handling code here:
        mudaMascaraTelefone(txtContatoUsuario);
    }//GEN-LAST:event_txtContatoUsuarioFocusLost

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
              UsuarioDao usudao = new UsuarioDao();
        if(usudao.existeUsuario(txtEmailUsuario.getText().trim())==true){
            
        }else if(verificarCarmpos()){

        }else if(verificarSenha()){

        }else{
            try {
                SalvarUsuario();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(UsuarioCadastroForm.class.getName()).log(Level.SEVERE, null, ex);
            }
 
            this.dispose();

        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(verificarCarmpos()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            }else{
                try {
                    SalvarUsuario();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(UsuarioCadastroForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                UsuarioListaForm usuariolista = null;
                try {
                    usuariolista = new UsuarioListaForm();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioCadastroForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                usuariolista.setVisible(true);
                this.dispose();

            }
        }
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        //        System.exit(0);
        limparCampos();
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
            java.util.logging.Logger.getLogger(UsuarioCadastroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuarioCadastroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuarioCadastroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuarioCadastroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UsuarioCadastroForm dialog = new UsuarioCadastroForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbNomeOriginal;
    private javax.swing.JLabel lbNomeOriginal1;
    private javax.swing.JLabel lbNomeOriginal2;
    private javax.swing.JLabel lbNomeOriginal4;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JFormattedTextField txtContatoUsuario;
    private javax.swing.JTextField txtEmailUsuario;
    private javax.swing.JTextField txtNomeUsuario;
    private javax.swing.JPasswordField txtSenhaUsuario;
    private javax.swing.JPasswordField txtSenhaUsuarioRep;
    // End of variables declaration//GEN-END:variables
}
