/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.TimeLineController;
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
public class TimeLineDao {
    ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
        public void SalvarTimeLine(TimeLineController time) {
       
        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            String sql="insert into `timeline` (`id_aluno_timeline`,`data_timeline`,`horario_timeline`,`detalhes_timeline`, `id_usuario_timeline`,`status_timeline`) VALUES (?,CURRENT_DATE(), NOW(),?,?,?)";
            //JOptionPane.showMessageDialog(null, sql);
            PreparedStatement pst = conexao.connection.prepareStatement(sql);

            //Enviando dados para o banco.
            pst.setInt(1,time.getIdTimeLineAluno());
            pst.setString(2,time.getDetalhesTimeLine());
            pst.setInt(3,time.getIdUsuarioTimeLine());
            pst.setInt(4,time.getStatusTimeLine());
            System.out.println(sql);
            pst.execute();

          ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Salvo", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!"+ex, "Salvar", 2, icon);

        }
        

        conexao.Desconectar(); //Desconectando do banco.
        
    }
        public boolean existeTimeLineAluno(int id){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("SELECT * from `timeline` where `id_aluno_timeline`="+id);
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
        
      public void ExcluirTimeLine(TimeLineController time) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma exclusão.
            PreparedStatement pst = conexao.connection.prepareStatement("delete from `timeline` where `id_timeline` = ?");
            pst.setInt(1, time.getIdTimeLine());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "Excluído", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Excluir", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
      
    public void AtualizarTimeLine(TimeLineController time) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma atualização.
            String sql="";
            sql="update `timeline` set `detalhes_timeline` = ?, status_timeline=? where `id_timeline` = ?";
            PreparedStatement pst = conexao.connection.prepareStatement(sql);

            //Enviando dados para o banco.
            pst.setString(1, time.getDetalhesTimeLine());
            pst.setInt(2, time.getStatusTimeLine());
            pst.setInt(3, time.getIdTimeLine());
            pst.execute();
            

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
        public List<TimeLineController> CarregarTimeLineId(Integer id) {

        List<TimeLineController> times = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        String sql="";
        
        sql="select * "
                + " from `timeline` "
                + "inner join `usuario` on `timeline`.`id_usuario_timeline`=`usuario`.`idusuario`"
                + "inner join `aluno` on `timeline`.`id_aluno_timeline`=`aluno`.`idaluno`"
                + "where `timeline`.`id_timeline`="+id;
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                TimeLineController time = new TimeLineController();
               //Dados para carregarem os valores das linhas da Tabela.
                time.setIdTimeLine(conexao.rs.getInt("id_timeline"));
                time.setIdTimeLineAluno(conexao.rs.getInt("id_aluno_timeline"));        
                time.setIdUsuarioTimeLine(conexao.rs.getInt("id_usuario_timeline"));
                time.setDetalhesTimeLine(conexao.rs.getString("detalhes_timeline"));
                time.setDataTimeLine(conexao.rs.getString("data_timeline"));
                time.setHorarioTimeLine(conexao.rs.getString("horario_timeline"));
                time.setNomealuno(conexao.rs.getString("nomealuno"));
                time.setNomeusuario(conexao.rs.getString("nomeusuario"));
                time.setStatusTimeLine(conexao.rs.getInt("status_timeline"));
                times.add(time); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
           ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Listar todas time line", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return times; //Retornando a ArrayList criada.

    }
        public List<TimeLineController> CarregarIdTimeLine(int id) {

        List<TimeLineController> times = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select `timeline`.`id_aluno_timeline`,`timeline`.`id_timeline`,`aluno`.`idaluno`,`aluno`.`nomealuno`,`timeline`.`detalhes_timeline`,`usuario`.`nomeusuario`,`usuario`.`idusuario` from `timeline` inner join `usuario` on `timeline`.`id_usuario_timeline`=`usuario`.`idusuario` INNER JOIN `aluno` on `timeline`.`id_aluno_timeline`=`aluno`.`idaluno` where  `timeline`.`id_timeline` =" + id+""); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                TimeLineController time = new TimeLineController();
                //Dados para carregarem os valores das linhas da Tabela.
                time.setIdTimeLine(conexao.rs.getInt("id_timeline"));
                time.setIdTimeLineAluno(conexao.rs.getInt("id_aluno_timeline"));        
                time.setIdUsuarioTimeLine(conexao.rs.getInt("id_usuario_timeline"));
                time.setDetalhesTimeLine(conexao.rs.getString("detalhes_timeline"));
                time.setDataTimeLine(conexao.rs.getString("data_timeline"));
                time.setHorarioTimeLine(conexao.rs.getString("horario_timeline"));
                time.setNomealuno(conexao.rs.getString("nomealuno"));
                time.setStatusTimeLine(conexao.rs.getInt("status_timeline"));
                time.setNomeusuario(conexao.rs.getString("nomeusuario"));
                times.add(time); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
           ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Carregar time line id", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return times; //Retornando a ArrayList criada.

    }
        public List<TimeLineController> CarregarIdAlunoTimeLine(int id) {

        List<TimeLineController> times = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
         String sql="";
        
        sql="select * "
                + " from `timeline` "
                + "inner join `usuario` on `timeline`.`id_usuario_timeline`=`usuario`.`idusuario`"
                + "inner join `aluno` on `timeline`.`id_aluno_timeline`=`aluno`.`idaluno`"
                + "where `aluno`.`idaluno`="+id + " order by data_timeline desc";       
        //Comando para listar.
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                TimeLineController time = new TimeLineController();
                //Dados para carregarem os valores das linhas da Tabela.
                time.setIdTimeLine(conexao.rs.getInt("id_timeline"));
                time.setIdTimeLineAluno(conexao.rs.getInt("id_aluno_timeline"));        
                time.setIdUsuarioTimeLine(conexao.rs.getInt("id_usuario_timeline"));
                time.setDetalhesTimeLine(conexao.rs.getString("detalhes_timeline"));
                time.setDataTimeLine(conexao.rs.getString("data_timeline"));
                time.setHorarioTimeLine(conexao.rs.getString("horario_timeline"));
                time.setNomealuno(conexao.rs.getString("nomealuno"));
                time.setStatusTimeLine(conexao.rs.getInt("status_timeline"));
                time.setNomeusuario(conexao.rs.getString("nomeusuario"));
                times.add(time); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return times; //Retornando a ArrayList criada.

    }
        public List<TimeLineController> CarregarTimeTodos(String nome) {

        List<TimeLineController> times = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        String sql="";
        
        sql="select * "
                + " from `timeline` "
                + "inner join `usuario` on `timeline`.`id_usuario_timeline`=`usuario`.`idusuario`"
                + "inner join `aluno` on `timeline`.`id_aluno_timeline`=`aluno`.`idaluno` where `aluno`.`nomealuno` like '%"+nome+"%'";
        
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
//        conexao.ExecutarSQL("select `timeline`.`id_aluno_timeline`,`timeline`.`id_timeline`,`aluno`.`idaluno`,`aluno`.`nomealuno`,`timeline`.`detalhes_timeline`,`usuario`.`nomeusuario`,`usuario`.`idusuario` from `timeline` inner join `usuario` on `timeline`.`id_usuario_timeline`=`usuario`.`idusuario` INNER JOIN `aluno` on `timeline`.`id_aluno_timeline`=`aluno`.`idaluno`"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                TimeLineController time = new TimeLineController();
                //Dados para carregarem os valores das linhas da Tabela.
                time.setIdTimeLine(conexao.rs.getInt("id_timeline"));

                time.setIdTimeLineAluno(conexao.rs.getInt("id_aluno_timeline"));  

                time.setIdUsuarioTimeLine(conexao.rs.getInt("id_usuario_timeline"));
                
                time.setStatusTimeLine(conexao.rs.getInt("status_timeline"));

                time.setDetalhesTimeLine(conexao.rs.getString("detalhes_timeline"));

                time.setDataTimeLine(conexao.rs.getString("data_timeline"));
  
                time.setHorarioTimeLine(conexao.rs.getString("horario_timeline"));
 
                time.setNomealuno(conexao.rs.getString("nomealuno"));
       
                time.setNomeusuario(conexao.rs.getString("nomeusuario"));
          
                times.add(time); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
           ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Carregar time lines", 2, icon);
              
        }

        conexao.Desconectar(); //Desconectando do banco.

        return times; //Retornando a ArrayList criada.

    }
}
