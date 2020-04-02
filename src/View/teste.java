/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dao.AlunoDao;
import Dao.UsuarioDao;
import Conexao.ConexaoBD;
import Controller.UsuarioController;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;
import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.*;
import javax.swing.SwingUtilities;
import java.applet.Applet;
import java.applet.AudioClip;

import java.io.FileInputStream;  
import java.io.InputStream;  
import java.net.URISyntaxException;
import sun.audio.AudioPlayer;  
import sun.audio.AudioStream;


/**
 *
 * @author Home
 */
public class teste extends javax.swing.JFrame {
    ConexaoBD con = new ConexaoBD(); //está é a minha conexão com o banco de dados MySQL

    public static int teste = 4; //variavel criada para ser usada como parametro dentro da minha consulta SQL
  
    /**
     * Creates new form teste
     */
    public teste() throws ParseException {
        initComponents();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d="2017-09-04";
        java.util.Date sdf = new SimpleDateFormat("yyyy-MM-dd").parse(d);
        jDateChooser1.setDate(sdf);
    }

	public void PastaCorrente() {
		try {
			String caminho = teste.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			caminho = caminho.substring(1, caminho.lastIndexOf('/') + 1);
			System.out.println(caminho);
			System.out.println(caminhoaplicacao());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

    public void tocarSom(){
        try {  
        InputStream arq = new FileInputStream("C:\\Users\\Home\\Documents\\NetBeansProjects\\refeitorio\\impressaonaoidentificada.wav");  
        AudioStream som = new AudioStream(arq);  
        AudioPlayer.player.start(som);  
        }  
        catch(Exception e) {  
        System.out.println("Erro na execução! "+ e);  
          
        //comando para interromper execução   
        //AudioPlayer.player.stop(som);  
     }  
          
   }  

public void execSom(String[] args){		
    AudioInputStream din = null;
		try {
			File file = new File(args[0]);
			AudioInputStream in = AudioSystem.getAudioInputStream(file);
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			if(line != null) {
				line.open(decodedFormat);
				byte[] data = new byte[4096];
				// Start
				line.start();
				
				int nBytesRead;
				while ((nBytesRead = din.read(data, 0, data.length)) != -1) {	
					line.write(data, 0, nBytesRead);
				}
				// Stop
				line.drain();
				line.stop();
				line.close();
				din.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(din != null) {
				try { din.close(); } catch(IOException e) { }
			}
		}
	
}
       public String datahoje(){
	//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	Date date = new Date(); 
	return dateFormat.format(date);
   }

          public void guardaCaminho(){
       String arq = "conf.txt";
       String texto="";
       texto=txtCaminho.getText().trim();
       if(Arquivo.Write(arq, texto)){
           JOptionPane.showMessageDialog(null, "Caminho da pasta salva com sucesso!!!!");
       }else{
           JOptionPane.showMessageDialog(null, "Arquivo de Entrada Criado!!!!");
       }
   }
public String caminhoaplicacao(){
   File f = new File("sons");
   return f.getAbsolutePath();
}
   public String lerCaminho(){
       String arq = "conf.txt";
       String texto=Arquivo.Read(arq);
       if(texto.isEmpty()){
           JOptionPane.showMessageDialog(null, "Não foi possível ler o caminho guardado");
       }
           else{
              txtCaminho.requestFocus();
              
           }  
   return texto;
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
    public ImageIcon criarImageIcon(String caminho, String descricao) {
		java.net.URL imgURL = getClass().getResource(caminho);
		if (imgURL != null) {
			return new ImageIcon(imgURL, descricao);
		} else {
			System.err.println("Não foi possível carregar o arquivo de imagem: " + caminho);
			return null;
		}
	}
             public void carregarImagem(String caminho){
         ImageIcon img = new ImageIcon(caminho);
          jLabel1.setIcon(new ImageIcon(img.getImage().getScaledInstance(215, 205, Image.SCALE_DEFAULT)));     
        }
public static String criptografia(String original) throws UnsupportedEncodingException, UnsupportedEncodingException
	{
		String senha = null;
		MessageDigest algoritmo;
		byte messageDigest[];
		StringBuilder hexString;
		try {
			//algoritmo =MessageDigest.getInstance("SHA-256");// 64 letras
			algoritmo = MessageDigest.getInstance("MD5");  // 32 letras
			messageDigest = algoritmo.digest(original.getBytes("UTF-8"));
			hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			senha = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("Senha normal: "+original+" - Senha criptografada: "+senha);
		return senha;
	}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtCaminho = new javax.swing.JTextField();
        txtCaminho1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        txtCaminho.setText("C:\\Users\\Home\\Documents\\NetBeansProjects\\refeitorio\\src\\fotosaluno\\CAM01496.jpg");

        jLabel2.setText("jLabel1");

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Som");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Caminho");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCaminho, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addComponent(txtCaminho1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jButton1))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addGap(140, 140, 140)
                .addComponent(jButton3)
                .addGap(129, 129, 129))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(34, 34, 34)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(26, 26, 26)))
                .addComponent(txtCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCaminho1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton6))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        File f = new File(txtCaminho.getText());
        
        String caminho=lerCaminho();
        String completo=caminho.trim()+f.getName().trim();
        txtCaminho1.setText(completo);
        File f1 = new File(completo);
        carregarImagem(completo);
        //carregarImagem(txtCaminho.getText());
       
            JOptionPane.showMessageDialog(null, completo);
            

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    
            // TODO add your handling code here:
//              UsuarioService service = new UsuarioService();
//		Criptografa criptografa = new Criptografa();
//		Usuario usr = new Usuario();
//				usr.setNome(nome);
//				usr.setLogin(login);
//UsuarioDao usr = new UsuarioDao();
//UsuarioController cusu = new UsuarioController();
//cusu.setSenhausuario("123");
//cusu.setIdusuario(1);
//        try {
//            usr.AtualizarUsuId(cusu);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(teste.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//JOptionPane.showMessageDialog(null, dataProBanco("22/08/2017"));
//     File f = new File("fotoaluno");
//     String s = f.getAbsolutePath();
//     String dir = System.getProperty("user_dir");
//     System.out.println(new File("src/fotoaluno").getAbsolutePath());
//     String caminhoarquivo=(new File("C:\\Users\\Home\\Documents\\NetBeansProjects\\refeitorio\\src\\fotosaluno\\CAM01497.jpg").getPath());
//    String nomeaquivo=(new File("C:\\Users\\Home\\Documents\\NetBeansProjects\\refeitorio\\src\\fotosaluno\\CAM01497.jpg").getName());
//    String caminhofinal=s+"\\"+nomeaquivo;
//    System.out.println(nomeaquivo);
//    System.out.println(caminhofinal);
//    System.out.println(dir);
        //guardaCaminho();
        File f = new File(txtCaminho.getText());
        String caminho=lerCaminho();
        String completo=caminho+"\\"+f.getName();
        File f1= new File(caminho,f.getName());
        
        System.out.println(f1.getPath());
        System.out.println();
        System.out.println(txtCaminho.getText());
    
        ImageIcon icon = new ImageIcon();
        jLabel1.setIcon(icon);
    //jLabel1.setIcon(new ImageIcon(img.getImage().getScaledInstance(215, 205, Image.SCALE_DEFAULT)));
//     String = camimho = new File(getClass().getClassLoader().getResource("/imagens"));
//     System.out.println(new File(getClass().getClassLoader().getResource("/imagens")));   
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(jDateChooser1.getDate()==null){
            JOptionPane.showMessageDialog(null,"vazio");
        }else{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String data=jDateChooser1.getDateFormatString();
        String data1=jDateChooser1.getDate().toString();
        String data2 = sdf.format(jDateChooser1.getDate());
        System.out.println(data);
        System.out.println(data1);
        System.out.println(data2);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        // Carrega o arquivo de áudio (não funciona com .mp3, só .wav) 
          tocarSom();
         
           
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        PastaCorrente();
        
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new teste().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(teste.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtCaminho;
    private javax.swing.JTextField txtCaminho1;
    // End of variables declaration//GEN-END:variables
}
