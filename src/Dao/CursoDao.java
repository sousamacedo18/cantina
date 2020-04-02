/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.CreditoController;
import Controller.CursoController;
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
public class CursoDao {
    ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
        public void SalvarCurso(CursoController curso) {
        if(!existeCurso(curso.getNomecurso())){
        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            String sql="insert into `curso` (`nomecurso`, `usuid`) VALUES (?,?)";
            //JOptionPane.showMessageDialog(null, sql);
            PreparedStatement pst = conexao.connection.prepareStatement(sql);

            //Enviando dados para o banco.
            pst.setString(1,curso.getNomecurso());
            pst.setInt(2,curso.getUsuid());
            
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
        public boolean existeCurso(String nome){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("select `idcurso` from `curso` where `nomecurso` like '"+nome+"'");
        try {   
            conexao.rs.first();
            if(conexao.rs.getRow()>0){
                JOptionPane.showMessageDialog(null, "Ops! Este Curso já foi cadastrado!!!");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
        public String buscarnome(int id) throws SQLException{
             conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("SELECT `nomecurso` from `curso` where `idcurso` ="+id);
            conexao.rs.first();
        return conexao.rs.getString("nomecurso");
        }
        
      public void ExcluirCurso(CursoController curso) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma exclusão.
            PreparedStatement pst = conexao.connection.prepareStatement("delete from `curso` where `idcurso` = ?");
            pst.setInt(1, curso.getIdcurso());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "Excluído", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Excluir", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
      
    public void AtualizarCurso(CursoController curso) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement("update `curso` set `nomecurso` = ? where `idcurso` = ?");

            //Enviando dados para o banco.
            pst.setString(1, curso.getNomecurso());
            pst.setInt(2, curso.getIdcurso());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
        public List<CursoController> CarregarCursoId(Integer id) {

        List<CursoController> cursos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select*from `curso` inner join `usuario` on `curso`.`usuid`=`usuario`.`idusuario` where `idcurso`=" + id ); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                CursoController curso = new CursoController();
                //Dados para carregarem os valores das linhas da Tabela.
                curso.setIdcurso(conexao.rs.getInt("idcurso"));
                curso.setUsuid(conexao.rs.getInt("usuid"));
                curso.setNomecurso(conexao.rs.getString("nomecurso"));
                curso.setNomeusuario(conexao.rs.getString("nomeusuario"));
                cursos.add(curso); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return cursos; //Retornando a ArrayList criada.

    }
        public List<CursoController> CarregarCursoNome(String nome) {

        List<CursoController> cursos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select `curso`.`idcurso`,`curso`.`usuid`,`curso`.`nomecurso`,`usuario`.`nomeusuario` from `curso` inner join `usuario` on `curso`.`usuid`=`usuario`.`idusuario` where `curso`.`nomecurso` like '" + nome+"'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                CursoController curso = new CursoController();
                //Dados para carregarem os valores das linhas da Tabela.
                curso.setIdcurso(conexao.rs.getInt("idcurso"));
                curso.setUsuid(conexao.rs.getInt("usuid"));
                curso.setNomecurso(conexao.rs.getString("nomecurso"));
                curso.setNomeusuario(conexao.rs.getString("nomeusuario"));
                cursos.add(curso); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return cursos; //Retornando a ArrayList criada.

    }
        public List<CursoController> CarregarCursoTodos() {

        List<CursoController> cursos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select `curso`.`idcurso`,`curso`.`nomecurso`,`usuario`.`nomeusuario`,`usuario`.`idusuario`, `curso`.`usuid` from `curso` inner join `usuario` on `curso`.`usuid`=`usuario`.`idusuario`"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                CursoController curso = new CursoController();
                //Dados para carregarem os valores das linhas da Tabela.
                curso.setIdcurso(conexao.rs.getInt("idcurso"));
                curso.setUsuid(conexao.rs.getInt("usuid"));
                curso.setNomecurso(conexao.rs.getString("nomecurso"));
                curso.setNomeusuario(conexao.rs.getString("nomeusuario"));
                cursos.add(curso); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return cursos; //Retornando a ArrayList criada.

    }
}
