/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Conexao.ConexaoBD;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import relatorios.Relatorioaluno;
import relatorios.Relatoriogenerico;
/**
 *
 * @author Home
 */
public class Relatorios extends javax.swing.JFrame {
 ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
  public static final String SRC = "relatorio.pdf";
  public static final String DEST = "relatoriopagina.pdf";


    /**
     * Creates new form Relatorios
     */
    public Relatorios() {
        initComponents();
        this.setExtendedState( MAXIMIZED_BOTH );
        sumirComponente();
        preecherCamposData();
    }
        public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte pagecontent;
        for (int i = 0; i < n; ) {
            pagecontent = stamper.getOverContent(++i);
            ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                    new Phrase(String.format("page %s of %s", i, n)), 559, 806, 0);
        }
        stamper.close();
        reader.close();
    }
    public void numerarPaginas() throws IOException, DocumentException{   
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Relatorios().manipulatePdf(SRC, DEST);
    

    }
   public void preecherCamposData(){
        Date hoje = new Date();
        Date diasdepois = new Date();
        Date diasantes = new Date();
        diasdepois.setDate(diasdepois.getDate()+30);
        diasantes.setDate(diasantes.getDate()-30);
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        //JLabel data = new JLabel(df.format(hoje));
        //txtDataInicial.setDate(diasantes);
        txtDataInicial.setDate(new Date());
        txtDataFinal.setDate(new Date());
       // txtDataFinal.setText(df.format(diasdepois));     
    }
       public String dataProBanco(String data){
       String dia = data.substring(0, 2);
       String mes = data.substring(3, 5);
       String ano = data.substring(6, 10);
       String databanco = ano+"-"+mes+"-"+dia;
       return databanco;
   }
  public String dataBanco(String data){
       String dia = data.substring(8, 10);
       String mes = data.substring(5, 7);
       String ano = data.substring(0, 4);
       String databanco = dia+"/"+mes+"/"+ano;
       return databanco;
   }
  public String formatarData(JDateChooser componente ){
        java.util.Date pega = componente.getDate();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(pega);
  }
    public void sumirComponente(){
           txtAluno.setVisible(true);
           txtCurso.setVisible(false);
           txtTurma.setVisible(false);
           btnBolsa.setVisible(false);
           
           btnAluno.setVisible(true);
           btnCurso.setVisible(false);
           btnTurma.setVisible(false);
    }
    public boolean verificarCamposData(){
        if(txtDataInicial.getDate().equals("") ||txtDataFinal.getDate().equals(""))
        return true;
        else{
            return false;
        }
        
    }
    public int validarSelect(){
        String aluno=txtAluno.getText();
        String curso=txtCurso.getText();
        String turma=txtTurma.getText();
        int retorno=0;
    
        if (btnSelecionar.getSelectedIndex()==0){
            if(txtAluno.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Você precisa escolher um ALUNO para iniciar a pesquisa");
            retorno=1;
            }

        }
        
            if (btnSelecionar.getSelectedIndex()==1){
            if(txtCurso.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Você precisa escolher um CURSO para iniciar a pesquisa");
            retorno=1;
            }
        }
        
    
        if (btnSelecionar.getSelectedIndex()==2){
            if(txtTurma.getText().equals("")|| txtTurma.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Você precisa escolher o CURSO e a TURMA para iniciar a pesquisa");
            retorno=1;
            }
        }
        

        if (btnSelecionar.getSelectedIndex()==4){
            if(txtCurso.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Você precisa escolher um CURSO para iniciar a pesquisa");
            retorno=1;
            }
        }
        return retorno;
    }
        
    
   public void mudancaSelect(){
          if (btnSelecionar.getSelectedIndex()==0){
           txtAluno.setVisible(true);
           btnAluno.setVisible(true);
           txtCurso.setVisible(false);
           txtTurma.setVisible(false);
           btnBolsa.setVisible(false);
           
           btnCurso.setVisible(false);
           btnTurma.setVisible(false);
           
        }
        if (btnSelecionar.getSelectedIndex()==1){
           txtAluno.setVisible(false);
           txtCurso.setVisible(true);
           txtTurma.setVisible(false);
           btnBolsa.setVisible(false);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(true);
           btnTurma.setVisible(false);
        }
        if (btnSelecionar.getSelectedIndex()==2){
           txtAluno.setVisible(false);
           txtCurso.setVisible(true);
           txtTurma.setVisible(true);
           btnBolsa.setVisible(false);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(true);
           btnTurma.setVisible(true);           
        }
        if (btnSelecionar.getSelectedIndex()==3){
           txtAluno.setVisible(false);
           txtCurso.setVisible(false);
           txtTurma.setVisible(false);
           btnBolsa.setVisible(true);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(false);
           btnTurma.setVisible(false);
        }
        if (btnSelecionar.getSelectedIndex()==4){
           txtAluno.setVisible(false);
           txtCurso.setVisible(true);
           txtTurma.setVisible(false);
           btnBolsa.setVisible(true);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(true);
           btnTurma.setVisible(false);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDataInicial = new com.toedter.calendar.JDateChooser();
        txtDataFinal = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        btnSelecionar = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        txtCurso = new javax.swing.JTextField();
        btnCurso = new javax.swing.JButton();
        txtTurma = new javax.swing.JTextField();
        btnTurma = new javax.swing.JButton();
        txtAluno = new javax.swing.JTextField();
        btnAluno = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnBolsa = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jcbxOrdem = new javax.swing.JComboBox<>();
        txtSelecCampo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Relatórios");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(382, 382, 382)
                .addComponent(jLabel1)
                .addContainerGap(397, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Período"));

        jLabel2.setText("Data Inicial");

        jLabel3.setText("Data Final");

        txtDataInicial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDataFinal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Relatório"));

        btnSelecionar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aluno", "Curso", "Curso+Turma", "Tipo de Bolsa", "Curso+Tipo de Bolsa", "Entre Datas - Pesquisa Geral", "Resumido: Todos alunos+ Tipo de Bolsa" }));
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });
        btnSelecionar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                btnSelecionarPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar"));

        btnCurso.setText("Buscar Curso");
        btnCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCursoActionPerformed(evt);
            }
        });

        btnTurma.setText("Buscar Turma");
        btnTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurmaActionPerformed(evt);
            }
        });

        btnAluno.setText("Buscar Aluno");
        btnAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlunoActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_file_11_1377261.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnBolsa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bolsa 100%", "Bolsa 50%", "Inativa" }));

        jLabel4.setText("Modo de Visualização");

        jcbxOrdem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CRESCENTE", "DECRESCENTE" }));

        txtSelecCampo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Horário Refeição", "Horário Reserva", "Bolsa" }));

        jLabel5.setText("Escolha a categoria para ordenar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnImprimir)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jcbxOrdem, javax.swing.GroupLayout.Alignment.LEADING, 0, 192, Short.MAX_VALUE)
                                    .addComponent(btnBolsa, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTurma))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAluno)))
                        .addContainerGap(97, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCurso))
                            .addComponent(jLabel4)
                            .addComponent(txtSelecCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCurso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTurma))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAluno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbxOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSelecCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImprimir)
                .addContainerGap())
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_door_in_35977.png"))); // NOI18N
        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(347, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelecionarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_btnSelecionarPropertyChange
        // TODO add your handling code here:


    }//GEN-LAST:event_btnSelecionarPropertyChange

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        // TODO add your handling code here:
         if (btnSelecionar.getSelectedIndex()==0){
           txtAluno.setVisible(true);
           btnAluno.setVisible(true);
           txtCurso.setVisible(false);
           txtTurma.setVisible(false);
           btnBolsa.setVisible(false);
           txtAluno.setText("");
           btnCurso.setVisible(false);
           btnTurma.setVisible(false);
           
        }
        if (btnSelecionar.getSelectedIndex()==1){
           txtAluno.setVisible(false);
           txtCurso.setVisible(true);
           txtCurso.setText("");
           txtTurma.setVisible(false);
           btnBolsa.setVisible(false);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(true);
           btnTurma.setVisible(false);
        }
        if (btnSelecionar.getSelectedIndex()==2){
           txtAluno.setVisible(false);
           txtCurso.setVisible(true);
           txtTurma.setVisible(true);
           txtCurso.setText("");
           txtTurma.setText("");
           btnBolsa.setVisible(false);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(true);
           btnTurma.setVisible(true);           
        }
        if (btnSelecionar.getSelectedIndex()==3){
           txtAluno.setVisible(false);
           txtCurso.setVisible(false);
           txtTurma.setVisible(false);
           btnBolsa.setVisible(true);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(false);
           btnTurma.setVisible(false);
        }
        if (btnSelecionar.getSelectedIndex()==4){
           txtAluno.setVisible(false);
           txtCurso.setVisible(true);
           txtCurso.setText("");
           txtTurma.setVisible(false);
           btnBolsa.setVisible(true);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(true);
           btnTurma.setVisible(false);
        }
        if (btnSelecionar.getSelectedIndex()==5){
           txtAluno.setVisible(false);
           txtCurso.setVisible(false);
           txtCurso.setText("");
           txtTurma.setVisible(false);
           btnBolsa.setVisible(false);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(false);
           btnTurma.setVisible(false);
        }
        if (btnSelecionar.getSelectedIndex()==6){
           txtAluno.setVisible(false);
           txtCurso.setVisible(false);
           txtCurso.setText("");
           txtTurma.setVisible(false);
           btnBolsa.setVisible(true);
           
           btnAluno.setVisible(false);
           btnCurso.setVisible(false);
           btnTurma.setVisible(false);
        }        
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
       
        int v=0;
        String ordem="";
        Relatoriogenerico rel = new Relatoriogenerico();
                                SimpleDateFormat dataini = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat datafin = new SimpleDateFormat("yyyy-MM-dd");        
        if(verificarCamposData()){
            JOptionPane.showMessageDialog(null, "Campos data devem ser preenchidos");           
        }
        else {
            
            if(validarSelect()==0){
                        String campo=null;
                        if(txtSelecCampo.getSelectedIndex()==0){
                            campo="NOMEALUNO"; 
                        }else if(txtSelecCampo.getSelectedIndex()==1){
                             campo="HORARIOREFEICAO";
                        }
                        else if(txtSelecCampo.getSelectedIndex()==2){
                             campo="HORAREFEITORIO";

                        }else if(txtSelecCampo.getSelectedIndex()==3){
                             campo="TIPOBOLSA";
                        }
                                
                                if(jcbxOrdem.getSelectedIndex()==0){
                                    ordem="ASC";
                                }else{
                                    ordem="DESC";
                                }

                                if(btnSelecionar.getSelectedIndex()==0){
                                    //perquisar pelo pelo aluno
                                    try {
                                        try {
                                            rel.gerarPdfRefeicao("Pesquisa por Aluno","Entre "+formatarData(txtDataInicial)+" até "+formatarData(txtDataFinal),0,dataini.format(txtDataInicial.getDate()), datafin.format(txtDataFinal.getDate()), Integer.parseInt(txtAluno.getText()),0,0,0,campo,ordem,1);
                                        } catch (BadElementException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (DocumentException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if(btnSelecionar.getSelectedIndex()==1){
                                    try {
                                        try {
                                            //perquisar pelo curso

                                            rel.gerarPdfRefeicao("Pesquisa por Curso","Entre "+formatarData(txtDataInicial)+" até "+formatarData(txtDataFinal),1,dataini.format(txtDataInicial.getDate()), datafin.format(txtDataFinal.getDate()),0,0,Integer.parseInt(txtCurso.getText()),0,campo,ordem,1);
                                        } catch (BadElementException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (DocumentException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                                if(btnSelecionar.getSelectedIndex()==2){
                                    try {
                                        try {
                                            //pesquisar pelo curso e pela turma
                                            rel.gerarPdfRefeicao("Pesquisa por Curso e Turma","Entre "+formatarData(txtDataInicial)+" até "+formatarData(txtDataFinal),2,dataini.format(txtDataInicial.getDate()), datafin.format(txtDataFinal.getDate()),0,0,Integer.parseInt(txtCurso.getText()),Integer.parseInt(txtTurma.getText()),campo,ordem,1);
                                        } catch (BadElementException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (DocumentException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if(btnSelecionar.getSelectedIndex()==3){
                                    try {
                                        try {
                                            //pesquisar por tipo de bolsa
                                            rel.gerarPdfRefeicao("Pesquisa por tipo de Bolsa","Entre "+formatarData(txtDataInicial)+" até "+formatarData(txtDataFinal),3,dataini.format(txtDataInicial.getDate()), datafin.format(txtDataFinal.getDate()),Integer.parseInt(txtAluno.getText()), btnBolsa.getSelectedIndex(),0,0,campo,ordem,1);
                                        } catch (BadElementException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (DocumentException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if(btnSelecionar.getSelectedIndex()==4){
                                    try {
                                        try {
                                            //pesquisar por curso e tipo de bolsa

                                            rel.gerarPdfRefeicao("Pesquisa por Curso e Tipo de Bolsa","Entre "+formatarData(txtDataInicial)+" até "+formatarData(txtDataFinal),4,dataini.format(txtDataInicial.getDate()), datafin.format(txtDataFinal.getDate()),Integer.parseInt(txtAluno.getText()), btnBolsa.getSelectedIndex(),Integer.parseInt(txtCurso.getText()),0,campo,ordem,1);
                                        } catch (BadElementException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (DocumentException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if(btnSelecionar.getSelectedIndex()==5){
                                    try {
                                        try {
                                            //pesquisar entre datas, período
                                            rel.gerarPdfRefeicao("Pesquisa por Período","Entre "+formatarData(txtDataInicial)+" até "+formatarData(txtDataFinal),5,dataini.format(txtDataInicial.getDate()), datafin.format(txtDataFinal.getDate()),0,0,0,0,campo,ordem,1);
                                        } catch (BadElementException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } catch (DocumentException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                                if(btnSelecionar.getSelectedIndex()==6){
                                String nomebolsa="";
//                                Relatoriogenerico rel = new Relatoriogenerico();
//                                SimpleDateFormat dataini = new SimpleDateFormat("yyyy-MM-dd");
//                                SimpleDateFormat datafin = new SimpleDateFormat("yyyy-MM-dd"); 
                                if(btnBolsa.getSelectedIndex()==0){
                                    nomebolsa="100%";
                                }else{
                                    nomebolsa="50%";
                                }
                                
                                    try {
                                        rel.gerarPdfRefeicaoResumido("Relatório de Auxílio Alimentação: "+nomebolsa,"Período de "+formatarData(txtDataInicial)+" à "+formatarData(txtDataFinal),dataini.format(txtDataInicial.getDate()), datafin.format(txtDataFinal.getDate()),btnBolsa.getSelectedIndex());// TODO add your handling code here:
                                                } catch (DocumentException ex) {
                                                    Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (FileNotFoundException ex) {
                                Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                       }
                                  }
            
   
      
            
            
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlunoActionPerformed
        // TODO add your handling code here:
        int retorno=0;
        PesquisaAlunoRelForm pesquisar = new PesquisaAlunoRelForm(this,true);
        pesquisar.setVisible(true);
        retorno=pesquisar.getId();
        if(retorno>0){
            txtAluno.setText(Integer.toString(retorno));
          
        }
        
    }//GEN-LAST:event_btnAlunoActionPerformed

    private void btnCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCursoActionPerformed
        // TODO add your handling code here:
        int retorno=0;
        PesquisarCursoForm pesquisar = new PesquisarCursoForm(this,true);
        pesquisar.setVisible(true);
        retorno=pesquisar.getIdcurso();
        if(retorno>0){
            txtCurso.setText(Integer.toString(retorno));
          
        }
    }//GEN-LAST:event_btnCursoActionPerformed

    private void btnTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurmaActionPerformed
        // TODO add your handling code here:
         int retorno=0;
        PesquisarTurmaForm pesquisar = new PesquisarTurmaForm(this,true);
        pesquisar.carregarTudo(Integer.parseInt(txtCurso.getText()),"%%");
        pesquisar.setVisible(true);
        retorno=pesquisar.getIdturma();
        if(retorno>0){
            txtTurma.setText(Integer.toString(retorno));
          
        }       
    }//GEN-LAST:event_btnTurmaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Relatorios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAluno;
    private javax.swing.JComboBox<String> btnBolsa;
    private javax.swing.JButton btnCurso;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JComboBox<String> btnSelecionar;
    private javax.swing.JButton btnTurma;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JComboBox<String> jcbxOrdem;
    private javax.swing.JTextField txtAluno;
    private javax.swing.JTextField txtCurso;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDataInicial;
    private javax.swing.JComboBox<String> txtSelecCampo;
    private javax.swing.JTextField txtTurma;
    // End of variables declaration//GEN-END:variables
}
