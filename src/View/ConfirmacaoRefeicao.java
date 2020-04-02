/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AlunoController;
import Controller.BolsaController;
import Controller.RefeitorioController;
import Controller.ReservaController;
import Dao.AlunoDao;
import Dao.BolsaDao;
import Dao.CreditoDao;
import Dao.RefeitorioDao;
import Dao.ReservaDao;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Home
 */
public class ConfirmacaoRefeicao extends javax.swing.JDialog {
java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
RefeitorioDao refeitoriodao = new RefeitorioDao();
CreditoDao creditodao = new CreditoDao();
private Integer tipobolsa;
private Integer tipobolsaSalvar;
public Integer idlogado;
private String caminhofoto="";
public Integer idaluno=0;
public Integer retorno=0;


    /**
     * Creates new form ConfimacaoRefeicao
     */
    public ConfirmacaoRefeicao(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        initComponents();
        this.setLocationRelativeTo(null);
        //btnConfirmar.setBackground(new Color(0,153,51));
        btnConfirmar.setBackground(Color.yellow);
        //btnConfirmar.setForeground(Color.white);
       
       // btnCancelar.setForeground(Color.white);
        
//        tbIndividual.setVisible(false);
//        jLabel9.setVisible(false);
//        lbTotalIndividual.setVisible(false);
    }
           public String lerCaminho(){
                    String arq = "conf.txt";
                    String texto=Arquivo.Read(arq);
                    if(texto.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Não foi possível ler o caminho guardado");
                    }

                    return texto;
               }
           
                    public void buscarAluno(Integer idAluno){

                                  AlunoDao dao = new AlunoDao();
                                  for (AlunoController u : dao.CarregarDedos(idAluno)) {

                                      lbNome.setText(u.getNomealuno());
                                      caminhofoto=lerJson();
                                      ImageIcon img = new ImageIcon(caminhofoto.trim()+u.getFotoaluno().trim());
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
                      catch (org.json.simple.parser.ParseException ex) {ex.printStackTrace(); }
                      catch (Exception ex) {ex.printStackTrace(); }
                              return pastafotoaluno;
                 }
             
        public void salvarEntrada() throws UnsupportedEncodingException, SQLException{
        RefeitorioController refeitorio = new RefeitorioController();
        RefeitorioDao refeitoriodao = new RefeitorioDao();
        AlunoDao dao = new AlunoDao();
        BolsaDao bolsadao = new BolsaDao();
        Integer bolsa=null,turma=null;
        double valor=0;
        int id = Integer.parseInt(lbIdAluno.getText());

        
                  for (AlunoController u : dao.CarregarDedos(id)) {
                      turma=u.getTurmaid();
                      bolsa=u.getBolsa();  
                  }
                  for (BolsaController u : bolsadao.buscarBolsaId(bolsa)) {
                      valor=u.getValorbolsa();
                  }
        
          refeitorio.setIdrefaluno(id);
           refeitorio.setQuantidaderefeitorio(1.0);
           refeitorio.setUsuid(idlogado);
           refeitorio.setIdturma(turma);
           refeitorio.setTipobolsa(bolsa);
           refeitorio.setValor(valor);
           
       if(refeitoriodao.temReserva(id)){
       
                        refeitoriodao.PegarRefeicao(refeitorio);// aqui já constatou que foi reservado agora vai resistrar pegando a refeição
           
                                dispose();
                            }else{

                        refeitoriodao.SalvarRefeitorio(refeitorio);//salvar entrada reservando a comida
                        refeitoriodao.PegarRefeicao(refeitorio);// já registar pegando a refeição
                        dispose();
       }
        avisoSonoroPositivo();
            
    }

    
    public void verReserva(int id) throws SQLException{
//        ReservaDao r = new ReservaDao();
//        if(r.buscarID(id)){
//            lbReserva.setText("RESERVADO");
//            lbReserva.setForeground(Color.GREEN);
//        }else{
//            lbReserva.setText("SEM RESERVA");
//            lbReserva.setForeground(Color.RED);
//        }
    }
     public void carregarDados(int idalu) throws SQLException{
         verReserva(idalu);
             Integer id=0;
             Integer limite=0;
             Integer registro=0;
             id=idalu;
             idaluno=idalu;
             
             if(id>0){//se for maior que zero, foi encontrado a digital
                        lbIdAluno.setText(String.valueOf(id));
                        buscarAluno(id);
                        carregarIndividual(id);
                        
                        if(refeitoriodao.temReserva(id)){

                            jLabel1.setText("Com Reserva");
                            jLabel1.setForeground(Color.GREEN);
//                            jLabel1.setBackground(new Color(0,100,0));
                            
                        }else{
                             jLabel1.setText("Sem Reserva");
                            jLabel1.setForeground(Color.RED);                         
//                            jLabel1.setBackground(new Color(255,215,0));                         
                        }
                        
                        tipobolsaSalvar=creditodao.buscarTipoBolsa(id);
                        
                                        if(refeitoriodao.tipoBolsa(id)==2){
                                            JOptionPane.showMessageDialog(null, "Cadastro Inativo!!!");
                                        }else{
//                                    try {
//                                        limite=refeitoriodao.limiteDia(id);
//                                    } catch (SQLException ex) {
//                                        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
                                    
                                    try {
                                        registro=refeitoriodao.contarRegistros(id);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                        
                        
                            if(refeitoriodao.tabloqueado(id)==2){
                             msgBloqueado();
                             btnConfirmar.setVisible(false);
                            }
//                            else if(refeitoriodao.contarConsumidas(id)>0){
//                            msgLimiteExcedido(); 
//                            btnConfirmar.setVisible(false);
//                            }
//                            else if(refeitoriodao.naoInicializadoPeriodo(id)){ 
//                                msgNaoInicializado();
//                                btnConfirmar.setVisible(false);
//                            }
//                              else if(refeitoriodao.prazoEsgotado(id)){
//                                msgPrazoEsgotado();
//                                btnConfirmar.setVisible(false);
//                            }else if(refeitoriodao.epracontrolar(id)){
//                                       if(refeitoriodao.calculaControleCredito(id)){
//                                           msgCreditosFinalizados();
//                                           btnConfirmar.setVisible(false);
//                                       }else{
//                                            if(tipobolsaSalvar==0){
//                                            msgBolsaIntegral();
//                                            btnConfirmar.setVisible(true);
//                                            //salvarEntrada();
//                                            
//                                            //creditodao.AtualizarCreditoUtilizados(id);
//                                            }else if(tipobolsaSalvar==1){
//                                            msgBolsaParcial();
//                                            btnConfirmar.setVisible(true);
//                                            //salvarEntrada();
//                                            //creditodao.AtualizarCreditoUtilizados(id);
//                                            } 
//                                           
//                                       }
//                            } else
//                            {
                                        if(tipobolsaSalvar==0){
                                        msgBolsaIntegral();
                                        btnConfirmar.setVisible(true);
                                        //salvarEntrada();
                                        //creditodao.AtualizarCreditoUtilizados(id);
                                        }else if(tipobolsaSalvar==1){
                                        msgBolsaParcial();
                                        btnConfirmar.setVisible(true);
                                       // salvarEntrada();
                                       // creditodao.AtualizarCreditoUtilizados(id);
                                        
//                                            }
                            }
                              
             
                                        }
             }
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
   public String caminhopastasons(){
   File f = new File("sons");
   return f.getAbsolutePath();
}
   public void avisoSonoroNaoIdentificado(){
       //tocarSom(caminhopastasons().trim()+"\\alarme.wav");
       java.awt.Toolkit.getDefaultToolkit().beep();
   }
   public void avisoSonoroPositivo(){
       tocarSom(caminhopastasons().trim()+"\\chimes.wav");
   }
   public void avisoSonoroAlerta(){
 java.awt.Toolkit.getDefaultToolkit().beep();
   }
   public void limpaConsultas(){
       lbFoto.setIcon(null);
       lbNome.setText(null);
      
//       DefaultTableModel tabela = (DefaultTableModel) tbIndividual.getModel();
//       //DefaultTableModel tabela1 = (DefaultTableModel) tbTodasEntradas.getModel();
//       tabela.setNumRows(0);
      // tabela1.setNumRows(0);
   }
   public void msgLimiteExcedido(){
       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
       fundoVermelho();
       lbMensagem.setText("Todos os Tickets utilizados hoje!!!!");
       avisoSonoroAlerta();
   }
   public void msgCreditosFinalizados(){
       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
       fundoVermelho();
       lbMensagem.setText("Créditos Finalizados!!!!"); 
       avisoSonoroAlerta();
   }
   public void msgNaoInicializado(){
       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
       fundoVermelho();
       lbMensagem.setText("Período ainda não inicializado!!!!");
       avisoSonoroAlerta();
   }
   public void msgBloqueado(){
       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
       fundoVermelho();
       lbMensagem.setText("Inativo! Procure a CAE");  
       avisoSonoroAlerta();
   }
   public void msgPrazoEsgotado(){
       //fundo vermelho - independente do tipo de bolsa, excedeu, a mensagem será de bloqueio.
       fundoVermelho();
       lbMensagem.setText("Período de utilização finalizado, procure a CAE!!!!"); 
       avisoSonoroAlerta();
   }
   public void msgBolsaIntegral(){
        //fundo verde
        fundoVerde();
       lbMensagem.setText("Bolsa Integral 100% Liberada!!!!");
       avisoSonoroPositivo();
   }
   public void msgBolsaParcial(){
        //fundo Amarelo
        fundoAmarelo();
       lbMensagem.setText("Bolsa Parcial 50% Liberada!!!!");
       avisoSonoroPositivo();
   }
   public void msgNaoIdentificada(){
       fundoVermelho();
       lbMensagem.setText("Impressão Digital Não Identificada!!!!");
       avisoSonoroNaoIdentificado();
   }
   public void fundoVermelho(){
       // - NÃO IDENTIFICADO,NÃO ENCONTRADO, LIMITE DIÁRIO(Fundo Vermelho)
       //pMensagem.setBackground(Color.RED);
       pMensagem.setBackground(new Color(139,0,0));
       lbMensagem.setForeground(Color.WHITE);
       //pMensagem.setBounds(0, 0, 100, 200);
   }
   public void fundoVerde(){
       //- Bolsa Integral
       //pMensagem.setBackground(Color.GREEN);
       pMensagem.setBackground(new Color(0,100,0));
       lbMensagem.setForeground(Color.WHITE);
       pMensagem.setBounds(0, 0, 400, 200);
   }
   public void fundoAmarelo(){
       //- Bolsa Parcial(Fundo Amarelo)
              //pMensagem.setBackground(Color.YELLOW);
              pMensagem.setBackground( new Color(255,215,0));
              lbMensagem.setForeground(Color.BLACK); 
              pMensagem.setBounds(0, 0, 400, 200);
   }

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
//            lbTotalIndividual.setText(Integer.toString(total));
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JP_PRINCIAL = new javax.swing.JPanel();
        lbFoto = new javax.swing.JToggleButton();
        lbIdAluno = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbNome = new javax.swing.JTextField();
        pMensagem = new javax.swing.JPanel();
        lbMensagem = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        JP_PRINCIAL.setBackground(new java.awt.Color(255, 255, 255));
        JP_PRINCIAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 4));

        lbIdAluno.setText("Idaluno");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 0));
        jLabel3.setText("Nome: ");

        jLabel7.setText("Id:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Com Reserva");

        lbNome.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbNome.setForeground(new java.awt.Color(255, 51, 0));
        lbNome.setEnabled(false);

        pMensagem.setBackground(new java.awt.Color(0, 102, 102));

        lbMensagem.setBackground(new java.awt.Color(0, 102, 102));
        lbMensagem.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbMensagem.setForeground(new java.awt.Color(255, 255, 255));
        lbMensagem.setText("Aguardando .....");

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

        javax.swing.GroupLayout pMensagemLayout = new javax.swing.GroupLayout(pMensagem);
        pMensagem.setLayout(pMensagemLayout);
        pMensagemLayout.setHorizontalGroup(
            pMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMensagemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMensagem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        pMensagemLayout.setVerticalGroup(
            pMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMensagemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        btnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(0, 153, 102));
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JP_PRINCIALLayout = new javax.swing.GroupLayout(JP_PRINCIAL);
        JP_PRINCIAL.setLayout(JP_PRINCIALLayout);
        JP_PRINCIALLayout.setHorizontalGroup(
            JP_PRINCIALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PRINCIALLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(JP_PRINCIALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PRINCIALLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(JP_PRINCIALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_PRINCIALLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(20, 20, 20)
                                .addComponent(lbIdAluno))
                            .addGroup(JP_PRINCIALLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel3))
                            .addComponent(jLabel1)
                            .addComponent(lbNome, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(JP_PRINCIALLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(96, Short.MAX_VALUE))
            .addComponent(pMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JP_PRINCIALLayout.setVerticalGroup(
            JP_PRINCIALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PRINCIALLayout.createSequentialGroup()
                .addComponent(pMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_PRINCIALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JP_PRINCIALLayout.createSequentialGroup()
                        .addGroup(JP_PRINCIALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbIdAluno)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(31, 31, 31)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_PRINCIAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        try {
        try {
            salvarEntrada();
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmacaoRefeicao.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (UnsupportedEncodingException ex) {
        Logger.getLogger(ConfirmacaoRefeicao.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        //        System.exit(0);
                       retorno=0;
        dispose();
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
            java.util.logging.Logger.getLogger(ConfirmacaoRefeicao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfirmacaoRefeicao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfirmacaoRefeicao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfirmacaoRefeicao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfirmacaoRefeicao dialog = new ConfirmacaoRefeicao(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel JP_PRINCIAL;
    private javax.swing.JToggleButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JToggleButton lbFoto;
    private javax.swing.JLabel lbIdAluno;
    private javax.swing.JLabel lbMensagem;
    private javax.swing.JTextField lbNome;
    private javax.swing.JPanel pMensagem;
    // End of variables declaration//GEN-END:variables
}
