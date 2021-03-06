/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import Controller.CursoController;
import Dao.CursoDao;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class PesquisarCursoForm extends javax.swing.JDialog {
private int idcurso;
private String nomecurso;
private int retorno=0;


    public int getIdcurso() {
        return idcurso;
    }

    public String getNomecurso() {
        return nomecurso;
    }

    public int getRetorno() {
        return retorno;
    }


    /**
     * Creates new form PesquisaAlunoForm
     */
    public PesquisarCursoForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        carregarTudo();
        this.setLocationRelativeTo(null);
        
        
    }
    public void carregarTudo(){
            DefaultTableModel tabela = (DefaultTableModel) tbCurso.getModel();
            tbCurso.setModel(tabela);
            tbCurso.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tbCurso.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbCurso.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabela.setNumRows(0);
            CursoDao dao = new CursoDao();

            for (CursoController u : dao.CarregarCursoNome("%%")) {
                tabela.addRow(new Object[]{u.getIdcurso(), u.getNomecurso()});

            }   
    }
    public void carregarCurso(String valor){
                if(valor.equals("")){
            JOptionPane.showMessageDialog(null, "Campos de pesquisa está vazio");
        }
        else{
            DefaultTableModel tabela = (DefaultTableModel) tbCurso.getModel();
            tbCurso.setModel(tabela);
            tbCurso.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tbCurso.getColumnModel().getColumn(0).setPreferredWidth(60);
            tbCurso.getColumnModel().getColumn(1).setPreferredWidth(300);
            tbCurso.getColumnModel().getColumn(2).setPreferredWidth(220);
            tabela.setNumRows(0);
            CursoDao dao = new CursoDao();

            for (CursoController u : dao.CarregarCursoNome(valor)) {
                tabela.addRow(new Object[]{u.getIdcurso(), u.getNomecurso()});

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

        JP_PRINCIPAL = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCurso = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lbFechar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCampo = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        btnSelecionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        JP_PRINCIPAL.setBackground(new java.awt.Color(60, 77, 87));
        JP_PRINCIPAL.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(50, 168, 82), 4, true));

        tbCurso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "NOME CURSO"
            }
        ));
        jScrollPane1.setViewportView(tbCurso);

        jPanel1.setBackground(new java.awt.Color(50, 168, 82));

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

        jLabel1.setBackground(new java.awt.Color(0, 102, 102));
        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pesquisar Curso");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(296, 296, 296)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        txtCampo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnPesquisar.setBackground(new java.awt.Color(0, 84, 140));
        btnPesquisar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btnPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        btnPesquisar.setText("Buscar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnSelecionar.setBackground(new java.awt.Color(235, 114, 21));
        btnSelecionar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btnSelecionar.setForeground(new java.awt.Color(255, 255, 255));
        btnSelecionar.setText("Selecionado");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addComponent(txtCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        JP_PRINCIPALLayout.setVerticalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
        carregarCurso(txtCampo.getText());
        
    
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        // TODO add your handling code here:

        int linhaSelecionada = -1;

        linhaSelecionada = tbCurso.getSelectedRow();
        if (linhaSelecionada>=0){
            int codigo=(int)tbCurso.getValueAt(linhaSelecionada, 0);
            String nome=(String)tbCurso.getValueAt(linhaSelecionada, 1);
            idcurso=codigo;
            nomecurso=nome;
            retorno=1;
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Curso");
        }
    }//GEN-LAST:event_btnSelecionarActionPerformed

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
            java.util.logging.Logger.getLogger(PesquisarCursoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PesquisarCursoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PesquisarCursoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PesquisarCursoForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PesquisarCursoForm dialog = new PesquisarCursoForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSelecionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JTable tbCurso;
    private javax.swing.JTextField txtCampo;
    // End of variables declaration//GEN-END:variables
}
