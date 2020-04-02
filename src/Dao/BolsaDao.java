/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.BolsaController;
import static Dao.AlunoDao.criptografia;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class BolsaDao {
     ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
     public void SalvarBolsa(BolsaController bolsa) throws UnsupportedEncodingException {
         conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            PreparedStatement pst = conexao.connection.prepareStatement("insert into `bolsa`(`nomebolsa`,`valorbolsa`) values (?,?)");

                                                                        //Enviando dados para o banco.
                                                                        pst.setString(1,bolsa.getNomebolsa());
                                                                        pst.setDouble(2,bolsa.getValorbolsa());

                                                                        pst.execute();

          ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Salvo", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!"+ex, "Salvar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.
     }
            public void AtualizarBolsa(BolsaController bolsa) throws UnsupportedEncodingException {

        conexao.Conectar(); //Conectando ao banco.

        try {
                    String sql=null;
  
            sql="update `bolsa` set `nomebolsa` = ?, `valorbolsa` = ? where `idbolsa` = ?";
            //System.out.println(sql);
            PreparedStatement pst = conexao.connection.prepareStatement(sql);
                pst.setString(1,bolsa.getNomebolsa());
                pst.setDouble(2, bolsa.getValorbolsa());
                pst.execute();
        
       
     
                 ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
     
        public void ExcluirBolsa(BolsaController bolsa) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma exclusão.
            PreparedStatement pst = conexao.connection.prepareStatement("delete from `bolsa` where `idbolsa` = ?");
            pst.setInt(1, bolsa.getIdbolsa());
            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!", "Excluído", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Excluir", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
           public List<BolsaController> buscarBolsaId(Integer id){
              List<BolsaController> bolsas = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `bolsa` where `idbolsa` =" + id); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
      try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                BolsaController bolsa = new BolsaController();
                //Dados para carregarem os valores das linhas da Tabela.
                bolsa.setIdbolsa(conexao.rs.getInt("idbolsa"));
                bolsa.setNomebolsa(conexao.rs.getString("nomebolsa"));
                bolsa.setValorbolsa(conexao.rs.getDouble("valorbolsa"));
                bolsas.add(bolsa); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return bolsas; //Retornando a ArrayList criada.
   
      } 
           public List<BolsaController> buscarBolsas(){
              List<BolsaController> bolsas = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `bolsa` order by `idbolsa` asc"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
      try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                BolsaController bolsa = new BolsaController();
                //Dados para carregarem os valores das linhas da Tabela.
                bolsa.setIdbolsa(conexao.rs.getInt("idbolsa"));
                bolsa.setNomebolsa(conexao.rs.getString("nomebolsa"));
                bolsa.setValorbolsa(conexao.rs.getDouble("valorbolsa"));
                bolsas.add(bolsa); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return bolsas; //Retornando a ArrayList criada.
   
      } 
     }

