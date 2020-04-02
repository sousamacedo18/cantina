/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.AlunoController;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import java.awt.Color;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import Dao.AlunoDao;
import java.sql.Blob;

/**
 *
 * @author leonskb4
 */

public class CapturarDigital extends javax.swing.JFrame {
   public int idaluno;
   public int idlogado=0;
   public String cpf=null;

    public int getIdlogado() {
        return idlogado;
    }

    public void setIdlogado(int idlogado) {
        this.idlogado = idlogado;
    }
   
    public int getIdaluno() {
        return idaluno;
    }

    public void setIdaluno(int idaluno) {
        this.idaluno = idaluno;
    }

   private int iddedo;
   private Blob dPolegar;
   private Blob dIndicador;
   private Blob dMedio;
   private Blob dAnelar;
   private Blob dMindinho;
   
   private int tPolegar;
   private int tIndicador;
   private int tMedio;
   private int tAnelar;
   private int tMindinho;
    /**
     * Creates new form CapturarHuella
     */
    private DPFPCapture leitor = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment recrutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";
    
    
    public DPFPFeatureSet featuresInscripcion;
    public DPFPFeatureSet featuresVerificacion;
    public DPFPFeatureSet extrairCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }
    public Image criarImagemDigital(DPFPSample sample){
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }
    public void desenharDigital(Image image){
        lblImagem.setIcon(new ImageIcon(image.getScaledInstance(lblImagem.getWidth(), lblImagem.getHeight(), image.SCALE_DEFAULT)));
        repaint();
    }
    public void estadoDigital(){
        EnviarTexto("Amostra de digitais necessarias para salvar template" +
        recrutador.getFeaturesNeeded()+"\n");
    }
    public void EnviarTexto(String string){
        txtMensagem.setWrapStyleWord(true);
        txtMensagem.append(string+"\n");
        
    }
    public void start(){
        leitor.startCapture();
        EnviarTexto("Utilizando leitor de digitais");
     
    }
    public void stop(){
        leitor.stopCapture();
        EnviarTexto("Leitor desconectado");
    }
    public DPFPTemplate getTemplate(){
        return template;
    }
    public void setTemplate(DPFPTemplate template){
        DPFPTemplate old = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
    
    public void processarCaptura(DPFPSample sample){
        featuresInscripcion = extrairCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        featuresVerificacion = extrairCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        
        if(featuresInscripcion!=null){
            try {
                System.out.println("Criando caracteristicas da digital");
                recrutador.addFeatures(featuresInscripcion);
                Image image=criarImagemDigital(sample);
                desenharDigital(image);
                
               // btnGuardar.setEnabled(true);
            } catch (DPFPImageQualityException e) {
                System.out.println("Error: " + e.getMessage());
            }finally{
                estadoDigital();
                switch(recrutador.getTemplateStatus()){
                    case TEMPLATE_STATUS_READY:
                        stop();
                        setTemplate(recrutador.getTemplate());
                        JOptionPane.showMessageDialog(null, "Template Criado!!! Pronto para salvar no banco de dados");
                        EnviarTexto("O modelo de impressão digital foi criado, e pode verificar ou identificar");
                        btnGuardar.setEnabled(true);
                        btnGuardar.grabFocus();
                        break;
                    case TEMPLATE_STATUS_FAILED:
                        recrutador.clear();
                        stop();
                        estadoDigital();
                        setTemplate(null);
                        JOptionPane.showMessageDialog(CapturarDigital.this, "Impressão digital não pôde ser criado, tente novamente");
                }
            }
        }
    }
    
    protected void iniciar(){
        leitor.addDataListener(new DPFPDataAdapter(){
            @Override public void dataAcquired(final DPFPDataEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override public void run(){
                        EnviarTexto("A impressão digital foi capturada");
                        processarCaptura(e.getSample());
                    }
                });
            }
        });
        leitor.addReaderStatusListener(new DPFPReaderStatusAdapter(){
            @Override public void readerConnected(final DPFPReaderStatusEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override public void run(){
                        EnviarTexto("O sensor ativado ou ligado");
                    }
                });
            }
            @Override public void readerDisconnected(final DPFPReaderStatusEvent e){
                SwingUtilities.invokeLater(new Runnable(){
                    @Override public void run(){
                        EnviarTexto("O sensor está desconectado ou desligado");
                    }
                });
            }
        });
        leitor.addSensorListener(new DPFPSensorAdapter(){
            @Override public void fingerTouched(final DPFPSensorEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EnviarTexto("O dedo foi colocado no leitor de impressões digitais");
                    }
                });
            }
            @Override public void fingerGone(final DPFPSensorEvent e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EnviarTexto("O dedo foi removido do leitor de impressões digitais");
                    }
                });
            }
        });
        leitor.addErrorListener(new DPFPErrorAdapter(){
            //@Override
            public void errorReader(final DPFPErrorAdapter e){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EnviarTexto("Error: " + e.toString());
                    }
                });
            }
        });
    }
    
     ConexaoBD conexao = new ConexaoBD();
    public void salvarDigital(){
        ByteArrayInputStream dadosDigital=new ByteArrayInputStream(template.serialize());
        Integer tamanhoDigital=template.serialize().length;
        String nome=JOptionPane.showInputDialog("Nome:");
        conexao.Conectar();
        try {
//            Connection c=con.conectar();
            PreparedStatement guardarStmt=conexao.connection.prepareStatement("insert into aluno (nomealuno,polegaraluno,emailaluno,senhaaluno,matriculaaluno,indicadoraluno,medioaluno,anelaraluno,mindinhoaluno,fotoaluno) values (?,?,' ',' ',' ',' ',' ',' ',' ',' ')");
            guardarStmt.setString(1, nome);
            guardarStmt.setBinaryStream(2, dadosDigital, tamanhoDigital);
            guardarStmt.execute();
            guardarStmt.close();
            JOptionPane.showMessageDialog(null, "Template salvo com sucesso");
            conexao.Desconectar();
            btnGuardar.setEnabled(false);
        } catch (SQLException e) {
            System.err.println("Error ao salvar template");
            System.err.println(e.getMessage());
        }finally{
            conexao.Desconectar();
        }
    }
     public void ExcluirDigital(int dedo){
                 String sql=null;
       switch(dedo)
                {
                    case 1:
                            sql="UPDATE `aluno` SET `polegaraluno`=? WHERE `idaluno`=?";
                            break;

                    case 2:
                            sql="UPDATE `aluno` SET `indicadoraluno`=? WHERE `idaluno`=?";
                            break;

                    case 3:
                            sql="UPDATE `aluno` SET `medioaluno`=? WHERE `idaluno`=?";
                            break;
                    case 4:
                           sql="UPDATE `aluno` SET `anelaraluno`=? WHERE `idaluno`=?";
                           break;
                    case 5:
                          sql="UPDATE `aluno` SET `mindinhoaluno`=? WHERE `idaluno`=?";
                           break;
                    default:
                            JOptionPane.showMessageDialog(null, "Não foi possível identificar o dedo escolhido");
                }
               conexao.Conectar();
        try {
            
//          Connection c=con.conectar();
 
            PreparedStatement guardarStmt=conexao.connection.prepareStatement(sql);
            guardarStmt.setString(1, "");
            guardarStmt.setInt(2, idaluno);
            guardarStmt.execute();
            guardarStmt.close();
            JOptionPane.showMessageDialog(null, "Template salvo com sucesso");
            conexao.Desconectar();
            btnGuardar.setEnabled(false);
        } catch (SQLException e) {
            System.err.println("Error ao salvar template");
            System.err.println(e.getMessage());
        }finally{
            conexao.Desconectar();
        }  
     }
    public void updateDigital(int dedo){
        
        ByteArrayInputStream dadosDigital=new ByteArrayInputStream(template.serialize());
        Integer tamanhoDigital=template.serialize().length;
        String sql=null;
       switch(dedo)
                {
                    case 1:
                            sql="UPDATE `aluno` SET `polegaraluno`=? WHERE `idaluno`=?";
                            break;

                    case 2:
                            sql="UPDATE `aluno` SET `indicadoraluno`=? WHERE `idaluno`=?";
                            break;

                    case 3:
                            sql="UPDATE `aluno` SET `medioaluno`=? WHERE `idaluno`=?";
                            break;
                    case 4:
                           sql="UPDATE `aluno` SET `anelaraluno`=? WHERE `idaluno`=?";
                           break;
                    case 5:
                          sql="UPDATE `aluno` SET `mindinhoaluno`=? WHERE `idaluno`=?";
                           break;
                    default:
                            JOptionPane.showMessageDialog(null, "Não foi possível identificar o dedo escolhido");
                }

        conexao.Conectar();
        try {
            
//          Connection c=con.conectar();
 
            PreparedStatement guardarStmt=conexao.connection.prepareStatement(sql);
            guardarStmt.setBinaryStream(1, dadosDigital, tamanhoDigital);
            guardarStmt.setInt(2, Integer.parseInt(lbId.getText().trim()));
            guardarStmt.execute();
            guardarStmt.close();
            JOptionPane.showMessageDialog(null, "Template salvo com sucesso");
            conexao.Desconectar();
            btnGuardar.setEnabled(false);
        } catch (SQLException e) {
            System.err.println("Error ao salvar template");
            System.err.println(e.getMessage());
        }finally{
            conexao.Desconectar();
        }
    }
    
 public void verificarDedos() throws SQLException{
     
  		AlunoDao dao = new AlunoDao();
                Color colorBg = new Color(255,45,78);
                btnAnelar.setText("");
                btnIndicador.setText("");
                btnMedio.setText("");
                btnPolegar.setText("");
                btnMindinha.setText("");
                btnCancelar.setEnabled(false);
          if(cpf==null){      

    for (AlunoController u : dao.CarregarDedos(getIdaluno())) {
        
          dPolegar=u.getPolegaraluno();
          dIndicador=u.getIndicadoraluno();
          dMedio=u.getMedioaluno();
          dAnelar=u.getAnelaraluno();
          dMindinho=u.getMindinhoaluno();
          lbId.setText(Integer.toString(u.getIdaluno()));
          lbNome.setText(u.getNomealuno());
          ImageIcon img = new ImageIcon(u.getFotoaluno());
          lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(90, 87, Image.SCALE_DEFAULT)));
        
          }
          }else{
     for (AlunoController u : dao.CarregarDedos(cpf)) {
        
          dPolegar=u.getPolegaraluno();
          dIndicador=u.getIndicadoraluno();
          dMedio=u.getMedioaluno();
          dAnelar=u.getAnelaraluno();
          dMindinho=u.getMindinhoaluno();
          lbId.setText(Integer.toString(u.getIdaluno()));
          lbNome.setText(u.getNomealuno());
          ImageIcon img = new ImageIcon(u.getFotoaluno());
          lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(90, 87, Image.SCALE_DEFAULT)));
        
          }             
          }

                   tPolegar = (int) dPolegar.length();  
                   tIndicador = (int) dIndicador.length();  
                   tMedio = (int) dMedio.length();  
                   tAnelar = (int) dAnelar.length();  
                   tMindinho = (int) dMindinho.length();  

                    
                    if(tPolegar==1 || tPolegar==0){
                      btnPolegar.setBackground(Color.red);
                      btnPolegar.setForeground(Color.red); 
                    }  else{
                      btnPolegar.setBackground(Color.GREEN);
                      btnPolegar.setForeground(Color.GREEN); 
                    }
                    
                    if(tIndicador==1 || tIndicador==0){
                      btnIndicador.setBackground(Color.red);
                      btnIndicador.setForeground(Color.red); 
                    }  else{
                      btnIndicador.setBackground(Color.GREEN);
                      btnIndicador.setForeground(Color.GREEN); 
                    }
                    
                    if(tMedio==1 || tMedio==0){
                      btnMedio.setBackground(Color.red);
                      btnMedio.setForeground(Color.red); 
                    }  else{
                      btnMedio.setBackground(Color.GREEN);
                      btnMedio.setForeground(Color.GREEN); 
                    }
                    
                    if(tAnelar==1 || tAnelar==0){
                      btnAnelar.setBackground(Color.red);
                      btnAnelar.setForeground(Color.red); 
                    }  else{
                      btnAnelar.setBackground(Color.GREEN);
                      btnAnelar.setForeground(Color.GREEN); 
                    }
                    
                    if(tMindinho==1 || tMindinho==0){
                      btnMindinha.setBackground(Color.red);
                      btnMindinha.setForeground(Color.red); 
                    }  else{
                      btnMindinha.setBackground(Color.GREEN);
                      btnMindinha.setForeground(Color.GREEN); 
                    }
     
 }
 
 public void carregarAluno(){
     
 }
    
    
    public CapturarDigital() throws SQLException {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não é possível alterar o assunto" + e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        initComponents();
        //verificarDedos();
        this.setResizable(false);
         this.setLocationRelativeTo(null);
         iddedo=0;
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
        btnGuardar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnPolegar = new javax.swing.JButton();
        lbmao = new javax.swing.JLabel();
        btnIndicador = new javax.swing.JButton();
        btnMedio = new javax.swing.JButton();
        btnAnelar = new javax.swing.JButton();
        btnMindinha = new javax.swing.JButton();
        lblImagem = new javax.swing.JLabel();
        JP_DADOS = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbId = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbNome = new javax.swing.JLabel();
        lbFoto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMensagem = new javax.swing.JTextArea();
        JP_TITULO = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        JP_PRINCIPAL.setBackground(new java.awt.Color(255, 255, 255));
        JP_PRINCIPAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 4));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_save_173091.png"))); // NOI18N
        btnGuardar.setText("Salvar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_1-04_511562.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_remove_2199096.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnPolegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPolegarActionPerformed(evt);
            }
        });

        lbmao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/mao1.png"))); // NOI18N

        btnIndicador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndicadorActionPerformed(evt);
            }
        });

        btnMedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedioActionPerformed(evt);
            }
        });

        btnAnelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnelarActionPerformed(evt);
            }
        });

        btnMindinha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMindinhaActionPerformed(evt);
            }
        });

        lblImagem.setBackground(new java.awt.Color(0, 0, 0));
        lblImagem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 2));

        JP_DADOS.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        jLabel1.setText("Id:");

        lbId.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        lbId.setText("lbId");

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        jLabel3.setText("Nome: ");

        lbNome.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        lbNome.setText("lbNome");

        txtMensagem.setColumns(20);
        txtMensagem.setRows(5);
        jScrollPane1.setViewportView(txtMensagem);

        javax.swing.GroupLayout JP_DADOSLayout = new javax.swing.GroupLayout(JP_DADOS);
        JP_DADOS.setLayout(JP_DADOSLayout);
        JP_DADOSLayout.setHorizontalGroup(
            JP_DADOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_DADOSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_DADOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_DADOSLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbNome))
                    .addGroup(JP_DADOSLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbId))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFoto)
                .addGap(100, 100, 100))
        );
        JP_DADOSLayout.setVerticalGroup(
            JP_DADOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_DADOSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_DADOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_DADOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbNome))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addComponent(lbFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        JP_TITULO.setBackground(new java.awt.Color(51, 51, 51));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cadastrar Impressão Digital");

        lbFechar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbFechar.setForeground(new java.awt.Color(255, 255, 51));
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(139, 139, 139)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        JP_TITULOLayout.setVerticalGroup(
            JP_TITULOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_TITULOLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(JP_TITULOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbFechar))
                .addContainerGap())
        );

        javax.swing.GroupLayout JP_PRINCIPALLayout = new javax.swing.GroupLayout(JP_PRINCIPAL);
        JP_PRINCIPAL.setLayout(JP_PRINCIPALLayout);
        JP_PRINCIPALLayout.setHorizontalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_TITULO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JP_DADOS, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addComponent(lblImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                                .addComponent(btnMindinha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbmao, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                                .addComponent(btnAnelar)
                                .addGap(18, 18, 18)
                                .addComponent(btnMedio)
                                .addGap(27, 27, 27)
                                .addComponent(btnIndicador)
                                .addGap(39, 39, 39)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPolegar)
                        .addGap(40, 40, 40)
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancelar)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JP_PRINCIPALLayout.setVerticalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addComponent(JP_TITULO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAnelar, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMedio, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIndicador, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbmao, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(btnMindinha, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addComponent(btnPolegar, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addGap(133, 133, 133)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JP_DADOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        iniciar();
        start();
        estadoDigital();
        btnGuardar.setEnabled(false);
      
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        stop();
    }//GEN-LAST:event_formWindowClosing

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       if(iddedo==0){
           JOptionPane.showMessageDialog(null, "Por favor! Escolha um dedo para salvar a digital");
       }else{
                    try {
            //            salvarDigital();
                        updateDigital(iddedo);
                        recrutador.clear();
                        lblImagem.setIcon(null);
                        verificarDedos();
                        start();
                    } catch (Exception e) {
                        Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, e);
                    }
       }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnPolegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPolegarActionPerformed
       try {
           // TODO add your handling code here:
           verificarDedos();
       } catch (SQLException ex) {
           Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, ex);
       }
        Color btn=btnPolegar.getBackground();
        if(tPolegar>0 || tPolegar==0){
            btnCancelar.setEnabled(true);
            btnExcluir.setEnabled(true);
                    
             int result = JOptionPane.showConfirmDialog(null,"Deseja Extrair Impressão Digital? ","Impressão Digital",JOptionPane.YES_NO_CANCEL_OPTION);   
  
           switch (result) {
               case JOptionPane.YES_OPTION:
                   btnPolegar.setBackground(Color.yellow);
                   btnPolegar.setForeground(Color.BLUE);
                   btnPolegar.setText("X");
                   iddedo=1;
                   break;
               case JOptionPane.NO_OPTION:
                   btnPolegar.setBackground(btn);
                   btnPolegar.setText("");
                   iddedo=0;
                   break;
               case JOptionPane.CANCEL_OPTION:
                   btnPolegar.setBackground(btn);
                   iddedo=0;
                   btnPolegar.setText("");
                   break;
               default:
                   break;
           }
        }
        else{
                        btnPolegar.setBackground(Color.yellow);
                        btnPolegar.setForeground(Color.BLUE);            
        }
    }//GEN-LAST:event_btnPolegarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        
       try {
           verificarDedos();
       } catch (SQLException ex) {
           Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnIndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndicadorActionPerformed
        // TODO add your handling code here:
               try {
           // TODO add your handling code here:
           verificarDedos();
       } catch (SQLException ex) {
           Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, ex);
       }
                Color btn=btnIndicador.getBackground();
        if(tIndicador>0 || tIndicador==0){
            btnCancelar.setEnabled(true);
            btnExcluir.setEnabled(true);
                    
             int result = JOptionPane.showConfirmDialog(null,"Deseja Extrair Impressão Digital? ","Impressão Digital",JOptionPane.YES_NO_CANCEL_OPTION);   
  
                   switch (result) {
                       case JOptionPane.YES_OPTION:
                           btnIndicador.setBackground(Color.yellow);
                           btnIndicador.setForeground(Color.BLUE);
                           btnIndicador.setText("X");
                           iddedo=2;
                           break;
                       case JOptionPane.NO_OPTION:
                           btnIndicador.setBackground(btn);
                           btnIndicador.setText("");
                           iddedo=0;
                           break;
                       case JOptionPane.CANCEL_OPTION:
                           btnIndicador.setBackground(btn);
                           iddedo=0;
                           btnIndicador.setText("");
                           break;
                       default:
                           break;
                   }
        }
        else{
                        btnIndicador.setBackground(Color.yellow);
                        btnIndicador.setForeground(Color.BLUE);            
        }
    }//GEN-LAST:event_btnIndicadorActionPerformed

    private void btnMedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedioActionPerformed
        // TODO add your handling code here:
               try {
           // TODO add your handling code here:
           verificarDedos();
       } catch (SQLException ex) {
           Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, ex);
       }
                    Color btn=btnMedio.getBackground();
        if(tMedio>0 || tMedio==0){
            btnCancelar.setEnabled(true);
            btnExcluir.setEnabled(true);        
             int result = JOptionPane.showConfirmDialog(null,"Deseja Extrair Impressão Digital? ","Impressão Digital",JOptionPane.YES_NO_CANCEL_OPTION);   
  
                   switch (result) {
                       case JOptionPane.YES_OPTION:
                           btnMedio.setBackground(Color.yellow);
                           btnMedio.setForeground(Color.BLUE);
                           btnMedio.setText("X");
                           iddedo=3;
                           break;
                       case JOptionPane.NO_OPTION:
                           btnMedio.setBackground(btn);
                           btnMedio.setText("");
                           iddedo=0;
                           break;
                       case JOptionPane.CANCEL_OPTION:
                           btnMedio.setBackground(btn);
                           iddedo=0;
                           btnMedio.setText("");
                           break;
                       default:
                           break;
                   }
        }
        else{
                        btnMedio.setBackground(Color.yellow);
                        btnMedio.setForeground(Color.BLUE);            
        }    
    }//GEN-LAST:event_btnMedioActionPerformed

    private void btnAnelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnelarActionPerformed
        // TODO add your handling code here:
               try {
           // TODO add your handling code here:
           verificarDedos();
       } catch (SQLException ex) {
           Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, ex);
       }
                            Color btn=btnAnelar.getBackground();
        if(tAnelar>0 || tAnelar==0){
            btnCancelar.setEnabled(true);
            btnExcluir.setEnabled(true);        
             int result = JOptionPane.showConfirmDialog(null,"Deseja Extrair Impressão Digital? ","Impressão Digital",JOptionPane.YES_NO_CANCEL_OPTION);   
  
                   switch (result) {
                       case JOptionPane.YES_OPTION:
                           btnAnelar.setBackground(Color.yellow);
                           btnAnelar.setForeground(Color.BLUE);
                           btnAnelar.setText("X");
                           iddedo=4;
                           break;
                       case JOptionPane.NO_OPTION:
                           btnAnelar.setBackground(btn);
                           btnAnelar.setText("");
                           iddedo=0;
                           break;
                       case JOptionPane.CANCEL_OPTION:
                           btnAnelar.setBackground(btn);
                           iddedo=0;
                           btnAnelar.setText("");
                           break;
                       default:
                           break;
                   }
        }
        else{
                        btnAnelar.setBackground(Color.yellow);
                        btnAnelar.setForeground(Color.BLUE);            
        } 
    }//GEN-LAST:event_btnAnelarActionPerformed

    private void btnMindinhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMindinhaActionPerformed
        // TODO add your handling code here:
               try {
           // TODO add your handling code here:
           verificarDedos();
       } catch (SQLException ex) {
           Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, ex);
       }
         Color btn=btnMindinha.getBackground();
        if(tMindinho>0 || tMindinho==0){
            btnCancelar.setEnabled(true);
            btnExcluir.setEnabled(true);        
             int result = JOptionPane.showConfirmDialog(null,"Deseja Extrair Impressão Digital? ","Impressão Digital",JOptionPane.YES_NO_CANCEL_OPTION);   
  
                   switch (result) {
                       case JOptionPane.YES_OPTION:
                           btnMindinha.setBackground(Color.yellow);
                           btnMindinha.setForeground(Color.BLUE);
                           btnMindinha.setText("X");
                           iddedo=5;
                           break;
                       case JOptionPane.NO_OPTION:
                           btnMindinha.setBackground(btn);
                           btnMindinha.setText("");
                           iddedo=0;
                           break;
                       case JOptionPane.CANCEL_OPTION:
                           btnMindinha.setBackground(btn);
                           iddedo=0;
                           btnMindinha.setText("");
                           break;
                       default:
                           break;
                   }
        }
        else{
                        btnMindinha.setBackground(Color.yellow);
                        btnMindinha.setForeground(Color.BLUE);            
        }        
        
    }//GEN-LAST:event_btnMindinhaActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
       if(iddedo==0){
           JOptionPane.showMessageDialog(null, "Por favor! Escolha um dedo para excluír a digital");
       }else{
                    try {
            //            salvarDigital();
                        btnExcluir.setEnabled(true);
                        ExcluirDigital(iddedo);
                        recrutador.clear();
                        lblImagem.setIcon(null);
                        verificarDedos();
                        start();
                    } catch (SQLException e) {
                        Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, e);
                    }
       }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
       stop();
       this.dispose();

    }//GEN-LAST:event_lbFecharMouseClicked

    private void lbFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseEntered
        //        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
        //        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.white);
    }//GEN-LAST:event_lbFecharMouseEntered

    private void lbFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseExited
        //        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
        //        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.yellow);
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
            java.util.logging.Logger.getLogger(CapturarDigital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturarDigital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturarDigital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturarDigital.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CapturarDigital().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(CapturarDigital.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_DADOS;
    private javax.swing.JPanel JP_PRINCIPAL;
    private javax.swing.JPanel JP_TITULO;
    private javax.swing.JButton btnAnelar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnIndicador;
    private javax.swing.JButton btnMedio;
    private javax.swing.JButton btnMindinha;
    private javax.swing.JButton btnPolegar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JLabel lbFoto;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lblImagem;
    private javax.swing.JLabel lbmao;
    private javax.swing.JTextArea txtMensagem;
    // End of variables declaration//GEN-END:variables
}
