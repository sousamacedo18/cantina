/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.CreditoController;
import Dao.CreditoDao;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class CreditoEditarForm extends javax.swing.JDialog {
    ConexaoBD conexao = new ConexaoBD();
    CreditoDao creditodao = new CreditoDao();
    CreditoController creditos = new CreditoController();
    private DefaultTableModel modelo = new DefaultTableModel();
    private String DataInicialFormatada;
    private String DataFinalFormatada;
    private Integer retorno=0;
    private String dataformatab;

    public Integer getRetorno() {
        return retorno;
    }        public void buscardataatual() throws ParseException{
        Date hoje = new Date();
        Date diasdepois = new Date();
        diasdepois.setDate(diasdepois.getDate()+30);
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        //JLabel data = new JLabel(df.format(hoje));
//        txtDataInicial.setText(df.format(hoje));
//        txtDataFinal.setText(df.format(diasdepois));
//        
        String d1=hoje.toString();
        String d2=diasdepois.toString();
//        java.util.Date sdf1 = new SimpleDateFormat("yyyy-MM-dd").parse(d1);
//        java.util.Date sdf2 = new SimpleDateFormat("yyyy-MM-dd").parse(d2);
        txtDataInicial.setDate(new Date());
        txtDataFinal.setDate(diasdepois);
    }
    
    /**
     * Creates new form CreditoEditarForm
     */
    public CreditoEditarForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
            public boolean verificarCarmpos(){
        
        if(txtId.getText().equals("")|| txtDataFinal.getDate()==null 
                || txtDataInicial.getDate()==null  || 
                txtTotCreditos.getText().equals("")||txtCreditoDia.getText().equals("")) {
            
           return true;
        }
        return false;
    }
       public void SalvarCredito(){
       SimpleDateFormat dataini = new SimpleDateFormat("yyyy-MM-dd");
       SimpleDateFormat datafin = new SimpleDateFormat("yyyy-MM-dd");
       creditos.setCreditodia(Double.parseDouble(txtCreditoDia.getText()));
       creditos.setDatainicial(dataini.format(txtDataInicial.getDate()));
       creditos.setDatafinal(datafin.format(txtDataFinal.getDate()));
       creditos.setTipobolsa(CbBolsa.getSelectedIndex());
       if(jcbxControle.isSelected()){
           creditos.setControle(1);
       } else if(!jcbxControle.isSelected()){
           creditos.setControle(0);
       }       
       if(jcbxBloquear.isSelected()){
           creditos.setBloquealuno(1);
       } else if(!jcbxBloquear.isSelected()){
           creditos.setBloquealuno(0);
       }       
       creditos.setIdcredito(Integer.parseInt(txtId.getText()));
       creditos.setIdcredaluno(Integer.parseInt(txtIdAluno.getText()));
       creditos.setObservacaocredito(txtObservacao.getText());
       creditos.setTotalcreditos(Double.parseDouble(txtTotCreditos.getText()));
        creditodao.AtualizarCredito(creditos);
        }
 
                    
      public String dataParaBanco(String data){
          String dia=data.substring(0,2);
          String mes=data.substring(3,5);
          String ano=data.substring(6);
          String dataformatada = ano+"-"+mes+"-"+dia;
          return dataformatada;
      }
      public String dataBanco(String data){
       String dia = data.substring(8, 10);
       String mes = data.substring(5, 7);
       String ano = data.substring(0, 4);
       String databanco = dia+"/"+mes+"/"+ano;
       return databanco;
   }
    public void CarregarDados(Integer id) throws ParseException{
      
       for (CreditoController u : creditodao.CarregarCredito(id)) {
	      txtIdAluno.setText(Integer.toString(u.getIdcredaluno()));
              txtNome.setText(u.getNomealuno()); 
              txtId.setText(Integer.toString(u.getIdcredito()));
              txtCreditoDia.setText(Double.toString(u.getCreditodia()));
              ((JTextField)txtDataInicial.getDateEditor().getUiComponent()).setText(dataBanco(u.getDatainicial()));
              ((JTextField)txtDataFinal.getDateEditor().getUiComponent()).setText(dataBanco(u.getDatafinal()));
//              txtDataInicial.setDate(date1);
//              txtDataFinal.setDate(date2);
              txtObservacao.setText(u.getObservacaocredito());
              txtTotCreditos.setText(Double.toString(u.getTotalcreditos()));
              txtCreditosUtilizados.setText(Double.toString(u.getCreditosutilizados()));
              CbBolsa.setSelectedIndex(u.getTipobolsa());
              
              if(u.getControle()==0){
                  jcbxControle.setSelected(false);
              }
              else{
                 jcbxControle.setSelected(true);
              }
              if(u.getBloquealuno()==0){
                  jcbxBloquear.setSelected(false);
              }
              else if(u.getBloquealuno()==1){
                 jcbxBloquear.setSelected(true);
              }
                      
	txtId.setEditable(false);
	txtIdAluno.setEditable(false);
	txtNome.setEditable(false);
        CbBolsa.requestFocus();
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

        jPanel5 = new javax.swing.JPanel();
        lbTitulo1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        lbNomeOriginal = new javax.swing.JLabel();
        lbNomeOriginal1 = new javax.swing.JLabel();
        lbNomeOriginal2 = new javax.swing.JLabel();
        txtTotCreditos = new javax.swing.JTextField();
        lbNomeOriginal3 = new javax.swing.JLabel();
        txtCreditoDia = new javax.swing.JTextField();
        lbNomeOriginal4 = new javax.swing.JLabel();
        txtObservacao = new javax.swing.JTextField();
        lbNomeOriginal5 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lbNomeOriginal6 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lbNomeOriginal7 = new javax.swing.JLabel();
        txtIdAluno = new javax.swing.JTextField();
        txtCreditosUtilizados = new javax.swing.JTextField();
        lbNomeOriginal8 = new javax.swing.JLabel();
        jcbxControle = new javax.swing.JCheckBox();
        jcbxBloquear = new javax.swing.JCheckBox();
        CbBolsa = new javax.swing.JComboBox<>();
        txtDataInicial = new com.toedter.calendar.JDateChooser();
        txtDataFinal = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));

        lbTitulo1.setBackground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 24)); // NOI18N
        lbTitulo1.setForeground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTitulo1.setText("Editar Créditos");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(lbTitulo1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitulo1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(204, 255, 255));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_save_173091.png"))); // NOI18N
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

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_Log Out_27856.png"))); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));

        lbNome.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNome.setForeground(new java.awt.Color(102, 102, 102));
        lbNome.setText("Tipo Bolsa");

        lbNomeOriginal.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal.setText("Final");

        lbNomeOriginal1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal1.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal1.setText("ID Aluno");

        lbNomeOriginal2.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal2.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal2.setText("Observações");

        txtTotCreditos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTotCreditos.setText("0.00");
        txtTotCreditos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTotCreditosFocusGained(evt);
            }
        });
        txtTotCreditos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotCreditosActionPerformed(evt);
            }
        });
        txtTotCreditos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtTotCreditosPropertyChange(evt);
            }
        });
        txtTotCreditos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotCreditosKeyPressed(evt);
            }
        });

        lbNomeOriginal3.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal3.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal3.setText("Início");

        txtCreditoDia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCreditoDia.setText("1.00");
        txtCreditoDia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCreditoDiaFocusGained(evt);
            }
        });
        txtCreditoDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreditoDiaActionPerformed(evt);
            }
        });
        txtCreditoDia.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCreditoDiaPropertyChange(evt);
            }
        });
        txtCreditoDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCreditoDiaKeyPressed(evt);
            }
        });

        lbNomeOriginal4.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal4.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal4.setText("Créditos por dia");

        txtObservacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtObservacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtObservacaoFocusGained(evt);
            }
        });
        txtObservacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacaoActionPerformed(evt);
            }
        });
        txtObservacao.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtObservacaoPropertyChange(evt);
            }
        });
        txtObservacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObservacaoKeyPressed(evt);
            }
        });

        lbNomeOriginal5.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal5.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal5.setText("Créditos Utilizados");

        lbNomeOriginal6.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal6.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal6.setText("Nome Aluno");

        lbNomeOriginal7.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal7.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal7.setText("ID");

        txtCreditosUtilizados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCreditosUtilizados.setText("0.00");
        txtCreditosUtilizados.setEnabled(false);
        txtCreditosUtilizados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCreditosUtilizadosFocusGained(evt);
            }
        });
        txtCreditosUtilizados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreditosUtilizadosActionPerformed(evt);
            }
        });
        txtCreditosUtilizados.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCreditosUtilizadosPropertyChange(evt);
            }
        });

        lbNomeOriginal8.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal8.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal8.setText("Total de Créditos");

        jcbxControle.setText("Controlar Créditos");
        jcbxControle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcbxControleKeyPressed(evt);
            }
        });

        jcbxBloquear.setText("bloquear");
        jcbxBloquear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcbxBloquearKeyPressed(evt);
            }
        });

        CbBolsa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CbBolsa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bolsa 100%", "Bolsa 50%", "Outros", " " }));
        CbBolsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CbBolsaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNomeOriginal2)
                    .addComponent(txtObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(txtTotCreditos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(51, 51, 51)
                                    .addComponent(txtCreditoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbNomeOriginal8)
                                    .addGap(25, 25, 25)
                                    .addComponent(lbNomeOriginal4)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCreditosUtilizados, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbNomeOriginal5)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbNomeOriginal7))
                            .addGap(4, 4, 4)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbNomeOriginal1)
                                    .addGap(3, 3, 3))
                                .addComponent(txtIdAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbNomeOriginal6)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(CbBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbNome)
                                    .addGap(118, 118, 118)
                                    .addComponent(lbNomeOriginal3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbNomeOriginal)
                                .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(8, 8, 8))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jcbxControle)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbxBloquear))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbNomeOriginal7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNomeOriginal6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbNomeOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNomeOriginal3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CbBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeOriginal5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNomeOriginal4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNomeOriginal8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCreditoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotCreditos, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCreditosUtilizados, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbxControle)
                    .addComponent(jcbxBloquear))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        }else{
            SalvarCredito();
            retorno=1;
            this.dispose();
            //
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //TODO add your handling code here:
        //                UsuarioListaForm cadastrousuario = new UsuarioListaForm();
        //                cadastrousuario.setVisible(true);
        //                limparCampos();
        retorno=0;
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtTotCreditosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotCreditosFocusGained

    }//GEN-LAST:event_txtTotCreditosFocusGained

    private void txtTotCreditosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotCreditosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotCreditosActionPerformed

    private void txtTotCreditosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTotCreditosPropertyChange

    }//GEN-LAST:event_txtTotCreditosPropertyChange

    private void txtCreditoDiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCreditoDiaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreditoDiaFocusGained

    private void txtCreditoDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreditoDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreditoDiaActionPerformed

    private void txtCreditoDiaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCreditoDiaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreditoDiaPropertyChange

    private void txtObservacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtObservacaoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacaoFocusGained

    private void txtObservacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacaoActionPerformed

    private void txtObservacaoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtObservacaoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacaoPropertyChange

    private void txtCreditosUtilizadosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCreditosUtilizadosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreditosUtilizadosFocusGained

    private void txtCreditosUtilizadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreditosUtilizadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreditosUtilizadosActionPerformed

    private void txtCreditosUtilizadosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCreditosUtilizadosPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreditosUtilizadosPropertyChange

    private void CbBolsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CbBolsaKeyPressed
        // TODO add your handling code here:
                       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtDataInicial.requestFocus();
               }
    }//GEN-LAST:event_CbBolsaKeyPressed

    private void txtTotCreditosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotCreditosKeyPressed
        // TODO add your handling code here:
                       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtCreditoDia.requestFocus();
               }
    }//GEN-LAST:event_txtTotCreditosKeyPressed

    private void txtCreditoDiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCreditoDiaKeyPressed
        // TODO add your handling code here:
                       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtObservacao.requestFocus();
               }
    }//GEN-LAST:event_txtCreditoDiaKeyPressed

    private void txtObservacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacaoKeyPressed
        // TODO add your handling code here:
               if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    jcbxControle.requestFocus();
               }
    }//GEN-LAST:event_txtObservacaoKeyPressed

    private void jcbxControleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcbxControleKeyPressed
        // TODO add your handling code here:
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    jcbxBloquear.requestFocus();
               }
    }//GEN-LAST:event_jcbxControleKeyPressed

    private void jcbxBloquearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcbxBloquearKeyPressed
        // TODO add your handling code here:
                       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnSalvar.requestFocus();
               }
    }//GEN-LAST:event_jcbxBloquearKeyPressed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        // TODO add your handling code here:
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                          if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        }else{
            SalvarCredito();
            retorno=1;
            this.dispose();
            //
        }
               }
    }//GEN-LAST:event_btnSalvarKeyPressed

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
            java.util.logging.Logger.getLogger(CreditoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreditoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreditoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreditoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CreditoEditarForm dialog = new CreditoEditarForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> CbBolsa;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JCheckBox jcbxBloquear;
    private javax.swing.JCheckBox jcbxControle;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbNomeOriginal;
    private javax.swing.JLabel lbNomeOriginal1;
    private javax.swing.JLabel lbNomeOriginal2;
    private javax.swing.JLabel lbNomeOriginal3;
    private javax.swing.JLabel lbNomeOriginal4;
    private javax.swing.JLabel lbNomeOriginal5;
    private javax.swing.JLabel lbNomeOriginal6;
    private javax.swing.JLabel lbNomeOriginal7;
    private javax.swing.JLabel lbNomeOriginal8;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JTextField txtCreditoDia;
    private javax.swing.JTextField txtCreditosUtilizados;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDataInicial;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdAluno;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtObservacao;
    private javax.swing.JTextField txtTotCreditos;
    // End of variables declaration//GEN-END:variables
}
