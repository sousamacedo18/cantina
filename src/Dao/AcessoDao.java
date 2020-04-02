/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.AcessoController;
import RealScan.RealScan_JNI;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class AcessoDao {
    
       ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
   public boolean temAcesso(int id) throws SQLException{
         conexao.Conectar();
        conexao.ExecutarSQL("select count(*) as total from acesso where idusuacesso="+id);
        if (conexao.rs.getRow()>0){
            return true;
        }     
       return false;
   }
      public void SalvarAcesso(AcessoController acesso) {
          
        conexao.Conectar(); //Conectando ao banco.
        try {
            //Comando para salvar.
            PreparedStatement pst = conexao.connection.prepareStatement("insert into acesso (idusuacesso, alunoacesso,creditoacesso,refeitorioacesso,relatorioacesso,usuarioacesso,acessosistema,cursoacesso,turmaacesso,usuid) values (?,?,?,?,?,?,?,?,?,?)");

            //Enviando dados para o banco.
            pst.setInt(1, acesso.getIdusuacesso());
            pst.setInt(2, acesso.getAlunoacesso());
            pst.setInt(3, acesso.getCreditoacesso());
            pst.setInt(4, acesso.getRefeitorioacesso());
            pst.setInt(5, acesso.getRelatorioacesso());
            pst.setInt(6, acesso.getUsuarioacesso());
            pst.setInt(7, acesso.getAcessosistema());
            pst.setInt(8, acesso.getCursoacesso());
            pst.setInt(9, acesso.getTurmaacesso());
            pst.setInt(10, acesso.getUsuid());
            pst.execute();
            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
           // JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Salvo", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Salvar", 2, icon);
        }
        conexao.Desconectar(); //Desconectando do banco.
    }

    public void AtualizarAcesso(AcessoController acesso) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            String sql = "update acesso SET alunoacesso = ?, creditoacesso = ?, refeitorioacesso = ?,relatorioacesso = ?, usuarioacesso = ?, acessosistema=?,cursoacesso=?,turmaacesso=?,timelineleituraacesso=?,timelineescritaacesso=? where idusuacesso = ?";
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement(sql);
          
            //Enviando dados para o banco.
       
            pst.setInt(1, acesso.getAlunoacesso());
            pst.setInt(2, acesso.getCreditoacesso());
            pst.setInt(3, acesso.getRefeitorioacesso());
            pst.setInt(4, acesso.getRelatorioacesso());
            pst.setInt(5, acesso.getUsuarioacesso());
            pst.setInt(6, acesso.getAcessosistema());
            pst.setInt(7, acesso.getCursoacesso());
            pst.setInt(8, acesso.getTurmaacesso());
            pst.setInt(9, acesso.getTimelineleitura());
            pst.setInt(10, acesso.getTimelineescrita());
            pst.setInt(11, acesso.getIdusuacesso());

            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!"+ex, "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
    public Integer contarRegistro(Integer id) throws SQLException{
       
        conexao.Conectar();
        conexao.ExecutarSQL("select count(*) as total from acesso where idusuacesso="+id);
        conexao.rs.first();
        return conexao.rs.getInt("total");
        
    }
             public List<AcessoController> totalRegistros(Integer id) {

        List<AcessoController> acessos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        //conexao.ExecutarSQL("SELECT * FROM ACESSO WHERE {SELECT NOMEALUNO FROM WHERE IDALUNO="+id+"} IDUSUACESSO='" + id + "'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
      conexao.ExecutarSQL("select count(*) as total from acesso where idusuacesso="+id);
        try {
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.
            do {
                AcessoController acesso = new AcessoController();
                //Dados para carregarem os valores das linhas da Tabela.
                acesso.setTotal(conexao.rs.getInt("total"));
                acessos.add(acesso); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }
        conexao.Desconectar(); //Desconectando do banco.
      
           return acessos; //Retornando a ArrayList criada. 

    }

         public List<AcessoController> CarregarAcesso(Integer id) {
        
        List<AcessoController> acessos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        //conexao.ExecutarSQL("SELECT * FROM ACESSO WHERE {SELECT NOMEALUNO FROM WHERE IDALUNO="+id+"} IDUSUACESSO='" + id + "'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
      String sql="select * from acesso inner join usuario on idusuacesso= idusuario where idusuacesso="+id;
            
      conexao.ExecutarSQL(sql);
        try {
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.
                               
            do {
                AcessoController acesso = new AcessoController();
                //Dados para carregarem os valores das linhas da Tabela.
                acesso.setIdacesso(conexao.rs.getInt("idacesso"));
                acesso.setIdusuacesso(conexao.rs.getInt("idusuacesso"));
                acesso.setAcessosistema(conexao.rs.getInt("acessosistema"));
                acesso.setUsuarioacesso(conexao.rs.getInt("usuarioacesso"));
                acesso.setAlunoacesso(conexao.rs.getInt("alunoacesso"));
                acesso.setCreditoacesso(conexao.rs.getInt("creditoacesso"));
                acesso.setRefeitorioacesso(conexao.rs.getInt("refeitorioacesso"));
                acesso.setRelatorioacesso(conexao.rs.getInt("relatorioacesso"));
                acesso.setNomeusuario(conexao.rs.getString("nomeusuario"));
                acesso.setCursoacesso(conexao.rs.getInt("cursoacesso"));
                acesso.setTurmaacesso(conexao.rs.getInt("turmaacesso"));
                acesso.setTimelineleitura(conexao.rs.getInt("timelineleituraacesso"));
                acesso.setTimelineescrita(conexao.rs.getInt("timelineescritaacesso"));
                acessos.add(acesso); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.
         
        } catch (SQLException ex) {
            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação de lista Time line!"+ ex, "Alterar", 2, icon);
        }
        conexao.Desconectar(); //Desconectando do banco.
      
           return acessos; //Retornando a ArrayList criada. 

    }

}
