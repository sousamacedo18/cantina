/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.AcessoController;
import Dao.AcessoDao;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class AcessoListaForm extends javax.swing.JFrame {
    private Point point = new Point();
    ConexaoBD conexao = new ConexaoBD();
    AcessoDao acessosdao = new AcessoDao();
    AcessoController acessos = new AcessoController();
  
    /**
     * Creates new form AcessoListaForm
     */
    public AcessoListaForm() {
        initComponents();
        this.setSize(570, 272);
        this.setLocationRelativeTo(null);
    }
    public void atualizarAcesso(){
        
        if(cbAluno.isSelected()) {
                        acessos.setAlunoacesso(1);
                        
                    }
                    else{
                        acessos.setAlunoacesso(0);
                    }
        if(cbCurso.isSelected()) {
                        acessos.setCursoacesso(1);
                    }
                    else{
                        acessos.setCursoacesso(0);
                    }
        if(cbTurma.isSelected()) {
                        acessos.setTurmaacesso(1);
                    }
                    else{
                        acessos.setTurmaacesso(0);
                    }
        if(cbUsuario.isSelected()) {
                        acessos.setUsuarioacesso(1);
                    }
                    else{
                        acessos.setUsuarioacesso(0);
                    }
        if(cbAcesso.isSelected()) {
                        acessos.setAcessosistema(1);
                    }
                    else{
                        acessos.setAcessosistema(0);
                    }
        if(cbCredito.isSelected()) {
                        acessos.setCreditoacesso(1);
                    }
                    else{
                        acessos.setCreditoacesso(0);
                    }
        if(cbCredito.isSelected()) {
                        acessos.setCreditoacesso(1);
                    }
                    else{
                        acessos.setCreditoacesso(0);
                    }
        if(cbRefeitorio.isSelected()) {
                        acessos.setRefeitorioacesso(1);
                    }
                    else{
                        acessos.setRefeitorioacesso(0);
                    }
        if(cbRelatorio.isSelected()) {
                        acessos.setRelatorioacesso(1);
                    }
                    else{
                        acessos.setRelatorioacesso(0);
                    }
            if(cbLer.isSelected()){
                acessos.setTimelineleitura(1);
            }
            else{
            acessos.setTimelineleitura(0);
        }
        
        if(cbEscrita.isSelected()){
            acessos.setTimelineescrita(1);
        }
        
        else{
            acessos.setTimelineescrita(0);
        }
       
     acessos.setIdusuacesso(Integer.parseInt(txtId.getText().trim()));
//       System.out.println(acessos.getAcessosistema()+"\n");
//       System.out.println(acessos.getAlunoacesso()+"\n");
//       System.out.println(acessos.getCreditoacesso()+"\n");
//       System.out.println(acessos.getCursoacesso()+"\n");
//       System.out.println(acessos.getIdacesso()+"\n");
//       System.out.println(acessos.getNomeusuario()+"\n");
//       System.out.println(acessos.getRefeitorioacesso()+"\n");
//       System.out.println(acessos.getIdusuacesso()+"\n");
       
       acessosdao.AtualizarAcesso(acessos);
    }
    public void listarAcesso(Integer id){
            
        	for (AcessoController u : acessosdao.CarregarAcesso(id)) {
                    txtId.setText(Integer.toString(u.getIdusuacesso()));
                    txtNome.setText(u.getNomeusuario());
                    
			if (u.getUsuarioacesso()==0){
                            cbUsuario.setSelected(false);
                                    }
                                    else{
                                        cbUsuario.setSelected(true);
                                    }
			if (u.getCursoacesso()==0){
                            cbCurso.setSelected(false);
                                    }
                                    else{
                                        cbCurso.setSelected(true);
                                    }
			if (u.getTurmaacesso()==0){
                            cbTurma.setSelected(false);
                                    }
                                    else{
                                        cbTurma.setSelected(true);
                                    }
			if (u.getAcessosistema()==0){
                            cbAcesso.setSelected(false);
                                    }
                                    else{
                                        cbAcesso.setSelected(true);
                                    }
			if (u.getAlunoacesso()==0){
                            cbAluno.setSelected(false);
                                    }
                                    else{
                                        cbAluno.setSelected(true);
                                    }
			if (u.getCreditoacesso()==0){
                            cbCredito.setSelected(false);
                                    }
                                    else{
                                        cbCredito.setSelected(true);
                                    }
			if (u.getRefeitorioacesso()==0){
                            cbRefeitorio.setSelected(false);
                                    }
                                    else{
                                        cbRefeitorio.setSelected(true);
                                    }             
			if (u.getRelatorioacesso()==0){
                            cbRelatorio.setSelected(false);
                                    }
                                    else{
                                        cbRelatorio.setSelected(true);
                                    } 
                        if(u.getTimelineleitura()==0){
                            cbLer.setSelected(false);
                        }else{
                            cbLer.setSelected(true);
                        }
                        
                        if(u.getTimelineescrita()==0){
                            cbEscrita.setSelected(false);
                        }else
                        {
                            cbEscrita.setSelected(true);
                                   
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

        jPanel2 = new javax.swing.JPanel();
        JP_principal = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbFechar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbAcesso = new javax.swing.JCheckBox();
        cbUsuario = new javax.swing.JCheckBox();
        cbCredito = new javax.swing.JCheckBox();
        cbAluno = new javax.swing.JCheckBox();
        cbCurso = new javax.swing.JCheckBox();
        cbTurma = new javax.swing.JCheckBox();
        cbRelatorio = new javax.swing.JCheckBox();
        cbRefeitorio = new javax.swing.JCheckBox();
        txtId = new javax.swing.JLabel();
        txtNome = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cbLer = new javax.swing.JCheckBox();
        cbEscrita = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(576, 114, 0, 321);

        JP_principal.setBackground(new java.awt.Color(255, 255, 255));
        JP_principal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 4));

        btnSalvar.setBackground(new java.awt.Color(235, 114, 21));
        btnSalvar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setText("Atualizar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Código: ");

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Nome: ");

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

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Controle de Acesso ao Sistema");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(62, 62, 62)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbFechar))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        cbAcesso.setBackground(new java.awt.Color(255, 255, 255));
        cbAcesso.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbAcesso.setForeground(new java.awt.Color(51, 51, 51));
        cbAcesso.setText("Acesso");

        cbUsuario.setBackground(new java.awt.Color(255, 255, 255));
        cbUsuario.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbUsuario.setForeground(new java.awt.Color(51, 51, 51));
        cbUsuario.setText("Usuário");

        cbCredito.setBackground(new java.awt.Color(255, 255, 255));
        cbCredito.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbCredito.setForeground(new java.awt.Color(51, 51, 51));
        cbCredito.setText("Crédito");

        cbAluno.setBackground(new java.awt.Color(255, 255, 255));
        cbAluno.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbAluno.setForeground(new java.awt.Color(51, 51, 51));
        cbAluno.setText("Aluno");

        cbCurso.setBackground(new java.awt.Color(255, 255, 255));
        cbCurso.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbCurso.setForeground(new java.awt.Color(51, 51, 51));
        cbCurso.setText("Curso");
        cbCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCursoActionPerformed(evt);
            }
        });

        cbTurma.setBackground(new java.awt.Color(255, 255, 255));
        cbTurma.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbTurma.setForeground(new java.awt.Color(51, 51, 51));
        cbTurma.setText("Turma");
        cbTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTurmaActionPerformed(evt);
            }
        });

        cbRelatorio.setBackground(new java.awt.Color(255, 255, 255));
        cbRelatorio.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbRelatorio.setForeground(new java.awt.Color(51, 51, 51));
        cbRelatorio.setText("Relatório");

        cbRefeitorio.setBackground(new java.awt.Color(255, 255, 255));
        cbRefeitorio.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbRefeitorio.setForeground(new java.awt.Color(51, 51, 51));
        cbRefeitorio.setText("Refeitório");

        txtId.setText("jLabel1");

        txtNome.setText("jLabel1");

        cbLer.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbLer.setText("Time Line - Leitura");
        cbLer.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbLerStateChanged(evt);
            }
        });
        cbLer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbLerMouseClicked(evt);
            }
        });

        cbEscrita.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        cbEscrita.setText("Time Line - Escrita");
        cbEscrita.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbEscritaStateChanged(evt);
            }
        });
        cbEscrita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbEscritaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(cbEscrita)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(cbLer)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(cbLer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbEscrita)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JP_principalLayout = new javax.swing.GroupLayout(JP_principal);
        JP_principal.setLayout(JP_principalLayout);
        JP_principalLayout.setHorizontalGroup(
            JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_principalLayout.createSequentialGroup()
                .addGroup(JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JP_principalLayout.createSequentialGroup()
                        .addGroup(JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_principalLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtId))
                            .addGroup(JP_principalLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(JP_principalLayout.createSequentialGroup()
                                        .addComponent(cbAcesso)
                                        .addGap(9, 9, 9)
                                        .addComponent(cbCredito)
                                        .addGap(9, 9, 9)
                                        .addComponent(cbCurso)
                                        .addGap(17, 17, 17)
                                        .addComponent(cbRelatorio))
                                    .addGroup(JP_principalLayout.createSequentialGroup()
                                        .addComponent(cbUsuario)
                                        .addGap(5, 5, 5)
                                        .addComponent(cbAluno)
                                        .addGap(19, 19, 19)
                                        .addComponent(cbTurma)
                                        .addGap(13, 13, 13)
                                        .addComponent(cbRefeitorio))
                                    .addComponent(txtNome)))
                            .addGroup(JP_principalLayout.createSequentialGroup()
                                .addGap(212, 212, 212)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        JP_principalLayout.setVerticalGroup(
            JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_principalLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_principalLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtId))
                        .addGap(13, 13, 13)
                        .addGroup(JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbAcesso)
                            .addComponent(cbCredito)
                            .addComponent(cbCurso)
                            .addComponent(cbRelatorio))
                        .addGap(5, 5, 5)
                        .addGroup(JP_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbUsuario)
                            .addComponent(cbAluno)
                            .addComponent(cbTurma)
                            .addComponent(cbRefeitorio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(JP_principalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        getContentPane().add(JP_principal);
        JP_principal.setBounds(0, 0, 570, 270);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        atualizarAcesso();
        this.dispose();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void cbCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCursoActionPerformed

    private void cbTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTurmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTurmaActionPerformed

    private void lbFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseExited
//        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
//        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.white);
    }//GEN-LAST:event_lbFecharMouseExited

    private void lbFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseEntered
//        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
//        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.yellow);
    }//GEN-LAST:event_lbFecharMouseEntered

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        this.dispose();
    }//GEN-LAST:event_lbFecharMouseClicked

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:        Point p = this.getLocation();
        Point p = this.getLocation();
        this.setLocation(p.x +evt.getX()- point.y,p.y +evt.getY()- point.y);
        
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
              point.x = evt.getX();
             point.y = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void cbEscritaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbEscritaMouseClicked
        // TODO add your handling code here:
        if(!cbLer.isSelected() && cbEscrita.isSelected()){
           cbLer.setSelected(true);
        }

                    
    }//GEN-LAST:event_cbEscritaMouseClicked

    private void cbLerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbLerMouseClicked
        // TODO add your handling code here:
                    if(!cbLer.isSelected() && cbEscrita.isSelected()){
                        cbEscrita.setSelected(false);
                    }
    }//GEN-LAST:event_cbLerMouseClicked

    private void cbLerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbLerStateChanged
        // TODO add your handling code here:

        
    }//GEN-LAST:event_cbLerStateChanged

    private void cbEscritaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbEscritaStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cbEscritaStateChanged

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
            java.util.logging.Logger.getLogger(AcessoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AcessoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AcessoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AcessoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AcessoListaForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_principal;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JCheckBox cbAcesso;
    private javax.swing.JCheckBox cbAluno;
    private javax.swing.JCheckBox cbCredito;
    private javax.swing.JCheckBox cbCurso;
    private javax.swing.JCheckBox cbEscrita;
    private javax.swing.JCheckBox cbLer;
    private javax.swing.JCheckBox cbRefeitorio;
    private javax.swing.JCheckBox cbRelatorio;
    private javax.swing.JCheckBox cbTurma;
    private javax.swing.JCheckBox cbUsuario;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JLabel txtId;
    private javax.swing.JLabel txtNome;
    // End of variables declaration//GEN-END:variables
}
