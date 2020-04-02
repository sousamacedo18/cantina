/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.CursoController;
import Controller.ReservaController;
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
public class ReservaDao {
             ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
             
                 public void SalvarReserva(ReservaController reserva) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            PreparedStatement pst = conexao.connection.prepareStatement("insert into `reserva`(`idalureserva`, `datareserva`, `horareserva`, `tentativas`,`horariorefeicao`) VALUES (?,CURRENT_DATE(), NOW(), 1,'00:00:00')");

                                                                        //Enviando dados para o banco.
                                                                        pst.setInt(1,reserva.getIdalureserva());
                                                                        pst.execute();

//          ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
//            JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Salvo", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!"+ex, "Salvar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
  
       public List<ReservaController> CarregarHoje() {

        List<ReservaController> reservas = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `reserva` inner join `aluno` ON `aluno`.`idaluno`=`reserva`.`idalureserva` inner join `creditorefeicao` on `creditorefeicao`.`idcredaluno`=`reserva`.`idalureserva` WHERE `DATARESERVA`=CURDATE() ORDER BY `IDRESERVA` DESC"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {
           
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {
                ReservaController reserva = new ReservaController();
                //Dados para carregarem os valores das linhas da Tabela.
                reserva.setIdreserva(conexao.rs.getInt("idreserva"));
                reserva.setIdalureserva(conexao.rs.getInt("idalureserva"));
                reserva.setCpf(conexao.rs.getString("cpfaluno"));
                reserva.setNome(conexao.rs.getString("nomealuno"));
                reserva.setFoto(conexao.rs.getString("fotoaluno"));
                reserva.setDatareserva(conexao.rs.getString("datareserva"));
                reserva.setHorareserva(conexao.rs.getString("horareserva"));
                reserva.setTentativas(conexao.rs.getInt("tentativas"));
                reserva.setBolsa(conexao.rs.getInt("tipobolsa"));
                reserva.setHorarefeicao(conexao.rs.getString("horariorefeicao"));
                

                reservas.add(reserva); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
                System.out.println(ex);
        }

        conexao.Desconectar(); //Desconectando do banco.

        return reservas; //Retornando a ArrayList criada.

    }
           public void AtualizarTentativas(ReservaController reserva) throws SQLException {

        conexao.Conectar(); //Conectando ao banco.
        


        try {
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement("update `reserva` set `tentativas` = ? where `datareserva`=CURDATE() and `idalureserva` = ?");

            //Enviando dados para o banco.
            pst.setInt(1, reserva.getTentativas());
            pst.setInt(2, reserva.getIdalureserva());
            pst.execute();

//            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
//            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
           public void AtualizaRefeicao(ReservaController reserva) throws SQLException {

        conexao.Conectar(); //Conectando ao banco.
        


        try {
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement("update `reserva` set `horariorefeicao` = NOW where `datareserva`=CURDATE() and `idalureserva` = ?");

            //Enviando dados para o banco.
//            pst.setInt(1, reserva.getTentativas());
           pst.setInt(1, reserva.getIdalureserva());
            pst.execute();

//            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
//            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
    public void limparTudo(){
    
        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para realizar uma atualização.
            PreparedStatement pst = conexao.connection.prepareStatement("TRUNCATE TABLE `reserva`");

            pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }    
    }
    public Integer contarRegistros(Integer id) throws SQLException{
         conexao.Conectar(); //Conectando ao banco.
       Integer registro=0;
           conexao.ExecutarSQL("select COUNT(`idalureserva`) as total FROM `reserva` WHERE `datareserva`=CURDATE() and `idalureserva`="+id);
           conexao.rs.first();
           registro= conexao.rs.getInt("total");
       return registro;
       
   } 
public boolean buscarID(int id) throws SQLException{
         conexao.Conectar(); //Conectando ao banco.
         Integer registro=0;
           conexao.ExecutarSQL("SELECT COUNT(`idalureserva`) as total FROM `reserva` WHERE `datareserva`=CURDATE() and `idalureserva`="+id);
           conexao.rs.first();
           registro= conexao.rs.getInt("total");
           if(registro>0){
               return true;
           }
        return false;   
}
public int tentativas(int id) throws SQLException{
           conexao.Conectar(); //Conectando ao banco.
           Integer registro=0;
           conexao.ExecutarSQL("select `tentativas` FROM `reserva` WHERE `idreserva`="+id);
           conexao.rs.first();
           registro= conexao.rs.getInt("tentativas");  
           return registro;
}
public void verificarTabela() throws SQLException{
           conexao.Conectar(); //Conectando ao banco.
           Integer registro=0;
           conexao.ExecutarSQL("SELECT COUNT(`idalureserva`)as total FROM `reserva` WHERE `datareserva`<CURDATE()");
           conexao.rs.first();
           registro= conexao.rs.getInt("total");  
           if(registro>0){
               limparTudo();
           }
    
}
}
