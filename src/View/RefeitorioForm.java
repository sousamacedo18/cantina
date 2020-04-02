/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AlunoController;
import Controller.RefeitorioController;
import Dao.AlunoDao;
import Dao.CreditoDao;
import Dao.RefeitorioDao;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;


import java.io.FileInputStream;  
import java.io.InputStream;  
import sun.audio.AudioPlayer;  
import sun.audio.AudioStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.readers.DPFPReaderDescription;
import com.digitalpersona.onetouch.readers.DPFPReadersCollection;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import relatorios.Relatoriogenerico;


/**
 *
 * @author Home
 */
public class RefeitorioForm extends javax.swing.JFrame {
 static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
RefeitorioDao refeitoriodao = new RefeitorioDao();
CreditoDao creditodao = new CreditoDao();
private Integer tipobolsa;
private Integer tipobolsaSalvar;
public Integer idlogado;
private String caminhofoto="";
SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
    class HoraThread implements Runnable{

        @Override
        public void run() {
            while (true){
                lbHoras.setText(formatoHora.format(new Date()));
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    System.out.println("Thread não foi finalizada"+e);
                }
            }
        }
 
    }

    public Integer getIdlogado() {
        return idlogado;
    }
       public String lerCaminho(){
       String arq = "conf.txt";
       String texto=Arquivo.Read(arq);
       if(texto.isEmpty()){
           JOptionPane.showMessageDialog(null, "Não foi possível ler o caminho guardado");
       }

   return texto;
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
        catch (org.json.simple.parser.ParseException ex) {ex.printStackTrace(); }
        catch (Exception ex) {ex.printStackTrace(); }
                return pastafotoaluno;
   }
   public String caminhopastasons(){
   File f = new File("sons");
   return f.getAbsolutePath();
}
    public void tocarSom(String file){
        try {  
        InputStream arq = new FileInputStream(file);  
        AudioStream som = new AudioStream(arq);  
        AudioPlayer.player.start(som);  
        }  
        catch(Exception e) {  
        System.out.println("Erro na execução! "+ e);  
          
        //comando para interromper execução   
        //AudioPlayer.player.stop(som);  
     }  
          
   } 
    	public Connection cn() {
		Connection conn = null;
		try { Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://10.1.1.176:3306/refeitorioifto", "refeitorio", "1234");
		} catch(Exception e) { System.out.println(e); }
		
		return conn;
	}
    	public byte[] get(){ 
		ResultSet rs;
		PreparedStatement st;
		byte[] digital = null;
		try { 
			st = cn().prepareStatement("SELECT * FROM ALUNO");
			rs = st.executeQuery();
			if(rs.next())
				digital = rs.getBytes("polegaraluno");
			else 
				System.out.println("Record not available");
			 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		 
		return digital;
	}
 	public static void listReaders() { 
        DPFPReadersCollection readers = DPFPGlobal.getReadersFactory().getReaders();
        if (readers == null || readers.size() == 0) {
            System.out.printf("There are no readers available.\n");
            return; 
        } 
        System.out.printf("Available readers:\n");
        for (DPFPReaderDescription readerDescription : readers)
            System.out.println(readerDescription.getSerialNumber());
    } 

	public static final EnumMap<DPFPFingerIndex, String> fingerNames;
    static { 
    	fingerNames = new EnumMap<DPFPFingerIndex, String>(DPFPFingerIndex.class);
    	fingerNames.put(DPFPFingerIndex.LEFT_PINKY,	  "left pinky");
    	fingerNames.put(DPFPFingerIndex.LEFT_RING,    "left ring");
    	fingerNames.put(DPFPFingerIndex.LEFT_MIDDLE,  "left middle");
    	fingerNames.put(DPFPFingerIndex.LEFT_INDEX,   "left index");
    	fingerNames.put(DPFPFingerIndex.LEFT_THUMB,   "left thumb");
    	fingerNames.put(DPFPFingerIndex.RIGHT_PINKY,  "right pinky");
    	fingerNames.put(DPFPFingerIndex.RIGHT_RING,   "right ring");
    	fingerNames.put(DPFPFingerIndex.RIGHT_MIDDLE, "right middle");
    	fingerNames.put(DPFPFingerIndex.RIGHT_INDEX,  "right index");
    	fingerNames.put(DPFPFingerIndex.RIGHT_THUMB,  "right thumb");
    } 
    
	public DPFPTemplate getTemplate(String activeReader, int nFinger) {
        System.out.printf("Performing fingerprint enrollment...\n");
         
        DPFPTemplate template = null;
         
        try { 
            DPFPFingerIndex finger = DPFPFingerIndex.values()[nFinger];
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();
             
            while (enrollment.getFeaturesNeeded() > 0)
            { 
                DPFPSample sample = getSample(activeReader, 
                	String.format("Scan your %s finger (%d remaining)\n", fingerName(finger), enrollment.getFeaturesNeeded()));
                if (sample == null)
                    continue; 
 
 
                DPFPFeatureSet featureSet;
                try { 
                    featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
                } catch (DPFPImageQualityException e) {
                    System.out.printf("Bad image quality: \"%s\". Try again. \n", e.getCaptureFeedback().toString());
                    continue; 
                } 
 
 
                enrollment.addFeatures(featureSet);
            } 
            template = enrollment.getTemplate();
            System.out.printf("The %s was enrolled.\n", fingerprintName(finger));
        } catch (DPFPImageQualityException e) {
            System.out.printf("Failed to enroll the finger.\n");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } 
         
        return template;
    }
	
	public boolean verify(String activeReader, DPFPTemplate template) {
		 
		 
        try { 
            DPFPSample sample = getSample(activeReader, "Scan your finger\n");
            if (sample == null)
                throw new Exception();
 
 
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
			 
            DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
            matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
             
            for (DPFPFingerIndex finger : DPFPFingerIndex.values()) {
                //DPFPTemplate template = user.getTemplate(finger); 
                if (template != null) {
                    DPFPVerificationResult result = matcher.verify(featureSet, template);
                    if (result.isVerified()) {
                        System.out.printf("Matching finger: %s, FAR achieved: %g.\n",
                        		fingerName(finger), (double)result.getFalseAcceptRate()/DPFPVerification.PROBABILITY_ONE);
                        return result.isVerified();
                    } 
                } 
            } 
        } catch (Exception e) {
            System.out.printf("Failed to perform verification.");
        } 
         
        return false; 
    } 
 
 
    public DPFPSample getSample(String activeReader, String prompt)
	throws InterruptedException
	{ 
	    final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
	    DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
	    capture.setReaderSerialNumber(activeReader);
	    capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
	    capture.addDataListener(new DPFPDataListener()
	    { 
	        public void dataAcquired(DPFPDataEvent e) {
	            if (e != null && e.getSample() != null) {
	                try { 
	                    samples.put(e.getSample());
	                } catch (InterruptedException e1) {
	                    e1.printStackTrace();
	                } 
	            } 
	        } 
	    }); 
	    capture.addReaderStatusListener(new DPFPReaderStatusAdapter()
	    { 
	    	int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;
			public void readerConnected(DPFPReaderStatusEvent e) {
				if (lastStatus != e.getReaderStatus())
					System.out.println("Reader is connected");
				lastStatus = e.getReaderStatus();
			} 
			public void readerDisconnected(DPFPReaderStatusEvent e) {
				if (lastStatus != e.getReaderStatus())
					System.out.println("Reader is disconnected");
				lastStatus = e.getReaderStatus();
			} 
	    	 
	    }); 
	    try { 
	        capture.startCapture();
	        System.out.print(prompt);
	        return samples.take();
	    } catch (RuntimeException e) {
	        System.out.printf("Failed to start capture. Check that reader is not used by another application.\n");
	        throw e;
	    } finally { 
	        capture.stopCapture();
	    } 
	} 
 
 
    public String fingerName(DPFPFingerIndex finger) {
    	return fingerNames.get(finger); 
    } 
    public String fingerprintName(DPFPFingerIndex finger) {
    	return fingerNames.get(finger) + " fingerprint"; 
    }       

    private void ordernarConsulta() throws SQLException {
        String campo=null,ordem=null;
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
       if(txtSelecOrdem.getSelectedIndex()==0){
           ordem="DESC"; 
       }else if(txtSelecOrdem.getSelectedIndex()==1){
            ordem="ASC";
       }
       
        carregarTodasEntradasPorOrdem(campo,ordem);
    }
class TableRenderer extends DefaultTableCellRenderer {
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                     

        String bolsa = table.getModel().getValueAt(row, 1).toString();
        if(bolsa.equals("100%")){
            //comp.setBackground(Color.GREEN);
            //comp.setBackground(new Color(100, 200, 50));
            comp.setBackground(new Color(0,100,0));
            comp.setForeground(Color.WHITE);
        } else if(bolsa.equals("50%")){
            //comp.setBackground(Color.RED);
            //comp.setBackground(new Color(255, 91, 96));
            comp.setBackground(new Color(255,215,0));
            comp.setForeground(Color.BLACK);
        }
        return comp;
    }
}
    /**
     * Creates new form refeitorio
     */
    public RefeitorioForm() throws SQLException {
        initComponents();
        carregarTodasEntradas();
         this.setResizable(false);
        Insets in = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());

            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

            int width = d.width-(in.left + in.top);
            int height = d.height-(in.top + in.bottom);
            setSize(width,height);
            setLocation(in.left,in.top);
//       this.setExtendedState(MAXIMIZED_BOTH);
        
         caminhofoto=lerJson();
         getRootPane().setDefaultButton(btnCapturar);
        Thread relogio = new Thread(new RefeitorioForm.HoraThread());
        relogio.start();

        }
protected String horahoje(java.util.Date dtData){
       SimpleDateFormat formatBra;   
       //formatBra = new SimpleDateFormat("dd/MM/yyyy");
       formatBra = new SimpleDateFormat("HH:mm:ss");
       try {
          Date newData = formatBra.parse(dtData.toString());
          return (formatBra.format(newData));
       } catch (ParseException Ex) {
          return "Erro na conversão da data";
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
//   public void registrarEntrada(int id) throws SQLException{
//           
//             Integer limite=0;
//             Integer registro=0;
//             
//             BuscarDigital cDigital = new BuscarDigital(this, true);
//             cDigital.setVisible(true);
//             id=cDigital.getIdaluno();
//             
//             if(id>0){//se for maior que zero, foi encontrado a digital
//                        //lbIdAluno.setText(String.valueOf(id));
//                       // buscarAluno(id);
//                        //carregarIndividual(id);
//                        tipobolsaSalvar=creditodao.buscarTipoBolsa(id);
//                        
//                                        if(refeitoriodao.verCreditos(id)==false){
//                                            JOptionPane.showMessageDialog(null, "Não há créditos para este aluno!!!");
//                                        }else{
//                                    try {
//                                        limite=refeitoriodao.limiteDia(id);
//                                    } catch (SQLException ex) {
//                                        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                    
//                                    try {
//                                        registro=refeitoriodao.contarRegistros(id);
//                                    } catch (SQLException ex) {
//                                        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                        
//                        
//                            if(refeitoriodao.tabloqueado(id)){
//                             //msgBloqueado();
//                            }else if(Objects.equals(registro, limite)){ 
//                              msgLimiteExcedido();  
//                            }else if(refeitoriodao.naoInicializadoPeriodo(id)){ 
//                                msgNaoInicializado();
//                            }else if(refeitoriodao.prazoEsgotado(id)){
//                                msgPrazoEsgotado();
//                            }else if(refeitoriodao.epracontrolar(id)){
//                                       if(refeitoriodao.calculaControleCredito(id)){
//                                           msgCreditosFinalizados();
//                                       }else{
//                                            if(tipobolsaSalvar==0){
//                                            msgBolsaIntegral();
//                                            salvarEntrada();
//                                            
//                                            creditodao.AtualizarCreditoUtilizados(id);
//                                            }else if(tipobolsaSalvar==1){
//                                            msgBolsaParcial();
//                                            salvarEntrada();
//                                            creditodao.AtualizarCreditoUtilizados(id);
//                                            } 
//                                           
//                                       }
//                            } else{
//                                        if(tipobolsaSalvar==0){
//                                        msgBolsaIntegral();
//                                        salvarEntrada();
//                                        creditodao.AtualizarCreditoUtilizados(id);
//                                        }else if(tipobolsaSalvar==1){
//                                        msgBolsaParcial();
//                                        salvarEntrada();
//                                        creditodao.AtualizarCreditoUtilizados(id);
//                                        
//                                            }
//                            }
//                                                    
//                              carregarTodasEntradas();
//                              carregarIndividual(id);
//             
//                                        }
//             }else{// caso não encontre vai retornar 0 
//                 
//                 msgNaoIdentificada();
//                 limpaConsultas();
//             }   
//   }
   private void validarEntrada(int id) throws SQLException, UnsupportedEncodingException{
                    if(id>0){
                    RefeitorioDao dao = new RefeitorioDao();
                    RefeitorioController cref = new RefeitorioController();
                    int bloqueado=dao.tabloqueado(id);
//                    JOptionPane.showMessageDialog(null, "aqui");
                    if(dao.tipoBolsa(id)==2){// verifica qual tipo de bolsa
                        JOptionPane.showMessageDialog(null,"Auxílio Inativo");
                        avisoSonoroAlerta();
                        buscarDigital();
                    }
                    else if (dao.tipoBolsa(id)<2){//verifica a bolsa não está inativa  
                         if (dao.temReserva(id)==false && dao.fezRefeicao(id)==true){
                                AvisoPosJEntForm aviso = new AvisoPosJEntForm(this,true);//ja foi entregue hoje
                                avisoSonoroAlerta();
                                aviso.setVisible(true);
                                buscarDigital();
                         }else{
                           ConfirmacaoRefeicao c =new ConfirmacaoRefeicao(this,true);
                            c.idlogado=idlogado;
                            c.carregarDados(id);
                            c.setVisible(true);
                            buscarDigital();
                         }
                    }
//                    else if(bloqueado==2) {
//                        avisoSonoroAlerta();
//                        AvisoBForm aviso = new AvisoBForm(this,true);
//                        aviso.setVisible(true);
//                        buscarAluno(id);
//                        buscarDigital();  
//                        }
                    
                }else{
                 avisoSonoroAlerta();
             }
   }
   public void buscarDigital() throws UnsupportedEncodingException, UnsupportedEncodingException, SQLException{
             int id = 0;
             carregarTodasEntradas();
             
             BuscarDigital cDigital = new BuscarDigital(this, true);
             cDigital.idlogado=idlogado;
             cDigital.setVisible(true);
             id=cDigital.getIdaluno(); 
             if(cDigital.retorno==1){
                         if(id>0){
                    RefeitorioDao dao = new RefeitorioDao();
                    RefeitorioController cref = new RefeitorioController();
                    int bloqueado=dao.tabloqueado(id);
//                    JOptionPane.showMessageDialog(null, "aqui");
                    if(dao.tipoBolsa(id)==2){// verifica qual tipo de bolsa
                        JOptionPane.showMessageDialog(null,"Auxílio Inativo");
                        avisoSonoroAlerta();
                        buscarDigital();
                    }
                    else if (dao.tipoBolsa(id)<2){//verifica a bolsa não está inativa  
                         if (dao.temReserva(id)==false && dao.fezRefeicao(id)==true){
                                AvisoPosJEntForm aviso = new AvisoPosJEntForm(this,true);//ja foi entregue hoje
                                avisoSonoroAlerta();
                                aviso.setVisible(true);
                                buscarDigital();
                         }else{
                           ConfirmacaoRefeicao c =new ConfirmacaoRefeicao(this,true);
                            c.idlogado=idlogado;
                            c.carregarDados(id);
                            c.setVisible(true);
                            buscarDigital();
                         }
                    }
//                    else if(bloqueado==2) {
//                        avisoSonoroAlerta();
//                        AvisoBForm aviso = new AvisoBForm(this,true);
//                        aviso.setVisible(true);
//                        buscarAluno(id);
//                        buscarDigital();  
//                        }
                    
                }else{
                 avisoSonoroAlerta();
             }
             
             }
             }
//   public void capturarDigital() throws SQLException{
//             Integer id=0;
//             Integer limite=0;
//             Integer registro=0;
//             
//             BuscarDigital cDigital = new BuscarDigital(this, true);
//             cDigital.setVisible(true);
//             id=cDigital.getIdaluno();
//             
//             if(id>0){//se for maior que zero, foi encontrado a digital
//                        lbIdAluno.setText(String.valueOf(id));
//                        buscarAluno(id);
//                        carregarIndividual(id);
//                        tipobolsaSalvar=creditodao.buscarTipoBolsa(id);
//                        
//                                        if(refeitoriodao.verCreditos(id)==false){
//                                            JOptionPane.showMessageDialog(null, "Não há créditos para este aluno!!!");
//                                        }else{
//                                    try {
//                                        limite=refeitoriodao.limiteDia(id);
//                                    } catch (SQLException ex) {
//                                        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                    
//                                    try {
//                                        registro=refeitoriodao.contarRegistros(id);
//                                    } catch (SQLException ex) {
//                                        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                        
//                        
//                            if(refeitoriodao.tabloqueado(id)){
//                             msgBloqueado();
//                            }else if(Objects.equals(registro, limite)){ 
//                              msgLimiteExcedido();  
//                            }else if(refeitoriodao.naoInicializadoPeriodo(id)){ 
//                                msgNaoInicializado();
//                            }else if(refeitoriodao.prazoEsgotado(id)){
//                                msgPrazoEsgotado();
//                            }else if(refeitoriodao.epracontrolar(id)){
//                                       if(refeitoriodao.calculaControleCredito(id)){
//                                           msgCreditosFinalizados();
//                                       }else{
//                                            if(tipobolsaSalvar==0){
//                                            msgBolsaIntegral();
//                                            salvarEntrada();
//                                            
//                                            creditodao.AtualizarCreditoUtilizados(id);
//                                            }else if(tipobolsaSalvar==1){
//                                            msgBolsaParcial();
//                                            salvarEntrada();
//                                            creditodao.AtualizarCreditoUtilizados(id);
//                                            } 
//                                           
//                                       }
//                            } else{
//                                        if(tipobolsaSalvar==0){
//                                        msgBolsaIntegral();
//                                        salvarEntrada();
//                                        creditodao.AtualizarCreditoUtilizados(id);
//                                        }else if(tipobolsaSalvar==1){
//                                        msgBolsaParcial();
//                                        salvarEntrada();
//                                        creditodao.AtualizarCreditoUtilizados(id);
//                                        
//                                            }
//                            }
//                                                    
//                              carregarTodasEntradas();
//                              carregarIndividual(id);
//             
//                                        }
//             }else{// caso não encontre vai retornar 0 
//                 
//                 msgNaoIdentificada();
//                 limpaConsultas();
//             }
//   }
//   public String caminhopastasons(){
//   File f = new File("sons");
//   return f.getAbsolutePath();

   public void avisoSonoroNaoIdentificado(){
       //tocarSom(caminhopastasons().trim()+"\\alarme.wav");
       tocarSom(caminhopastasons().trim()+"\\beeper.wav");
   }
   public void avisoSonoroPositivo(){
       tocarSom(caminhopastasons().trim()+"\\chimes.wav");
   }
   public void avisoSonoroAlerta(){
        tocarSom(caminhopastasons().trim()+"\\alarme.wav");
   }

//   public void limpaConsultas(){
//       lbFoto.setIcon(null);
//       lbNome.setText(null);
//      
//       DefaultTableModel tabela = (DefaultTableModel) tbIndividual.getModel();
//       //DefaultTableModel tabela1 = (DefaultTableModel) tbTodasEntradas.getModel();
//       tabela.setNumRows(0);
//      // tabela1.setNumRows(0);
//   }
//   public void msgLimiteExcedido(){
//       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
//       fundoVermelho();
//       lbMensagem.setText("Todos os Tickets utilizados hoje!!!!");
//       avisoSonoroAlerta();
//   }
//   public void msgCreditosFinalizados(){
//       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
//       fundoVermelho();
//       lbMensagem.setText("Créditos Finalizados!!!!"); 
//       avisoSonoroAlerta();
//   }
//   public void msgNaoInicializado(){
//       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
//       fundoVermelho();
//       lbMensagem.setText("Período ainda não inicializado!!!!");
//       avisoSonoroAlerta();
//   }
//   public void msgBloqueado(){
//       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
//       fundoVermelho();
//       lbMensagem.setText("Bloqueado! Procure a CAE");  
//       avisoSonoroAlerta();
//   }
//   public void msgPrazoEsgotado(){
//       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
//       fundoVermelho();
//       lbMensagem.setText("Período de utilização finalizado, procure a CAE!!!!"); 
//       avisoSonoroAlerta();
//   }
//   public void msgBolsaIntegral(){
//        //fundo verde
//        fundoVerde();
//       lbMensagem.setText("Bolsa Integral 100% Liberada!!!!");
//       avisoSonoroPositivo();
//   }
//   public void msgBolsaParcial(){
//        //fundo Amarelo
//        fundoAmarelo();
//       lbMensagem.setText("Bolsa Parcial 50% Liberada!!!!");
//       avisoSonoroPositivo();
//   }
//   public void msgNaoIdentificada(){
//       fundoVermelho();
//       lbMensagem.setText("Impressão Digital Não Identificada!!!!");
//       avisoSonoroNaoIdentificado();
//   }
//   public void fundoVermelho(){
//       // - NÃO IDENTIFICADO,NÃO ENCONTRADO, LIMITE DIÁRIO(Fundo Vermelho)
//       //pMensagem.setBackground(Color.RED);
//       pMensagem.setBackground(new Color(139,0,0));
//       lbMensagem.setForeground(Color.WHITE);
//       //pMensagem.setBounds(0, 0, 100, 200);
//   }
//   public void fundoVerde(){
//       //- Bolsa Integral
//       //pMensagem.setBackground(Color.GREEN);
//       pMensagem.setBackground(new Color(0,100,0));
//       lbMensagem.setForeground(Color.WHITE);
//       pMensagem.setBounds(0, 0, 400, 200);
//   }
//   public void fundoAmarelo(){
//       //- Bolsa Parcial(Fundo Amarelo)
//              //pMensagem.setBackground(Color.YELLOW);
//              pMensagem.setBackground( new Color(255,215,0));
//              lbMensagem.setForeground(Color.WHITE); 
//              pMensagem.setBounds(0, 0, 400, 200);
//   }

    public void carregarIndividual(Integer id){
//           Integer total=0;
//            DefaultTableModel tabela = (DefaultTableModel) tbIndividual.getModel();
//            tbIndividual.setModel(tabela);
//            tbIndividual.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//            tbIndividual.getColumnModel().getColumn(0).setPreferredWidth(60);
//            tbIndividual.getColumnModel().getColumn(1).setPreferredWidth(60);
//            tbIndividual.getColumnModel().getColumn(2).setPreferredWidth(300);
//            tbIndividual.getColumnModel().getColumn(3).setPreferredWidth(80);
//            tbIndividual.getColumnModel().getColumn(4).setPreferredWidth(80);
//            tbIndividual.getColumnModel().getColumn(5).setPreferredWidth(80);
//            tabela.setNumRows(0);
//            RefeitorioDao dao = new RefeitorioDao();
//            String data=null;
//
//            for (RefeitorioController u : dao.CarregarIndividual(id)) {
//                tabela.addRow(new Object[]
//                {
//                    //data=dateFormat.format(u.getDatarefeitorio()),
//                    u.getIdrefeitorio(),
//                    u.getIdrefaluno(), 
//                    u.getNomealuno(),
//                    dataBanco(u.getDatarefeitorio()),
//                    u.getHorarefeitorio(),
//                    u.getQuantidaderefeitorio()
//                });
//
//                total++;
//            }
            //lbTotalIndividual.setText(Integer.toString(total));
    }

    public void carregarTodasEntradas() throws SQLException{
        Integer total=0,bolsa100=0,bolsa50=0;
        
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
            DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            centralizado.setHorizontalAlignment(SwingConstants.CENTER);
            direita.setHorizontalAlignment(SwingConstants.RIGHT);
//            tabela.getColumnModel().getColumn(0).setCellRenderer(esquerda);
//            tbTodasEntradas.getColumnModel().getColumn(3).setCellRenderer(centralizado);
//            tabela.getColumnModel().getColumn(2).setCellRenderer(direita);

            DefaultTableModel tabela = (DefaultTableModel) tbTodasEntradas.getModel();
            //tbTodasEntradas.setRowSorter(new TableRowSorter(tabela));
            tbTodasEntradas.setModel(tabela);
            tbTodasEntradas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tbTodasEntradas.getColumnModel().getColumn(0).setPreferredWidth(320);
            tbTodasEntradas.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbTodasEntradas.getColumnModel().getColumn(2).setPreferredWidth(90);
            tbTodasEntradas.getColumnModel().getColumn(3).setPreferredWidth(60);
//            tbTodasEntradas.getColumnModel().getColumn(3).setCellRenderer(centralizado);
            //tbTodasEntradas.getColumnModel().getColumn(4).setPreferredWidth(60);
            tbTodasEntradas.getColumnModel().getColumn(4).setPreferredWidth(200);
            tbTodasEntradas.getColumnModel().getColumn(5).setPreferredWidth(50);
        
            tabela.setNumRows(0);
            RefeitorioDao dao = new RefeitorioDao();
      
            for (RefeitorioController u : dao.CarregarRefeicoes("HORARIOREFEICAO","DESC")) {
                tipobolsa=u.getTipobolsa();
                String bolsa=null;
                if (tipobolsa==0){bolsa="100%";}else{bolsa="50%";}

                tabela.addRow(new Object[]
                {
                    
                   
                    u.getNomealuno(),
                    bolsa,
                    dataBanco(u.getDatarefeitorio()),
                    //u.getHorarefeitorio(),
                    u.getHorariorefeicao(),
                    u.getNomeusuario(),
                    u.getIdrefaluno()   
                        
                    
                });
               
                                if(u.getTipobolsa()==0){
                                bolsa100++;
                                        
                                }
                                if(u.getTipobolsa()==1){
                                bolsa50++;
                                      
                                }
                total++;    
            }
                  //colorir tabela
//                TableCellRenderer tcr = new Colorir();
//                TableColumn column =  tbTodasEntradas.getColumnModel().getColumn(1);
//                column.setCellRenderer(tcr);  
                //fim do comando colorir tabela   
                tbTodasEntradas.setDefaultRenderer(Object.class, new TableRenderer());
                lbTotalEntradas.setText(Integer.toString(total));
                lbTotal100.setText(Integer.toString(bolsa100));
                lbTotal50.setText(Integer.toString(bolsa50)); 
                lbTotalRes.setText(Integer.toString(dao.contarReservas()));
                
              if(tbTodasEntradas.getRowCount()>0){
             int id=(int)tbTodasEntradas.getValueAt(0, 5);
                  buscarAluno(id);
                
    }
    }
    public void carregarTodasEntradasPorOrdem(String campo,String ordem) throws SQLException{
        Integer total=0,bolsa100=0,bolsa50=0;
        
            DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
            DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
            DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
            esquerda.setHorizontalAlignment(SwingConstants.LEFT);
            centralizado.setHorizontalAlignment(SwingConstants.CENTER);
            direita.setHorizontalAlignment(SwingConstants.RIGHT);
//            tabela.getColumnModel().getColumn(0).setCellRenderer(esquerda);
//            tbTodasEntradas.getColumnModel().getColumn(3).setCellRenderer(centralizado);
//            tabela.getColumnModel().getColumn(2).setCellRenderer(direita);

            DefaultTableModel tabela = (DefaultTableModel) tbTodasEntradas.getModel();
            //tbTodasEntradas.setRowSorter(new TableRowSorter(tabela));
            tbTodasEntradas.setModel(tabela);
            tbTodasEntradas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tbTodasEntradas.getColumnModel().getColumn(0).setPreferredWidth(320);
            tbTodasEntradas.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbTodasEntradas.getColumnModel().getColumn(2).setPreferredWidth(90);
            //  tbTodasEntradas.getColumnModel().getColumn(3).setPreferredWidth(60);
            //tbTodasEntradas.getColumnModel().getColumn(3).setCellRenderer(centralizado);
            //tbTodasEntradas.getColumnModel().getColumn(4).setPreferredWidth(60);
            tbTodasEntradas.getColumnModel().getColumn(3).setPreferredWidth(60);
            tbTodasEntradas.getColumnModel().getColumn(4).setPreferredWidth(200);
            tbTodasEntradas.getColumnModel().getColumn(5).setPreferredWidth(60);
        
            tabela.setNumRows(0);
            RefeitorioDao dao = new RefeitorioDao();
      
            for (RefeitorioController u : dao.CarregarRefeicoes(campo,ordem)) {
                tipobolsa=u.getTipobolsa();
                String bolsa=null;
                if (tipobolsa==0){bolsa="100%";}else{bolsa="50%";}

                tabela.addRow(new Object[]
                {
                    
                   
                    u.getNomealuno(),
                    bolsa,
                    dataBanco(u.getDatarefeitorio()),
                  //  u.getHorarefeitorio(),
                    u.getHorariorefeicao(),
                    u.getNomeusuario(),
                    u.getIdrefaluno()   
                        
                    
                });
               
                                if(u.getTipobolsa()==0){
                                bolsa100++;
                                        
                                }
                                if(u.getTipobolsa()==1){
                                bolsa50++;
                                      
                                }
                total++;    
            }
                  //colorir tabela
//                TableCellRenderer tcr = new Colorir();
//                TableColumn column =  tbTodasEntradas.getColumnModel().getColumn(1);
//                column.setCellRenderer(tcr);  
                //fim do comando colorir tabela   
                tbTodasEntradas.setDefaultRenderer(Object.class, new TableRenderer());
                lbTotalEntradas.setText(Integer.toString(total));
                lbTotal100.setText(Integer.toString(bolsa100));
                lbTotal50.setText(Integer.toString(bolsa50)); 
                lbTotalRes.setText(Integer.toString(dao.contarReservas()));
                
              if(tbTodasEntradas.getRowCount()>0){
             int id=(int)tbTodasEntradas.getValueAt(0, 5);
                  buscarAluno(id);
                
    }
    }

    public void limparFoto(){
                      ImageIcon img = new ImageIcon(lerJson()+"icone-amigo_homem.png");
                      lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(lbFoto.getWidth(), lbFoto.getHeight(),img.getImage().SCALE_DEFAULT)));    
    }
        
        public void buscarAluno(Integer idAluno){
                limparFoto();
                  AlunoDao dao = new AlunoDao();
                  for (AlunoController u : dao.CarregarDedos(idAluno)) {
                      
                      //lbNome.setText(u.getNomealuno());
                      ImageIcon img = new ImageIcon(lerJson().trim()+u.getFotoaluno().trim());
                      
                      lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(lbFoto.getWidth(), lbFoto.getHeight(),img.getImage().SCALE_DEFAULT)));
                   
    }
        }
    public void salvarEntrada(int id) throws UnsupportedEncodingException{
        RefeitorioController refeitorio = new RefeitorioController();
        RefeitorioDao refeitoriodao = new RefeitorioDao();
        //refeitorio.setIdrefaluno(Integer.parseInt(lbIdAluno.getText()));
//        refeitorio.setUsuid(idlogado);
//        refeitorio.setQuantidaderefeitorio(1.0);
        refeitorio.setIdrefaluno(id);
        refeitoriodao.PegarRefeicao(refeitorio);
    }
    public void gerarRelatorioPdf() throws DocumentException, BadElementException {
       SimpleDateFormat out = new SimpleDateFormat("HH:mm");
       PdfTemplate t;
       String result = out.format(new Date());
        int b1=0,b2=0,total=0;
        String nomebolsa="";
        String dataformatada="";
        
        
        RefeitorioDao r = new RefeitorioDao();
        Document document = new Document(PageSize.A4);
        document.setMargins(36, 72, 108, 180);
        document.setMarginMirroring(true);
        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
        com.itextpdf.text.Font conteudoFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
        
  
    try {
            try {
                PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
            } catch (DocumentException ex) {
                Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        document.open();
         Image img = null;
           try {
               img = Image.getInstance("lg-ifto-nova.png");
               img.setAlignment(Element.ALIGN_LEFT);
               img.scaleAbsolute(330,40);
           } catch (IOException ex) {
               Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
           }
        Paragraph p = new Paragraph("Relatório de Refeições dos Alunos do IFTO Campus - Araguaína",FontFactory.getFont(FontFactory.TIMES_ROMAN,18,Font.BOLD,BaseColor.RED));
         document.add(new Paragraph(" "));
        document.add(img);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(p));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Impresso em: "+datahoje()+" às "+result));
        document.add(new Paragraph(""));
        //TABELA
        PdfPTable table = new PdfPTable(8);

        table.setTotalWidth(new float[]{ 50, 50, 120, 30, 60, 60, 40 , 120 });
        table.setLockedWidth(true); 
        //CABEÇALHO DA TABELA
        PdfPCell cell = new PdfPCell(new Paragraph("ID",titleFont));
        PdfPCell cel2 = new PdfPCell(new Paragraph("ID_ALU",titleFont));
        PdfPCell cel3 = new PdfPCell(new Paragraph("NOME ESTUDANTE",titleFont));
        PdfPCell cel4 = new PdfPCell(new Paragraph("BOLSA",titleFont));
        PdfPCell cel5 = new PdfPCell(new Paragraph("DATA",titleFont));
        PdfPCell cel6 = new PdfPCell(new Paragraph("HORÁRIO",titleFont));
        PdfPCell cel7 = new PdfPCell(new Paragraph("QUANT.",titleFont));
        PdfPCell cel8 = new PdfPCell(new Paragraph("ADMINISTRADOR",titleFont));
                //ADICIONAR CABEÇALHO NA TABELA
                table.addCell(cell);
                table.addCell(cel2);
                table.addCell(cel3);
                table.addCell(cel4);
                table.addCell(cel5);
                table.addCell(cel6);
                table.addCell(cel7);
                table.addCell(cel8);
        for (RefeitorioController u : r.CarregarRefeicoes("HORARIOREFEICAO","DESC")) {
         dataformatada=dataBanco(u.getDatarefeitorio());
         if(u.getTipobolsa()==0){nomebolsa="100%";b1++;}else if (u.getTipobolsa()==1){nomebolsa="50%";b2++;}
         total++;
         cell = new PdfPCell(new Paragraph(u.getIdrefeitorio().toString(),conteudoFont));
         cel2 = new PdfPCell(new Paragraph(u.getIdrefaluno().toString(),conteudoFont));
         cel3 = new PdfPCell(new Paragraph(u.getNomealuno(),conteudoFont));
         cel4 = new PdfPCell(new Paragraph(nomebolsa,conteudoFont));
         cel5 = new PdfPCell(new Paragraph(dataformatada,conteudoFont));
         cel6 = new PdfPCell(new Paragraph(u.getHorarefeitorio(),conteudoFont));
         cel7 = new PdfPCell(new Paragraph(u.getQuantidaderefeitorio().toString(),conteudoFont));
         cel8 = new PdfPCell(new Paragraph(u.getNomeusuario(),conteudoFont));
                //ADICIONAR CABEÇALHO NA TABELA
                table.addCell(cell);
                table.addCell(cel2);
                table.addCell(cel3);
                table.addCell(cel4);
                table.addCell(cel5);
                table.addCell(cel6);
                table.addCell(cel7);
                table.addCell(cel8);            
            
        }
        //sumário
         
//         cell = new PdfPCell(new Paragraph("Total de Bolsas 100%: "+b1));
//         cell.setColspan(4);
//         cel2 = new PdfPCell(new Paragraph("Total de Bolsas 50%: "+b2));
//         cel2.setColspan(4);
         table.addCell(cell);
         table.addCell(cel2);
        document.add(table);
        
        document.add(new Paragraph(" "));
        
        //tabelas das somas
       PdfPTable table1 = new PdfPTable(4);
       table1.setTotalWidth(new float[]{ 150, 150, 100, 100});
       table1.setLockedWidth(true);
       
       
       //cabeçalho
       cell.addElement(new Paragraph("RESUMO DA ALIMENTAÇÕES",titleFont));
       cell.setColspan(4);

       //cell.setVerticalAlignment(Element.ALIGN_CENTER);
       //cell.setBackgroundColor(BaseColor.GRAY);       
       cel2.addElement(new Paragraph("SOMA DOS AUXILIOS 100%: "+b1));
       cel2.setColspan(2);
       cel3.addElement(new Paragraph("SOMA DOS AUXILIOS 50%: "+b2));
       cel3.setColspan(2);
       cel4.addElement(new Paragraph("TOTAL DE LIBERAÇÕES:"+total));
       cel4.setColspan(4);
       table1.addCell(cell);
       table1.addCell(cel2);
       table1.addCell(cel3);
       table1.addCell(cel4);
       document.add(table1);
       
    } catch (FileNotFoundException ex) {
        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
        document.close();
    }
    try {
        Desktop.getDesktop().open(new File("relatorio.pdf"));
    } catch (IOException ex) {
        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
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
        jTable1 = new javax.swing.JTable();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        JP_PRINCIPAL = new javax.swing.JPanel();
        lbFoto = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnCapturar = new javax.swing.JButton();
        txtSelecCampo = new javax.swing.JComboBox<>();
        txtSelecOrdem = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbTodasEntradas = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbTotalEntradas = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbTotal100 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbTotal50 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbTotalRes = new javax.swing.JLabel();
        btnCapturarId = new javax.swing.JButton();
        lbHoras = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        JP_PRINCIPAL.setBackground(new java.awt.Color(255, 255, 255));
        JP_PRINCIPAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(50, 168, 82), 6));

        lbFoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 255, 204), 1, true));

        jPanel1.setBackground(new java.awt.Color(50, 168, 82));

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Refeitório");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(230, 230, 230))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_list_numbered_103614.png"))); // NOI18N
        jButton1.setText("Ordenar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_simpline_5_2305642.png"))); // NOI18N
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnCapturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_simpline_55_2305607 (1).png"))); // NOI18N
        btnCapturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapturarActionPerformed(evt);
            }
        });

        txtSelecCampo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Horário Refeição", "Horário Reserva", "Bolsa" }));

        txtSelecOrdem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Decrescente", "Crescente", " " }));
        txtSelecOrdem.setSelectedIndex(1);
        txtSelecOrdem.setToolTipText("");

        tbTodasEntradas.setBackground(new java.awt.Color(0, 255, 255));
        tbTodasEntradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome Aluno", "Bolsa", "Data", "Refeição", "Usuário", "IdAluno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTodasEntradas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTodasEntradasMouseClicked(evt);
            }
        });
        tbTodasEntradas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbTodasEntradasKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbTodasEntradas);

        jPanel7.setBackground(new java.awt.Color(51, 51, 51));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Liberações Diárias : ");

        lbTotalEntradas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTotalEntradas.setForeground(new java.awt.Color(255, 51, 0));
        lbTotalEntradas.setText("Contagem");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 51));
        jLabel10.setText("Total de Bolsas 100%: ");

        lbTotal100.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTotal100.setForeground(new java.awt.Color(255, 0, 51));
        lbTotal100.setText("bolsa100");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setText("Total de Bolsas 50%: ");

        lbTotal50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTotal50.setForeground(new java.awt.Color(255, 51, 0));
        lbTotal50.setText("bolsa50");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Reservas:");

        lbTotalRes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTotalRes.setForeground(new java.awt.Color(255, 51, 0));
        lbTotalRes.setText("Reservas");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotalEntradas)
                .addGap(27, 27, 27)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal100)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal50)
                .addGap(43, 43, 43)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTotalRes)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(lbTotal50)
                        .addComponent(jLabel9)
                        .addComponent(lbTotalRes))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(lbTotal100))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(lbTotalEntradas)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCapturarId.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/search-3-32.png"))); // NOI18N
        btnCapturarId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapturarIdActionPerformed(evt);
            }
        });

        lbHoras.setBackground(new java.awt.Color(51, 51, 51));
        lbHoras.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbHoras.setForeground(new java.awt.Color(51, 51, 51));
        lbHoras.setText("jLabel2");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/clock-4-32.png"))); // NOI18N

        javax.swing.GroupLayout JP_PRINCIPALLayout = new javax.swing.GroupLayout(JP_PRINCIPAL);
        JP_PRINCIPAL.setLayout(JP_PRINCIPALLayout);
        JP_PRINCIPALLayout.setHorizontalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PRINCIPALLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSelecCampo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSelecOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1025, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnCapturar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCapturarId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lbHoras)))
                .addGap(186, 222, Short.MAX_VALUE))
        );
        JP_PRINCIPALLayout.setVerticalGroup(
            JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                                .addComponent(txtSelecCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSelecOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1)
                            .addComponent(btnImprimir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(JP_PRINCIPALLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(JP_PRINCIPALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbHoras)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapturarId, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapturar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIPAL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        Integer id=0;
  if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
           
             BuscarDigital cDigital = new BuscarDigital(this, true);
             cDigital.setVisible(true);
             id=cDigital.getIdaluno();
             buscarAluno(id);
             
         }
    }//GEN-LAST:event_formKeyPressed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
    Relatoriogenerico relatorio = new Relatoriogenerico();
    int linhas = tbTodasEntradas.getRowCount();
    if(linhas>0){
    try {
       String campo=null,ordem=null;
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
       if(txtSelecOrdem.getSelectedIndex()==0){
           ordem="DESC"; 
       }else if(txtSelecOrdem.getSelectedIndex()==1){
            ordem="ASC";
       }
        try {
           try {
               relatorio.gerarPdfRefeicao("Pesquisa por Refeições","",7, "", "", 0, 0, 0, 0,campo,ordem,0);
           } catch (FileNotFoundException ex) {
               Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
           }
        } catch (BadElementException ex) {
            Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (DocumentException ex) {
        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
    }
    }else{
        JOptionPane.showMessageDialog(null, "Não há dados para apresentar");
             
    }

    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnCapturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturarActionPerformed
        // TODO add your handling code here:
           //limparFoto();
     try {
         buscarDigital();
     } catch (UnsupportedEncodingException ex) {
         Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
     } catch (SQLException ex) {
         Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
     }


        
    }//GEN-LAST:event_btnCapturarActionPerformed

    private void tbTodasEntradasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTodasEntradasMouseClicked
        // TODO add your handling code here:
                int linhaSelecionada = -1;

        linhaSelecionada = tbTodasEntradas.getSelectedRow();
        if (linhaSelecionada>=0){
            int i=(int)tbTodasEntradas.getValueAt(linhaSelecionada, 5);
            buscarAluno(i);
        }
    }//GEN-LAST:event_tbTodasEntradasMouseClicked

    private void tbTodasEntradasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTodasEntradasKeyReleased
        // TODO add your handling code here:
        
                        int linhaSelecionada = -1;

        linhaSelecionada = tbTodasEntradas.getSelectedRow();
        if (linhaSelecionada>=0){
            int i=(int)tbTodasEntradas.getValueAt(linhaSelecionada, 5);
             buscarAluno(i);
        }
    }//GEN-LAST:event_tbTodasEntradasKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     try {
         // TODO add your handling code here:
         ordernarConsulta();
     } catch (SQLException ex) {
         Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
     }
    }//GEN-LAST:event_jButton1ActionPerformed

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

    private void btnCapturarIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturarIdActionPerformed
        int id = 0;
     try {
         carregarTodasEntradas();
        PesquisarAlunoFormAutenticar Pafa = new PesquisarAlunoFormAutenticar(this, true);

             
             Pafa.setVisible(true);
             id=Pafa.idAluno; 
             if(Pafa.retorno==1){
                         if(id>0){
                    RefeitorioDao dao = new RefeitorioDao();
                    RefeitorioController cref = new RefeitorioController();
                    int bloqueado=dao.tabloqueado(id);
//                    JOptionPane.showMessageDialog(null, "aqui");
                    if(dao.tipoBolsa(id)==2){// verifica qual tipo de bolsa
                        JOptionPane.showMessageDialog(null,"Auxílio Inativo");
                        avisoSonoroAlerta();
                        buscarDigital();
                    }
                    else if (dao.tipoBolsa(id)<2){//verifica a bolsa não está inativa  
                         if (dao.temReserva(id)==false && dao.fezRefeicao(id)==true){
                                AvisoPosJEntForm aviso = new AvisoPosJEntForm(this,true);//ja foi entregue hoje
                                avisoSonoroAlerta();
                                aviso.setVisible(true);
                                buscarDigital();
                         }else{
                           ConfirmacaoRefeicao c =new ConfirmacaoRefeicao(this,true);
                            c.idlogado=idlogado;
                            c.carregarDados(id);
                            c.setVisible(true);
                            buscarDigital();
                         }
                    }
//                    else if(bloqueado==2) {
//                        avisoSonoroAlerta();
//                        AvisoBForm aviso = new AvisoBForm(this,true);
//                        aviso.setVisible(true);
//                        buscarAluno(id);
//                        buscarDigital();  
//                        }
                    
                }else{
                 avisoSonoroAlerta();
             }
             
             }
     } catch (SQLException ex) {
         Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
     } catch (UnsupportedEncodingException ex) {
         Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
     }

      
    }//GEN-LAST:event_btnCapturarIdActionPerformed
    
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
            java.util.logging.Logger.getLogger(RefeitorioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RefeitorioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RefeitorioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RefeitorioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RefeitorioForm().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_PRINCIPAL;
    private javax.swing.JButton btnCapturar;
    private javax.swing.JButton btnCapturarId;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JToggleButton lbFoto;
    private javax.swing.JLabel lbHoras;
    private javax.swing.JLabel lbTotal100;
    private javax.swing.JLabel lbTotal50;
    private javax.swing.JLabel lbTotalEntradas;
    private javax.swing.JLabel lbTotalRes;
    private javax.swing.JTable tbTodasEntradas;
    private javax.swing.JComboBox<String> txtSelecCampo;
    private javax.swing.JComboBox<String> txtSelecOrdem;
    // End of variables declaration//GEN-END:variables
}
