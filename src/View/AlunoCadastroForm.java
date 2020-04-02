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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.MatteBorder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




/**
 *
 * @author Home
 */
public class AlunoCadastroForm extends javax.swing.JFrame{

    ConexaoBD conexao = new ConexaoBD();
    AlunoDao alunosdao = new AlunoDao();
    AlunoController alunos = new AlunoController();
    public Integer idlogado;
    private DefaultTableModel modelo = new DefaultTableModel();
    ArrayList<Integer> codigosCurso;
    ArrayList<Integer> codigosTurma;
    public String caminhofoto;
    
    
//     public ArrayList<Integer> carregarCurso(){
//                                jcbxCurso.removeAllItems();
//                                 CursoDao dao = new CursoDao();
//                                  ArrayList<Integer> arr = new ArrayList<>();
//                                 for (CursoController u : dao.CarregarCursoTodos()) {
//                                         jcbxCurso.addItem(u);
//                                         arr.add(u.getIdcurso());
//                                
//                                 }
//      return arr;                                 
//     }
//     public ArrayList<Integer> carregarTurma(Integer idcurso){
//                                jcbxTurma.removeAllItems();
//                                 TurmaDao dao = new TurmaDao();
//                                  ArrayList<Integer> arr = new ArrayList<>();
//                                 for (TurmaController u : dao.CarregarTurmaIdCurso(idcurso)) {
//                                         jcbxTurma.addItem(u);
//                                         arr.add(u.getIdturma());
//                                
//                                 }
//      return arr;                                 
//     }
//     
//     public Integer pegarIdCurso(){
//         int id=0;
//         id=codigosCurso.get(jcbxCurso.getSelectedIndex());
//         
//         return id;
//     } 
//     
//     public Integer pegarIdTurma(){
//         int id=0;
//         id=codigosTurma.get(jcbxTurma.getSelectedIndex());
//         
//         return id;
//     } 
        public void capturaFoto(){
        CamCap w = new CamCap(this,true);
        w.cpf=retirarFormatacaoCpf(txtCpfAluno.getText());
        w.setVisible(true);
        txtFotoAluno.setText(lerJson().trim()+w.getArquivo());
        carregarImagem(txtFotoAluno.getText().trim());
        
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
  public String lerCaminho(){
       String arq = "conf.txt";
       String texto=Arquivo.Read(arq);
       if(texto.isEmpty()){
           JOptionPane.showMessageDialog(null, "Não foi possível ler o caminho guardado");
       }
  
   return texto;
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
    return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
      CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
  }

    public boolean verificarCarmpos(){
        
        if(txtNomeAluno.getText().equals("")|| txtEmailAluno.getText().equals("") || txtIdCurso.getText().equals("")
                || txtIdTurma.getText().equals("")
                || txtCpfAluno.getText().equals("") ) {
            
           return true;
        }
        return false;
    }
    

    public void SalvarAluno() throws UnsupportedEncodingException{
       File f = new File(txtFotoAluno.getText().trim());
       
       alunos.setNomealuno(txtNomeAluno.getText().toUpperCase().trim());
       alunos.setEmailaluno(txtEmailAluno.getText().toLowerCase().trim());
       alunos.setCpfaluno(retirarFormatacaoCpf(txtCpfAluno.getText().trim()));
       alunos.setSenhaaluno(txtSenha.getText());
       alunos.setCursoid(Integer.parseInt(txtIdCurso.getText()));
       alunos.setTurmaid(Integer.parseInt(txtIdTurma.getText()));
       alunos.setBolsa(CbBolsa.getSelectedIndex());
       alunos.setUsuid(idlogado);
       if(f.exists()){
                alunos.setFotoaluno(f.getName());
        }else{
           alunos.setFotoaluno("sem-foto.png");
       }
       //alunos.setSenhausuario(txtSenhaUsuario.getText());
       AlunoDao aludao = new AlunoDao();
       if(aludao.existeAluno(retirarFormatacaoCpf(txtCpfAluno.getText().trim()))==true){
           JOptionPane.showMessageDialog(null, "Ops! já foi cadastrado um aluno para este CPF!!!");
       }else{
        alunosdao.SalvarAluno(alunos);
         JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Deseja capturar as digitais deste(a) aluno(a)?", "Confirmar",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
                 this.dispose();
                capturarDigitais();
        }else{
            this.dispose();
        }
AlunoListaForm a = new AlunoListaForm();
a.idlogado=idlogado;
this.dispose();
a.setVisible(true);
        }
    }
    public void limparCampos(){
          
            txtNomeAluno.setText("");
            txtCpfAluno.setText("");
            txtEmailAluno.setText("");
            txtFotoAluno.setText("");
    }
    public void LerCampos() {
         
         txtNomeAluno.setEnabled(false);
         txtCpfAluno.setEnabled(false);
         txtEmailAluno.setEnabled(false);
         txtFotoAluno.setEnabled(false);
         btnSalvar.setEnabled(false);
         //btnCancelar.setEnabled(false);
         
    }
        public void LiberarCampos() {
      
        txtNomeAluno.setEnabled(true);
        txtCpfAluno.setEnabled(true);
        txtEmailAluno.setEnabled(true);
        txtFotoAluno.setEnabled(true);
        txtNomeAluno.requestFocus();
        btnSalvar.setEnabled(true);
        //btnCancelar.setEnabled(true);
        

    }
        public void carregarImagem(String caminho){
         ImageIcon img = new ImageIcon(caminho);
          lbFoto.setIcon(new ImageIcon(img.getImage().getScaledInstance(215, 205, Image.SCALE_DEFAULT)));     
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
     * Creates new form AlunoCadastroForm
     */
    public AlunoCadastroForm() {
       super();
       initComponents();
       txtNomeAluno.setEnabled(true);
       txtEmailAluno.setEnabled(true);
       txtCpfAluno.setEnabled(true);
       txtFotoAluno.setEnabled(true);
//           Insets in = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
//
//            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//
//            int width = d.width-(in.left + in.top);
//            int height = d.height-(in.top + in.bottom);
//            setSize(width,height);
//            setLocation(in.left,in.top);
       this.setLocationRelativeTo(null);
//       this.carregarCurso();      
       //this.setExtendedState( MAXIMIZED_BOTH );
       btnGetTurma.setEnabled(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        lbNomeOriginal = new javax.swing.JLabel();
        lbNomeOriginal1 = new javax.swing.JLabel();
        txtEmailAluno = new javax.swing.JTextField();
        txtNomeAluno = new javax.swing.JTextField();
        txtCpfAluno = new javax.swing.JTextField();
        lbNomeOriginal2 = new javax.swing.JLabel();
        txtFotoAluno = new javax.swing.JTextField();
        lbNomeOriginal3 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        lbNomeOriginal4 = new javax.swing.JLabel();
        lbNomeOriginal5 = new javax.swing.JLabel();
        txtNomeCurso = new javax.swing.JTextField();
        txtIdCurso = new javax.swing.JTextField();
        txtIdTurma = new javax.swing.JTextField();
        txtNomeTurma = new javax.swing.JTextField();
        btnGetCurso = new javax.swing.JButton();
        btnGetTurma = new javax.swing.JButton();
        CbBolsa = new javax.swing.JComboBox<>();
        lbNome1 = new javax.swing.JLabel();
        lbFoto = new javax.swing.JToggleButton();
        btnTiraFoto = new javax.swing.JButton();
        btnEscolherFoto = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lbFechar = new javax.swing.JLabel();
        lbTitulo1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Aluno");
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(60, 77, 87));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(50, 168, 82), 4, true));

        lbNome.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNome.setForeground(new java.awt.Color(255, 255, 255));
        lbNome.setText("CPF");

        lbNomeOriginal.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal.setText("Senha");

        lbNomeOriginal1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal1.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal1.setText("Nome");

        txtEmailAluno.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtEmailAluno.setForeground(new java.awt.Color(102, 102, 102));
        txtEmailAluno.setEnabled(false);
        txtEmailAluno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailAlunoKeyPressed(evt);
            }
        });

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
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCpfAlunoFocusLost(evt);
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

        txtFotoAluno.setEditable(false);
        txtFotoAluno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFotoAluno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFotoAlunoFocusGained(evt);
            }
        });
        txtFotoAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFotoAlunoActionPerformed(evt);
            }
        });
        txtFotoAluno.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFotoAlunoPropertyChange(evt);
            }
        });

        lbNomeOriginal3.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal3.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal3.setText("Turma");

        txtSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbNomeOriginal4.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal4.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal4.setText("Email");

        lbNomeOriginal5.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNomeOriginal5.setForeground(new java.awt.Color(255, 255, 255));
        lbNomeOriginal5.setText("Curso");

        txtNomeCurso.setEditable(false);
        txtNomeCurso.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtNomeCurso.setForeground(new java.awt.Color(102, 102, 102));

        txtIdCurso.setEditable(false);
        txtIdCurso.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtIdCurso.setForeground(new java.awt.Color(102, 102, 102));

        txtIdTurma.setEditable(false);
        txtIdTurma.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtIdTurma.setForeground(new java.awt.Color(102, 102, 102));

        txtNomeTurma.setEditable(false);
        txtNomeTurma.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 0, 14)); // NOI18N
        txtNomeTurma.setForeground(new java.awt.Color(102, 102, 102));
        txtNomeTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeTurmaActionPerformed(evt);
            }
        });

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

        CbBolsa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CbBolsa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bolsa 100%", "Bolsa 50%", "Inativa", "" }));
        CbBolsa.setToolTipText("");
        CbBolsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbBolsaActionPerformed(evt);
            }
        });
        CbBolsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CbBolsaKeyPressed(evt);
            }
        });

        lbNome1.setFont(new java.awt.Font("Source Sans Pro ExtraLight", 1, 14)); // NOI18N
        lbNome1.setForeground(new java.awt.Color(255, 255, 255));
        lbNome1.setText("Tipo Bolsa");

        btnTiraFoto.setBackground(new java.awt.Color(0, 84, 140));
        btnTiraFoto.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        btnTiraFoto.setForeground(new java.awt.Color(255, 255, 255));
        btnTiraFoto.setText("Acessar WebCam");
        btnTiraFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiraFotoActionPerformed(evt);
            }
        });

        btnEscolherFoto.setBackground(new java.awt.Color(50, 168, 82));
        btnEscolherFoto.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        btnEscolherFoto.setForeground(new java.awt.Color(255, 255, 255));
        btnEscolherFoto.setText("Escolher Foto");
        btnEscolherFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolherFotoActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(255, 255, 0));
        btnSalvar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setName(""); // NOI18N
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

        jPanel5.setBackground(new java.awt.Color(50, 168, 82));

        lbFechar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
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

        lbTitulo1.setBackground(new java.awt.Color(204, 255, 255));
        lbTitulo1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        lbTitulo1.setForeground(new java.awt.Color(255, 255, 255));
        lbTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTitulo1.setText("Cadastro de Estudante");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitulo1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(lbFechar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbNome1)
                                    .addComponent(lbNomeOriginal)))
                            .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(CbBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtFotoAluno)
                            .addComponent(txtSenha)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(lbNomeOriginal3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(lbNomeOriginal5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtIdCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNomeTurma, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                                    .addComponent(txtNomeCurso))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnGetCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGetTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(12, 12, 12)
                                            .addComponent(lbNome))
                                        .addComponent(lbNomeOriginal1, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNomeAluno)
                                        .addComponent(txtCpfAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbNomeOriginal4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtEmailAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnTiraFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEscolherFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(151, 151, 151)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGetCurso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtIdCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNomeCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNomeTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtIdTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbNomeOriginal3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnGetTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CbBolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNomeAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbNomeOriginal1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCpfAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbNome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbNomeOriginal4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmailAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbNomeOriginal5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNomeOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFotoAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbNomeOriginal2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTiraFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEscolherFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
public static String rtrim(String toTrim) {
    char[] val = toTrim.toCharArray();
    int len = val.length;

    while (len > 0 && val[len - 1] <= ' ') {
        len--;
    }
    return len < val.length ? toTrim.substring(0, len) : toTrim;
}

public void tirarfoto(){
                   String matricula=txtCpfAluno.getText();
               String nomeaquivo="";
               matricula = matricula.replaceAll(" ","");
               matricula=matricula.trim();
        
                    
          if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        }else{
//               Webcam webcamform =  new Webcam(this,true);
//               webcamform.setArquivo(matricula);
//               webcamform.setVisible(true);
//               String caminho=webcamform.getCaminhofoto();
//               txtFotoAluno.setText(caminho);
//               carregarImagem(caminho);
//               btnSalvar.requestFocus();
               CamCap webcamform =  new CamCap(this,true);
               //JOptionPane.showMessageDialog(null, "webcam acionada");
//               webcamform.setArquivo(matricula);
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
public static String ltrim(String toTrim) {
    int st = 0;
    char[] val = toTrim.toCharArray();
    int len = val.length;

    while (st < len && val[st] <= ' ') {
        st++;
    }
    return st > 0 ? toTrim.substring(st, len) : toTrim;
}
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String cpf="";
        cpf=retirarFormatacaoCpf(txtCpfAluno.getText().trim());
        if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        }else if (isCPF(cpf)){
           
           
            try {
                SalvarAluno();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AlunoCadastroForm.class.getName()).log(Level.SEVERE, null, ex);
            }
//            AlunoListaForm alunolista =  new AlunoListaForm();
//            alunolista.setVisible(true);
            

        }else{
            JOptionPane.showMessageDialog(null,"CPF INVALIDO!!!!");
            txtCpfAluno.requestFocus();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtFotoAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFotoAlunoActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtFotoAlunoActionPerformed

    private void btnEscolherFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolherFotoActionPerformed
        // TODO add your handling code here:
                try{
            File f = new File("fotoaluno");
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

    private void btnTiraFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiraFotoActionPerformed
        String cpf="";
        File arquivo = new File(txtFotoAluno.getText().trim());
        cpf=retirarFormatacaoCpf(txtCpfAluno.getText());
        
       AlunoDao dao = new AlunoDao();
               if(txtCpfAluno.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Campo cpf está vazio, é preciso preenche-lo para nomear a foto");
        }else if(isCPF(cpf)){
                   
                       
                    if(dao.existeAluno(cpf)){
                                JOptionPane.showMessageDialog(null,"CPF cadastrado no banco de dados");
                    }else{
        

               
                    if(arquivo.exists()){
                        int result = JOptionPane.showConfirmDialog(null,"A foto deste aluno será excluída, deseja continuar? ","Foto",JOptionPane.YES_NO_CANCEL_OPTION);   

                            if(result==JOptionPane.YES_OPTION)  
                              System.out.println("a foto será excluída foto");
//                                            Webcam w = new Webcam(this,true);
//                                            w.setArquivo(cpf);
//                                            w.setVisible(true);
//                                            txtFotoAluno.setText(w.getCaminhofoto());
//                                            carregarImagem(w.getCaminhofoto());
                                    capturaFoto();

                            }else{
//                            Webcam w = new Webcam(this,true);
//                            w.setArquivo(cpf);
//                            w.setVisible(true);
//                            txtFotoAluno.setText(w.getCaminhofoto());
//                            carregarImagem(w.getCaminhofoto());

                                capturaFoto();

                    }
        }
       }else{
                   JOptionPane.showMessageDialog(null,"CPF invalido");
                   txtCpfAluno.requestFocus();
        }
    }//GEN-LAST:event_btnTiraFotoActionPerformed

    private void txtFotoAlunoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFotoAlunoPropertyChange

    }//GEN-LAST:event_txtFotoAlunoPropertyChange

    private void txtFotoAlunoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFotoAlunoFocusGained

       
    }//GEN-LAST:event_txtFotoAlunoFocusGained

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
        txtSenha.requestFocus();
        }
    }//GEN-LAST:event_btnGetTurmaActionPerformed

    private void txtNomeAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeAlunoKeyPressed
              if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                   txtCpfAluno.requestFocus();
               }
    }//GEN-LAST:event_txtNomeAlunoKeyPressed

    private void txtCpfAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfAlunoKeyPressed
                      if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                          if(txtCpfAluno.getText().trim().length()==11){
                                      String cpf="";
                                        cpf=retirarFormatacaoCpf(txtCpfAluno.getText());
                                       AlunoDao dao = new AlunoDao();
                                       if(dao.existeAluno(cpf)){
                                           txtCpfAluno.requestFocus();
                                       }else{
                                 if(isCPF(txtCpfAluno.getText().trim())==true){
                                    txtCpfAluno.setText(imprimeCPF(txtCpfAluno.getText()));
                                    txtEmailAluno.requestFocus();
                                        } else{
                        JOptionPane.showMessageDialog(null,"Cpf inválido!!");

                    }
                                       }
                          }else{
                        JOptionPane.showMessageDialog(null,"Cpf inválido!!");

                    }
                   
               }
    }//GEN-LAST:event_txtCpfAlunoKeyPressed

    private void txtEmailAlunoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailAlunoKeyPressed
               if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                   btnGetCurso.requestFocus();
               }
    }//GEN-LAST:event_txtEmailAlunoKeyPressed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
               if(verificarCarmpos()){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos");
        }else{
            try {
                SalvarAluno();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AlunoCadastroForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            AlunoListaForm alunolista =  new AlunoListaForm();
            alunolista.setVisible(true);
            this.dispose();

        }
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void txtCpfAlunoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCpfAlunoFocusGained
        // TODO add your handling code here:
        String s="";
            s=retirarFormatacaoCpf(txtCpfAluno.getText());
            txtCpfAluno.setText(s);
        

    }//GEN-LAST:event_txtCpfAlunoFocusGained

    private void txtCpfAlunoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCpfAlunoFocusLost
        // TODO add your handling code here:
//        if(isCPF(txtCpfAluno.getText().trim())==true){
//            imprimeCPF(txtCpfAluno.getText());
//        }else{
//            JOptionPane.showMessageDialog(null,"Cpf inválido!!");
//            
//        }
    }//GEN-LAST:event_txtCpfAlunoFocusLost

    private void CbBolsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CbBolsaKeyPressed
  
        
    }//GEN-LAST:event_CbBolsaKeyPressed

    private void txtNomeTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeTurmaActionPerformed
        // TODO add your handling code here:
     
        
    }//GEN-LAST:event_txtNomeTurmaActionPerformed

    private void CbBolsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbBolsaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbBolsaActionPerformed

    private void lbFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseClicked
        this.dispose();
        AlunoListaForm alunoform = new AlunoListaForm();
        //            JOptionPane.showMessageDialog(null,"já está aberto");
            alunoform.setExtendedState(alunoform.MAXIMIZED_BOTH);
//            JOptionPane.showMessageDialog(null,"A janela está aberta, verifique no radapé");
                                  
                        alunoform.idlogado=idlogado;

                        alunoform.setVisible(true);
    }//GEN-LAST:event_lbFecharMouseClicked

    private void lbFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseEntered
//        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white);
//        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.white);
    }//GEN-LAST:event_lbFecharMouseEntered

    private void lbFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFecharMouseExited
//        MatteBorder label_border = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black);
//        lbFechar.setBorder(label_border);
        lbFechar.setForeground(Color.black);
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
            java.util.logging.Logger.getLogger(AlunoCadastroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunoCadastroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunoCadastroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunoCadastroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlunoCadastroForm().setVisible(true);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CbBolsa;
    private javax.swing.JButton btnEscolherFoto;
    private javax.swing.JButton btnGetCurso;
    private javax.swing.JButton btnGetTurma;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnTiraFoto;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JTextField txtIdCurso;
    private javax.swing.JTextField txtIdTurma;
    private javax.swing.JTextField txtNomeAluno;
    private javax.swing.JTextField txtNomeCurso;
    private javax.swing.JTextField txtNomeTurma;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables

    private void salvarCredito() {
        CreditoController credcontrole= new CreditoController();
        CreditoDao creddao = new CreditoDao();
        String id="";
        Integer idaluno=0;
        id=JOptionPane.showInputDialog(null, "Forneça o ID como modelo para geras os créditos?", "Atenção", JOptionPane.PLAIN_MESSAGE);
        
        if(!creddao.existeCredito(Integer.parseInt(id))){
            
        
        try
         {
      	    int idcredito = Integer.parseInt(id); //Converte a string para inteiro
            if(idcredito>0){
                      try {
                          idaluno=alunosdao.ultimoAlunoSalvo();
                          for (CreditoController u : creddao.CarregarCredito(idcredito)) {
                              credcontrole.setIdcredaluno(idaluno);
                              credcontrole.setTipobolsa(u.getTipobolsa());
                              credcontrole.setTotalcreditos(u.getTotalcreditos());
                              credcontrole.setDatainicial(u.getDatainicial());
                              credcontrole.setDatafinal(u.getDatafinal());
                              credcontrole.setCreditodia(u.getCreditodia());
                              credcontrole.setBloquealuno(u.getBloquealuno());
                              credcontrole.setControle(u.getControle());
                              credcontrole.setUsuid(idlogado);
                          }
                          creddao.SalvarCreditoAutomatico(credcontrole);
                          this.dispose();
                          
                      } catch (SQLException ex) {
                          Logger.getLogger(AlunoCadastroForm.class.getName()).log(Level.SEVERE, null, ex);
                      }
            }
                     
         }
         catch (NumberFormatException nfe) //Se naum for um numero inteiro entrará aqui
         {
      	    JOptionPane.showMessageDialog(null, "Não é um numero válido!!!!!");
         }
        }

        
//             JDialog.setDefaultLookAndFeelDecorated(true);
//    int response = JOptionPane.showConfirmDialog(null, "Deseja Realmente Excluir Esse Registro?", "Confirm",
//        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                        if (response == JOptionPane.YES_OPTION) {  
//
//                        }
    }

    private void capturarDigitais() {
                     int linhaSelecionada = -1;
//             linhaSelecionada = tblAluno.getSelectedRow();
//        if (linhaSelecionada>=0){
//            int id=(int)tblAluno.getValueAt(linhaSelecionada, 0);
              CapturarDigital digital = null;
                 try {
                      digital = new CapturarDigital();
                      digital.cpf=retirarFormatacaoCpf(txtCpfAluno.getText());
                     
                      digital.verificarDedos();
                      digital.setVisible(true);
                 } catch (SQLException ex) {
                     Logger.getLogger(AlunoListaForm.class.getName()).log(Level.SEVERE, null, ex);
                 }
    
        }

}
