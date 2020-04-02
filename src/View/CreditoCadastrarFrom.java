/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.AlunoController;
import Controller.CreditoController;
import Dao.AlunoDao;
import Dao.CreditoDao;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Home
 */
public class CreditoCadastrarFrom extends javax.swing.JDialog {
    ConexaoBD conexao = new ConexaoBD();
    CreditoDao creditodao = new CreditoDao();
    CreditoController creditos = new CreditoController();
    private DefaultTableModel modelo = new DefaultTableModel();
    private String DataInicialFormatada;
    private String DataFinalFormatada;
    private Integer retorno;
    public Integer idlogado;

    public Integer getRetorno() {
        return retorno;
    }
    
    /**
     * Creates new form CreditoCadastrarFrom
     */
    public void formatarData(){
        try {
	MaskFormatter mascara = new MaskFormatter("##/##/####");
	mascara.setPlaceholderCharacter('_');
        JTextField x = new JFormattedTextField(mascara);
        } catch (ParseException e) {
                e.printStackTrace();
        }

    }
       public String datahoje(){
	//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	Date date = new Date(); 
	return dateFormat.format(date);
   }
   public String dataBanco(String data){
       String dia = data.substring(8, 10);
       String mes = data.substring(5, 7);
       String ano = data.substring(0, 4);
       String databanco = dia+"/"+mes+"/"+ano;
       return databanco;
   }
       public String dataProBanco(String data){
       String dia = data.substring(0, 2);
       String mes = data.substring(3, 5);
       String ano = data.substring(6, 10);
       String databanco = ano+"-"+mes+"-"+dia;
       return databanco;
   }
    public CreditoCadastrarFrom(java.awt.Frame parent, boolean modal) throws ParseException {
        super(parent, modal);
        initComponents();
        formatarData();
        buscardataatual();
        jButton1.requestFocus();
    }
    public void preenchercampoaluno(Integer id){
                        AlunoDao dao = new AlunoDao();
        		for (AlunoController u : dao.buscarAlunoIdNome(id)) {
			txtIdAluno.setText(Integer.toString(u.getIdaluno()));
			txtNome.setText(u.getNomealuno());
		
                }
    }
        public boolean verificarCarmpos(){
        
        if(txtIdAluno.getText().equals("")|| txtDataFinal.getDate()==null 
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
       
       if(jcbxControle.isSelected()){
           creditos.setControle(1);
       } else if(!jcbxControle.isSelected()){
           creditos.setControle(0);
       }
       creditos.setDatafinal(datafin.format(txtDataFinal.getDate()));
       creditos.setDatainicial(dataini.format(txtDataInicial.getDate()));
       creditos.setTipobolsa(CbBolsa.getSelectedIndex());
       creditos.setIdcredaluno(Integer.parseInt(txtIdAluno.getText()));
       creditos.setObservacaocredito(txtObservacao.getText());
       creditos.setTotalcreditos(Double.parseDouble(txtTotCreditos.getText()));
       creditos.setUsuid(idlogado);
        creditodao.SalvarCredito(creditos);
        }
//            public void formatarDataParabanco(String dataini, String datafin){
//        //String data = "25/12/2008";
//                dataini = txtDataFinal.getText();
//                datafin = txtDataInicial.getText();
//		Date date = new Date();
//                String resultado;
//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");	
//        try {
//        	date = format.parse(dataini);
//        	Calendar calendar = Calendar.getInstance();
//    		calendar.setTime(date);
//    		int dia = calendar.get(Calendar.DATE);
//    		int mes = calendar.get(Calendar.MONTH) + 1;   
//    		int ano = calendar.get(Calendar.YEAR); 
//                String d=(Integer.toString(dia));                
//                String m=(Integer.toString(mes));                
//                String a=(Integer.toString(ano));                
//    		//System.out.print(ano+"/"+mes+"/"+dia);
//                resultado = a+"-"+m+"-"+d;
//                DataInicialFormatada=resultado;
//                
//        	date = format.parse(dataini);
//        	Calendar calendar2 = Calendar.getInstance();
//    		calendar2.setTime(date);
//    		dia = calendar2.get(Calendar.DATE);
//    		mes = calendar2.get(Calendar.MONTH) + 1;   
//    		ano = calendar2.get(Calendar.YEAR); 
//                d=(Integer.toString(dia));                
//                m=(Integer.toString(mes));                
//                a=(Integer.toString(ano));                
//    		//System.out.print(ano+"/"+mes+"/"+dia);
//                resultado = a+"-"+m+"-"+d;
//                DataFinalFormatada=resultado;                
//                //JOptionPane.showMessageDialog(null,ano+"-"+mes+"-"+dia );
//        } catch (ParseException e) {
//        	e.printStackTrace();
//        }
//              
//	}
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
        jPanel2 = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        lbNomeOriginal = new javax.swing.JLabel();
        lbNomeOriginal1 = new javax.swing.JLabel();
        lbNomeOriginal2 = new javax.swing.JLabel();
        txtTotCreditos = new javax.swing.JTextField();
        lbNomeOriginal3 = new javax.swing.JLabel();
        CbBolsa = new javax.swing.JComboBox<>();
        txtCreditoDia = new javax.swing.JTextField();
        lbNomeOriginal4 = new javax.swing.JLabel();
        txtObservacao = new javax.swing.JTextField();
        lbNomeOriginal5 = new javax.swing.JLabel();
        txtIdAluno = new javax.swing.JTextField();
        lbNomeOriginal6 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jcbxControle = new javax.swing.JCheckBox();
        txtDataInicial = new com.toedter.calendar.JDateChooser();
        txtDataFinal = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));

        lbTitulo1.setBackground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 24)); // NOI18N
        lbTitulo1.setForeground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTitulo1.setText("Cadastro de Créditos");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(lbTitulo1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lbTitulo1)
                .addContainerGap(33, Short.MAX_VALUE))
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
        lbNomeOriginal1.setText("ID");

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

        CbBolsa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CbBolsa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bolsa 100%", "Bolsa 50%", "Outros", " " }));
        CbBolsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CbBolsaKeyPressed(evt);
            }
        });

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
        lbNomeOriginal5.setText("Total de Créditos");

        lbNomeOriginal6.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal6.setForeground(new java.awt.Color(102, 102, 102));
        lbNomeOriginal6.setText("Nome Aluno");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jcbxControle.setText("Controlar Créditos");
        jcbxControle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcbxControleKeyPressed(evt);
            }
        });

        txtDataInicial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDataFinal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNomeOriginal2)
                    .addComponent(jcbxControle)
                    .addComponent(txtObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNomeOriginal1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(lbNomeOriginal6))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(txtIdAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbNome)
                                .addComponent(CbBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbNomeOriginal3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbNomeOriginal)
                                .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtTotCreditos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCreditoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNomeOriginal4)))
                    .addComponent(lbNomeOriginal5))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNomeOriginal6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtIdAluno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(txtNome)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbNome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(lbNomeOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(CbBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbNomeOriginal3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNomeOriginal4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNomeOriginal5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotCreditos, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCreditoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbxControle)
                .addGap(72, 72, 72))
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
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(335, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        PesquisarAlunoForm pesquisa = new PesquisarAlunoForm(this,true);
        pesquisa.setVisible(true);
        int retorno=pesquisa.getRetorno();
        if(retorno==1){
        int id=pesquisa.getId();
           preenchercampoaluno(id);   
        txtNome.setEnabled(false);
        txtIdAluno.setEnabled(false);
        CbBolsa.requestFocus();
        }
        
 

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
               if(verificarCarmpos()){
                       JOptionPane.showMessageDialog(null, "Preencha todos os campos");
             }else{
                   if(creditodao.existeCredito(Integer.parseInt(txtIdAluno.getText()))){
                       
                   }else{
                        SalvarCredito();
                        retorno=1;
                        this.dispose();
            //
                  }
               }
    }//GEN-LAST:event_btnSalvarActionPerformed
    
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
         //TODO add your handling code here:
//                UsuarioListaForm cadastrousuario = new UsuarioListaForm();
//                cadastrousuario.setVisible(true);
//                limparCampos();
                this.retorno=0;
                this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void CbBolsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CbBolsaKeyPressed
              if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                 txtDataInicial.requestFocus();
               }
    }//GEN-LAST:event_CbBolsaKeyPressed

    private void txtCreditoDiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCreditoDiaKeyPressed
        // TODO add your handling code here:
                              if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                 txtObservacao.requestFocus();
               }
    }//GEN-LAST:event_txtCreditoDiaKeyPressed

    private void txtTotCreditosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotCreditosKeyPressed
        // TODO add your handling code here:
                              if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                 txtCreditoDia.requestFocus();
               }
    }//GEN-LAST:event_txtTotCreditosKeyPressed

    private void txtObservacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacaoKeyPressed
        // TODO add your handling code here:
                              if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                 jcbxControle.requestFocus();
               }
    }//GEN-LAST:event_txtObservacaoKeyPressed

    private void jcbxControleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcbxControleKeyPressed
        // TODO add your handling code here:
                              if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                 btnSalvar.requestFocus();
               }
    }//GEN-LAST:event_jcbxControleKeyPressed

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
            java.util.logging.Logger.getLogger(CreditoCadastrarFrom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreditoCadastrarFrom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreditoCadastrarFrom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreditoCadastrarFrom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CreditoCadastrarFrom dialog = null;
                try {
                    dialog = new CreditoCadastrarFrom(new javax.swing.JFrame(), true);
                } catch (ParseException ex) {
                    Logger.getLogger(CreditoCadastrarFrom.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JCheckBox jcbxControle;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbNomeOriginal;
    private javax.swing.JLabel lbNomeOriginal1;
    private javax.swing.JLabel lbNomeOriginal2;
    private javax.swing.JLabel lbNomeOriginal3;
    private javax.swing.JLabel lbNomeOriginal4;
    private javax.swing.JLabel lbNomeOriginal5;
    private javax.swing.JLabel lbNomeOriginal6;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JTextField txtCreditoDia;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDataInicial;
    private javax.swing.JTextField txtIdAluno;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtObservacao;
    private javax.swing.JTextField txtTotCreditos;
    // End of variables declaration//GEN-END:variables
}
