/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AcessoController;
import Dao.AcessoDao;
import Conexao.ConexaoBD;
import Controller.LoginController;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.Date;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 *
 * @author Home
 */
public class PrincipalForm extends javax.swing.JFrame {
AcessoController acessos = new AcessoController();
AcessoDao acessodao = new AcessoDao();
ConexaoBD conexao = new ConexaoBD();
LoginController logado = new LoginController();

        public String caminho;
        private String usuario;
        private String porta;
        private String  nomebanco;
        private String senha;
        private String local;

public int idusuarioacesso;
public int usuarioacesso;
public int alunoacesso;
public int acessosistema;
public int creditoacesso;
public int refeitorioacesso;
public int relatorioacesso;
public int cursoacesso;
public int turmaacesso;
public int timelineleitura;
public int timelineescrita;
public int idlogado;
public String nomeusuario;
public String[] forms;
int posicao;



TrocarAuxilioForm mudarStatusAux = new TrocarAuxilioForm();
UsuarioListaForm usuarioform = new UsuarioListaForm();
CursoListaForm cursoform = new CursoListaForm();
TurmaListaForm turma = new TurmaListaForm();
RefeitorioForm ref= new RefeitorioForm();
ReservaForm r = new ReservaForm();
SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
String horas="";
String caminhoGuardarBkup="";

    public Integer getIdlogado() {
        return idlogado;
    }

    /**
     * Creates new form PrincipalForm
     */
    
    public PrincipalForm() throws SQLException, InterruptedException, IOException {

        initComponents();
         URL caminhoIcone = getClass().getResource("/ico/if_Food_572824.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);  
        lbId.setVisible(false);
        lbIdtitulo.setVisible(false);
        this.setExtendedState( MAXIMIZED_BOTH);
        lerJson();
        diaExtensoHoraEntrada();

//        pegarhora();
        Thread relogio = new Thread(new PrincipalForm.HoraThread());
        relogio.start();
        
//setIcon()

    }
           public void lerJson(){
        JSONObject resultado=null;
        
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("conf.json"));
            
            resultado=(JSONObject)obj;
                    nomebanco = (String)resultado.get("nomebanco");
                    porta     = (String)resultado.get("porta");
                    usuario       = (String)resultado.get("usuario");
                    senha  = (String)resultado.get("senha");
                    local     = (String)resultado.get("local");
                                      
        } 
        catch (FileNotFoundException ex) {ex.printStackTrace(); }
        catch (IOException ex) {ex.printStackTrace(); }
        catch (Exception ex) {ex.printStackTrace(); }
        caminho="jdbc:mysql://"+local+":"+porta+"/"+nomebanco;
          //System.out.println(caminho+usuario+senha);

   }  
    private void setIcon(){
     setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("\\Galeria\\Icones1\\if_restaurant_103236")));   
    }
        public Integer idLogado(){
            Integer id;
            id=0;
            id=Integer.parseInt(lbId.getText());
            return id;
        }

    public void listaAcesso(int i){
        for(AcessoController a: acessodao.CarregarAcesso(i)){
            idusuarioacesso=a.getIdusuacesso();
            nomeusuario=a.getNomeusuario();
            alunoacesso=a.getAlunoacesso();
            usuarioacesso=a.getUsuarioacesso();
            creditoacesso=a.getCreditoacesso();
            refeitorioacesso=a.getRefeitorioacesso();
            //System.out.println("refeitorio "+a.getRefeitorioacesso());
            relatorioacesso=a.getRelatorioacesso();
            cursoacesso=a.getCursoacesso();
            turmaacesso=a.getTurmaacesso();
            timelineleitura=a.getTimelineleitura();
            lbId.setText(Integer.toString(idusuarioacesso));
            lbNome.setText(nomeusuario);
        }
    }
        public void GerarBackup () throws IOException{
            LocalDateTime dataAgora = LocalDateTime.now();
            DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

            String nome = dataAgora.format(dataFormato);

                 Runtime bck = Runtime.getRuntime();
                    bck.exec("C:/xampp/mysql/bin/mysqldump.exe -v -v -v --host="+local+" --user="+usuario+" --password="+senha+" --port="+porta+" --opt --routines --triggers --protocol=tcp --force --allow-keywords --compress --add-drop-table --default-character-set=latin1 --hex-blob --result-file=backup/"+ nome +".sql --databases "+nomebanco);

    } 
    public void GerarBackup (String nome) throws IOException{
    Runtime bck = Runtime.getRuntime();
    bck.exec("C:/xampp/mysql/bin/mysqldump.exe -v -v -v --host="+local+" --user="+usuario+" --password="+senha+" --port="+porta+" --opt --routines --triggers --protocol=tcp --force --allow-keywords --compress --add-drop-table --default-character-set=latin1 --hex-blob --result-file=backup/"+ nome +".sql --databases "+nomebanco);

    }  
    class HoraThread implements Runnable{

        @Override
        public void run() {
            while (true){
                lbHoras.setText(formatoHora.format(new Date()));
                try{
                    Thread.sleep(1000);
                    pegarhora();
                    
                }catch(InterruptedException e){
                    System.out.println("Thread não foi finalizada"+e);
                } catch (IOException ex) {
                    Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
 
    }
  public void diaExtensoHoraEntrada() throws IOException{
        int segundos;
        int minutos;
        int horas;
        int segonds;  
        int ontem;
        Date d  = new Date();
        String[] semanaExtenso = {"Domingo","Segunda-Feira","Terça-Feira","Quarta-Feira","Quinta-Feira","Sexta-Feira","Sabado"};   
        Calendar data;
        
                data = Calendar.getInstance();
                int nu = data.get(data.DAY_OF_WEEK);
                ontem = data.get(Calendar.DAY_OF_MONTH);
                LbDiaExtenso.setText(semanaExtenso[nu-1]);
                data = Calendar.getInstance();
                horas = data.get(Calendar.HOUR_OF_DAY);
                minutos = data.get(Calendar.MINUTE);
                segundos = data.get(Calendar.SECOND); 
                //LbHoraEntrada.setText(horas+":"+minutos);
                
                Date datasistema = new Date();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                LbData.setText(formato.format(datasistema));

  }
  public static void filelist() throws IOException
    {
        String path = new File("./backup").getCanonicalPath();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

    for (File file : listOfFiles)
       
    {
        if (file.isFile())
        {
            String[] filename = file.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
           // if(filename[0].equalsIgnoreCase("a")) //matching defined filename
                System.out.println("File exist: "+filename[1]+"."+filename[1]); // match occures.Apply any condition what you need
               // System.out.println("File exist: "+filename[0]+"."+filename[1]); // match occures.Apply any condition what you need
               file.delete();
        }
     }
}
    public void pegarhora() throws IOException{
        int segundos;
        int minutos;
        int horas;
        int segonds;
        int ontem;
        String arquivo;
        
       
        Calendar data; 
        
                 data = Calendar.getInstance();   
                 horas = data.get(Calendar.HOUR_OF_DAY);
                 minutos = data.get(Calendar.MINUTE);
                 segundos = data.get(Calendar.SECOND);
                 
                //System.out.println(horas+":"+minutos+":"+segundos);
                segonds=(horas*60+minutos)*60+segundos;
                LocalDateTime dataAgora = LocalDateTime.now();
                DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

            String nome = dataAgora.format(dataFormato);               
       
            
                if(horas==15 && minutos==00 && segundos==00){
                    GerarBackup(nome);
                }
                

    }
    public void executandoHora() throws InterruptedException{
        int segundos;
        int minutos;
        int horas;
        Calendar data;
        
        try{
            while(true){
                Thread.sleep(1000);
                data = Calendar.getInstance();
                horas = data.get(Calendar.HOUR_OF_DAY);
                minutos = data.get(Calendar.MINUTE);
                segundos = data.get(Calendar.SECOND);
                System.out.println(horas+":"+minutos+":"+segundos);
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }
    public void abrirListaUsuarios() throws SQLException{
            // TODO add your handling code here:
        listaAcesso(Integer.parseInt(lbId.getText()));
        System.out.println(usuarioacesso);
        if(usuarioacesso==0){
            JOptionPane.showMessageDialog(null, "Desculpe você não tem acesso a este modulo, procure o Administrador");
        }else{
          if(usuarioform.isVisible()){
              usuarioform.setExtendedState(usuarioform.NORMAL);
//              JOptionPane.showMessageDialog(null,"A janela está aberta, verifique no radapé");
          }else{
                    usuarioform.idlogado=idLogado();
                    usuarioform.setVisible(true);
        }
        }
   }
    public void abrirListaAlunos(){
        AlunoListaForm alunoform = new AlunoListaForm();
              // TODO add your handling code here:
        listaAcesso(Integer.parseInt(lbId.getText()));
        if(alunoacesso==0){
            JOptionPane.showMessageDialog(null, "Desculpe você não tem acesso a este modulo, procure o Administrador");
        }else{
        if(alunoform.isVisible()){
//            JOptionPane.showMessageDialog(null,"já está aberto");
            alunoform.setExtendedState(alunoform.MAXIMIZED_BOTH);
//            JOptionPane.showMessageDialog(null,"A janela está aberta, verifique no radapé");
                                   }else{
                        alunoform.idlogado=Integer.parseInt(lbId.getText());
                        alunoform.listaAcesso(Integer.parseInt(lbId.getText()));

                        alunoform.setVisible(true); 
                        }
    }
    }
    public void abrirListaTimeLine(){
         TimeLineListaDialog timelinedialog = new TimeLineListaDialog(this, true);
              // TODO add your handling code here:
        listaAcesso(Integer.parseInt(lbId.getText()));
        if(timelineleitura==0){
            JOptionPane.showMessageDialog(null, "Desculpe você não tem acesso a este modulo, procure o Administrador");
        }else{
            
        if(timelinedialog.isVisible()){
          JOptionPane.showMessageDialog(null,"já está aberto");
            //timelinedialog.setExtendedState(alunoform.MAXIMIZED_BOTH);
//            JOptionPane.showMessageDialog(null,"A janela está aberta, verifique no radapé");
                                   }else{
                        timelinedialog.idUsuario=Integer.parseInt(lbId.getText());
                        timelinedialog.setVisible(true); 
                        }
        timelinedialog.idUsuario=Integer.parseInt(lbId.getText());
    }
    }
    public void abrirListaCurso(){
            // TODO add your handling code here:
         listaAcesso(Integer.parseInt(lbId.getText()));
        if(cursoacesso==0){
            JOptionPane.showMessageDialog(null, "Desculpe você não tem acesso a este modulo, procure o Administrador");
        }else{
        if(cursoform.isVisible()){
            cursoform.setExtendedState(cursoform.NORMAL);
//             JOptionPane.showMessageDialog(null,"A janela está aberta, verifique no radapé");
        }else{
        cursoform.idlogado=idLogado();
        cursoform.setVisible(true);
        //cursoform.idlogado=Integer.parseInt(lbId.getText());
        
        }    
        }
    }
    public void abrirListaTurma(){
                       listaAcesso(Integer.parseInt(lbId.getText()));
        if(turmaacesso==0){
            JOptionPane.showMessageDialog(null, "Desculpe você não tem acesso a este modulo, procure o Administrador");
        }else{
        if(turma.isVisible()){
            turma.setExtendedState(turma.NORMAL);
//            JOptionPane.showMessageDialog(null,"A janela está aberta, verifique no radapé");
        }
        turma.idlogado=this.idLogado();
        turma.preencherTabela();
        turma.setVisible(true);
        }
    }
    public void abrirListaCreditos() throws ParseException{
//               listaAcesso(Integer.parseInt(lbId.getText()));
//        if(creditoacesso==0){
//            JOptionPane.showMessageDialog(null, "Desculpe você não tem acesso a este modulo, procure o Administrador");
//        }else{
//        CreditoListaForm credito = new CreditoListaForm();
//        credito.idlogado=this.idLogado();
//        credito.preencherTabela();
//        credito.setVisible(true);
//        }
    }
    public void abrirRefeitorio() throws SQLException{
              // TODO add your handling code here:
              
         if(refeitorioacesso==0){
            JOptionPane.showMessageDialog(null, "Desculpe você não tem acesso a este modulo, procure o Administrador");
        }else{
          
          if(ref.isVisible()){
             ref.setExtendedState(ref.NORMAL);
//               JOptionPane.showMessageDialog(null,"A janela está aberta, verifique no radapé");
          }else{
          
          ref.idlogado=this.idLogado();
           ref.setVisible(true); 
          }

          }

    }
    public void abrirTestar(){
        
    }
    public void abrirRelatorios(){
                if(relatorioacesso==0){
            JOptionPane.showMessageDialog(null, "Desculpe você não tem acesso a este modulo, procure o Administrador");
        }else{
         Relatorios rel= new Relatorios();
         rel.setVisible(true);
                }
    }
    public void sairSistema(){
         this.dispose();
    }
    public void adicionar(String nome) {
		for (int i = 0; i < this.forms.length; i++) {
			if (this.forms[i] != null) {
				this.posicao += 1;
			}
		}
		this.forms[posicao] = nome;
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
        btnUsuario = new javax.swing.JButton();
        btnAluno = new javax.swing.JButton();
        btnRefeitorio = new javax.swing.JButton();
        btnRelatorio = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnCurso = new javax.swing.JButton();
        btnTurma = new javax.swing.JButton();
        btnReservar = new javax.swing.JButton();
        btnTimeLine = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbIdtitulo = new javax.swing.JLabel();
        lbId = new javax.swing.JLabel();
        lbNomeTitulo = new javax.swing.JLabel();
        lbNome = new javax.swing.JLabel();
        btnTrocarUsu = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        LbData = new javax.swing.JLabel();
        LbDiaExtenso = new javax.swing.JLabel();
        lbHoras = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mAdministrador = new javax.swing.JMenuItem();
        mAluno = new javax.swing.JMenuItem();
        mCredito = new javax.swing.JMenuItem();
        mCurso = new javax.swing.JMenuItem();
        mTurma = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mRefeitorio = new javax.swing.JMenuItem();
        mTestarDigitais = new javax.swing.JMenuItem();
        mPesquisarConsumo = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mRelatorios = new javax.swing.JMenuItem();
        mSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(getIconImage());

        jPanel1.setBackground(new java.awt.Color(0, 153, 77));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnUsuario.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/group-24.png"))); // NOI18N
        btnUsuario.setText("Usuários");
        btnUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });

        btnAluno.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/student-24.png"))); // NOI18N
        btnAluno.setText("Alunos");
        btnAluno.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlunoActionPerformed(evt);
            }
        });

        btnRefeitorio.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnRefeitorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_Food_572824.png"))); // NOI18N
        btnRefeitorio.setText("Refeição");
        btnRefeitorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeitorioActionPerformed(evt);
            }
        });

        btnRelatorio.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/combo-24.png"))); // NOI18N
        btnRelatorio.setText("Relatórios");
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/exit-32.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnCurso.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_diploma_416373.png"))); // NOI18N
        btnCurso.setText("Cursos");
        btnCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCursoActionPerformed(evt);
            }
        });

        btnTurma.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnTurma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/google-groups-24.png"))); // NOI18N
        btnTurma.setText("Turmas");
        btnTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurmaActionPerformed(evt);
            }
        });

        btnReservar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnReservar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/if_alacarte_30045.png"))); // NOI18N
        btnReservar.setText("Reservar");
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        btnTimeLine.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        btnTimeLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/time-5-24.png"))); // NOI18N
        btnTimeLine.setText("Time Line");
        btnTimeLine.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimeLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimeLineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(btnUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimeLine, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTurma)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefeitorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRelatorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAluno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimeLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCurso, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTurma, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReservar, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRefeitorio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRelatorio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSair, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))))
                .addGap(57, 57, 57))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAluno, btnRefeitorio, btnRelatorio, btnSair, btnUsuario});

        btnAluno.getAccessibleContext().setAccessibleDescription("Administradores");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));

        lbIdtitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbIdtitulo.setText("ID:");

        lbId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbId.setForeground(new java.awt.Color(102, 102, 102));
        lbId.setText("Administrador");

        lbNomeTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbNomeTitulo.setText("Logado: ");

        lbNome.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbNome.setForeground(new java.awt.Color(102, 102, 102));
        lbNome.setText("Nome");

        btnTrocarUsu.setBackground(new java.awt.Color(204, 204, 204));
        btnTrocarUsu.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        btnTrocarUsu.setForeground(new java.awt.Color(255, 255, 255));
        btnTrocarUsu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones1/change-user-32.png"))); // NOI18N
        btnTrocarUsu.setToolTipText("Trocar Administrador");
        btnTrocarUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrocarUsuActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LbData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LbData.setForeground(new java.awt.Color(51, 51, 51));
        LbData.setText("23/10/2019");

        LbDiaExtenso.setBackground(new java.awt.Color(51, 51, 51));
        LbDiaExtenso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LbDiaExtenso.setForeground(new java.awt.Color(51, 51, 51));
        LbDiaExtenso.setText("Terça-feira");

        lbHoras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbHoras.setForeground(new java.awt.Color(255, 0, 0));
        lbHoras.setText("jLabel1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LbDiaExtenso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LbData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(lbHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LbData)
                    .addComponent(LbDiaExtenso)
                    .addComponent(lbHoras))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTrocarUsu)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbIdtitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbId))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbNomeTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbNome)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 778, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTrocarUsu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbIdtitulo)
                                    .addComponent(lbId))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbNomeTitulo)
                                    .addComponent(lbNome))
                                .addGap(17, 17, 17)))
                        .addContainerGap())))
        );

        jMenu1.setText("Cadastros");

        mAdministrador.setText("Administrador");
        mAdministrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAdministradorActionPerformed(evt);
            }
        });
        jMenu1.add(mAdministrador);

        mAluno.setText("Aluno");
        mAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mAlunoActionPerformed(evt);
            }
        });
        jMenu1.add(mAluno);

        mCredito.setText("Crédito");
        mCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mCreditoActionPerformed(evt);
            }
        });
        jMenu1.add(mCredito);

        mCurso.setText("Curso");
        mCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mCursoActionPerformed(evt);
            }
        });
        jMenu1.add(mCurso);

        mTurma.setText("Turma");
        mTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTurmaActionPerformed(evt);
            }
        });
        jMenu1.add(mTurma);

        jMenuItem1.setText("Configurar Conexão banco de dados");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Refeitório");

        mRefeitorio.setText("Refeitório");
        mRefeitorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRefeitorioActionPerformed(evt);
            }
        });
        jMenu2.add(mRefeitorio);

        mTestarDigitais.setText("Testar Digitais");
        mTestarDigitais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTestarDigitaisActionPerformed(evt);
            }
        });
        jMenu2.add(mTestarDigitais);

        mPesquisarConsumo.setText("Pesquisar Consumo");
        jMenu2.add(mPesquisarConsumo);

        jMenuItem2.setText("Trocar Auxílio de Vários");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Relatórios");

        mRelatorios.setText("Emissão de Relatórios");
        mRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRelatoriosActionPerformed(evt);
            }
        });
        jMenu3.add(mRelatorios);

        jMenuBar1.add(jMenu3);

        mSair.setText("Sair");
        jMenuBar1.add(mSair);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 403, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
    try {
        // TODO add your handling code here:
        abrirListaUsuarios();
       
    } catch (SQLException ex) {
        Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
    try {
        // TODO add your handling code here:
        GerarBackup();
    } catch (IOException ex) {
        Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
    }
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnRefeitorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeitorioActionPerformed
    try {
        abrirRefeitorio();
    } catch (SQLException ex) {
        Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
    }

    }//GEN-LAST:event_btnRefeitorioActionPerformed

    private void mRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRelatoriosActionPerformed
            abrirRelatorios();
    }//GEN-LAST:event_mRelatoriosActionPerformed

    private void btnAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlunoActionPerformed
        // TODO add your handling code here:
                abrirListaAlunos();
    }//GEN-LAST:event_btnAlunoActionPerformed

    private void btnCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCursoActionPerformed
        // TODO add your handling code here:
            abrirListaCurso();
    }//GEN-LAST:event_btnCursoActionPerformed

    private void btnTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurmaActionPerformed
        // TODO add your handling code here:
            abrirListaTurma();
        
    }//GEN-LAST:event_btnTurmaActionPerformed

    private void mTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTurmaActionPerformed
        // TODO add your handling code here:
        abrirListaTurma();
    }//GEN-LAST:event_mTurmaActionPerformed

    private void mAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAdministradorActionPerformed
    try {
        // TODO add your handling code here:
        abrirListaUsuarios();
    } catch (SQLException ex) {
        Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_mAdministradorActionPerformed

    private void mAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAlunoActionPerformed
        // TODO add your handling code here:
        abrirListaAlunos();
    }//GEN-LAST:event_mAlunoActionPerformed

    private void mCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCreditoActionPerformed
    try {
        // TODO add your handling code here:
        abrirListaCreditos();
    } catch (ParseException ex) {
        Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_mCreditoActionPerformed

    private void mCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCursoActionPerformed
        // TODO add your handling code here:
        abrirListaCurso();
    }//GEN-LAST:event_mCursoActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
        // TODO add your handling code here:
        abrirRelatorios();
    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void mTestarDigitaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTestarDigitaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mTestarDigitaisActionPerformed

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
  
       
        if(r.isVisible()){
            
            r.setExtendedState(MAXIMIZED_BOTH);
        }else{
            r.idlogado=this.idLogado();
            r.setVisible(true);
        }

    }//GEN-LAST:event_btnReservarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        ConfConexaoForm conf = new ConfConexaoForm(this,false);
        conf.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mRefeitorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRefeitorioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mRefeitorioActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
        
        mudarStatusAux.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnTimeLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeLineActionPerformed
        // TODO add your handling code here:
  abrirListaTimeLine();
        
        
    }//GEN-LAST:event_btnTimeLineActionPerformed

    private void btnTrocarUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrocarUsuActionPerformed
        // TODO add your handling code here:
        int retorno=0;
        TrocarUsuario t = new TrocarUsuario(this,true);
        t.setVisible(true);
        retorno=t.getRetorno();
        if(retorno==1){
            lbId.setText(Integer.toString(t.getIdusuario()));
            idlogado=t.getIdusuario();
            lbNome.setText(t.getNome());
            listaAcesso(t.getIdusuario());
        }

    }//GEN-LAST:event_btnTrocarUsuActionPerformed

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
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PrincipalForm().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PrincipalForm.class.getName()).log(Level.SEVERE, null, ex);
                }
   
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LbData;
    private javax.swing.JLabel LbDiaExtenso;
    private javax.swing.JButton btnAluno;
    private javax.swing.JButton btnCurso;
    private javax.swing.JButton btnRefeitorio;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnTimeLine;
    private javax.swing.JButton btnTrocarUsu;
    private javax.swing.JButton btnTurma;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbHoras;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbIdtitulo;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbNomeTitulo;
    private javax.swing.JMenuItem mAdministrador;
    private javax.swing.JMenuItem mAluno;
    private javax.swing.JMenuItem mCredito;
    private javax.swing.JMenuItem mCurso;
    private javax.swing.JMenuItem mPesquisarConsumo;
    private javax.swing.JMenuItem mRefeitorio;
    private javax.swing.JMenuItem mRelatorios;
    private javax.swing.JMenu mSair;
    private javax.swing.JMenuItem mTestarDigitais;
    private javax.swing.JMenuItem mTurma;
    // End of variables declaration//GEN-END:variables
}
