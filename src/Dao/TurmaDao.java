/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.CursoController;
import Controller.TurmaController;
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
public class TurmaDao {
    
        ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
         
        public void SalvarTurma(TurmaController turma) {
       if(!existeTurma(turma.getNometurma(),turma.getCursoid())){
        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            String sql="insert into `turma` (`nometurma`, `usuid`,`cursoid`) VALUES (?,?,?)";
            //JOptionPane.showMessageDialog(null, sql);
            PreparedStatement pst = conexao.connection.prepareStatement(sql);

            //Enviando dados para o banco.
            pst.setString(1,turma.getNometurma());
            pst.setInt(2,turma.getUsuid());
            pst.setInt(3,turma.getCursoid());
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
               public boolean existeTurma(String nome, int idcurso){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("select `nometurma`,`cursoid` from `turma` where `nometurma` like "+"'"+nome+"' and `cursoid`="+idcurso);
        try {   
            conexao.rs.first();
            if(conexao.rs.getRow()>0){
                JOptionPane.showMessageDialog(null, "Ops! Esta Turma já foi cadastrada para esté curso!!!");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    } 
            public String buscarnome(int id) throws SQLException{
             conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("select `nometurma` from `turma` where `idturma` ="+id);
            conexao.rs.first();
        return conexao.rs.getString("nometurma");
        }    
      public void ExcluirTurma(TurmaController turma) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma exclusão.
            PreparedStatement pst = conexao.connection.prepareStatement("delete from `turma` where `idturma` = ?");
            pst.setInt(1, turma.getIdturma());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "Excluído", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Excluir", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
      
    public void AtualizarTurma(TurmaController turma) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement("update `turma` set `nometurma` = ?, `cursoid`=? where `idturma` = ?");

            //Enviando dados para o banco.
            pst.setString(1, turma.getNometurma());
            pst.setInt(2, turma.getCursoid());
            pst.setInt(3, turma.getIdturma());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
        public List<TurmaController> CarregarTurmaId(Integer id) {

        List<TurmaController> turmas = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select `turma`.`idturma`,`turma`.`nometurma`,`turma`.`usuid`,`usuario`.`nomeusuario`,`turma`.`cursoid`,`curso`.`nomecurso` from `turma` inner join `usuario` on `turma`.`usuid`=`usuario`.`idusuario` inner join `curso` on `turma`.`cursoid`=`curso`.`idcurso` where `idturma`=" + id ); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                TurmaController turma = new TurmaController();
                //Dados para carregarem os valores das linhas da Tabela.
                turma.setIdturma(conexao.rs.getInt("idturma"));
                turma.setUsuid(conexao.rs.getInt("usuid"));
                turma.setCursoid(conexao.rs.getInt("cursoid"));
                turma.setNometurma(conexao.rs.getString("nometurma"));
                turmas.add(turma); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return turmas; //Retornando a ArrayList criada.

    }
        public List<TurmaController> CarregarTurmaIdCurso(Integer id) {

        List<TurmaController> turmas = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
       // conexao.ExecutarSQL("SELECT TURMA.IDTURMA,TURMA.NOMETURMA, TURMA.USUID,USUARIO.NOMEUSUARIO,TURMA.CURSOID,CURSO.NOMECURSO FROM TURMA INNER JOIN USUARIO ON TURMA.USUID=USUARIO.IDUSUARIO INNER JOIN CURSO ON TURMA.CURSOID=CURSO.IDCURSO  WHERE TURMA.CURSOID=" + id +"ORDER BY TURMA.NOMETURMA"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
        conexao.ExecutarSQL("select `turma`.`idturma`,`turma`.`nometurma`,`turma`.`usuid`,`usuario`.`nomeusuario`,`turma`.`cursoid`,`curso`.`nomecurso` from `turma` inner join `usuario` on `turma`.`usuid`=`usuario`.`idusuario` inner join `curso` on `turma`.`cursoid`=`curso`.`idcurso` where `turma`.`cursoid`=" + id+" order by `turma`.`nometurma`"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                TurmaController turma = new TurmaController();
                //Dados para carregarem os valores das linhas da Tabela.
                turma.setIdturma(conexao.rs.getInt("idturma"));
                turma.setUsuid(conexao.rs.getInt("usuid"));
                turma.setCursoid(conexao.rs.getInt("cursoid"));
                turma.setNometurma(conexao.rs.getString("nometurma"));
                turmas.add(turma); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return turmas; //Retornando a ArrayList criada.

    }
        public List<TurmaController> CarregarTurmaIdCursoNomeCurso(Integer id,String nomecurso) {

        List<TurmaController> turmas = new ArrayList<>(); //Instanciando um ArrayList.
        String sql="";
        conexao.Conectar();
        //Comando para listar.
        //conexao.ExecutarSQL("SELECT TURMA.IDTURMA,TURMA.NOMETURMA, TURMA.USUID,USUARIO.NOMEUSUARIO,TURMA.CURSOID,CURSO.NOMECURSO FROM TURMA INNER JOIN USUARIO ON TURMA.USUID=USUARIO.IDUSUARIO INNER JOIN CURSO ON TURMA.CURSOID=CURSO.IDCURSO  WHERE TURMA.CURSOID=" + id +" AND NOMETURMA LIKE '"+nomecurso+"' ORDER BY TURMA.NOMETURMA"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
        sql="select `turma`.`idturma`,`turma`.`nometurma`,`turma`.`usuid`,`usuario`.`nomeusuario`,`turma`.`cursoid`,`curso`.`nomecurso` from `turma` inner join `usuario` on `turma`.`usuid`=`usuario`.`idusuario` inner join `curso` on `turma`.`cursoid`=`curso`.`idcurso` where `turma`.`cursoid`=" + id+" and `nometurma`like'"+nomecurso+"' order by `turma`.`nometurma`";
        
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                TurmaController turma = new TurmaController();
                //Dados para carregarem os valores das linhas da Tabela.
                turma.setIdturma(conexao.rs.getInt("idturma"));
                turma.setUsuid(conexao.rs.getInt("usuid"));
                turma.setCursoid(conexao.rs.getInt("cursoid"));
                turma.setNometurma(conexao.rs.getString("nometurma"));
                turmas.add(turma); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return turmas; //Retornando a ArrayList criada.

    }
        public List<TurmaController> CarregarTurmaNome(String nome) {

        List<TurmaController> turmas = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select `turma`.`idturma`,`turma`.`nometurma`,`turma`.`usuid`,`usuario`.`nomeusuario`,`turma`.`cursoid`,`curso`.`nomecurso` from `turma` inner join `usuario` on `usuid`=`idusuario` inner join `curso` on `cursoid` =`idcurso` where `nomecurso` like '" + nome+"'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {
                TurmaController turma = new TurmaController();
                //Dados para carregarem os valores das linhas da Tabela.
                turma.setIdturma(conexao.rs.getInt("idturma"));
                turma.setUsuid(conexao.rs.getInt("usuid"));
                turma.setCursoid(conexao.rs.getInt("cursoid"));
                turma.setNometurma(conexao.rs.getString("nometurma"));
                turmas.add(turma); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return turmas; //Retornando a ArrayList criada.

    }
        public List<TurmaController> CarregarTurmasTodas() {

        List<TurmaController> turmas = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select `turma`.`idturma`,`turma`.`nometurma`,`turma`.`usuid`,`usuario`.`nomeusuario`,`turma`.`cursoid`,`curso`.`nomecurso` from `turma`  inner join `usuario` on `turma`.`usuid`=`usuario`.`idusuario` inner join `curso` on `turma`.`cursoid`=`curso`.`idcurso`"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                TurmaController turma = new TurmaController();
                //Dados para carregarem os valores das linhas da Tabela.
                turma.setIdturma(conexao.rs.getInt("idturma"));
                turma.setUsuid(conexao.rs.getInt("usuid"));
                turma.setCursoid(conexao.rs.getInt("cursoid"));
                turma.setNometurma(conexao.rs.getString("nometurma"));
                turma.setNomecurso(conexao.rs.getString("nomecurso"));
                turma.setNomeusuario(conexao.rs.getString("nomeusuario"));
                turmas.add(turma); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return turmas; //Retornando a ArrayList criada.

    }
}
