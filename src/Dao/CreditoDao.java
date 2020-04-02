/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.AlunoController;
import Controller.CreditoController;
import Controller.UsuarioController;
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
public class CreditoDao {
    
    ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
    

    
    public void SalvarCredito(CreditoController credito) {
         if(!existeCredito(credito.getIdcredaluno())){
        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            String sql="INSERT INTO `creditorefeicao` (`idcredaluno`, `tipobolsa`, `datainicial`, `datafinal`, `totalcreditos`, `creditodia`, `observacaocredito`,`creditosutilizados`,`controle`,`usuid`,bloquearaluno) VALUES (?,?,?,?,?,?,?,0.00,?,?,0)";
            //JOptionPane.showMessageDialog(null, sql);
            PreparedStatement pst = conexao.connection.prepareStatement(sql);

            //Enviando dados para o banco.
            pst.setInt(1,credito.getIdcredaluno());
            pst.setInt(2,credito.getTipobolsa());
            pst.setString(3, credito.getDatainicial());
            pst.setString(4, credito.getDatafinal());
            pst.setDouble(5, credito.getTotalcreditos());
            pst.setDouble(6, credito.getCreditodia());
            pst.setString(7, credito.getObservacaocredito());            
            pst.setInt(8, credito.getControle());
            pst.setInt(9, credito.getUsuid());
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
    public void SalvarCreditoAutomatico(CreditoController credito) {
         if(!existeCredito(credito.getIdcredaluno())){
        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            String sql="INSERT INTO `creditorefeicao` (`idcredaluno`, `tipobolsa`, `datainicial`, `datafinal`, `totalcreditos`, `creditodia`,`creditosutilizados`,`controle`,`usuid`,bloquearaluno,observacaocredito) VALUES (?,?,?,?,?,?,0.00,?,?,?,'')";
            //JOptionPane.showMessageDialog(null, sql);
            PreparedStatement pst = conexao.connection.prepareStatement(sql);

            //Enviando dados para o banco.
            pst.setInt(1,credito.getIdcredaluno());
            pst.setInt(2,credito.getTipobolsa());
            pst.setString(3, credito.getDatainicial());
            pst.setString(4, credito.getDatafinal());
            pst.setDouble(5, credito.getTotalcreditos());
            pst.setDouble(6, credito.getCreditodia());           
            pst.setInt(7, credito.getControle());
            pst.setInt(8, credito.getUsuid());
            pst.setInt(9, credito.getBloquealuno());
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
    public Integer buscarTipoBolsa(int id) throws SQLException{
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("SELECT `tipobolsa` from `aluno` where `idaluno`="+id);
        conexao.rs.first();
        return conexao.rs.getInt("tipobolsa");
    }
    public boolean existeCredito(int idaluno){
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("SELECT `tipobolsa` from `creditorefeicao` where `idcredaluno`="+idaluno);
        try {   
            conexao.rs.first();
            if(conexao.rs.getRow()>0){
                JOptionPane.showMessageDialog(null, "Já existe créditos salvos para esse aluno");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
       public Double buscarCreditosUtilizados(int id) throws SQLException{
         Double total=0.00;
         Double creditos=0.00;
        conexao.Conectar(); //Conectando ao banco.
        conexao.ExecutarSQL("SELECT `creditosutilizados` from `creditorefeicao` where `idcredaluno`="+id);
        conexao.rs.first();
        creditos=conexao.rs.getDouble("creditosutilizados");
        conexao.Desconectar();
        total=creditos+1.00;
        return total;
        
    }
    public List<AlunoController> list() {

        List<AlunoController> aluno = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("SELECT* from aluno order by nomealuno");

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                AlunoController alunos = new AlunoController();
                //Dados para carregarem os valores das linhas da Tabela.
                alunos.setIdaluno(conexao.rs.getInt("idaluno"));
                alunos.setNomealuno(conexao.rs.getString("nomealuno"));
                alunos.setEmailaluno(conexao.rs.getString("emailaluno"));
                alunos.setSenhaaluno(conexao.rs.getString("senhaaluno"));

                aluno.add(alunos); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Tabela vazia!", "Usuários", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return aluno; //Retornando valores armazenados no list.

    }


    public void ExcluirCredito(CreditoController credito) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma exclusão.
            PreparedStatement pst = conexao.connection.prepareStatement("DELETE FROM CREDITOREFEICAO WHERE IDCREDITO = ?");
            pst.setInt(1, credito.getIdcredito());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "Excluído", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Excluir", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
     public void AtualizarCreditoUtilizados(int idaluno) throws SQLException { 
         Double creditoscalculados=buscarCreditosUtilizados(idaluno);
         conexao.Conectar(); //Conectando ao banco.
         PreparedStatement pst;
        try {
            pst = conexao.connection.prepareStatement("UPDATE CREDITOREFEICAO SET CREDITOSUTILIZADOS =? WHERE IDCREDALUNO=?");
            pst.setDouble(1,creditoscalculados);
            pst.setInt(2,idaluno);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         conexao.Desconectar();
     }
   
    public void AtualizarCredito(CreditoController credito) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement("UPDATE CREDITOREFEICAO SET IDCREDALUNO = ?, DATAINICIAL = ?,DATAFINAL = ?, TOTALCREDITOS = ?, CREDITODIA = ?,BLOQUEARALUNO=?,OBSERVACAOCREDITO=?,TIPOBOLSA=?,CONTROLE=?  WHERE IDCREDITO = ?");

            //Enviando dados para o banco.
            pst.setInt(1, credito.getIdcredaluno());
            pst.setString(2, credito.getDatainicial());
            pst.setString(3, credito.getDatafinal());
            pst.setDouble(4, credito.getTotalcreditos());
            pst.setDouble(5, credito.getCreditodia());
            pst.setInt(6, credito.getBloquealuno());
            pst.setString(7, credito.getObservacaocredito());
            pst.setInt(8,credito.getTipobolsa());
            pst.setInt(9,credito.getControle());
            pst.setInt(10, credito.getIdcredito());
            
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

        Vector VectorCreditos = new Vector(); //Vetor para armazenar dados consultados.

        conexao.Conectar();
        //Pesquisa generalizada na tabela.
        conexao.ExecutarSQL("SELECT * FROM CREDITOREFEICAO WHERE IDCREDITO ='%" + pesquisa + "%'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        conexao.rs.first(); //Serve para filtrar o primeiro resultado.

        try {
            do {

                Vector creditos = new Vector();
                //Dados para carregarem os valores das linhas da Tabela.
                creditos.add(conexao.rs.getInt("IDCREDITO"));
                creditos.add(conexao.rs.getInt("IDCREDALUNO"));
                creditos.add(conexao.rs.getString("DATAINICIAL"));
                creditos.add(conexao.rs.getString("DATAFINAL"));
                creditos.add(conexao.rs.getDouble("TOTALCREDITOS"));
                creditos.add(conexao.rs.getDouble("CREDITODIA"));
                creditos.add(conexao.rs.getInt("BLOQUEARALUNO"));
                creditos.add(conexao.rs.getString("OBSERVACAOCREDITO"));

                VectorCreditos.add(creditos); //Salvando dados no vetor.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Sem resultado!", "Pesquisa", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return VectorCreditos; //Retornando os valores armazenados no vetor.

    }
    public Vector PesquisaEntreDatas(String datainicial, String datafinal) throws SQLException {

        Vector VectorCreditos = new Vector(); //Vetor para armazenar dados consultados.

        conexao.Conectar();
        //Pesquisa generalizada na tabela.
        conexao.ExecutarSQL("SELECT * FROM `creditorefeicao` WHERE `datainicial` >='"+datainicial+"' and `datafinal` <='"+datafinal+"'"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        conexao.rs.first(); //Serve para filtrar o primeiro resultado.

        try {
            do {

                Vector creditos = new Vector();
                //Dados para carregarem os valores das linhas da Tabela.
                creditos.add(conexao.rs.getInt("IDCREDITO"));
                creditos.add(conexao.rs.getInt("IDCREDALUNO"));
                creditos.add(conexao.rs.getString("DATAINICIAL"));
                creditos.add(conexao.rs.getString("DATAFINAL"));
                creditos.add(conexao.rs.getDouble("TOTALCREDITOS"));
                creditos.add(conexao.rs.getDouble("CREDITODIA"));
                creditos.add(conexao.rs.getInt("TIPOBOLSA"));
                creditos.add(conexao.rs.getInt("BLOQUEARALUNO"));
                creditos.add(conexao.rs.getString("OBSERVACAOCREDITO"));

                VectorCreditos.add(creditos); //Salvando dados no vetor.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Sem resultado!", "Pesquisa", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

        return VectorCreditos; //Retornando os valores armazenados no vetor.

    }




    public List<CreditoController> CarregarCredito(Integer id) {

        List<CreditoController> creditos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.

//        conexao.ExecutarSQL("SELECT creditorefeicao.idcredito,creditorefeicao.idcredaluno,creditorefeicao.tipobolsa," +
//                            "creditorefeicao.creditodia,creditorefeicao.datainicial,creditorefeicao.datafinal," +
//                            " creditorefeicao.observacaocredito,creditorefeicao.totalcreditos," +
//                            " creditorefeicao.creditosutilizados, creditorefeicao.usuid," +
//                            " usuario.nomeusuario FROM `creditorefeicao` Inner join `aluno` on `creditorefeicao`.`idcredaluno`=`aluno`.`idaluno`" +
//                            " inner join usuario on creditorefeicao.usuid=usuario.idusuario"
//                            + " WHERE IDCREDITO='" + id + "'");
        conexao.ExecutarSQL("SELECT * FROM `creditorefeicao` Inner join `aluno` on `creditorefeicao`.`idcredaluno`=`aluno`.`idaluno`" +
                            " inner join usuario on creditorefeicao.usuid=usuario.idusuario"
                            + " WHERE IDCREDITO='" + id + "'");
        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                CreditoController credito = new CreditoController();
                //Dados para carregarem os valores das linhas da Tabela.
                credito.setIdcredito(conexao.rs.getInt("idcredito"));
                credito.setUsuid(conexao.rs.getInt("usuid"));
                credito.setControle(conexao.rs.getInt("controle"));
                credito.setIdcredaluno(conexao.rs.getInt("idcredaluno"));
                credito.setNomealuno(conexao.rs.getString("nomealuno"));
                credito.setNomeusuario(conexao.rs.getString("nomeusuario"));
                credito.setBloquealuno(conexao.rs.getInt("bloquearaluno"));
                credito.setTipobolsa(conexao.rs.getInt("tipobolsa"));
                credito.setCreditodia(conexao.rs.getDouble("creditodia"));
                credito.setDatainicial(conexao.rs.getString("datainicial"));
                credito.setDatafinal(conexao.rs.getString("datafinal"));
                credito.setObservacaocredito(conexao.rs.getString("observacaocredito"));
                credito.setTotalcreditos(conexao.rs.getDouble("totalcreditos"));
                credito.setCreditosutilizados(conexao.rs.getDouble("creditosutilizados"));

                creditos.add(credito); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return creditos; //Retornando a ArrayList criada.

    }
    public List<CreditoController> CarregarEntreDatas(String datainicial, String datafinal) {

        List<CreditoController> creditos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
//        conexao.ExecutarSQL("SELECT creditorefeicao.idcredito,creditorefeicao.idcredaluno,creditorefeicao.tipobolsa," +
//                            "creditorefeicao.creditodia,creditorefeicao.datainicial,creditorefeicao.datafinal," +
//                            " creditorefeicao.observacaocredito,creditorefeicao.totalcreditos," +
//                            " creditorefeicao.creditosutilizados, creditorefeicao.usuid," +
//                            " usuario.nomeusuario FROM `creditorefeicao` Inner join `aluno` on `creditorefeicao`.`idcredaluno`=`aluno`.`idaluno`" +
//                            " inner join usuario on creditorefeicao.usuid=usuario.idusuario"
//                            + " WHERE `datainicial` >='"+datainicial+"' and `datafinal` <='"+datafinal+"'"); 
        conexao.ExecutarSQL("SELECT * FROM `creditorefeicao` Inner join `aluno` on `creditorefeicao`.`idcredaluno`=`aluno`.`idaluno`" +
                            " inner join usuario on creditorefeicao.usuid=usuario.idusuario"
                            + " WHERE `datainicial` >='"+datainicial+"' and `datafinal` <='"+datafinal+"'"); 

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                CreditoController credito = new CreditoController();
                //Dados para carregarem os valores das linhas da Tabela.
                credito.setIdcredito(conexao.rs.getInt("idcredito"));
                credito.setUsuid(conexao.rs.getInt("usuid"));
                credito.setControle(conexao.rs.getInt("controle"));
                credito.setIdcredaluno(conexao.rs.getInt("idcredaluno"));
                credito.setNomealuno(conexao.rs.getString("nomealuno"));
                credito.setNomeusuario(conexao.rs.getString("nomeusuario"));
                credito.setBloquealuno(conexao.rs.getInt("bloquearaluno"));
                credito.setControle(conexao.rs.getInt("controle"));                
                credito.setTipobolsa(conexao.rs.getInt("tipobolsa"));
                credito.setCreditodia(conexao.rs.getDouble("creditodia"));
                credito.setDatainicial(conexao.rs.getString("datainicial"));
                credito.setDatafinal(conexao.rs.getString("datafinal"));
                credito.setObservacaocredito(conexao.rs.getString("observacaocredito"));
                credito.setTotalcreditos(conexao.rs.getDouble("totalcreditos"));
                credito.setCreditosutilizados(conexao.rs.getDouble("creditosutilizados"));

                creditos.add(credito); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return creditos; //Retornando a ArrayList criada.

    }
    public List<CreditoController> CarregarEntreDatasIdaluno(String datainicial, String datafinal, Integer id) {

        List<CreditoController> creditos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
//        conexao.ExecutarSQL("SELECT creditorefeicao.idcredito,creditorefeicao.idcredaluno,creditorefeicao.tipobolsa," +
//                            "creditorefeicao.creditodia,creditorefeicao.datainicial,creditorefeicao.datafinal," +
//                            " creditorefeicao.observacaocredito,creditorefeicao.totalcreditos," +
//                            " creditorefeicao.creditosutilizados, creditorefeicao.usuid," +
//                            " usuario.nomeusuario FROM `creditorefeicao` Inner join `aluno` on `creditorefeicao`.`idcredaluno`=`aluno`.`idaluno`" +
//                            " inner join usuario on creditorefeicao.usuid=usuario.idusuario"
//                            + " WHERE `datainicial` >='"+datainicial+"' and `datafinal` <='"+datafinal+"' and `aluno`.`idaluno`="+id);
        
        conexao.ExecutarSQL("SELECT * FROM `creditorefeicao` Inner join `aluno` on `creditorefeicao`.`idcredaluno`=`aluno`.`idaluno`" +
                            " inner join usuario on creditorefeicao.usuid=usuario.idusuario"
                            + " WHERE `datainicial` >='"+datainicial+"' and `datafinal` <='"+datafinal+"' and `aluno`.`idaluno`="+id);
        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                CreditoController credito = new CreditoController();
                //Dados para carregarem os valores das linhas da Tabela.
                credito.setIdcredito(conexao.rs.getInt("idcredito"));
                credito.setUsuid(conexao.rs.getInt("usuid"));
                credito.setControle(conexao.rs.getInt("controle"));
                credito.setIdcredaluno(conexao.rs.getInt("idcredaluno"));
                credito.setNomealuno(conexao.rs.getString("nomealuno"));
                credito.setNomeusuario(conexao.rs.getString("nomeusuario"));
                credito.setBloquealuno(conexao.rs.getInt("bloquearaluno"));
                credito.setTipobolsa(conexao.rs.getInt("tipobolsa"));
                credito.setControle(conexao.rs.getInt("controle"));                
                credito.setCreditodia(conexao.rs.getDouble("creditodia"));
                credito.setDatainicial(conexao.rs.getString("datainicial"));
                credito.setDatafinal(conexao.rs.getString("datafinal"));
                credito.setObservacaocredito(conexao.rs.getString("observacaocredito"));
                credito.setTotalcreditos(conexao.rs.getDouble("totalcreditos"));
                credito.setCreditosutilizados(conexao.rs.getDouble("creditosutilizados"));

                creditos.add(credito); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return creditos; //Retornando a ArrayList criada.

    }
        public List<CreditoController> CarregarTodosCreditos() {

        List<CreditoController> creditos = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        //conexao.ExecutarSQL("SELECT*FROM CREDITOREFEICAO INNER JOIN ALUNO ON idcredaluno = idaluno"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
        //conexao.ExecutarSQL("SELECT `creditorefeicao`.`idcredito`,`creditorefeicao`.`idcredaluno`,`creditorefeicao`.`tipobolsa`,`creditorefeicao`.`creditodia`,`creditorefeicao`.`datainicial`,`creditorefeicao`.`datafinal`,`creditorefeicao`.`observacaocredito`,`creditorefeicao`.`totalcreditos`,`aluno`.`nomealuno`, `aluno`.`idaluno`,`creditorefeicao`.`creditosutilizados`, `creditorefeicao`.`usuid`,`usuario`.`nomeusuario` FROM `creditorefeicao` INNER JOIN `aluno` on `creditorefeicao`.`idcredaluno`=`aluno`.`idaluno` INNER JOIN `usuario` on `creditorefeicao`.`usuid`=`usuario`.`idusuario`");
        
        conexao.ExecutarSQL("SELECT * FROM `creditorefeicao` INNER JOIN `aluno` on `creditorefeicao`.`idcredaluno`=`aluno`.`idaluno` INNER JOIN `usuario` on `creditorefeicao`.`usuid`=`usuario`.`idusuario`");
        
        try {
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.
            do {
                CreditoController credito = new CreditoController();
                //Dados para carregarem os valores das linhas da Tabela.
                credito.setIdcredito(conexao.rs.getInt("idcredito"));
                credito.setUsuid(conexao.rs.getInt("usuid"));
                credito.setControle(conexao.rs.getInt("controle"));
                credito.setIdcredaluno(conexao.rs.getInt("idcredaluno"));
                credito.setNomealuno(conexao.rs.getString("nomealuno"));
                credito.setNomeusuario(conexao.rs.getString("nomeusuario"));
                credito.setBloquealuno(conexao.rs.getInt("bloquearaluno"));
                credito.setTipobolsa(conexao.rs.getInt("tipobolsa"));
                credito.setCreditodia(conexao.rs.getDouble("creditodia"));
                credito.setControle(conexao.rs.getInt("controle"));
                credito.setDatainicial(conexao.rs.getString("datainicial"));
                credito.setDatafinal(conexao.rs.getString("datafinal"));
                credito.setObservacaocredito(conexao.rs.getString("observacaocredito"));
                credito.setTotalcreditos(conexao.rs.getDouble("totalcreditos"));
                credito.setCreditosutilizados(conexao.rs.getDouble("creditosutilizados"));
                creditos.add(credito); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return creditos; //Retornando a ArrayList criada.

    }
}
