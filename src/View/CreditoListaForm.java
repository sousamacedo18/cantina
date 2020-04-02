/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.CreditoController;
import Dao.CreditoDao;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Home
 */
public class CreditoListaForm extends javax.swing.JFrame {
     ConexaoBD conexao = new ConexaoBD();
    CreditoDao creditosdao = new CreditoDao();
    CreditoController creditos = new CreditoController();
    private DefaultTableModel modelo = new DefaultTableModel();
    private Integer contador;
    private Double contAluno;
    private String DataInicialFormatada;
    private String DataFinalFormatada;
    public Integer idlogado;
    /**
     * Creates new form CreditoListaForm
     */
    public CreditoListaForm() throws ParseException {
        initComponents();
        //preencherTabela();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setExtendedState( MAXIMIZED_BOTH );
        buscardataatual();
        txtIdaluno.setText("");
        txtIdaluno.setVisible(false);
        btnBuscar.setVisible(false);
        lbIdtitulo.setVisible(false);
    }
    public void formatarData(){
        Date data = new Date(System.currentTimeMillis());  
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd"); 
        System.out.print(formatarDate.format(data));
    }

    public void formatarDataParabanco(String dataini, String datafin){
        Date datainicial = new Date(dataini);
        Date datafinal = new Date(datafin);
        SimpleDateFormat df;
        df = new SimpleDateFormat("dd/MM/yyyy");
        //JLabel data = new JLabel(df.format(hoje));
        DataInicialFormatada=df.format(datainicial);
        DataFinalFormatada=df.format(datafinal); 
              
	}
    
public void recuperaData(String di,String df) {
String datai = di;
String dataf = df;

SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
DataInicialFormatada = formato.format(datai);
DataFinalFormatada = formato.format(dataf);
}
   public void buscardataatual() throws ParseException{
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
    public void preencherTabela(){
               contador=0;
               contAluno=0.00;
               Double utilizados=0.00;
               int bolsa100=0,bolsa50=0;
               String bloqueado="",controle="",bolsa="";
               
               
               DefaultTableModel tabela = (DefaultTableModel) tblCredito.getModel();
               tblCredito.setModel(tabela);
               tblCredito.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
               tblCredito.getColumnModel().getColumn(0).setPreferredWidth(60);
               tblCredito.getColumnModel().getColumn(1).setPreferredWidth(60);
               tblCredito.getColumnModel().getColumn(2).setPreferredWidth(300);
               tblCredito.getColumnModel().getColumn(3).setPreferredWidth(60);
               tblCredito.getColumnModel().getColumn(3).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(4).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(5).setPreferredWidth(100);
               tblCredito.getColumnModel().getColumn(6).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(7).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(8).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(9).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(10).setPreferredWidth(250);
               tblCredito.getColumnModel().getColumn(11).setPreferredWidth(250);

       		tabela.setNumRows(0);
		CreditoDao dao = new CreditoDao();
            
		for (CreditoController u : dao.CarregarTodosCreditos()) {
                       //JOptionPane.showMessageDialog(null,"aqui");
                       if(u.getControle()==1){controle="Sim";}else{controle="Não";}
                       if(u.getBloquealuno()==1){bloqueado="Sim";}else{bloqueado="Não";}
                       if(u.getTipobolsa()==0){bolsa="100%"; bolsa100++;}else{bolsa="50%";bolsa50++;}
                       utilizados=utilizados+u.getCreditosutilizados();
			tabela.addRow(new Object[]
                        {u.getIdcredito(), 
                         u.getIdcredaluno(),
                         u.getNomealuno(),
                         bolsa,
                         dataBanco(u.getDatainicial()),
                         dataBanco(u.getDatafinal()),
                         u.getTotalcreditos(), 
                         u.getCreditosutilizados(),
                         u.getCreditodia(),
                         bloqueado,
                         controle,
                         u.getObservacaocredito(),
                         u.getNomeusuario()
                        }
                        );
		contador++;
                contAluno=contAluno+u.getTotalcreditos();
                }
                if(contador>0){
                tblCredito.setRowSelectionInterval(0,0);
                }
                lbtotal.setText(Integer.toString(contador));
                lbtotalAluno.setText(Double.toString(contAluno));
                lbtotalUtilizados.setText(Double.toString(utilizados));
                lbtotal100.setText(Integer.toString(bolsa100));
                lbtotal50.setText(Integer.toString(bolsa50));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCredito = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lbtotal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CbPesquisa = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        lbIdtitulo = new javax.swing.JLabel();
        txtIdaluno = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lbtotalAluno = new javax.swing.JLabel();
        txtDataInicial = new com.toedter.calendar.JDateChooser();
        txtDataFinal = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        lbtotal100 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbtotal50 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbtotalUtilizados = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("Lista de Créditos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(314, 314, 314)
                .addComponent(jLabel1)
                .addContainerGap(863, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(21, 21, 21))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblCredito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "idAluno", "Nome Aluno", "Bolsa", "Data Inicial", "Data Final", "Total Créditos", "Utilizados", "Crédito/Dia", "Bloqueio ", "Controle", "Observação", "Administrador"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCredito);
        if (tblCredito.getColumnModel().getColumnCount() > 0) {
            tblCredito.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("Total de Créditos:");

        lbtotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbtotal.setForeground(new java.awt.Color(255, 0, 0));
        lbtotal.setText("Total");

        jLabel3.setText("Data Inicial");

        jLabel4.setText("Data Final");

        CbPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entre datas", "Entre datas + aluno" }));
        CbPesquisa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CbPesquisaItemStateChanged(evt);
            }
        });
        CbPesquisa.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                CbPesquisaPropertyChange(evt);
            }
        });

        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lbIdtitulo.setText("ID Aluno");

        btnBuscar.setText("Buscar Aluno");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Total de Alunos:");

        lbtotalAluno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbtotalAluno.setForeground(new java.awt.Color(255, 0, 0));
        lbtotalAluno.setText("Aluno");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Total de Bolsas 100%:");

        lbtotal100.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbtotal100.setForeground(new java.awt.Color(255, 0, 0));
        lbtotal100.setText("100");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("Total de Bolsas 50%:");

        lbtotal50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbtotal50.setForeground(new java.awt.Color(255, 0, 0));
        lbtotal50.setText("50");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Total de Créditos Utilizados:");

        lbtotalUtilizados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbtotalUtilizados.setForeground(new java.awt.Color(255, 0, 0));
        lbtotalUtilizados.setText("Utilizados");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(CbPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(107, 107, 107))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbIdtitulo)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtIdaluno, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbtotal)
                .addGap(92, 92, 92)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbtotal50)
                .addGap(71, 71, 71)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbtotal100)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbtotalAluno)
                .addGap(51, 51, 51)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbtotalUtilizados)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(lbIdtitulo))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CbPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtIdaluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnBuscar))
                                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(lbtotal50))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(lbtotalAluno)
                        .addComponent(lbtotal))
                    .addComponent(jLabel2)
                    .addComponent(lbtotal100)
                    .addComponent(jLabel6)
                    .addComponent(lbtotalUtilizados)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

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

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_Log Out_27856.png"))); // NOI18N
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        CreditoCadastrarFrom cadastrocredito = null;
         try {
             cadastrocredito = new CreditoCadastrarFrom(this,true);
         } catch (ParseException ex) {
             Logger.getLogger(CreditoListaForm.class.getName()).log(Level.SEVERE, null, ex);
         }
        cadastrocredito.idlogado=this.idlogado;
        cadastrocredito.setVisible(true);
        int retorno=cadastrocredito.getRetorno();
        if(retorno==1){
            preencherTabela();
        }
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:       
        CreditoEditarForm creditoEditar = new CreditoEditarForm(this, true);
        int linhaSelecionada = -1;
        linhaSelecionada = tblCredito.getSelectedRow();
        if (linhaSelecionada>=0){
            int id=(int)tblCredito.getValueAt(linhaSelecionada, 0);
            try {
                creditoEditar.CarregarDados(id);
            } catch (ParseException ex) {
                Logger.getLogger(CreditoListaForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            creditoEditar.setVisible(true);
            Integer retorno=creditoEditar.getRetorno();
            if(retorno==1){
                preencherTabela();
            }
                
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Crédito");
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Deseja Realmente Excluir Esse Registro?", "Confirm",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {

            DefaultTableModel tabela = (DefaultTableModel) tblCredito.getModel();
            tblCredito.setModel(tabela);
            int linhaSelecionada = -1;
            linhaSelecionada = tblCredito.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int usu = (int) tblCredito.getValueAt(linhaSelecionada, 0);
                CreditoDao dao = new CreditoDao();
                creditos.setIdcredito(usu);
                dao.ExcluirCredito(creditos);
                tabela.removeRow(linhaSelecionada);
            } else {
                JOptionPane.showMessageDialog(null, "É necesário selecionar um registro.");
            }
        }

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        //PesquisaAlunoForm pesquisar = new PesquisaAlunoForm(this,true);
//        pesquisar.setVisible(true);
//        txtIdaluno.setText(Integer.toString(pesquisar.getId()));
        
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void CbPesquisaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CbPesquisaPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_CbPesquisaPropertyChange

    private void CbPesquisaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CbPesquisaItemStateChanged
        // TODO add your handling code here:
                Integer index=CbPesquisa.getSelectedIndex();
        
        if(index==0){
            txtIdaluno.setText("");
            txtIdaluno.setVisible(false);
            btnBuscar.setVisible(false);
            lbIdtitulo.setVisible(false);
            
        }
        else{
            txtIdaluno.setText("");
            txtIdaluno.setVisible(true);
            btnBuscar.setVisible(true); 
            lbIdtitulo.setVisible(true);
            
            
        }
    }//GEN-LAST:event_CbPesquisaItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       SimpleDateFormat dataini = new SimpleDateFormat("yyyy-MM-dd");
       SimpleDateFormat datafin = new SimpleDateFormat("yyyy-MM-dd");
       String datainicial=dataini.format(txtDataInicial.getDate());
       String datafinal= datafin.format(txtDataFinal.getDate());    
        if(CbPesquisa.getSelectedIndex()==0){
//            String datainicial=dataParaBanco(txtDataInicial.getText());
//            String datafinal= dataParaBanco(txtDataFinal.getText());


                        contador=0;
                        contAluno=0.00;
                                
              
               DefaultTableModel tabela = (DefaultTableModel) tblCredito.getModel();
               tblCredito.setModel(tabela);
               tblCredito.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
              tblCredito.getColumnModel().getColumn(0).setPreferredWidth(60);
               tblCredito.getColumnModel().getColumn(1).setPreferredWidth(60);
               tblCredito.getColumnModel().getColumn(2).setPreferredWidth(300);
               tblCredito.getColumnModel().getColumn(3).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(4).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(5).setPreferredWidth(100);
               tblCredito.getColumnModel().getColumn(6).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(7).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(8).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(9).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(10).setPreferredWidth(250);
               tblCredito.getColumnModel().getColumn(11).setPreferredWidth(250);
       		tabela.setNumRows(0);
		CreditoDao dao = new CreditoDao();
            
		  for (CreditoController u : creditosdao.CarregarEntreDatas(datainicial, datafinal)) {
          
						tabela.addRow(new Object[]
                        {u.getIdcredito(), 
                         u.getIdcredaluno(),
                         u.getNomealuno(),
                         dataBanco(u.getDatainicial()),
                         dataBanco(u.getDatafinal()),
                         u.getTotalcreditos(), 
                         u.getCreditosutilizados(),
                         u.getCreditodia(),
                         u.getBloquealuno(),
                         u.getControle(),
                         u.getObservacaocredito(),
                         u.getNomeusuario()
                        }
                        );
		contador++;
                contAluno=contAluno+u.getTotalcreditos();
                }
                lbtotal.setText(Integer.toString(contador));
                lbtotalAluno.setText(Double.toString(contAluno));
             }
        if(CbPesquisa.getSelectedIndex()==1){
          if(txtIdaluno.equals("")){
              JOptionPane.showMessageDialog(null, "Nessa opção de pesquisa o campo ID ALUNO deve ser preenchido!!!");
          }  else{
//            String datainicial=dataParaBanco(txtDataInicial.getText());
//            String datafinal= dataParaBanco(txtDataFinal.getText());
//            String datainicial=txtDataInicial.getDateFormatString();
//            String datafinal= txtDataFinal.getDateFormatString();
            Integer idaluno= (Integer.parseInt(txtIdaluno.getText()));

                        contador=0;
                        contAluno=0.00;
              
               DefaultTableModel tabela = (DefaultTableModel) tblCredito.getModel();
               tblCredito.setModel(tabela);
               tblCredito.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
              tblCredito.getColumnModel().getColumn(0).setPreferredWidth(60);
               tblCredito.getColumnModel().getColumn(1).setPreferredWidth(60);
               tblCredito.getColumnModel().getColumn(2).setPreferredWidth(300);
               tblCredito.getColumnModel().getColumn(3).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(4).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(5).setPreferredWidth(100);
               tblCredito.getColumnModel().getColumn(6).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(7).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(8).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(9).setPreferredWidth(80);
               tblCredito.getColumnModel().getColumn(10).setPreferredWidth(250);
               tblCredito.getColumnModel().getColumn(11).setPreferredWidth(250);

       		tabela.setNumRows(0);
		CreditoDao dao = new CreditoDao();
            
		  for (CreditoController u : creditosdao.CarregarEntreDatasIdaluno(datainicial, datafinal, idaluno)) {
          
						tabela.addRow(new Object[]
                        {u.getIdcredito(), 
                         u.getIdcredaluno(),
                         u.getNomealuno(),
                         dataBanco(u.getDatainicial()),
                         dataBanco(u.getDatafinal()),
                         u.getTotalcreditos(), 
                         u.getCreditosutilizados(),
                         u.getCreditodia(),
                         u.getBloquealuno(),
                         u.getControle(),
                         u.getObservacaocredito(),
                         u.getNomeusuario()
                        }
                        );
		contador++;
                contAluno=contAluno+u.getTotalcreditos();
                }
                lbtotal.setText(Integer.toString(contador)); 
                lbtotalAluno.setText(Double.toString(contAluno));
          }
        }
        
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
            java.util.logging.Logger.getLogger(CreditoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreditoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreditoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreditoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CreditoListaForm().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(CreditoListaForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CbPesquisa;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbIdtitulo;
    private javax.swing.JLabel lbtotal;
    private javax.swing.JLabel lbtotal100;
    private javax.swing.JLabel lbtotal50;
    private javax.swing.JLabel lbtotalAluno;
    private javax.swing.JLabel lbtotalUtilizados;
    private javax.swing.JTable tblCredito;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDataInicial;
    private javax.swing.JTextField txtIdaluno;
    // End of variables declaration//GEN-END:variables
}
