/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AlunoController;
import Dao.ReservaDao;
import Controller.RefeitorioController;
import Dao.AlunoDao;
import Dao.RefeitorioDao;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import javax.swing.table.TableRowSorter;
import static javax.swing.text.StyleConstants.FontFamily;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import relatorios.Relatoriogenerico;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Home
 */
public class ReservaForm extends javax.swing.JFrame {
int contador=0;
private String caminhofoto="";
int tipobolsa=0;
int idlogado=0;
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
     * Creates new form ReservaForm
     */
    public ReservaForm() throws SQLException {
        initComponents();
        preecherTabela();
        
           this.setResizable(false);
        Insets in = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());

            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

            int width = d.width-(in.left + in.top);
            int height = d.height-(in.top + in.bottom);
            setSize(width,height);
            setLocation(in.left,in.top);
    }
     public String dataBanco(String data){
       String dia = data.substring(8, 10);
       String mes = data.substring(5, 7);
       String ano = data.substring(0, 4);
       String databanco = dia+"/"+mes+"/"+ano;
       return databanco;
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
        public void avisoSonoroPositivo(){
       tocarSom(caminhopastasons().trim()+"\\chimes.wav");
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
 public String retirarFormatacaoCpf(String CPF){
          return(CPF.replaceAll("[.-]", ""));
    }
     public String imprimeCPF(String CPF) {
         if(CPF.length()==11){
    return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
      CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
         }else{
             return null;
         }
  }
    public void preecherTabela() throws SQLException{
               contador=0;
               Integer total=0,bolsa100=0,bolsa50=0;
               DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
               DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
               DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
               esquerda.setHorizontalAlignment(SwingConstants.LEFT);
               centralizado.setHorizontalAlignment(SwingConstants.CENTER);
               direita.setHorizontalAlignment(SwingConstants.RIGHT);
               DefaultTableModel tabela = (DefaultTableModel) tbTodasEntradas.getModel();
               tbTodasEntradas.setRowSorter(new TableRowSorter(tabela));
               tbTodasEntradas.setModel(tabela);
               tbTodasEntradas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
               tbTodasEntradas.getColumnModel().getColumn(0).setPreferredWidth(300);
               tbTodasEntradas.getColumnModel().getColumn(1).setPreferredWidth(110);
               tbTodasEntradas.getColumnModel().getColumn(2).setPreferredWidth(110);
               tbTodasEntradas.getColumnModel().getColumn(3).setPreferredWidth(110);
               tbTodasEntradas.getColumnModel().getColumn(4).setPreferredWidth(300);
               tbTodasEntradas.getColumnModel().getColumn(5).setPreferredWidth(110);
        
       		tabela.setNumRows(0);
                
	
		RefeitorioDao dao = new RefeitorioDao();

		for (RefeitorioController u : dao.CarregarReservas()) {
                    tipobolsa=u.getTipobolsa();
                    String bolsa=null;
                    if (tipobolsa==0){bolsa="100%";}else{bolsa="50%";}
			tabela.addRow(new Object[]
                        {
                            
                           
                         u.getNomealuno(),  
                         bolsa,
                         dataBanco(u.getDatarefeitorio()),
                         u.getHorarefeitorio(),
                         u.getNomeusuario(),
                         u.getIdrefaluno()
                        
                        });
                              if(u.getTipobolsa()==0){
                                bolsa100++;  
                                }
                                if(u.getTipobolsa()==1){
                                bolsa50++;
                                      
                                }
		contador++;
          
                }
//                 tblReserva.setRowSelectionInterval(0,0);
                //lbTotal.setText(Integer.toString(tabela.getRowCount()));
    
        
                lbTotal100.setText(Integer.toString(bolsa100));
                lbTotal50.setText(Integer.toString(bolsa50));
                lbTotalEntradas.setText(Integer.toString(contador));
                lbTotalCons.setText(Integer.toString(dao.contarConsumidas()));
                tbTodasEntradas.setDefaultRenderer(Object.class, new TableRenderer());
              if(tbTodasEntradas.getRowCount()>0){
             int id=(int)tbTodasEntradas.getValueAt(0, 5);
             buscarAluno(id);  
              }
   
    }
        public void buscarAluno(Integer idAluno){
   
                  AlunoDao dao = new AlunoDao();
                  for (AlunoController u : dao.CarregarDedos(idAluno)) {
                      
                      //lbNome.setText(u.getNomealuno());
                      ImageIcon img = new ImageIcon(lerJson().trim()+u.getFotoaluno().trim());
                      
                      lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(lbFoto.getWidth(), lbFoto.getHeight(),img.getImage().SCALE_DEFAULT)));
                   
    }
        }
    public void salvar(int id){
        RefeitorioDao rdao =  new RefeitorioDao();
        RefeitorioController rcol = new RefeitorioController();
        rcol.setIdrefaluno(id);
        rcol.setQuantidaderefeitorio(1.0);
        rcol.setUsuid(idlogado);
        rdao.SalvarRefeitorio(rcol);
        
        
    }
//    public void alterar(int id) throws SQLException, SQLException{
//        ReservaDao rdao1 =  new ReservaDao();
//        int t=rdao1.tentativas(id);
//        t++;
//        ReservaDao rdao =  new ReservaDao();
//        ReservaController rcol = new ReservaController();
//        rcol.setIdalureserva(id);
//        rcol.setTentativas(t);
//        rdao.AtualizarTentativas(rcol);  
//    }
   public void buscarDigital() throws SQLException, UnsupportedEncodingException{
             Integer id=0;
             preecherTabela();
             BuscarDigital cDigital = new BuscarDigital(this, true);
             //cDigital.idlogado=idlogado;
             cDigital.setVisible(true);
             id=cDigital.getIdaluno();
             
             if(id>0){
                 RefeitorioDao rdao =  new RefeitorioDao();
                 int reserva=rdao.contarRegistros(id);
                 int refeicao= rdao.contarConsumidas(id);
                 if(rdao.tipoBolsa(id)==2){
                     JOptionPane.showMessageDialog(null,"Cadastro Inativo");
                 }
                 else if(refeicao>0){
                      AvisoNRForm aviso = new AvisoNRForm(this,true);
                     aviso.setVisible(true);
                     buscarDigital();                    
                 }else if(reserva>0){
                     AvisoForm aviso = new AvisoForm(this,true);
                     aviso.setVisible(true);
                     buscarDigital();
                 }else{
                     RefeitorioDao dao = new RefeitorioDao();
                    RefeitorioController cref = new RefeitorioController();                    
                    cref.setIdrefaluno(id);
                    cref.setQuantidaderefeitorio(1.0);
                    cref.setUsuid(idlogado);
                    dao.SalvarRefeitorio(cref);
//                    AvisoPosEntForm apef= new AvisoPosEntForm(this,true);
//                    apef.buscarAluno(id);
//                    apef.setVisible(true);
                    buscarAluno(id);
                    avisoSonoroPositivo();
                    buscarDigital();
//                     ConfirmacaoRefeicao c =new ConfirmacaoRefeicao(this,true);
//                      c.idlogado=idlogado;
//                  try {
//                        c.carregarDados(id);
//                        } catch (SQLException ex) {
//                        Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        c.setVisible(true);
//                         if(c.retorno==1){
//                              buscarAluno(id);
//                             AvisoPosForm avisopos = new AvisoPosForm(this,true);
//                             avisopos.setVisible(true);
//                             buscarDigital();
//                         }else if(c.retorno==0){
//                             buscarDigital();
//                 }
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

        jPanel4 = new javax.swing.JPanel();
        JP_PRINCIPAL = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnCapturar = new javax.swing.JButton();
        lbFoto = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTodasEntradas = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbTotalEntradas = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbTotal100 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbTotal50 = new javax.swing.JLabel();
        lbTotalCons = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1202, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 774, -1, -1));
        jPanel4.getAccessibleContext().setAccessibleName("");

        JP_PRINCIPAL.setBackground(new java.awt.Color(60, 77, 87));
        JP_PRINCIPAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(50, 168, 82), 4));
        JP_PRINCIPAL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnImprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_simpline_5_2305642.png"))); // NOI18N
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        JP_PRINCIPAL.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 95, 50));

        btnCapturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_simpline_55_2305607 (1).png"))); // NOI18N
        btnCapturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapturarActionPerformed(evt);
            }
        });
        JP_PRINCIPAL.add(btnCapturar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 370, 190, 40));

        lbFoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 255, 204), 1, true));
        JP_PRINCIPAL.add(lbFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 140, 192, 213));

        tbTodasEntradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nome Aluno", "Bolsa", "Data", "Horário", "Usuário", "IdAlu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTodasEntradas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tbTodasEntradasMouseMoved(evt);
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
        jScrollPane1.setViewportView(tbTodasEntradas);

        JP_PRINCIPAL.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1050, 560));

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Reservas:");

        lbTotalEntradas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTotalEntradas.setForeground(new java.awt.Color(255, 51, 0));
        lbTotalEntradas.setText("Contagem");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 204));
        jLabel10.setText("Total de Bolsas 100%: ");

        lbTotal100.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTotal100.setForeground(new java.awt.Color(255, 255, 255));
        lbTotal100.setText("bolsa100");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setText("Total de Bolsas 50%: ");

        lbTotal50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTotal50.setForeground(new java.awt.Color(255, 255, 255));
        lbTotal50.setText("bolsa50");

        lbTotalCons.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTotalCons.setForeground(new java.awt.Color(255, 255, 255));
        lbTotalCons.setText("Consumidas");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 204, 0));
        jLabel12.setText("Consumidas:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotalEntradas)
                .addGap(94, 94, 94)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal100)
                .addGap(39, 39, 39)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal50)
                .addGap(64, 64, 64)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotalCons)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(lbTotal50)
                        .addComponent(lbTotalCons)
                        .addComponent(jLabel12))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(lbTotal100))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(lbTotalEntradas)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JP_PRINCIPAL.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 720, 1260, -1));

        jPanel1.setBackground(new java.awt.Color(50, 168, 82));

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Reserva de Refeição");

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
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 751, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        JP_PRINCIPAL.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 4, 1267, 64));

        getContentPane().add(JP_PRINCIPAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1275, 768));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbTodasEntradasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTodasEntradasMouseClicked

        int linhaSelecionada = -1;

        linhaSelecionada = tbTodasEntradas.getSelectedRow();
        if (linhaSelecionada>=0){
            int id=(int)tbTodasEntradas.getValueAt(linhaSelecionada, 5);
             buscarAluno(id);
        }
 
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Aluno");
        }
    }//GEN-LAST:event_tbTodasEntradasMouseClicked

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        Relatoriogenerico relatorio = new Relatoriogenerico();
    int linhas = tbTodasEntradas.getRowCount();
    if(linhas>0){
            try {
                try {
                    relatorio.gerarPdfRefeicao("Pesquisando Reserva","",8, "", "", 0, 0, 0, 0,"datarefeitorio","desc",0);
                } catch (BadElementException ex) {
                    Logger.getLogger(ReservaForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ReservaForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (DocumentException ex) {
                Logger.getLogger(ReservaForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ReservaForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
        JOptionPane.showMessageDialog(null, "Não há dados para apresentar");
             
    }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnCapturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturarActionPerformed
    try {
        // TODO add your handling code here:
        
        buscarDigital();
    } catch (SQLException ex) {
        Logger.getLogger(ReservaForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedEncodingException ex) {
        Logger.getLogger(ReservaForm.class.getName()).log(Level.SEVERE, null, ex);
    }

    }//GEN-LAST:event_btnCapturarActionPerformed

    private void tbTodasEntradasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTodasEntradasMouseMoved
        // TODO add your handling code here:

        
    }//GEN-LAST:event_tbTodasEntradasMouseMoved

    private void tbTodasEntradasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTodasEntradasKeyReleased
        // TODO add your handling code here:
                int linhaSelecionada = -1;

        linhaSelecionada = tbTodasEntradas.getSelectedRow();
        if (linhaSelecionada>=0){
            int id=(int)tbTodasEntradas.getValueAt(linhaSelecionada, 5);
             buscarAluno(id);
        }
 
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Aluno");
        }
    }//GEN-LAST:event_tbTodasEntradasKeyReleased

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        //        System.exit(0);
        this.dispose();
        AlunoListaForm lista = new AlunoListaForm();
        
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
            java.util.logging.Logger.getLogger(ReservaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReservaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReservaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReservaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ReservaForm().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(ReservaForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_PRINCIPAL;
    private javax.swing.JButton btnCapturar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JToggleButton lbFoto;
    private javax.swing.JLabel lbTotal100;
    private javax.swing.JLabel lbTotal50;
    private javax.swing.JLabel lbTotalCons;
    private javax.swing.JLabel lbTotalEntradas;
    private javax.swing.JTable tbTodasEntradas;
    // End of variables declaration//GEN-END:variables
}
