/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.AlunoController;
import java.sql.Blob;
import Conexao.ConexaoBD;
import Controller.CreditoController;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class AlunoDao {
            ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
            CreditoController cred = new CreditoController(); 
            
    public void SalvarAluno(AlunoController aluno) throws UnsupportedEncodingException {
       if(!existeAluno(aluno.getCpfaluno().trim())){
        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            PreparedStatement pst = conexao.connection.prepareStatement("insert into aluno(nomealuno,emailaluno,cpfaluno,senhaaluno,cursoid,turmaid,usuid,polegaraluno,indicadoraluno,medioaluno,anelaraluno,mindinhoaluno,fotoaluno,tipobolsa) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                                                                        //Enviando dados para o banco.
                                                                        pst.setString(1,aluno.getNomealuno());
                                                                        pst.setString(2,aluno.getEmailaluno());
                                                                        pst.setString(3,aluno.getCpfaluno());
                                                                        pst.setString(4,criptografia(aluno.getSenhaaluno()));
                                                                        pst.setInt(5,aluno.getCursoid());
                                                                        pst.setInt(6,aluno.getTurmaid());
                                                                        pst.setInt(7,aluno.getUsuid());
                                                                        pst.setString(8,"X");
                                                                        pst.setString(9,"X");
                                                                        pst.setString(10,"X");
                                                                        pst.setString(11,"X");
                                                                        pst.setString(12,"X");
                                                                        pst.setString(13,aluno.getFotoaluno());
                                                                        pst.setInt(14, aluno.getBolsa());
                                                                        pst.execute();

          ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Salvo", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de salvar cadastro de estudante!"+ex, "Salvar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.
       }
    }
        public boolean existeAluno(String cpf){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("SELECT `idaluno` from `aluno` where `cpfaluno`="+cpf);
        try {   
            conexao.rs.first();
            if(conexao.rs.getRow()>0){
                
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
         public String buscarnome(int id) throws SQLException{
             conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("SELECT `nomealuno` from `aluno` where `idaluno` ="+id);
            conexao.rs.first();
        return conexao.rs.getString("nomealuno");
        }
         public String buscarfoto(int id) throws SQLException{
             
             conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("SELECT `fotoaluno` from `aluno` where `idaluno` ="+id);
            conexao.rs.first();
        return conexao.rs.getString("fotoaluno");
        }
     public void AtualizarAuxilio(AlunoController aluno) throws UnsupportedEncodingException {
                 conexao.Conectar(); //Conectando ao banco.

                    try {
                 String sql="update `aluno` set `tipobolsa`=? where `idaluno` = ?";
                PreparedStatement pst = conexao.connection.prepareStatement(sql);
                pst.setInt(1, aluno.getBolsa());
                pst.setInt(2, aluno.getIdaluno());
                pst.execute();
//            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
//            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de atualizar auxílio de estudante!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.
     }    
    public void AtualizarAluno(AlunoController aluno) throws UnsupportedEncodingException {

        conexao.Conectar(); //Conectando ao banco.

        try {
            
            String sql=null;
            String senha=aluno.getSenhaaluno();
            if (senha.equals("")){
            sql="update `aluno` set `nomealuno` = ?, `emailaluno` = ?, `cpfaluno` = ?,`fotoaluno`=?,`cursoid`=?,`turmaid`=?,`tipobolsa`=? where `idaluno` = ?";
            //System.out.println(sql);
            PreparedStatement pst = conexao.connection.prepareStatement(sql);
                pst.setString(1,aluno.getNomealuno());
                pst.setString(2, aluno.getEmailaluno());
                //pst.setString(3, aluno.getSenhaaluno());
                pst.setString(3, aluno.getCpfaluno());
                pst.setString(4, aluno.getFotoaluno());
                pst.setInt(5, aluno.getCursoid());
                pst.setInt(6, aluno.getTurmaid());
                pst.setInt(7, aluno.getBolsa());
                pst.setInt(8, aluno.getIdaluno());
                pst.execute();
            }else{
               sql="update aluno set `nomealuno` = ?, `emailaluno` = ?, `senhaaluno`=?,`cpfaluno` = ?,`fotoaluno`=?,`cursoid`=?,`turmaid`=?,`tipobolsa`=? where `idaluno` = ?"; 
                System.out.println(sql);
               PreparedStatement pst = conexao.connection.prepareStatement(sql);
                pst.setString(1,aluno.getNomealuno());
                pst.setString(2, aluno.getEmailaluno());
                pst.setString(3, criptografia(aluno.getSenhaaluno()));
                pst.setString(4, aluno.getCpfaluno());
                pst.setString(5, aluno.getFotoaluno());
                pst.setInt(6, aluno.getCursoid());
                pst.setInt(7, aluno.getTurmaid());                
                pst.setInt(8, aluno.getBolsa());
                pst.setInt(9, aluno.getIdaluno());
                pst.execute();
            }

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de atualizar cadastro de estudante!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
    public String pegarsenha(Integer id) throws SQLException{
                conexao.Conectar(); //Conectando ao banco.
                String senha = null;
                conexao.ExecutarSQL("select `senhaaluno` from `aluno` where `idaluno` = "+id);
                conexao.rs.first();
                return conexao.rs.getString("senhaaluno"); 
                
    }
    public Integer ultimoAlunoSalvo() throws SQLException{
                conexao.Conectar(); //Conectando ao banco.
                String senha = null;
                conexao.ExecutarSQL("select max(idaluno) as id from `aluno`");
                conexao.rs.first();
                return conexao.rs.getInt("id"); 
                
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
		//System.out.println("Senha normal: "+original+" - Senha criptografada: "+senha);
		return senha;
	}
        public void ExcluirAluno(AlunoController aluno) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma exclusão.
            PreparedStatement pst = conexao.connection.prepareStatement("delete from `aluno` where `idaluno` = ?");
            pst.setInt(1, aluno.getIdaluno());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "Excluído", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de excluir cadastro de estudante!", "Excluir", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
      public List<AlunoController> buscarAlunoIdNome(Integer id){
              List<AlunoController> alunos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `aluno` where `idaluno`=" + id); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
      try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                AlunoController aluno = new AlunoController();
                //Dados para carregarem os valores das linhas da Tabela.
                aluno.setIdaluno(conexao.rs.getInt("idaluno"));
                aluno.setNomealuno(conexao.rs.getString("nomealuno"));
                alunos.add(aluno); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de buscar estudante pelo nome!", "Buscar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return alunos; //Retornando a ArrayList criada.
   
      } 
     public List<AlunoController> CarregarAluno(Integer id) {

        List<AlunoController> alunos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `aluno` inner join `curso` on `aluno`.`cursoid`=`curso`.`idcurso`" +
"                inner join `usuario` on `aluno`.`usuid`=`usuario`.`idusuario`" +
"                 inner join `turma` on `aluno`.`turmaid`=`turma`.`idturma` where `aluno`.`idaluno`=" + id); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                AlunoController aluno = new AlunoController();
                //Dados para carregarem os valores das linhas da Tabela.
                aluno.setIdaluno(conexao.rs.getInt("idaluno"));
                aluno.setNomealuno(conexao.rs.getString("nomealuno"));
                aluno.setEmailaluno(conexao.rs.getString("emailaluno"));
                aluno.setCpfaluno(conexao.rs.getString("cpfaluno"));
                aluno.setFotoaluno(conexao.rs.getString("fotoaluno"));
                aluno.setCursoid(conexao.rs.getInt("cursoid"));
                aluno.setTurmaid(conexao.rs.getInt("turmaid"));
                aluno.setNomecurso(conexao.rs.getString("nomecurso"));
                aluno.setNometurma(conexao.rs.getString("nometurma"));
                aluno.setBolsa(conexao.rs.getInt("tipobolsa"));

                alunos.add(aluno); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de listar estudantes por id!", "Atenção", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return alunos; //Retornando a ArrayList criada.

    }
     public List<AlunoController> CarregarDedos(Integer id) {

        List<AlunoController> alunos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `aluno` where `idaluno`='" + id + "'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                AlunoController aluno = new AlunoController();
                //Dados para carregarem os valores das linhas da Tabela.
                aluno.setIdaluno(conexao.rs.getInt("idaluno"));
                aluno.setNomealuno(conexao.rs.getString("nomealuno"));
                aluno.setEmailaluno(conexao.rs.getString("emailaluno"));
                aluno.setCpfaluno(conexao.rs.getString("cpfaluno"));
                aluno.setFotoaluno(conexao.rs.getString("fotoaluno"));
                aluno.setBolsa(conexao.rs.getInt("tipobolsa"));
                aluno.setTurmaid(conexao.rs.getInt("turmaid"));
                
                aluno.setPolegaraluno(conexao.rs.getBlob("polegaraluno"));
                aluno.setIndicadoraluno(conexao.rs.getBlob("indicadoraluno"));
                aluno.setMedioaluno(conexao.rs.getBlob("medioaluno"));
                aluno.setAnelaraluno(conexao.rs.getBlob("anelaraluno"));
                aluno.setMindinhoaluno(conexao.rs.getBlob("mindinhoaluno"));

                alunos.add(aluno); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de listar dedos de estudantes!", "Dedos", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return alunos; //Retornando a ArrayList criada.

    }
     public List<AlunoController> CarregarDedos(String cpf) {

        List<AlunoController> alunos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `aluno` where `cpfaluno` like'" + cpf + "'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                AlunoController aluno = new AlunoController();
                //Dados para carregarem os valores das linhas da Tabela.
                aluno.setIdaluno(conexao.rs.getInt("idaluno"));
                aluno.setNomealuno(conexao.rs.getString("nomealuno"));
                aluno.setEmailaluno(conexao.rs.getString("emailaluno"));
                aluno.setCpfaluno(conexao.rs.getString("cpfaluno"));
                aluno.setFotoaluno(conexao.rs.getString("fotoaluno"));
                aluno.setBolsa(conexao.rs.getInt("tipobolsa"));
                
                aluno.setPolegaraluno(conexao.rs.getBlob("polegaraluno"));
                aluno.setIndicadoraluno(conexao.rs.getBlob("indicadoraluno"));
                aluno.setMedioaluno(conexao.rs.getBlob("medioaluno"));
                aluno.setAnelaraluno(conexao.rs.getBlob("anelaraluno"));
                aluno.setMindinhoaluno(conexao.rs.getBlob("mindinhoaluno"));

                alunos.add(aluno); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de excluir cadastro de estudante por cpf!", "Excluir", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return alunos; //Retornando a ArrayList criada.

    }
     public List<AlunoController> CarregarTodosAlunos(String campo,String ordem) {

        List<AlunoController> aluno = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        String sql=null;
        sql="select * from `aluno` inner join `curso` on `aluno`.`cursoid`=`curso`.`idcurso` inner join `turma` on `aluno`.`turmaid`=`turma`.`idturma` order by "+campo+" "+ordem;
     //    System.out.println(sql);
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.
         
                    
            do {
                ///JOptionPane.showMessageDialog(null, "passei aqui");
                AlunoController alunos = new AlunoController();
                //Dados para carregarem os valores das linhas da Tabela.
                alunos.setIdaluno(conexao.rs.getInt("idaluno"));
                alunos.setNomealuno(conexao.rs.getString("nomealuno"));
                alunos.setEmailaluno(conexao.rs.getString("emailaluno"));
                alunos.setCpfaluno(conexao.rs.getString("cpfaluno"));
                alunos.setNomecurso(conexao.rs.getString("nomecurso"));
                alunos.setNometurma(conexao.rs.getString("nometurma"));
                alunos.setFotoaluno(conexao.rs.getString("fotoaluno"));
                alunos.setBolsa(conexao.rs.getInt("tipobolsa"));

                aluno.add(alunos); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
             ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de lista todos os estudantes!", "Listar", 2, icon);
           
        }
        conexao.Desconectar(); //Desconectando do banco.

        return aluno; //Retornando a ArrayList criada.

    }
     public List<AlunoController> CarregarPorParamentros(Integer campo,String valor) {

        List<AlunoController> aluno = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        
        String sql="";
        if(campo==0){
           sql="select * from `aluno` inner join `curso` on `aluno`.`cursoid`=`curso`.`idcurso` inner join `turma` on `aluno`.`turmaid`=`turma`.`idturma` where `nomealuno` like '%"+valor+"%'";
        }else if(campo==1){
          sql="select * from `aluno` inner join `curso` on `aluno`.`cursoid`=`curso`.`idcurso` inner join `turma` on `aluno`.`turmaid`=`turma`.`idturma` where `cpfaluno` like '"+valor+"'"; 
        }
        else if(campo==3){
           sql="select * from `aluno` inner join `curso` on `aluno`.`cursoid`=`curso`.`idcurso` inner join `turma` on `aluno`.`turmaid`=`turma`.`idturma` where  `idaluno` ="+valor;  
        }
        
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.
                    
            do {
                ///JOptionPane.showMessageDialog(null, "passei aqui");
                AlunoController alunos = new AlunoController();
                //Dados para carregarem os valores das linhas da Tabela.
                alunos.setIdaluno(conexao.rs.getInt("idaluno"));
                alunos.setNomealuno(conexao.rs.getString("nomealuno"));
                alunos.setEmailaluno(conexao.rs.getString("emailaluno"));
                alunos.setNomecurso(conexao.rs.getString("nomecurso"));
                alunos.setNometurma(conexao.rs.getString("nometurma"));                
                alunos.setCpfaluno(conexao.rs.getString("cpfaluno"));
                alunos.setFotoaluno(conexao.rs.getString("fotoaluno"));
                alunos.setBolsa(conexao.rs.getInt("tipobolsa"));

                aluno.add(alunos); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de estudantes por parametros!", "Excluir", 2, icon);
            
        }
        conexao.Desconectar(); //Desconectando do banco.

        return aluno; //Retornando a ArrayList criada.

    }
    
    
}
