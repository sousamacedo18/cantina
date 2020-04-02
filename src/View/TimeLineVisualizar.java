/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.TimeLineController;
import Dao.AlunoDao;
import Dao.TimeLineDao;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Home
 */
public class TimeLineVisualizar extends javax.swing.JDialog {
public int idUsuario;
public int retorno=0;
public int idtimeline=0;
    /**
     * Creates new form TimeLineVisualizar
     */
    public TimeLineVisualizar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }
         public void carregarCampos(int id) throws SQLException{
                TimeLineDao dao = new TimeLineDao();
               for (TimeLineController u : dao.CarregarTimeLineId(id)) {
          
//                    txtIdAluno.setText(Integer.toString(u.getIdUsuarioTimeLine()));
                    lbNome.setText(u.getNomealuno());
                    txtDetalhes.setText(u.getDetalhesTimeLine());
                    lbStatus.setText(nomeStatus(u.getStatusTimeLine()));
                    idtimeline=id;
                  
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
            public void preencherTabela(int id) throws SQLException{
               int idaluno=0;
              
               DefaultTableModel tabela = (DefaultTableModel) tblTimeLine.getModel();
               tblTimeLine.setModel(tabela);
              // tblAluno.setRowSorter(new TableRowSorter(tabela));
               tblTimeLine.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
               tblTimeLine.getColumnModel().getColumn(0).setPreferredWidth(90);
               tblTimeLine.getColumnModel().getColumn(1).setPreferredWidth(150);
               tblTimeLine.getColumnModel().getColumn(2).setPreferredWidth(200);
               tblTimeLine.getColumnModel().getColumn(3).setPreferredWidth(80);
               tblTimeLine.getColumnModel().getColumn(4).setPreferredWidth(80);
               tblTimeLine.getColumnModel().getColumn(5).setPreferredWidth(50);
               tblTimeLine.getColumnModel().getColumn(6).setPreferredWidth(200);
               tblTimeLine.getColumnModel().getColumn(7).setPreferredWidth(200);
           

               
       		tabela.setNumRows(0);
                
		TimeLineDao dao = new TimeLineDao();
                       
		for (TimeLineController u : dao.CarregarIdAlunoTimeLine(id)) {
           
			tabela.addRow(new Object[]{
                            u.getIdTimeLine(),
                            u.getNomealuno(), 
                            u.getDetalhesTimeLine(), 
                            u.getDataTimeLine(),
                            u.getHorarioTimeLine(),
                            u.getStatusTimeLine(),
                            nomeStatus(u.getStatusTimeLine()),
                            u.getNomeusuario()
                       
                                                 });
                        idaluno=u.getIdTimeLineAluno();
                                                 
   
                }
                if(tblTimeLine.getRowCount()>0){
                    tblTimeLine.clearSelection();
                    tblTimeLine.changeSelection(0, 0, false, false);
                    tblTimeLine.setRowSelectionInterval(0,0);
                           int linhaSelecionada = -1;

                            linhaSelecionada = tblTimeLine.getSelectedRow();
                            if (linhaSelecionada>=0){
                                int i=(int)tblTimeLine.getValueAt(linhaSelecionada, 0);
                                carregarCampos(i);
                                  carregarFoto(idaluno);
                            }
                }else{
                    JOptionPane.showMessageDialog(null, "Não histório de Time Line para este estudante");
                    this.dispose();
                }
        
            }
           public void carregarFoto(int id) throws SQLException{
            String caminhofoto="";
            

             AlunoDao dao = new AlunoDao();
 
             caminhofoto=lerJson().trim()+dao.buscarfoto(id).trim();
             File f =new  File(caminhofoto);
//                     System.out.println(caminhofoto);
             if(f.exists()){
                    ImageIcon img = new ImageIcon(caminhofoto);
                    System.out.println(caminhofoto);
                   lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(lbFoto.getWidth(), lbFoto.getHeight(),img.getImage().SCALE_DEFAULT)));               
             }else {
                 System.out.println(caminhofoto);
                  ImageIcon img = new ImageIcon(lerJson().trim()+"sem-foto.png");
                   lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(lbFoto.getWidth(), lbFoto.getHeight(),img.getImage().SCALE_DEFAULT)));
             }
     
          }    

                   
        
            public String lerJson(){
        JSONObject resultado=null;
        String pastafotoaluno= null;
        
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("conf.json"));
            resultado=(JSONObject)obj;
                     pastafotoaluno   = (String)resultado.get("pastafotoaluno");   
                   
        } 
        catch (FileNotFoundException ex) {ex.printStackTrace(); }
        catch (IOException ex) {ex.printStackTrace(); }
        catch (ParseException ex) {ex.printStackTrace(); }
        catch (Exception ex) {ex.printStackTrace(); }
                return pastafotoaluno;
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
        lbFechar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbFoto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbNome = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDetalhes = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTimeLine = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        JP_PRINCIPAL.setBackground(new java.awt.Color(255, 255, 255));
        JP_PRINCIPAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 3));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

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

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Visualizando Time Line do Estudante");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(108, 108, 108)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFechar)
                    .addComponent(jLabel1))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        lbFoto.setBackground(new java.awt.Color(204, 204, 204));
        lbFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/sem-foto.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Nome:");

        lbNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbNome.setText("ESTUDANTE NÃO ENCONTRADO");

        txtDetalhes.setColumns(20);
        txtDetalhes.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        txtDetalhes.setRows(5);
        jScrollPane1.setViewportView(txtDetalhes);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Detalhes ");

        tblTimeLine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_TIMELINE", "ESTUDANTE", "DETALHES", "DATA", "HORA", "COD ST", "STATUS", "NOME ADM"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTimeLine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTimeLineMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblTimeLine);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Status:");

        lbStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(255, 0, 0));
        lbStatus.setText("Stauts ");

        javax.swing.GroupLayout JP_PRINCIPALLayout = new javax.swing.GroupLayout(JP_PRINCIPAL);
        JP_PRINCIPAL.setLayout(JP_PRINCIPALLayout);
        JP_PRINCIPALLayout.setHorizontalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(lbNome)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(lbFoto))
                            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbStatus))))))
        );
        JP_PRINCIPALLayout.setVerticalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNome)
                .addGap(11, 11, 11)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbStatus))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblTimeLineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTimeLineMouseClicked
            AlunoEditarForm alunoEditar= new AlunoEditarForm();
        int linhaSelecionada = -1;

        linhaSelecionada = tblTimeLine.getSelectedRow();
        if (linhaSelecionada>=0){
            int id=(int)tblTimeLine.getValueAt(linhaSelecionada, 0);
                try {
                    carregarCampos(id);
                } catch (SQLException ex) {
                    Logger.getLogger(TimeLineVisualizar.class.getName()).log(Level.SEVERE, null, ex);
                }
     
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Aluno");
        }
    }//GEN-LAST:event_tblTimeLineMouseClicked

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
            java.util.logging.Logger.getLogger(TimeLineVisualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TimeLineVisualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TimeLineVisualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TimeLineVisualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TimeLineVisualizar dialog = new TimeLineVisualizar(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JLabel lbFoto;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JTable tblTimeLine;
    private javax.swing.JTextArea txtDetalhes;
    // End of variables declaration//GEN-END:variables
}
