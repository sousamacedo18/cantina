/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.AcessoController;
import Controller.LoginController;
import Controller.UsuarioController;
import Dao.AcessoDao;
import Dao.UsuarioDao;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class UsuarioListaForm extends javax.swing.JFrame {
    ConexaoBD conexao = new ConexaoBD();
    UsuarioDao usuariosdao = new UsuarioDao();
    UsuarioController usuarios = new UsuarioController();
    AcessoDao acessosdao = new AcessoDao();
    AcessoController acessos = new AcessoController();
    AcessoListaForm acessoform = new AcessoListaForm();
    LoginController logado = new LoginController();
//    PrincipalForm f = new PrincipalForm();
    public Integer contador;
    public Integer idusuario;
    public Integer idlogado;
   
    
public Integer idusuarioacesso;
public Integer usuarioacesso;
public Integer alunoacesso;
public Integer acessosistema;
public Integer creditoacesso;
public Integer refeitorioacesso;
public Integer relatorioacesso;
public Integer cursoacesso;
public Integer turmaacesso;;
public String nomeusuario;
    public void listaAcesso(Integer i){
        for(AcessoController a: acessosdao.CarregarAcesso(i)){
            idusuarioacesso=a.getIdusuacesso();
            nomeusuario=a.getNomeusuario();
            alunoacesso=a.getAlunoacesso();
            usuarioacesso=a.getUsuarioacesso();
            creditoacesso=a.getCreditoacesso();
            refeitorioacesso=a.getRefeitorioacesso();
            relatorioacesso=a.getRelatorioacesso();
            cursoacesso=a.getCursoacesso();
            turmaacesso=a.getTurmaacesso();
            acessosistema=a.getAcessosistema();
        }
    }
    
    public void montarAcesso(Integer id){
                                acessos.setAcessosistema(0);
                                acessos.setAlunoacesso(0);
                                acessos.setUsuarioacesso(0);
                                acessos.setCreditoacesso(0);
                                acessos.setRefeitorioacesso(0);
                                acessos.setCursoacesso(0);
                                acessos.setTurmaacesso(0);
                                acessos.setIdusuacesso((id));
                                acessos.setUsuid(idlogado);
                                acessosdao.SalvarAcesso(acessos);
    }
    public void preencherTabela() throws SQLException{
        String nomeadministrador="";
        String nomebloqueio="";
        String emaster="";
        int idadm=0;
                                contador=0;
                                DefaultTableModel tabela = (DefaultTableModel) tblUsuario.getModel();
                                tblUsuario.setModel(tabela);
                                tblUsuario.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                                tblUsuario.getColumnModel().getColumn(0).setPreferredWidth(80);
                                tblUsuario.getColumnModel().getColumn(1).setPreferredWidth(250);
                                tblUsuario.getColumnModel().getColumn(2).setPreferredWidth(120);
                                tblUsuario.getColumnModel().getColumn(3).setPreferredWidth(250);
                                tblUsuario.getColumnModel().getColumn(4).setPreferredWidth(80);
                                tblUsuario.getColumnModel().getColumn(5).setPreferredWidth(250);
                                tblUsuario.getColumnModel().getColumn(6).setPreferredWidth(80);
                                 tabela.setNumRows(0);
                                 UsuarioDao dao = new UsuarioDao();
                                 UsuarioDao dao1 = new UsuarioDao();

                                 for (UsuarioController u : dao.CarregarTodosUsuarios()) {
                                     if(u.getBloquear()==1){nomebloqueio="SIM";}else{nomebloqueio="NÃO";}
                                     if(u.getAdm()==1){emaster="SIM";}else{emaster="NÃO";}
                  
                                         tabela.addRow(new Object[]
                                         {u.getIdusuario(),
                                         u.getNomeusuario(), 
                                         u.getContatousuario(), 
                                         u.getEmailusuario(),
                                         nomebloqueio,
                                         nomeadministrador,
                                         emaster});
                                 contador++;
                                 }
                                 
                                 lbtotal.setText(Integer.toString(tblUsuario.getRowCount()));
}
    
    /**
     * Creates new form UsuarioListaForm
     */
    public UsuarioListaForm() throws SQLException {
        initComponents();
        preencherTabela();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.idlogado=logado.getIdusuario();
        this.setSize(894, 400);
      
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
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbtotal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        JP_Barra_Titulo = new javax.swing.JPanel();
        lbFechar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAcesso = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(60, 77, 87));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(50, 168, 82), 4));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Total de Administradores:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        lbtotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbtotal.setForeground(new java.awt.Color(255, 255, 255));
        lbtotal.setText("Total");
        jPanel4.add(lbtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, -1, -1));

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Contato", "Email", "Bloqueado", "Administrador", "Master"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblUsuario);
        if (tblUsuario.getColumnModel().getColumnCount() > 0) {
            tblUsuario.getColumnModel().getColumn(0).setResizable(false);
            tblUsuario.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblUsuario.getColumnModel().getColumn(1).setResizable(false);
            tblUsuario.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblUsuario.getColumnModel().getColumn(2).setResizable(false);
            tblUsuario.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblUsuario.getColumnModel().getColumn(3).setResizable(false);
            tblUsuario.getColumnModel().getColumn(3).setPreferredWidth(120);
            tblUsuario.getColumnModel().getColumn(4).setResizable(false);
            tblUsuario.getColumnModel().getColumn(5).setResizable(false);
            tblUsuario.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 490, 280));

        JP_Barra_Titulo.setBackground(new java.awt.Color(50, 168, 82));
        JP_Barra_Titulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(50, 168, 82)));
        JP_Barra_Titulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        JP_Barra_Titulo.add(lbFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 20, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Lista de Administradores");
        JP_Barra_Titulo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel4.add(JP_Barra_Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 50));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_plus_1646001.png"))); // NOI18N
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_icon-136-document-edit_314724.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_Line_ui_icons_Svg-03_1465842.png"))); // NOI18N
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnAcesso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_handrawn_Lock_Open_Close_436164.png"))); // NOI18N
        btnAcesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcessoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAcesso)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, 280));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(109, 109, 109))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        UsuarioCadastroForm cadastrousuario = new UsuarioCadastroForm(this,true);
        cadastrousuario.idusuario=idlogado;
         cadastrousuario.setVisible(true);
         if(cadastrousuario.retorno==1){
            try {
                preencherTabela();
                lbtotal.setText(Integer.toString(tblUsuario.getRowCount()));
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioListaForm.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
     JDialog.setDefaultLookAndFeelDecorated(true);
    int response = JOptionPane.showConfirmDialog(null, "Deseja Realmente Excluir Esse Registro?", "Confirm",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response == JOptionPane.YES_OPTION) {       
           
    
        DefaultTableModel tabela = (DefaultTableModel) tblUsuario.getModel();
                        tblUsuario.setModel(tabela);
			int linhaSelecionada = -1;
			linhaSelecionada = tblUsuario.getSelectedRow();
			if (linhaSelecionada >= 0) {
				int usu = (int) tblUsuario.getValueAt(linhaSelecionada, 0);
				UsuarioDao dao = new UsuarioDao();
                                usuarios.setIdusuario(usu);
				dao.ExcluirUsuario(usuarios);
				tabela.removeRow(linhaSelecionada);
                                lbtotal.setText(Integer.toString(tabela.getRowCount()));
			} else {
				JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
			}
    }
    
           	
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
  
       UsuarioEditarForm usuarioEditar= new UsuarioEditarForm(this,true);
       		int linhaSelecionada = -1;
                
		linhaSelecionada = tblUsuario.getSelectedRow();
                if (linhaSelecionada>=0){
                int id=(int)tblUsuario.getValueAt(linhaSelecionada, 0);
                usuarioEditar.editarCampos(id);
                usuarioEditar.verCheckbox(idlogado);
                usuarioEditar.idlogado=idlogado;
                usuarioEditar.setVisible(true);
                if(usuarioEditar.retorno==1){
                    try {
                        preencherTabela();
                    } catch (SQLException ex) {
                        Logger.getLogger(UsuarioListaForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Selecione um Usuário");
                }      
               
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAcessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcessoActionPerformed
        contador=0;
        idusuario=0;
        DefaultTableModel tabela = (DefaultTableModel) tblUsuario.getModel();
                        tblUsuario.setModel(tabela);
			int linhaSelecionada = -1;
			linhaSelecionada = tblUsuario.getSelectedRow();
			if (linhaSelecionada >= 0) {
				int usu = (int) tblUsuario.getValueAt(linhaSelecionada, 0);
				AcessoDao dao = new AcessoDao();
                                    try {
                                        listaAcesso(idlogado);
                                        if (acessosistema==0){
                                            JOptionPane.showMessageDialog(null, "Você não tem acesso a este modulo, procure o administrador do sistema");
                                        }else{
                                        contador=dao.contarRegistro(usu);
                                               if(contador==0){
                                               montarAcesso(usu);
                                               acessoform.listarAcesso(usu);
                                               
                                               acessoform.setVisible(true);                               
                                               }else{
                                               acessoform.listarAcesso(usu);                                 
                                               acessoform.setVisible(true); 
                                               }
                                               }
                                    } catch (SQLException ex) {
                                        Logger.getLogger(UsuarioListaForm.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                
                               // JOptionPane.showMessageDialog(null, contador);


				
			} else {
				JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
			}
    }//GEN-LAST:event_btnAcessoActionPerformed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
//        System.exit(0);
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
            java.util.logging.Logger.getLogger(UsuarioListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuarioListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuarioListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuarioListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UsuarioListaForm().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioListaForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }



    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_Barra_Titulo;
    private javax.swing.JButton btnAcesso;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JLabel lbtotal;
    private javax.swing.JTable tblUsuario;
    // End of variables declaration//GEN-END:variables
}
