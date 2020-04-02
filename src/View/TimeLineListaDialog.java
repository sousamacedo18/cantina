/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AcessoController;
import Controller.TimeLineController;
import Dao.AcessoDao;
import Dao.TimeLineDao;
import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class TimeLineListaDialog extends javax.swing.JDialog {
public int idUsuario;
public int timelineleitura;
public int timelineescrita;
AcessoDao acessodao = new AcessoDao();
    /**
     * Creates new form TimeLineListaDialog
     */
    public void listaAcesso(Integer i){
        for(AcessoController a: acessodao.CarregarAcesso(i)){

            timelineescrita=a.getTimelineescrita();
     
        }
            if(timelineescrita==0){
            btnEditar.setEnabled(false);
            btnNovo.setEnabled(false);
            btnExcluir.setEnabled(false);
        } 
    }
    
    private String nomeStatus(int st){
        String nome;
        String [] status = new String[10];
        status[0]="EM ATENDIMENTO";
        status[1]="PSICOLÓGIA";
        status[2]="ASSISTÊNCIAL SOCIAL";
        status[3]="AUXÍLIO";
        status[4]="ATENÇÃO";
        status[5]="OUTROS";
        nome=status[st];
        
        return nome;
        

    }
    public TimeLineListaDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        preencherTabela("");
        this.setLocationRelativeTo(null);
        

    }
    class TableRenderer extends DefaultTableCellRenderer {
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                     

        String status = table.getModel().getValueAt(row, 5).toString();
//        if(bolsa.equals("0")){
//            //comp.setBackground(Color.GREEN);
//            //comp.setBackground(new Color(100, 200, 50));
//            comp.setBackground(new Color(0,100,0));
//            comp.setForeground(Color.WHITE);
//        } 
//        else
        if(status.equals("2")){
            //comp.setBackground(Color.RED);
            //comp.setBackground(new Color(255, 91, 96));
            comp.setBackground(new Color(255,215,0));
            comp.setForeground(Color.BLACK);
        }
//        else{
//            comp.setBackground(new Color(255,201,0));
//            comp.setForeground(Color.BLACK);            
//        }
        return comp;
    }
}
            public void preencherTabela(String nome){
               
              
               DefaultTableModel tabela = (DefaultTableModel) tblTimeLine.getModel();
               tblTimeLine.setModel(tabela);
              // tblAluno.setRowSorter(new TableRowSorter(tabela));
               tblTimeLine.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
               tblTimeLine.getColumnModel().getColumn(0).setPreferredWidth(90);
               tblTimeLine.getColumnModel().getColumn(1).setPreferredWidth(250);
               tblTimeLine.getColumnModel().getColumn(2).setPreferredWidth(200);
               tblTimeLine.getColumnModel().getColumn(3).setPreferredWidth(80);
               tblTimeLine.getColumnModel().getColumn(4).setPreferredWidth(80);
               tblTimeLine.getColumnModel().getColumn(5).setPreferredWidth(50);
               tblTimeLine.getColumnModel().getColumn(6).setPreferredWidth(200);
               tblTimeLine.getColumnModel().getColumn(7).setPreferredWidth(200);
               tblTimeLine.getColumnModel().getColumn(8).setPreferredWidth(40);

               
       		tabela.setNumRows(0);
                
		TimeLineDao dao = new TimeLineDao();
                       
		for (TimeLineController u : dao.CarregarTimeTodos(nome)) {
           
			tabela.addRow(new Object[]{
                            u.getIdTimeLine(),
                            u.getNomealuno(), 
                            u.getDetalhesTimeLine(), 
                            u.getDataTimeLine(),
                            u.getHorarioTimeLine(),
                            u.getStatusTimeLine(),
                            nomeStatus(u.getStatusTimeLine()),
                            u.getNomeusuario(),
                            u.getIdTimeLineAluno()
                                                 });
                                                 
   
                }
//                tblTimeLine.setDefaultRenderer(Object.class, new TableRenderer());
            }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JP_Principal = new javax.swing.JPanel();
        JP_TITULO = new javax.swing.JPanel();
        lbTitulo1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTimeLine = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnTimeLine = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        JP_Principal.setBackground(new java.awt.Color(60, 77, 87));

        JP_TITULO.setBackground(new java.awt.Color(50, 168, 82));

        lbTitulo1.setBackground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        lbTitulo1.setForeground(new java.awt.Color(255, 255, 255));
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTitulo1.setText("Time Line dos Estudantes");

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

        javax.swing.GroupLayout JP_TITULOLayout = new javax.swing.GroupLayout(JP_TITULO);
        JP_TITULO.setLayout(JP_TITULOLayout);
        JP_TITULOLayout.setHorizontalGroup(
            JP_TITULOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_TITULOLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        JP_TITULOLayout.setVerticalGroup(
            JP_TITULOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_TITULOLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(JP_TITULOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitulo1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(lbFechar))
                .addContainerGap())
        );

        tblTimeLine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_TIMELINE", "ESTUDANTE", "DETALHES", "DATA", "HORA", "COD ST", "STATUS", "NOME ADM", "ID_EST"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTimeLine);

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_plus_1646001.png"))); // NOI18N
        btnNovo.setToolTipText("Novo Cadastro");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_icon-136-document-edit_314724.png"))); // NOI18N
        btnEditar.setToolTipText("Alterar Cadastro");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_Line_ui_icons_Svg-03_1465842.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluír");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnTimeLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/time-5-24.png"))); // NOI18N
        btnTimeLine.setToolTipText("Ver Time Line");
        btnTimeLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimeLineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnTimeLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimeLine, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("Pesquisa");

        txtPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtPesquisaPropertyChange(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_edit-find_118922.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JP_PrincipalLayout = new javax.swing.GroupLayout(JP_Principal);
        JP_Principal.setLayout(JP_PrincipalLayout);
        JP_PrincipalLayout.setHorizontalGroup(
            JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_TITULO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JP_PrincipalLayout.setVerticalGroup(
            JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                .addComponent(JP_TITULO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPesquisar)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(JP_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        this.dispose();
    }//GEN-LAST:event_lbFecharMouseClicked

    private void lbFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseEntered
        //        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
        //        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.yellow);
    }//GEN-LAST:event_lbFecharMouseEntered

    private void lbFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseExited
        //        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
        //        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.white);
    }//GEN-LAST:event_lbFecharMouseExited

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        TimeLineCadastroDialog t = new TimeLineCadastroDialog(this,true);
        t.idUsuario=idUsuario;
        t.setVisible(true);
        int ret = t.getRetorno();
        System.out.println(ret);
        if(ret==1){
            preencherTabela("");
        }
        

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
                TimeLineEditarDialog timeEditar = new TimeLineEditarDialog(this,true);
        int linhaSelecionada = -1;

        linhaSelecionada = tblTimeLine.getSelectedRow();
        if (linhaSelecionada>=0){
            int id=(int)tblTimeLine.getValueAt(linhaSelecionada, 0);
            timeEditar.editarCampos(id);
            timeEditar.setVisible(true);
            int ret = timeEditar.getRetorno();
                System.out.println(ret);
            if(ret==1){
            preencherTabela("");
            }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Aluno");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
                JDialog.setDefaultLookAndFeelDecorated(true);
                TimeLineController timecontroller = new TimeLineController();
                int response = JOptionPane.showConfirmDialog(null, "Deseja Realmente Excluir Esse Registro?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
            
                        DefaultTableModel tabela = (DefaultTableModel) tblTimeLine.getModel();
                        tblTimeLine.setModel(tabela);
                        int linhaSelecionada = -1;
                        linhaSelecionada = tblTimeLine.getSelectedRow();
                        if (linhaSelecionada >= 0) {
                                int tim = (int) tblTimeLine.getValueAt(linhaSelecionada, 0);
                                TimeLineDao dao = new TimeLineDao();
                                timecontroller.setIdTimeLine(tim);
                                dao.ExcluirTimeLine(timecontroller);
                                tabela.removeRow(linhaSelecionada);
                            } else {
                                JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
                            }
                    }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtPesquisaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaPropertyChange

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        // TODO add your handling code here:
        //        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //            execPesquisar();
            //        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
            
        String nome = txtPesquisa.getText();
        preencherTabela(nome);
        //        execPesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        listaAcesso(idUsuario);
    }//GEN-LAST:event_formWindowActivated

    private void btnTimeLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeLineActionPerformed
        // TODO add your handling code here:
       

        int linhaSelecionada = -1;

        linhaSelecionada = tblTimeLine.getSelectedRow();
        if (linhaSelecionada>=0){

            int id=(int)tblTimeLine.getValueAt(linhaSelecionada, 8);
       

                TimeLineVisualizarTime tlv = new TimeLineVisualizarTime(this,true);

                try {
                    tlv.preencherTabela(id);
                } catch (SQLException ex) {
                    Logger.getLogger(AlunoListaForm.class.getName()).log(Level.SEVERE, null, ex);
                }

                tlv.setVisible(true);
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Aluno");
        }

    }//GEN-LAST:event_btnTimeLineActionPerformed

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
            java.util.logging.Logger.getLogger(TimeLineListaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimeLineListaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimeLineListaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimeLineListaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TimeLineListaDialog dialog = new TimeLineListaDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel JP_Principal;
    private javax.swing.JPanel JP_TITULO;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnTimeLine;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JTable tblTimeLine;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
