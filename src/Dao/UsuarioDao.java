/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.UsuarioController;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class UsuarioDao {
        ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.

    
    public void SalvarUsuario(UsuarioController usuario) throws UnsupportedEncodingException {
      if(!existeUsuario(usuario.getEmailusuario().trim())){
        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            PreparedStatement pst = conexao.connection.prepareStatement("insert into `usuario` (`nomeusuario`, `contatousuario`, `emailusuario`,`senhausuario`,`admuser`,`bloquearuser`,`usuid`) VALUES (?, ?, ?, ?, ? ,?, ?)");

            //Enviando dados para o banco.
            pst.setString(1,usuario.getNomeusuario());
            pst.setString(2, usuario.getContatousuario());
            pst.setString(3, usuario.getEmailusuario());
            pst.setString(4, criptografia(usuario.getSenhausuario()));
            pst.setInt(5, usuario.getAdm());
            pst.setInt(6, usuario.getBloquear());
            pst.setInt(7, usuario.getUsuid());
            pst.execute();

          ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Salvo", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!"+ex, "Salvar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.
      }
    }
public boolean existeUsuario(String email){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("select `idusuario` from `usuario` where `emailusuario` like "+"'"+email+"'");
        try {   
            conexao.rs.first();
            if(conexao.rs.getRow()>0){
                JOptionPane.showMessageDialog(null, "Ops! Este Email já foi cadastrado!!!");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
}
public boolean usuarioParaLogar(String email){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("select `idusuario` from `usuario` where `emailusuario` like "+"'"+email+"'");
        try {   
            conexao.rs.first();
            if(conexao.rs.getRow()==0){
                JOptionPane.showMessageDialog(null, "Este E-mail não consta neste sistema!!!");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
}
public boolean Logar(String email,String senha){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("select * from `usuario` where `emailusuario` ='"+email.trim()+"' and `senhausuario` ='"+senha+"'");
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

public boolean eAdm(int id){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("select `admuser` from `usuario` where `idusuario`="+id);
        try {   
            conexao.rs.first();
            if(conexao.rs.getRow()>0){
                return true;
            }else{
             return false;   
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
            return false;
        
}
public boolean temAcesso(int id){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("select * from `acesso` where `acessosistema`=1 and `idusuacesso`="+id);
        try {   
            conexao.rs.first();
            if(conexao.rs.getRow()>0){
                return true;
            }else{
             return false;   
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
            return false;
        
}
    
    public String pegarsenha(Integer id) throws SQLException{
                conexao.Conectar(); //Conectando ao banco.
                String senha = null;
                conexao.ExecutarSQL("select `senhausuario` from `usuario` where `idusuario` = "+id);
                conexao.rs.first();
                return conexao.rs.getString("SENHAUSUARIO"); 
                
    }
    public String pegarnome(String email) throws SQLException{
                conexao.Conectar(); //Conectando ao banco.
                String senha = null;
                conexao.ExecutarSQL("select `nomeusuario` from `usuario` where `emailusuario` like '"+email+"'");
                conexao.rs.first();
                return conexao.rs.getString("nomeusuario"); 
                
    }
    public String nomeadministrador(Integer id) throws SQLException{
                conexao.Conectar(); //Conectando ao banco.
                String senha = null;
                conexao.ExecutarSQL("select `nomeusuario` from `usuario` where `idusuario` = "+id);
                conexao.rs.first();
                return conexao.rs.getString("nomeusuario"); 
                
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
    public List<UsuarioController> list() {

        List<UsuarioController> usuario = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select `idusuario`, `nomeusuario`, `contatousuario`, `emailusuario` from `usuario` order by `nomeusuario`");

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                UsuarioController usuarios = new UsuarioController();
                //Dados para carregarem os valores das linhas da Tabela.
                usuarios.setIdusuario(conexao.rs.getInt("idusuario"));
                usuarios.setNomeusuario(conexao.rs.getString("nomeusuario"));
                usuarios.setContatousuario(conexao.rs.getString("contatousuario"));
                usuarios.setEmailusuario(conexao.rs.getString("emailusuario"));
                usuarios.setSenhausuario(conexao.rs.getString("senhausuario"));

                usuario.add(usuarios); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Tabela vazia!", "Usuários", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return usuario; //Retornando valores armazenados no list.

    }


    public void ExcluirUsuario(UsuarioController usuario) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma exclusão.
            PreparedStatement pst = conexao.connection.prepareStatement("delete from `usuario` where `idusuario`= ?");
            pst.setInt(1, usuario.getIdusuario());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "Excluído", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Excluir", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }

   
    public void AtualizarUsuario(UsuarioController usuario, int tipo) throws UnsupportedEncodingException {
            String sql="";
        conexao.Conectar(); //Conectando ao banco.
        
             
        try {
            if (tipo==0){
            sql="update `usuario` set `nomeusuario` = ?,`contatousuario` = ?, `emailusuario` = ?, `senhausuario`= ?,`admuser`=?,`bloquearuser`=? where `idusuario` = ?";
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement(sql);

            //Enviando dados para o banco.
            pst.setString(1, usuario.getNomeusuario());
            pst.setString(2, usuario.getContatousuario());
            pst.setString(3, usuario.getEmailusuario());
            pst.setString(4, criptografia(usuario.getSenhausuario()));
            pst.setInt(5, usuario.getAdm());
            pst.setInt(6, usuario.getBloquear());
            pst.setInt(7, usuario.getIdusuario());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);
            }
            else if(tipo==1){
            sql="update `usuario` set `nomeusuario` = ?, `contatousuario` = ?,`emailusuario` = ?,`senhausuario` = ? where `idusuario` = ?";
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement(sql);

            //Enviando dados para o banco.
            pst.setString(1, usuario.getNomeusuario());
            pst.setString(2, usuario.getContatousuario());
            pst.setString(3, usuario.getEmailusuario());
            pst.setString(4, criptografia(usuario.getSenhausuario()));
            pst.setInt(5, usuario.getIdusuario());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);                
            }
        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
    public void AtualizarUsuId(UsuarioController usuario) throws UnsupportedEncodingException {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement("update `usuario` set `senhausuario` = ? where `idusuario` = ?");

            pst.setString(1, criptografia(usuario.getSenhausuario()));
            pst.setInt(2, usuario.getIdusuario());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }

    
    public Vector PesquisaGeral(String pesquisa) throws SQLException {

        Vector VectorUsuarios = new Vector(); //Vetor para armazenar dados consultados.

        conexao.Conectar();
        //Pesquisa generalizada na tabela.
        conexao.ExecutarSQL("select `idusuario`,`nomeusuario`,`contatousuario`,`emailusuario` from `usuario` where `idusuario` like'%" + pesquisa + "%'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        conexao.rs.first(); //Serve para filtrar o primeiro resultado.

        try {
            do {

                Vector usuarios = new Vector();
                //Dados para carregarem os valores das linhas da Tabela.
                usuarios.add(conexao.rs.getInt("idusuario"));
                usuarios.add(conexao.rs.getString("nomeusuario"));
                usuarios.add(conexao.rs.getString("contatousuario"));
                usuarios.add(conexao.rs.getString("emailusuario"));

                VectorUsuarios.add(usuarios); //Salvando dados no vetor.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Sem resultado!", "Pesquisa", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return VectorUsuarios; //Retornando os valores armazenados no vetor.

    }


        public List<UsuarioController>LogarUsuario(String email,String senha) {

        List<UsuarioController> usuarios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        String sql="select * from `usuario` where `emailusuario` ='"+email.trim()+"' and `senhausuario` ='"+senha+"'";
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
            //System.out.println(sql);
        try {

          conexao.rs.first(); //Serve para filtrar o primeiro resultado.

           do {

                UsuarioController usuario = new UsuarioController();
                //Dados para carregarem os valores das linhas da Tabela.
                usuario.setIdusuario(conexao.rs.getInt("idusuario"));
                usuario.setNomeusuario(conexao.rs.getString("nomeusuario"));
                usuario.setContatousuario(conexao.rs.getString("contatousuario"));
                usuario.setEmailusuario(conexao.rs.getString("emailusuario"));
                usuario.setSenhausuario(conexao.rs.getString("senhausuario"));
                usuario.setBloquear(conexao.rs.getInt("bloquearuser"));
                usuario.setAdm(conexao.rs.getInt("admuser"));
                usuario.setUsuid(conexao.rs.getInt("usuid"));

                usuarios.add(usuario); //Salvando dados na list.

         } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
                        System.exit(0);
        }

        conexao.Desconectar(); //Desconectando do banco.

        return usuarios; //Retornando a ArrayList criada.

    }


    public List<UsuarioController> CarregarUsuarios(Integer id) {

        List<UsuarioController> usuarios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select `idusuario`,`nomeusuario`,`contatousuario`,`emailusuario`,`senhausuario` FROM `usuario` where `idusuario`='" + id + "'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                UsuarioController usuario = new UsuarioController();
                //Dados para carregarem os valores das linhas da Tabela.
                usuario.setIdusuario(conexao.rs.getInt("idusuario"));
                usuario.setNomeusuario(conexao.rs.getString("nomeusuario"));
                usuario.setContatousuario(conexao.rs.getString("contatousuario"));
                usuario.setEmailusuario(conexao.rs.getString("emailusuario"));
                usuario.setSenhausuario(conexao.rs.getString("senhausuario"));

                usuarios.add(usuario); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return usuarios; //Retornando a ArrayList criada.

    }
        public List<UsuarioController> CarregarTodosUsuarios() {

        List<UsuarioController> usuarios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `usuario`"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.
            do {
                UsuarioController usuario = new UsuarioController();
                //Dados para carregarem os valores das linhas da Tabela.
                usuario.setIdusuario(conexao.rs.getInt("idusuario"));
                usuario.setNomeusuario(conexao.rs.getString("nomeusuario"));
                usuario.setContatousuario(conexao.rs.getString("contatousuario"));
                usuario.setEmailusuario(conexao.rs.getString("emailusuario"));
                usuarios.add(usuario); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return usuarios; //Retornando a ArrayList criada.

    }
}
