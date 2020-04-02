/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.AlunoController;
import Dao.AlunoDao;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class TrocarAuxilioForm extends javax.swing.JFrame {
    ConexaoBD conexao = new ConexaoBD();
    AlunoDao alunosdao = new AlunoDao();
    AlunoController alunos = new AlunoController();
    private DefaultTableModel modelo = new DefaultTableModel();
    /**
     * Creates new form TrocarAuxilioForm
     */
    public TrocarAuxilioForm() {
        initComponents();
         URL caminhoIcone = getClass().getResource("/ico/if_Food_572824.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);
        this.setLocationRelativeTo(null);
//        this.setResizable(false);
        this.setExtendedState( MAXIMIZED_BOTH );
        preencherTabela("nomealuno", "ASC");
    }
    public void selecionarTodos(){
              
               DefaultTableModel tabela = (DefaultTableModel) jTblAluno.getModel();
               jTblAluno.setModel(tabela); 
               for (int i=0;tabela.getRowCount()>i;i++){
                 tabela.setValueAt(true, i, 3);
               }
              JOptionPane.showMessageDialog(this, "Todos selecionados!!!!!!");
    }
     public void desmarcarTodos(){
              
               DefaultTableModel tabela = (DefaultTableModel) jTblAluno.getModel();
               jTblAluno.setModel(tabela); 
               for (int i=0;tabela.getRowCount()>i;i++){
                 tabela.setValueAt(false, i, 3);
               }
              JOptionPane.showMessageDialog(this, "Todos desmarcados!!!!!!");
    }
          public void SalvarAluno(int id) throws UnsupportedEncodingException{
//       File f = new File(txtFotoAluno.getText().trim());
       alunos.setIdaluno(id);
       alunos.setBolsa(cbxAuxilio.getSelectedIndex());
        try {
            alunosdao.AtualizarAuxilio(alunos);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AlunoEditarForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
     public void salvarAlteracoes() throws UnsupportedEncodingException{
         
                           
               DefaultTableModel tabela = (DefaultTableModel) jTblAluno.getModel();
               jTblAluno.setModel(tabela);
//               JOptionPane.showMessageDialog(this, tabela.getValueAt(0, 3));
               for (int i=0;tabela.getRowCount()>i;i++){
                 if(tabela.getValueAt(i, 3).equals(true)){
//                     v+=(String) tabela.getValueAt(i, 0);
                     SalvarAluno((int) tabela.getValueAt(i, 0));
                     System.out.println(tabela.getValueAt(i, 0));
                 }
                 
               }
              preencherTabela("nomealuno","ASC");
    }
  public void preencherTabela(String campo, String ordem){
               int contador = 0;
               int bolsa100=0,bolsa50=0,bolsainativa=0;
               DefaultTableModel tabela = (DefaultTableModel) jTblAluno.getModel();
               jTblAluno.setModel(tabela);
              // tblAluno.setRowSorter(new TableRowSorter(tabela));
               jTblAluno.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
               jTblAluno.getColumnModel().getColumn(0).setPreferredWidth(80);
              
               jTblAluno.getColumnModel().getColumn(1).setPreferredWidth(250);
               
               jTblAluno.getColumnModel().getColumn(2).setPreferredWidth(80);
               jTblAluno.getColumnModel().getColumn(3).setPreferredWidth(110);
//               jTblAluno.getColumnModel().getColumn(4).setPreferredWidth(120);
//               jTblAluno.getColumnModel().getColumn(5).setPreferredWidth(250);
       		tabela.setNumRows(0);
                
		AlunoDao dao = new AlunoDao();
                        String bolsa=null;
		for (AlunoController u : dao.CarregarTodosAlunos(campo,ordem)) {
                        if(u.getBolsa()==0){
                            bolsa="100%";
                            bolsa100++;
                        }else if(u.getBolsa()==1){
                           bolsa="50%"; 
                           bolsa50++;
                        }else if(u.getBolsa()==2){
                            bolsa="Inativa";
                            bolsainativa++;
                        }
			tabela.addRow(new Object[]{
                            u.getIdaluno(),
                            u.getNomealuno(),
                            bolsa,
                            false});
		contador++;
                }
                if(contador>0){
                    jTblAluno.clearSelection();
                    jTblAluno.changeSelection(0, 0, false, false);
                    jTblAluno.setRowSelectionInterval(0,0);
//                    tblAluno.setDefaultRenderer(Object.class, new AlunoListaForm.ColorirTabela());

                    lbAlunos.setText("Total Estudantes: "+Integer.toString(contador));
                    lb100.setText("Auxílio 100%: "+Integer.toString(bolsa100));
                    lb50.setText("Auxílio 50%: "+Integer.toString(bolsa50));
                    lbinativas.setText("Inativas: "+Integer.toString(bolsainativa));
                
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTblAluno = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSelecionar = new javax.swing.JToggleButton();
        cbxAuxilio = new javax.swing.JComboBox<>();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        lbAlunos = new javax.swing.JLabel();
        lb100 = new javax.swing.JLabel();
        lb50 = new javax.swing.JLabel();
        lbinativas = new javax.swing.JLabel();
        btnDesmacar = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTblAluno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "NOME", "AUXÍLIO", "SELECIONAR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTblAluno);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("Alterar Status Auxílios");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addGap(456, 456, 456))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(21, 21, 21))
        );

        btnSelecionar.setText("Selecionar Todos");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });

        cbxAuxilio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bolsa 100%", "Bolsa 50%", "Inativa", "" }));

        jToggleButton1.setText("Salvar");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        lbAlunos.setText("Total Alunos:");

        lb100.setText("Total 100%");

        lb50.setText("Total 50%");

        lbinativas.setText("Inativas:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb100)
                    .addComponent(lbAlunos)
                    .addComponent(lb50)
                    .addComponent(lbinativas))
                .addContainerGap(910, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbAlunos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb100)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbinativas)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        btnDesmacar.setText("Desmarcar Todas");
        btnDesmacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesmacarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbxAuxilio, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSelecionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDesmacar)
                        .addGap(14, 14, 14)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSelecionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                        .addComponent(cbxAuxilio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDesmacar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        // TODO add your handling code here:
        selecionarTodos();
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void btnDesmacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesmacarActionPerformed
        // TODO add your handling code here:
        desmarcarTodos();
    }//GEN-LAST:event_btnDesmacarActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
            Object[] opcoes = { "sim", "não" };
            String codigo = null;
           Object resposta;
            resposta = JOptionPane.showInputDialog(null,"Deseja mudar o status dos auxílios marcados?","Alterar",JOptionPane.PLAIN_MESSAGE,null,opcoes,"não");
            
            try {
                    if (resposta == "sim") {
                        codigo = JOptionPane.showInputDialog("codigo: 123456");
                        System.out.println(codigo);
                        if(codigo.equals("123456")){
                          salvarAlteracoes();  
                        }else{
                            JOptionPane.showMessageDialog(this, "Código invalido");
                        }
                    
                    }
            } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TrocarAuxilioForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jToggleButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(TrocarAuxilioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrocarAuxilioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrocarAuxilioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrocarAuxilioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrocarAuxilioForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnDesmacar;
    private javax.swing.JToggleButton btnSelecionar;
    private javax.swing.JComboBox<String> cbxAuxilio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTblAluno;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lb100;
    private javax.swing.JLabel lb50;
    private javax.swing.JLabel lbAlunos;
    private javax.swing.JLabel lbinativas;
    // End of variables declaration//GEN-END:variables
}
