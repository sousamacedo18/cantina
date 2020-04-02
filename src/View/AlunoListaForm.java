/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.AcessoController;
import Controller.AlunoController;
import Dao.AcessoDao;
import Dao.AlunoDao;
import Dao.TimeLineDao;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.in;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import relatorios.Relatorioaluno;

/**
 *
 * @author Home
 */
public class AlunoListaForm extends javax.swing.JFrame {
    ConexaoBD conexao = new ConexaoBD();
    AlunoDao alunosdao = new AlunoDao();
    AlunoController alunos = new AlunoController();
    public Integer idlogado;
    public int timelineleitura;
 
    AcessoDao acessodao = new AcessoDao();
//    private DefaultTableModel modelo = new DefaultTableModel();
    private Integer contador;
    /**
     * Creates new form CadastroListaAluno
     */
 
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
        catch (ParseException ex) {ex.printStackTrace(); }
        catch (Exception ex) {ex.printStackTrace(); }
                return pastafotoaluno;
   }

private void ordernarConsulta() throws SQLException {
        String campo=null,ordem=null;
       if(txtSelecCampo.getSelectedIndex()==0){
           campo="`nomealuno`"; 
       }else if(txtSelecCampo.getSelectedIndex()==1){
            campo="`tipobolsa`";
       }
       else if(txtSelecCampo.getSelectedIndex()==2){
            campo="`nomecurso`";
       
       }else if(txtSelecCampo.getSelectedIndex()==3){
            campo="`nometurma`";
       
              }else if(txtSelecCampo.getSelectedIndex()==4){
            campo="`cpfaluno`";
       }
       if(txtSelecOrdem.getSelectedIndex()==0){
           ordem="desc"; 
       }else if(txtSelecOrdem.getSelectedIndex()==1){
            ordem="asc";
       }
       
        preencherTabela(campo,ordem);
    }
       class ColorirTabela extends DefaultTableCellRenderer {
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                     

        String bolsa = table.getModel().getValueAt(row, 5).toString();
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
        
        } else if(bolsa.equals("Inativa")){
            comp.setBackground(Color.RED);
            //comp.setBackground(new Color(255, 91, 96));
            //comp.setBackground(new Color(255,215,0));
            comp.setForeground(Color.BLACK);
        }
        return comp;
    }
}
  class DescolorirTabela extends DefaultTableCellRenderer {
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                     
            comp.setBackground(Color.WHITE);
            //comp.setBackground(new Color(255, 91, 96));
            //comp.setBackground(new Color(255,215,0));
            comp.setForeground(Color.BLACK);
        
        return comp;
    }
}
 public void verificarBotomRadio(){
//     if(rbBotom.isSelected()){
//         tblAluno.setDefaultRenderer(Object.class, new AlunoListaForm.ColorirTabela());
//     }else if(rbBotom1.isSelected()){
//        
//         tblAluno.setDefaultRenderer(Object.class, new AlunoListaForm.DescolorirTabela());
//     }
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
    
        public void preencherTabela(String campo, String ordem){
               contador=0;
               int bolsa100=0,bolsa50=0,bolsainativa=0;
               DefaultTableModel tabela = (DefaultTableModel) tblAluno.getModel();
               tblAluno.setModel(tabela);
              // tblAluno.setRowSorter(new TableRowSorter(tabela));
               tblAluno.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
               tblAluno.getColumnModel().getColumn(0).setPreferredWidth(80);
              
               tblAluno.getColumnModel().getColumn(1).setPreferredWidth(250);
               
               tblAluno.getColumnModel().getColumn(2).setPreferredWidth(250);
               tblAluno.getColumnModel().getColumn(3).setPreferredWidth(250);
               tblAluno.getColumnModel().getColumn(4).setPreferredWidth(120);
               tblAluno.getColumnModel().getColumn(5).setPreferredWidth(250);
       		tabela.setNumRows(0);
                
		AlunoDao dao = new AlunoDao();
                        String bolsa=null;
		for (AlunoController u : dao.CarregarTodosAlunos(campo,ordem)) {
                        if(u.getBolsa()==0){
                            bolsa="100%";
                            bolsa100++;
                        }else if(u.getBolsa()==1){
                           bolsa="50%"; 
                           bolsa50++;
                        }else if(u.getBolsa()==2){
                            bolsa="Inativa";
                            bolsainativa++;
                        }
			tabela.addRow(new Object[]{
                            u.getIdaluno(),
                            u.getNomealuno(), 
                            imprimeCPF(u.getCpfaluno()), 
                            u.getNomecurso(), 
                            u.getNometurma(),
                            bolsa});
		contador++;
                }
                if(contador>0){
                    tblAluno.clearSelection();
                    tblAluno.changeSelection(0, 0, false, false);
                    tblAluno.setRowSelectionInterval(0,0);
                   // tblAluno.setDefaultRenderer(Object.class, new AlunoListaForm.ColorirTabela());
                    lbBolsa100.setText(Integer.toString(bolsa100));
                    lbBolsa50.setText(Integer.toString(bolsa50));
                    lbInativa.setText(Integer.toString(bolsainativa));
                
                }
   
    }
        public void carregarFoto() throws SQLException{
            String caminhofoto="";
            DefaultTableModel tabela = (DefaultTableModel) tblAluno.getModel();
            tblAluno.setModel(tabela);            
//             tblAluno.setModel(tabela);
             int linhaSelecionada = -1;
          
                linhaSelecionada = tblAluno.getSelectedRow();
                 if (linhaSelecionada>=0){
                int id=(int)tblAluno.getValueAt(linhaSelecionada, 0);
             AlunoDao dao = new AlunoDao();
 
             caminhofoto=lerJson().trim()+dao.buscarfoto(id).trim();
             File f =new  File(caminhofoto);
                     //System.out.println(caminhofoto);
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

                   
        }
        
            public void listaAcesso(Integer i){
        for(AcessoController a: acessodao.CarregarAcesso(i)){

            timelineleitura=a.getTimelineleitura();
          
     
        }
    }
    public AlunoListaForm() {
        initComponents();   
        preencherTabela("`nomealuno`","ASC");
        URL caminhoIcone = getClass().getResource("/ico/if_Food_572824.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);
         this.setResizable(false);
        Insets in = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
             

                      if(timelineleitura==0){
                          btnTimeLine.setEnabled(false);
                      }
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

            int width = d.width-(in.left + in.top);
            int height = d.height-(in.top + in.bottom);
            setSize(width,height);
            setLocation(in.left,in.top);
        
   

                         String caminhofoto=lerJson()+"sem-foto.png";
                         ImageIcon img = new ImageIcon(caminhofoto);
                         lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(122, 119, Image.SCALE_DEFAULT)));
        
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
        JP_Principal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAluno = new javax.swing.JTable();
        txtPesquisa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        SlctCampo = new javax.swing.JComboBox<>();
        btnPesquisar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnDigital = new javax.swing.JButton();
        btnDigitalTeste = new javax.swing.JButton();
        btnTimeLine = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbBolsa100 = new javax.swing.JLabel();
        lbBolsa50 = new javax.swing.JLabel();
        lbInativa = new javax.swing.JLabel();
        txtSelecCampo = new javax.swing.JComboBox<>();
        txtSelecOrdem = new javax.swing.JComboBox<>();
        btOrdenar = new javax.swing.JButton();
        lbFoto = new javax.swing.JToggleButton();
        btImprimir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        JP_Principal.setBackground(new java.awt.Color(60, 77, 87));
        JP_Principal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(50, 168, 82), 4));

        tblAluno.setAutoCreateRowSorter(true);
        tblAluno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nome Aluno", "Cpf", "Curso", "Turma", "Bolsa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        tblAluno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblAlunoFocusGained(evt);
            }
        });
        tblAluno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAlunoMouseClicked(evt);
            }
        });
        tblAluno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblAlunoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblAlunoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblAluno);
        if (tblAluno.getColumnModel().getColumnCount() > 0) {
            tblAluno.getColumnModel().getColumn(0).setResizable(false);
            tblAluno.getColumnModel().getColumn(1).setResizable(false);
            tblAluno.getColumnModel().getColumn(2).setResizable(false);
            tblAluno.getColumnModel().getColumn(4).setResizable(false);
            tblAluno.getColumnModel().getColumn(5).setResizable(false);
        }

        txtPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtPesquisaPropertyChange(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setText("Pesquisa");

        SlctCampo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SlctCampo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Cpf" }));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_edit-find_118922.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_plus_1646001.png"))); // NOI18N
        btnNovo.setToolTipText("Novo Cadastro");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_icon-136-document-edit_314724.png"))); // NOI18N
        btnEditar.setToolTipText("Alterar Cadastro");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_Line_ui_icons_Svg-03_1465842.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluír");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_simpline_55_2305607 (1).png"))); // NOI18N
        btnDigital.setMnemonic('o');
        btnDigital.setToolTipText("Cadastrar Biometria");
        btnDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigitalActionPerformed(evt);
            }
        });

        btnDigitalTeste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/one-finger-32.png"))); // NOI18N
        btnDigitalTeste.setToolTipText("Testar Biometria");
        btnDigitalTeste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDigitalTesteActionPerformed(evt);
            }
        });

        btnTimeLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/time-5-24.png"))); // NOI18N
        btnTimeLine.setToolTipText("Ver Time Line");
        btnTimeLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimeLineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btnDigital, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDigitalTeste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimeLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDigital, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDigitalTeste, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimeLine, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        jLabel4.setForeground(java.awt.Color.white);
        jLabel4.setText("* CPF apenas números");

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setText("Prescione enter para iniciar a pesquisa");

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 204));
        jLabel2.setText("Total de Bolsas 100%:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setText("Total de Bolsas 50%");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("Bolsas Inativas");

        lbBolsa100.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBolsa100.setForeground(new java.awt.Color(255, 255, 255));
        lbBolsa100.setText("total");

        lbBolsa50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBolsa50.setForeground(new java.awt.Color(255, 255, 255));
        lbBolsa50.setText("total");

        lbInativa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbInativa.setForeground(new java.awt.Color(255, 255, 255));
        lbInativa.setText("total");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbBolsa100)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbBolsa50)
                .addGap(165, 165, 165)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbInativa)
                .addGap(66, 66, 66))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(lbBolsa100)
                    .addComponent(lbBolsa50)
                    .addComponent(lbInativa))
                .addContainerGap())
        );

        txtSelecCampo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Bolsa", "Curso", "Turma", "CPF" }));

        txtSelecOrdem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Decrescente", "Crescente", " " }));

        btOrdenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_list_numbered_103614.png"))); // NOI18N
        btOrdenar.setText("Ordenar Tabela");
        btOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOrdenarActionPerformed(evt);
            }
        });

        lbFoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 255, 204), 1, true));

        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if__80ui_2303185.png"))); // NOI18N
        btImprimir.setText("Imprimir");
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(50, 168, 82));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Lista de Alunos");

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
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFechar)
                    .addComponent(jLabel1))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout JP_PrincipalLayout = new javax.swing.GroupLayout(JP_Principal);
        JP_Principal.setLayout(JP_PrincipalLayout);
        JP_PrincipalLayout.setHorizontalGroup(
            JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnPesquisar))
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(SlctCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                        .addComponent(txtSelecCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtSelecOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btImprimir)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 81, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JP_PrincipalLayout.setVerticalGroup(
            JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PrincipalLayout.createSequentialGroup()
                        .addComponent(SlctCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPesquisar)
                            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel5)
                        .addGap(46, 46, 46)
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btOrdenar)
                            .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSelecCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSelecOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btImprimir, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PrincipalLayout.createSequentialGroup()
                        .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Principal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        AlunoCadastroForm cadastroaluno = new AlunoCadastroForm();
        cadastroaluno.idlogado=this.idlogado;
       
        cadastroaluno.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        AlunoEditarForm alunoEditar= new AlunoEditarForm();
        int linhaSelecionada = -1;

        linhaSelecionada = tblAluno.getSelectedRow();
        if (linhaSelecionada>=0){
            int id=(int)tblAluno.getValueAt(linhaSelecionada, 0);
            alunoEditar.editarCampos(id);
            alunoEditar.idlogado=idlogado;
            alunoEditar.setVisible(true);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Aluno");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Deseja Realmente Excluir Esse Registro?", "Confirm",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {

            DefaultTableModel tabela = (DefaultTableModel) tblAluno.getModel();
            tblAluno.setModel(tabela);
            int linhaSelecionada = -1;
            linhaSelecionada = tblAluno.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int alu = (int) tblAluno.getValueAt(linhaSelecionada, 0);
                AlunoDao dao = new AlunoDao();
                alunos.setIdaluno(alu);
                dao.ExcluirAluno(alunos);
                tabela.removeRow(linhaSelecionada);
            } else {
                JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
            }
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            execPesquisar();
        }
        
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtPesquisaPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPesquisaPropertyChange

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
 
            execPesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void tblAlunoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAlunoMouseClicked
        try {
            carregarFoto();

        } catch (SQLException ex) {
            Logger.getLogger(AlunoListaForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tblAlunoMouseClicked

    private void btnDigitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDigitalActionPerformed
        // TODO add your handling code here:
 
    
             int linhaSelecionada = -1;
             linhaSelecionada = tblAluno.getSelectedRow();
        if (linhaSelecionada>=0){
            int id=(int)tblAluno.getValueAt(linhaSelecionada, 0);
              CapturarDigital digital = null;
                 try {
                      digital = new CapturarDigital();
                      digital.setIdaluno(id);
                      digital.setIdlogado(idlogado);
                      digital.verificarDedos();
                      digital.setVisible(true);
                 } catch (SQLException ex) {
                     Logger.getLogger(AlunoListaForm.class.getName()).log(Level.SEVERE, null, ex);
                 }
    
        }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Aluno");
        }

    }//GEN-LAST:event_btnDigitalActionPerformed

    private void btnDigitalTesteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDigitalTesteActionPerformed
        // TODO add your handling code here:
        int retorno=0;
        FormTesteDigital ftd = new FormTesteDigital(this,true);
        ftd.setVisible(true);
        retorno=ftd.getIdaluno();
        if(retorno==1){
            
        }
    }//GEN-LAST:event_btnDigitalTesteActionPerformed

    private void tblAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblAlunoKeyPressed

    }//GEN-LAST:event_tblAlunoKeyPressed

    private void tblAlunoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblAlunoFocusGained

    }//GEN-LAST:event_tblAlunoFocusGained

    private void tblAlunoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblAlunoKeyReleased
        // TODO add your handling code here:
        try {
            carregarFoto();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblAlunoKeyReleased

    private void btOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOrdenarActionPerformed
        try {
            // TODO add your handling code here:
            ordernarConsulta();
        } catch (SQLException ex) {
            Logger.getLogger(RefeitorioForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btOrdenarActionPerformed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        // TODO add your handling code here:
                String campo=null,ordem=null;
       if(txtSelecCampo.getSelectedIndex()==0){
           campo="`nomealuno`"; 
       }else if(txtSelecCampo.getSelectedIndex()==1){
            campo="`tipobolsa`";
       }
       else if(txtSelecCampo.getSelectedIndex()==2){
            campo="`nomecurso`";
       
       }else if(txtSelecCampo.getSelectedIndex()==3){
            campo="`nometurma`";
       
       }else if(txtSelecCampo.getSelectedIndex()==4){
            campo="`cpfaluno`";
       }
       if(txtSelecOrdem.getSelectedIndex()==0){
           ordem="desc"; 
       }else if(txtSelecOrdem.getSelectedIndex()==1){
            ordem="asc";
       }
        Relatorioaluno  aluno=new  Relatorioaluno();
        try {
            aluno.gerarPdfTodosAlunos(campo, ordem);
        } catch (DocumentException ex) {
            Logger.getLogger(AlunoListaForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AlunoListaForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btImprimirActionPerformed

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

    private void btnTimeLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeLineActionPerformed
        // TODO add your handling code here:
        TimeLineDao dao = new TimeLineDao();
        
             int linhaSelecionada = -1;

        linhaSelecionada = tblAluno.getSelectedRow();
        if (linhaSelecionada>=0){
            
            int id=(int)tblAluno.getValueAt(linhaSelecionada, 0);
            if(!dao.existeTimeLineAluno(id)){
                JOptionPane.showMessageDialog(null,"Nao há Time Line para esse Estudante");
            }else{
            
                        TimeLineVisualizar tlv = new TimeLineVisualizar(this,true);

                 try {
                     tlv.preencherTabela(id);
                 } catch (SQLException ex) {
                     Logger.getLogger(AlunoListaForm.class.getName()).log(Level.SEVERE, null, ex);
                 }
           
           tlv.setVisible(true);
        }
            }
        else{
            JOptionPane.showMessageDialog(null, "Selecione um Aluno");
        }
        

    }//GEN-LAST:event_btnTimeLineActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(AlunoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunoListaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlunoListaForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_Principal;
    private javax.swing.JComboBox<String> SlctCampo;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btOrdenar;
    private javax.swing.JButton btnDigital;
    private javax.swing.JButton btnDigitalTeste;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnTimeLine;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbBolsa100;
    private javax.swing.JLabel lbBolsa50;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JToggleButton lbFoto;
    private javax.swing.JLabel lbInativa;
    private javax.swing.JTable tblAluno;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JComboBox<String> txtSelecCampo;
    private javax.swing.JComboBox<String> txtSelecOrdem;
    // End of variables declaration//GEN-END:variables

    private void execPesquisar() {
         Integer indexSelect=0;
        String valor="";
        indexSelect=SlctCampo.getSelectedIndex();
        valor=txtPesquisa.getText().trim();
       
               contador=0;
               DefaultTableModel tabela = (DefaultTableModel) tblAluno.getModel();
               tblAluno.setModel(tabela);
               tblAluno.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
               tblAluno.getColumnModel().getColumn(0).setPreferredWidth(80);
              
               tblAluno.getColumnModel().getColumn(1).setPreferredWidth(250);
               
               tblAluno.getColumnModel().getColumn(2).setPreferredWidth(250);
               tblAluno.getColumnModel().getColumn(3).setPreferredWidth(250);
               tblAluno.getColumnModel().getColumn(4).setPreferredWidth(120);
               tblAluno.getColumnModel().getColumn(5).setPreferredWidth(250);
       		tabela.setNumRows(0);
		AlunoDao dao = new AlunoDao();
                 String bolsa=null;
		for (AlunoController u : dao.CarregarPorParamentros(indexSelect, valor)) {
			
		          if(u.getBolsa()==0){
                            bolsa="100%";
                        }else if(u.getBolsa()==1){
                           bolsa="50%"; 
                        }else if(u.getBolsa()==2){
                            bolsa="Inativa";
                        }
			tabela.addRow(new Object[]{
                            u.getIdaluno(),
                            u.getNomealuno(), 
                            imprimeCPF(u.getCpfaluno()), 
                            u.getNomecurso(), 
                            u.getNometurma(),
                            bolsa});
                        contador++;
                }
               
    }
}
