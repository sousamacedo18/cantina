
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Conexao.ConexaoBD;
import Controller.AlunoController;
import Dao.AlunoDao;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Home
 */
public class AlunoEditarForm extends javax.swing.JFrame {
    ConexaoBD conexao = new ConexaoBD();
    AlunoDao alunosdao = new AlunoDao();
    AlunoController alunos = new AlunoController();
    private DefaultTableModel modelo = new DefaultTableModel();
    private Integer contador;
    public Integer idaluno;
    private String novasenha="";
    public int idlogado=0;
    /**
     * Creates new form AlunoEditarForm
     */
    public AlunoEditarForm() {
        initComponents();
        LiberarCampos();
        
//           Insets in = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
//
//            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//
//            int width = d.width-(in.left + in.top);
//            int height = d.height-(in.top + in.bottom);
//            setSize(width,height);
//            
//            setLocation(in.left,in.top);
        this.setLocationRelativeTo(null);
        btnTiraFoto.setForeground(Color.white);
        btnTiraFoto.setBackground(new Color(0,84,140));
        btnEscolherFoto.setForeground(Color.white);
        btnEscolherFoto.setBackground(new Color(0,84,140));
        
    }
    public void capturaFoto(){
        CamCap w = new CamCap(this,true);
        w.cpf=retirarFormatacaoCpf(txtCpfAluno.getText());
        w.setVisible(true);
        txtFotoAluno.setText(lerJson().trim()+w.getArquivo());
        carregarImagem(txtFotoAluno.getText().trim());
        
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
        catch (ParseException ex) {ex.printStackTrace(); }
        catch (Exception ex) {ex.printStackTrace(); }
                return pastafotoaluno;
   }
           public static boolean isCPF(String CPF) {
// considera-se erro CPF's formados por uma sequencia de numeros iguais
    if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
        CPF.equals("22222222222") || CPF.equals("33333333333") ||
        CPF.equals("44444444444") || CPF.equals("55555555555") ||
        CPF.equals("66666666666") || CPF.equals("77777777777") ||
        CPF.equals("88888888888") || CPF.equals("99999999999") ||
       (CPF.length() != 11))
       return(false);

    char dig10, dig11;
    int sm, i, r, num, peso;

// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
    try {
// Calculo do 1o. Digito Verificador
      sm = 0;
      peso = 10;
      for (i=0; i<9; i++) {              
// converte o i-esimo caractere do CPF em um numero:
// por exemplo, transforma o caractere '0' no inteiro 0         
// (48 eh a posicao de '0' na tabela ASCII)         
        num = (int)(CPF.charAt(i) - 48); 
        sm = sm + (num * peso);
        peso = peso - 1;
      }

      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11))
         dig10 = '0';
      else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

// Calculo do 2o. Digito Verificador
      sm = 0;
      peso = 11;
      for(i=0; i<10; i++) {
        num = (int)(CPF.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso - 1;
      }

      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11))
         dig11 = '0';
      else dig11 = (char)(r + 48);

// Verifica se os digitos calculados conferem com os digitos informados.
      if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
         return(true);
      else return(false);
    } catch (InputMismatchException erro) {
        return(false);
    }
  }
    public String retirarFormatacaoCpf(String CPF){
          return(CPF.replaceAll("[.-]", ""));
    }
     public String imprimeCPF(String CPF) {
             if(!CPF.equals(null))  {
                return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
             }
             return null;
    
  }
        public void limparCampos(){
          
            txtNomeAluno.setText("");
            txtEmailAluno.setText("");
            txtCpfAluno.setText("");
            
    }

    public void LerCampos() {
         
         txtId.setEnabled(true);
         txtNomeAluno.setEnabled(false);
         txtEmailAluno.setEnabled(false);
         txtCpfAluno.setEnabled(false);
         btnSalvar.setEnabled(false);
        
        
    }
        public void LiberarCampos() {
      
        txtNomeAluno.setEnabled(true);
        txtEmailAluno.setEnabled(true);
        txtCpfAluno.setEnabled(true);
        txtNomeAluno.requestFocus();
        btnSalvar.setEnabled(true);
        
    }
        public boolean verificarCarmpos(){
        
                if(txtNomeAluno.getText().equals("")
                || txtEmailAluno.getText().equals("") 
                || txtIdCurso.getText().equals("") 
                || txtIdTurma.getText().equals("") 
                || txtCpfAluno.getText().equals("")){
                return true;
        }
       return false; 
    }
     public void SalvarAluno() throws UnsupportedEncodingException{
       File f = new File(txtFotoAluno.getText().trim());
       alunos.setIdaluno(Integer.parseInt(txtId.getText()));
       alunos.setCursoid(Integer.parseInt(txtIdCurso.getText()));
       alunos.setTurmaid(Integer.parseInt(txtIdTurma.getText()));
       alunos.setNomealuno(txtNomeAluno.getText());
       alunos.setCpfaluno(retirarFormatacaoCpf(txtCpfAluno.getText()));
       alunos.setEmailaluno(txtEmailAluno.getText());
       alunos.setBolsa(CbBolsa.getSelectedIndex());
         
       alunos.setFotoaluno(f.getName());
       alunos.setSenhaaluno(novasenha);
        try {
            alunosdao.AtualizarAluno(alunos);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AlunoEditarForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    public void editarCampos(Integer id){
        alunos.setIdaluno(id);
      
       AlunoDao dao = new AlunoDao();
      for (AlunoController u : dao.CarregarAluno(id)) {
        txtId.setText(Integer.toString(u.getIdaluno()));
        txtIdCurso.setText(Integer.toString(u.getCursoid()));
        txtIdTurma.setText(Integer.toString(u.getTurmaid()));
        txtNomeAluno.setText(u.getNomealuno());
        txtNomeCurso.setText(u.getNomecurso());
        txtNomeTurma.setText(u.getNometurma());
        CbBolsa.setSelectedIndex(u.getBolsa());
          
        txtCpfAluno.setText(imprimeCPF(u.getCpfaluno()));
        txtEmailAluno.setText(u.getEmailaluno());
        txtFotoAluno.setText(lerJson().trim()+u.getFotoaluno().trim());
        carregarImagem(lerJson().trim()+u.getFotoaluno().trim());
    }
    txtNomeAluno.requestFocus();
    }
         public void carregarImagem(String caminho){
         ImageIcon img = new ImageIcon(caminho);
          lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(lbFoto.getHeight(), lbFoto.getWidth(), Image.SCALE_DEFAULT)));     
        }
        public void tirarfoto(){    
               String cpf=txtCpfAluno.getText();
               String nomeaquivo="";
               cpf = cpf.replaceAll(" ","");
               cpf=cpf.trim();
        
                    //JOptionPane.showMessageDialog(null, matricula);
          if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        }else{
               CamCap webcamform =  new CamCap(this,true);
               //JOptionPane.showMessageDialog(null, "webcam acionada");
//               webcamform.setArquivo(retirarFormatacaoCpf(cpf));
               webcamform.setVisible(true);
               if(webcamform.getRetorno()==1){
               String caminho=webcamform.getCaminhofoto();
               nomeaquivo=(new File(txtFotoAluno.getText()).getName()+"jpg");
               carregarImagem(caminho);
               txtFotoAluno.setText(caminho);
               btnSalvar.requestFocus();
               }else{
                   btnTiraFoto.requestFocus();
               }
          } 
        }
        public void pesquisarFormCurso(){
        PesquisarCursoForm pesqcurso = new PesquisarCursoForm(this,true);
        pesqcurso.setVisible(true);
        int ret=pesqcurso.getRetorno();
        if(ret==1){
        int idc=pesqcurso.getIdcurso();
        String nome=pesqcurso.getNomecurso();
        txtIdCurso.setText(Integer.toString(idc));
        txtNomeCurso.setText(nome);
        btnGetTurma.setEnabled(true);
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

        JP_Principal = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        lbNomeOriginal = new javax.swing.JLabel();
        lbNomeOriginal1 = new javax.swing.JLabel();
        txtEmailAluno = new javax.swing.JTextField();
        txtNomeAluno = new javax.swing.JTextField();
        txtCpfAluno = new javax.swing.JTextField();
        lbNomeOriginal2 = new javax.swing.JLabel();
        lbNomeOriginal3 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtFotoAluno = new javax.swing.JTextField();
        lbNomeOriginal5 = new javax.swing.JLabel();
        lbNomeOriginal4 = new javax.swing.JLabel();
        txtIdCurso = new javax.swing.JTextField();
        txtIdTurma = new javax.swing.JTextField();
        txtNomeCurso = new javax.swing.JTextField();
        txtNomeTurma = new javax.swing.JTextField();
        btnGetCurso = new javax.swing.JButton();
        btnGetTurma = new javax.swing.JButton();
        btnAlterarSenha = new javax.swing.JButton();
        CbBolsa = new javax.swing.JComboBox<>();
        lbNome1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lbTitulo1 = new javax.swing.JLabel();
        lbFechar = new javax.swing.JLabel();
        btnTiraFoto = new javax.swing.JButton();
        btnEscolherFoto = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        lbFoto = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        JP_Principal.setBackground(new java.awt.Color(60, 77, 87));
        JP_Principal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(50, 168, 82), 4, true));

        lbNome.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNome.setForeground(new java.awt.Color(255, 255, 255));
        lbNome.setText("CPF");

        lbNomeOriginal.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal.setText("Email");

        lbNomeOriginal1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal1.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal1.setText("Código");

        txtEmailAluno.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtEmailAluno.setForeground(new java.awt.Color(102, 102, 102));
        txtEmailAluno.setEnabled(false);

        txtNomeAluno.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtNomeAluno.setForeground(new java.awt.Color(102, 102, 102));
        txtNomeAluno.setEnabled(false);
        txtNomeAluno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeAlunoKeyPressed(evt);
            }
        });

        txtCpfAluno.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtCpfAluno.setForeground(new java.awt.Color(102, 102, 102));
        txtCpfAluno.setEnabled(false);
        txtCpfAluno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCpfAlunoFocusGained(evt);
            }
        });
        txtCpfAluno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCpfAlunoKeyPressed(evt);
            }
        });

        lbNomeOriginal2.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal2.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal2.setText("Foto");

        lbNomeOriginal3.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal3.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal3.setText("Nome");

        txtId.setEditable(false);
        txtId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtId.setEnabled(false);

        txtFotoAluno.setEditable(false);
        txtFotoAluno.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtFotoAluno.setForeground(new java.awt.Color(102, 102, 102));
        txtFotoAluno.setEnabled(false);

        lbNomeOriginal5.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal5.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal5.setText("Curso");

        lbNomeOriginal4.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal4.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal4.setText("Turma");

        txtIdCurso.setEditable(false);
        txtIdCurso.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtIdCurso.setForeground(new java.awt.Color(102, 102, 102));

        txtIdTurma.setEditable(false);
        txtIdTurma.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtIdTurma.setForeground(new java.awt.Color(102, 102, 102));

        txtNomeCurso.setEditable(false);
        txtNomeCurso.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtNomeCurso.setForeground(new java.awt.Color(102, 102, 102));

        txtNomeTurma.setEditable(false);
        txtNomeTurma.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtNomeTurma.setForeground(new java.awt.Color(102, 102, 102));

        btnGetCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_add_370092.png"))); // NOI18N
        btnGetCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetCursoActionPerformed(evt);
            }
        });

        btnGetTurma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_add_370092.png"))); // NOI18N
        btnGetTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetTurmaActionPerformed(evt);
            }
        });

        btnAlterarSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_Line_ui_icons_Svg-16_1465830.png"))); // NOI18N
        btnAlterarSenha.setText("Alterar Senha");
        btnAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarSenhaActionPerformed(evt);
            }
        });

        CbBolsa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CbBolsa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bolsa 100%", "Bolsa 50%", "Inativa" }));
        CbBolsa.setToolTipText("");
        CbBolsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CbBolsaKeyPressed(evt);
            }
        });

        lbNome1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNome1.setForeground(new java.awt.Color(255, 255, 255));
        lbNome1.setText("Tipo Bolsa");

        jPanel5.setBackground(new java.awt.Color(50, 168, 82));

        lbTitulo1.setBackground(new java.awt.Color(255, 255, 255));
        lbTitulo1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 24)); // NOI18N
        lbTitulo1.setForeground(new java.awt.Color(255, 255, 255));
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTitulo1.setText("Alterar Cadastro de Aluno");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 647, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTitulo1)
                    .addComponent(lbFechar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTiraFoto.setBackground(new java.awt.Color(0, 84, 140));
        btnTiraFoto.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        btnTiraFoto.setText("Acessar WebCam");
        btnTiraFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiraFotoActionPerformed(evt);
            }
        });

        btnEscolherFoto.setBackground(new java.awt.Color(50, 168, 82));
        btnEscolherFoto.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        btnEscolherFoto.setText("Escolher Foto");
        btnEscolherFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolherFotoActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(235, 114, 21));
        btnSalvar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setText("Salvar");
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

        javax.swing.GroupLayout JP_PrincipalLayout = new javax.swing.GroupLayout(JP_Principal);
        JP_Principal.setLayout(JP_PrincipalLayout);
        JP_PrincipalLayout.setHorizontalGroup(
            JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNomeOriginal3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbNome, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbNomeOriginal5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbNomeOriginal4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeAluno)
                            .addComponent(txtCpfAluno)
                            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbNome1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtIdTurma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                                .addComponent(txtIdCurso, javax.swing.GroupLayout.Alignment.LEADING)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CbBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                                    .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNomeCurso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtNomeTurma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnGetCurso)
                                                        .addComponent(btnGetTurma)))))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                .addComponent(btnTiraFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEscolherFoto)))
                        .addGap(42, 42, 42))
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnAlterarSenha)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lbNomeOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmailAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(lbNomeOriginal2)
                                .addGap(11, 11, 11)
                                .addComponent(txtFotoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        JP_PrincipalLayout.setVerticalGroup(
            JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomeAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNomeOriginal3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JP_PrincipalLayout.createSequentialGroup()
                                .addComponent(lbNome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11))
                            .addComponent(txtCpfAluno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbNomeOriginal5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(lbNomeOriginal4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JP_PrincipalLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGetCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdCurso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNomeCurso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGetTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNomeTurma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(5, 5, 5)
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CbBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEscolherFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTiraFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(7, 7, 7)
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNomeOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFotoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(JP_PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAlterarSenha))
                    .addGroup(JP_PrincipalLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP_Principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        }else{
            try {
                SalvarAluno();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AlunoEditarForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            AlunoListaForm alunolista =  new AlunoListaForm();
            alunolista.idlogado=idlogado;
            alunolista.setVisible(true);
            this.dispose();

        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnTiraFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiraFotoActionPerformed
//        File arquivo = new File(txtFotoAluno.getText().trim());
//        String cpf="";
//        if(txtCpfAluno.getText().isEmpty()){
//            JOptionPane.showMessageDialog(null, "Campo cpf está vazio, é preciso preenche-lo para nomear a foto");
//        }else{
//               cpf=retirarFormatacaoCpf(txtCpfAluno.getText());
//                    if(arquivo.exists()){
//                        int result = JOptionPane.showConfirmDialog(null,"A foto deste aluno será excluída, deseja continuar? ","Foto",JOptionPane.YES_NO_CANCEL_OPTION);   
//
//                            if(result==JOptionPane.YES_OPTION)  
//                              System.out.println("a foto será excluída foto");
//                                            Webcam w = new Webcam(this,true);
//                                            w.setArquivo(cpf);
//                                            w.setVisible(true);
//                                            txtFotoAluno.setText(w.getCaminhofoto());
//                                            carregarImagem(w.getCaminhofoto());
//
//                            }else{
//                            Webcam w = new Webcam(this,true);
//                            w.setArquivo(cpf);
//                            w.setVisible(true);
//                            txtFotoAluno.setText(w.getCaminhofoto());
//                            carregarImagem(w.getCaminhofoto());
//                    }
//        }
if(txtCpfAluno.getText().equals(""))
{
 JOptionPane.showMessageDialog(null, "preencha o campo CPF");
}else{
capturaFoto();
}
    }//GEN-LAST:event_btnTiraFotoActionPerformed

    private void btnEscolherFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolherFotoActionPerformed
        // TODO add your handling code here:
        try{
            File f = new File(lerJson());
            String s = f.getAbsolutePath();
            //JOptionPane.showMessageDialog(null, s);
            JFileChooser buscarfoto= new JFileChooser();
            buscarfoto.setCurrentDirectory(new File(s));
            buscarfoto.setDialogTitle("Carregar Foto do Aluno");
            buscarfoto.showOpenDialog(this);
            String dir = System.getProperty("user_dir");
            String foto = buscarfoto.getSelectedFile().getAbsolutePath();
            // JOptionPane.showMessageDialog(null, dir);
            //String foto = " "+buscarfoto.getSelectedFile().getName();
            txtFotoAluno.setText(foto);
            ImageIcon img = new ImageIcon(foto);
            //  lbFoto.setIcon(new ImageIcon(Image.getScaledInstance(lbFoto.getWidth(),lbFoto.getHeight(), Image.SCALE_DEFAULT)));
            lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(215, 205, Image.SCALE_DEFAULT)));
            //lbFoto.setIcon(img);

        }
        catch (Exception erro){

        }
    }//GEN-LAST:event_btnEscolherFotoActionPerformed

    private void btnGetCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetCursoActionPerformed
        // TODO add your handling code here:
        if(txtIdTurma.getText().equals("") && txtNomeTurma.getText().equals("")){
            pesquisarFormCurso();
        }
        else{
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult =JOptionPane.showConfirmDialog (null, "Deseja mudar de curso?","Atenção",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null, "Nova turma deverá ser pesquisada referente ao curso escolhido");
                txtIdTurma.setText("");
                txtNomeTurma.setText("");
                pesquisarFormCurso();
            }
        }
    }//GEN-LAST:event_btnGetCursoActionPerformed

    private void btnGetTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetTurmaActionPerformed
        // TODO add your handling code here:
        PesquisarTurmaForm pesqturma = new PesquisarTurmaForm(this,true);
        int id=Integer.parseInt(txtIdCurso.getText());
        pesqturma.carregarTudo(id,"%%");
        pesqturma.setVisible(true);
        int ret=pesqturma.getRetorno();
        if(ret==1){
            int idc=pesqturma.getIdturma();
            String nome=pesqturma.getNometurma();
            txtIdTurma.setText(Integer.toString(idc));
            txtNomeTurma.setText(nome);
            txtEmailAluno.requestFocus();
        }
    }//GEN-LAST:event_btnGetTurmaActionPerformed

    private void btnAlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarSenhaActionPerformed
        // TODO add your handling code here:
        TrocarSenha troca = new TrocarSenha(this, true);
        try {
            troca.setarSenhaAtual(Integer.parseInt(txtId.getText()));
        } catch (SQLException ex) {
            Logger.getLogger(AlunoEditarForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        troca.setVisible(true);
        int retorno=troca.getRetorno();
        if(retorno==1){
            novasenha=troca.getSenhanova();
        }
    }//GEN-LAST:event_btnAlterarSenhaActionPerformed

    private void txtNomeAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeAlunoKeyPressed
                      if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        txtCpfAluno.requestFocus();
                    }
    }//GEN-LAST:event_txtNomeAlunoKeyPressed

    private void txtCpfAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfAlunoKeyPressed
                                     
                      if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                          if(txtCpfAluno.getText().trim().length()==11){
                                 if(isCPF(txtCpfAluno.getText().trim())==true){
                                    txtCpfAluno.setText(imprimeCPF(txtCpfAluno.getText()));
                                    btnGetCurso.requestFocus();
                    }else{
                        JOptionPane.showMessageDialog(null,"Cpf inválido!!");

                    }
                          }
                   
               }
    }//GEN-LAST:event_txtCpfAlunoKeyPressed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
               if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        }else{
            try {
                SalvarAluno();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AlunoEditarForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            AlunoListaForm alunolista =  new AlunoListaForm();
            alunolista.setVisible(true);
            this.dispose();

        }
               }
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void txtCpfAlunoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCpfAlunoFocusGained
        // TODO add your handling code here:
                String s="";
            s=retirarFormatacaoCpf(txtCpfAluno.getText());
            txtCpfAluno.setText(s);
    }//GEN-LAST:event_txtCpfAlunoFocusGained

    private void CbBolsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CbBolsaKeyPressed
     
        
    }//GEN-LAST:event_CbBolsaKeyPressed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        limparCampos();
        AlunoListaForm alunolista =  new AlunoListaForm();
        alunolista.setVisible(true);
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
            java.util.logging.Logger.getLogger(AlunoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunoEditarForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlunoEditarForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CbBolsa;
    private javax.swing.JPanel JP_Principal;
    private javax.swing.JButton btnAlterarSenha;
    private javax.swing.JButton btnEscolherFoto;
    private javax.swing.JButton btnGetCurso;
    private javax.swing.JButton btnGetTurma;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnTiraFoto;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbFechar;
    private javax.swing.JToggleButton lbFoto;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbNome1;
    private javax.swing.JLabel lbNomeOriginal;
    private javax.swing.JLabel lbNomeOriginal1;
    private javax.swing.JLabel lbNomeOriginal2;
    private javax.swing.JLabel lbNomeOriginal3;
    private javax.swing.JLabel lbNomeOriginal4;
    private javax.swing.JLabel lbNomeOriginal5;
    private javax.swing.JLabel lbTitulo1;
    private javax.swing.JTextField txtCpfAluno;
    private javax.swing.JTextField txtEmailAluno;
    private javax.swing.JTextField txtFotoAluno;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdCurso;
    private javax.swing.JTextField txtIdTurma;
    private javax.swing.JTextField txtNomeAluno;
    private javax.swing.JTextField txtNomeCurso;
    private javax.swing.JTextField txtNomeTurma;
    // End of variables declaration//GEN-END:variables
}
