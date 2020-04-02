/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Conexao.ConexaoBD;
import Controller.AlunoController;
import Controller.RefeitorioController;
import static com.ibm.media.codec.audio.g723.G723Tables.s;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.media.Format;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class RefeitorioDao {
             ConexaoBD conexao = new ConexaoBD(); //Classe para conexão.
public Integer limiteDia(Integer id) throws SQLException{
      conexao.Conectar(); //Conectando ao banco.
    Integer limite =0;
      conexao.ExecutarSQL("select `creditodia` from `creditorefeicao` where  `idcredaluno`="+id);
      conexao.rs.first();
      
      limite=(int)conexao.rs.getDouble("creditodia");
      conexao.Desconectar();
    
    return limite;
}
public Integer tipoBolsa(Integer id) throws SQLException{
      conexao.Conectar(); //Conectando ao banco.
      conexao.ExecutarSQL("select `tipobolsa` from `aluno` where  `idaluno`="+id);
      conexao.rs.first();
      int ret = conexao.rs.getInt("tipobolsa");
            conexao.Desconectar();
            return ret;

}
public Boolean existealuno(Integer id) throws SQLException{
      conexao.Conectar(); //Conectando ao banco.
      conexao.ExecutarSQL("select * from `aluno` where  `idaluno`="+id);
      conexao.rs.first();
      int row= conexao.rs.getRow();
      conexao.Desconectar();
      if(row>0){
          return true;
          
      }
    return false;
}
public boolean prazoEsgotado(Integer id) throws SQLException{
      int ret=0;
       conexao.Conectar(); //Conectando ao banco. 
       conexao.ExecutarSQL("select count(idcredito) as total from `creditorefeicao` where  `datafinal`<CURDATE() and `idcredaluno`="+id);
       conexao.rs.first();
       ret = conexao.rs.getInt("total");
        conexao.Desconectar();
       if(ret==1){
           return true;
       }else{
       return false;
       }
      
}   
public boolean epracontrolar(Integer id) throws SQLException{
      int ret=0;
       conexao.Conectar(); //Conectando ao banco. 
       conexao.ExecutarSQL("select count(idcredito) as total FROM `creditorefeicao` WHERE  controle=1 and idcredaluno="+id);
       conexao.rs.first();
       ret = conexao.rs.getInt("total");
        conexao.Desconectar();
       if(ret==1){
           return true;
       }else{
       return false;
       }
      
}   
public boolean calculaControleCredito(Integer id) throws SQLException{
      int ret=0;
       conexao.Conectar(); //Conectando ao banco. 
       conexao.ExecutarSQL("select count(idcredito) as total from `creditorefeicao` where  `creditosutilizados`=`totalcreditos` and `idcredaluno`=1"+id);
       conexao.rs.first();
       ret = conexao.rs.getInt("total");
        conexao.Desconectar();
       if(ret==1){
           return true;
       }else{
       return false;
       }
      
}   
public int tabloqueado(Integer id) throws SQLException{
      int ret=0;
       conexao.Conectar(); //Conectando ao banco. 
       conexao.ExecutarSQL("select `tipobolsa` from `aluno` where `idaluno`="+id);
       conexao.rs.first();
       ret = conexao.rs.getInt("tipobolsa");
        conexao.Desconectar();
       if(ret==2){
           return 1;
       }
       return 0;
      
}   
public boolean naoInicializadoPeriodo(Integer id) throws SQLException{
      int ret=0;
       conexao.Conectar(); //Conectando ao banco. 
       conexao.ExecutarSQL("select count(`idcredito`) as total from `creditorefeicao` where  `datainicial` >CURDATE() and `idcredaluno`="+id);
       conexao.rs.first();
       ret = conexao.rs.getInt("total");
        conexao.Desconectar();
       if(ret==1){
           return true;
       }else{
       return false;
       }
}   
public Integer contarRegistros(Integer id) throws SQLException{
         conexao.Conectar(); //Conectando ao banco.
       Integer registro=0;
           conexao.ExecutarSQL("select count(`idrefeitorio`) as total FROM `refeitorio` WHERE `datarefeitorio`=CURDATE() and `horariorefeicao`='00:00:00' and `idrefaluno`="+id);
           conexao.rs.first();
           registro= conexao.rs.getInt("total");
       return registro;
       
   }
public Integer contarReservas() throws SQLException{
         conexao.Conectar(); //Conectando ao banco.
       Integer registro=0;
           conexao.ExecutarSQL("select count(`idrefeitorio`) as total FROM `refeitorio` WHERE `datarefeitorio`=CURDATE() and `horariorefeicao`='00:00:00' ");
           conexao.rs.first();
           registro= conexao.rs.getInt("total");
       return registro;
       
   }
public Integer contarConsumidas() throws SQLException{
         conexao.Conectar(); //Conectando ao banco.
       Integer registro=0;
           conexao.ExecutarSQL("select count(`idrefeitorio`) as total FROM `refeitorio` where `datarefeitorio`=CURDATE() and `horariorefeicao`<>'00:00:00'");
           conexao.rs.first();
           registro= conexao.rs.getInt("total");
       return registro;
       
   }
public Integer contarConsumidas(int id) throws SQLException{
         conexao.Conectar(); //Conectando ao banco.
       Integer registro=0;
           conexao.ExecutarSQL("select count(`idrefeitorio`) as total FROM `refeitorio` where `datarefeitorio`=CURDATE() and `horariorefeicao`<>'00:00:00' and `idrefaluno`="+id);
           conexao.rs.first();
           registro= conexao.rs.getInt("total");
       return registro;
       
   }
public boolean temReserva(Integer id) throws SQLException{
         conexao.Conectar(); //Conectando ao banco.
       Integer registro=0;
           conexao.ExecutarSQL("select count(`idrefeitorio`)as total FROM `refeitorio` where `datarefeitorio`=CURDATE() and `horariorefeicao`='00:00:00' and `idrefaluno`="+id);
           conexao.rs.first();
           registro= conexao.rs.getInt("total");
           if(registro>0){
               return true;
           }
       return false;
       
   }
public boolean fezRefeicao(Integer id) throws SQLException{
         conexao.Conectar(); //Conectando ao banco.
       Integer registro=0;
           conexao.ExecutarSQL("select count(`idrefeitorio`)as total from `refeitorio` where `datarefeitorio`=CURDATE() and `horariorefeicao`<>'00:00:00' and `idrefaluno`="+id);
           conexao.rs.first();
           registro= conexao.rs.getInt("total");
           if(registro>0){
               return true;
           }
       return false;
       
   }
    public void SalvarRefeitorio(RefeitorioController refeitorio) {

        conexao.Conectar(); //Conectando ao banco.

        try {
            //Comando para salvar.
            PreparedStatement pst = conexao.connection.prepareStatement("insert into `refeitorio`(`idrefaluno`, `datarefeitorio`, `horarefeitorio`,`horariorefeicao`, `quantidaderefeitorio`,`usuid`) VALUES (?,CURRENT_DATE(), NOW(),'00:00:00', ?,?)");

                                                                        //Enviando dados para o banco.
                                                                        pst.setInt(1,refeitorio.getIdrefaluno());
                                                                        pst.setDouble(2,refeitorio.getQuantidaderefeitorio());
                                                                        pst.setInt(3, refeitorio.getUsuid());
                                                                        pst.execute();

//          ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
//            JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Salvo", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!"+ex, "Salvar", 2, icon);

        }
            
        conexao.Desconectar(); //Desconectando do banco.

    }
        public void PegarRefeicao(RefeitorioController refeitorio) throws UnsupportedEncodingException {

        conexao.Conectar(); //Conectando ao banco.

        try {
             String sql="";
            sql="update `refeitorio` set `horariorefeicao` = NOW(),`idturmarefeitorio`=?,`valorbolsarefeitorio`=?,`tipobolsarefeitorio`=? where `datarefeitorio`=CURDATE() and `idrefaluno`= ?";
           
            PreparedStatement pst = conexao.connection.prepareStatement(sql);
                pst.setInt(1, refeitorio.getIdturma());
                pst.setDouble(2, refeitorio.getValor());
                pst.setInt(3, refeitorio.getTipobolsa());
                pst.setInt(4, refeitorio.getIdrefaluno());
            //System.out.println(sql);
                pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            //JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
        public void Atualizar(RefeitorioController refeitorio) throws UnsupportedEncodingException {

        conexao.Conectar(); //Conectando ao banco.

        try {
             String sql="";
            sql="update `refeitorio`  set `idturmarefeitorio`=?,`valorbolsarefeitorio`=?,`tipobolsafeitorio`=? where  `idrefeitorio` = ?";
           
            PreparedStatement pst = conexao.connection.prepareStatement(sql);
                pst.setInt(1, refeitorio.getIdturma());
                pst.setDouble(2, refeitorio.getValor());
                pst.setInt(3, refeitorio.getTipobolsa());
                pst.setInt(4, refeitorio.getIdrefeitorio());
            //System.out.println(sql);
                pst.execute();

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/circle-with-check-symbol-36.png")));
            //JOptionPane.showMessageDialog(null, "Alterado com sucesso!", "Alterado", 2, icon);

        } catch (SQLException ex) {

            ImageIcon icon = (new javax.swing.ImageIcon(getClass().getResource("/Galeria/Icones/round-error-symbol-36.png")));
            JOptionPane.showMessageDialog(null, "Não possível executar a ação!", "Alterar", 2, icon);

        }

        conexao.Desconectar(); //Desconectando do banco.

    }
     public List<RefeitorioController> CarregarIndividual(Integer id) {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` where `datarefeitorio`=CURRENT_DATE() and `aluno`.`idaluno`="+id); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarTudo() {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` order  by `idrefeitorio` asc "); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarPorIdCurso(String datainicial,String datafinal,int idcurso,String ordem) {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        conexao.ExecutarSQL("select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio` BETWEEN '"+datainicial+"' and '"+datafinal+"' and `curso`.`idcurso`="+idcurso+" order by `idrefeitorio` "+ordem); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarPorIdTurma(String datainicial,String datafinal,int idcurso,int idturma,String ordem) {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
      String sql = "select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio` BETWEEN '"+datainicial+"' and '"+datafinal+"' and `turma`.`idturma`="+idturma+" order by `idrefeitorio` "+ordem;
       
      conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
//        System.out.println(sql);
        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {
                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarEntreDatas(String datainicial,String datafinal,String ordem) {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.

        String sql = "select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio` BETWEEN '"+datainicial+"' and '"+datafinal+"' order by `idrefeitorio` "+ordem;
        
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
//        System.out.println(sql);
        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarPorIdAluno(String datainicial,String datafinal,int idaluno,String ordem) {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        String sql = "select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio` BETWEEN '"+datainicial+"' and '"+datafinal+"' and `aluno`.`idaluno`="+idaluno+" order by `idrefeitorio` "+ordem;
 
        conexao.ExecutarSQL(sql);

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {
                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarPorIdBolsa(String datainicial,String datafinal,int tipobolsa,String ordem) {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
         String sql = "select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio` BETWEEN '"+datainicial+"' and '"+datafinal+"' and `refeitorio`.`tipobolsarefeitorio`="+tipobolsa+" order by `idrefeitorio` "+ordem;
        
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {
                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarPorIdCursoIdBolsa(String datainicial,String datafinal,int idcurso,int tipobolsa,String ordem) {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
         String sql = "select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio` BETWEEN '"+datainicial+"' and '"+datafinal+"' and `refeitorio`.`tipobolsarefeitorio`="+tipobolsa+" and `aluno`.`cursoid`="+idcurso+" order by `idrefeitorio` "+ordem;
                
        //Comando para listar.
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarReservas() {

        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        //conexao.ExecutarSQL("SELECT * FROM `REFEITORIO`  INNER JOIN `ALUNO` ON `REFEITORIO`.`IDREFALUNO`=`ALUNO`.`IDALUNO` INNER JOIN `CREDITOREFEICAO` ON  `REFEITORIO`.`IDREFALUNO`=`CREDITOREFEICAO`.`IDCREDALUNO` INNER JOIN USUARIO ON REFEITORIO.USUID=USUARIO.IDUSUARIO WHERE `DATAREFEITORIO`=CURDATE() ORDER BY `IDREFEITORIO` DESC"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
        //String sql = "select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio`=CURDATE() and `horariorefeicao`='00:00:00' order by `horariorefeicao`";
        String sql = "select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio`=CURDATE() and `horariorefeicao`='00:00:00' order by `horariorefeicao`";
            
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
        try {
           
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {
                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));
                

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
                System.out.println(ex);
        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarRefeicoes(String campo,String ordem) {
         int qtddados=0;
         
        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
        //conexao.ExecutarSQL("SELECT * FROM `REFEITORIO`  INNER JOIN `ALUNO` ON `REFEITORIO`.`IDREFALUNO`=`ALUNO`.`IDALUNO` INNER JOIN `CREDITOREFEICAO` ON  `REFEITORIO`.`IDREFALUNO`=`CREDITOREFEICAO`.`IDCREDALUNO` INNER JOIN USUARIO ON REFEITORIO.USUID=USUARIO.IDUSUARIO WHERE `DATAREFEITORIO`=CURDATE() ORDER BY `IDREFEITORIO` DESC"); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
       
         String sql = "select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno`  inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` where `datarefeitorio`=CURDATE() and `horariorefeicao`<>'00:00:00' order by `"+campo+"` "+ordem;
           
//System.out.println(sql);
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.

        try {
            
            
            conexao.rs.first(); //Serve para filtrar o primeiro resultado.
            qtddados=conexao.rs.getRow();
            
            do {
                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setHorariorefeicao(conexao.rs.getString("horariorefeicao"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));
                

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {
                System.out.println(ex+"RefeitorioDao: CarregarRefeicoes consulta voltou vazio");
        }
        

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> Carregarrefeicao(int tipo,String datainicial,String datafinal,int idaluno,int idbolsa, int idcurso, int idturma,String campo,String ordem) {
         String sql=null;
        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
         //PESQUISANDO PELO ALUNO
        if(tipo==0){
        sql="select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` inner join `turma` on `turma`.`idturma`=`aluno`.`turmaid` where  `refeitorio`.`idrefaluno`="+idaluno+" and `datarefeitorio` between  '"+datainicial+"' and '"+datafinal+"' and horariorefeicao<>'00:00:00' order by "+campo+ " "+ordem;
        }        
         //PESQUISANDO PELO CURSO
        if(tipo==1){
        sql="select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` inner join `turma` on `turma`.`idturma`=`aluno`.`turmaid` where  `curso`.`idcurso`="+idcurso+" and `datarefeitorio` between  '"+datainicial+"' and '"+datafinal+"' and horariorefeicao<>'00:00:00'  order by "+campo+ " "+ordem;            

        }        
        //PESQUISANDO PELO CURSO E TURMA
        if(tipo==2){
        sql="select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` inner join `turma` on `turma`.`idturma`=`aluno`.`turmaid` where   `curso`.`idcurso`="+idcurso+" and `turma`.`idturma`="+idturma+" and `datarefeitorio` between  '"+datainicial+"' and '"+datafinal+"' and horariorefeicao<>'00:00:00'  order by "+campo+ " "+ordem;            

        }           
        //PESQUISANDO PELO TIPO DE BOLSA
        if(tipo==3){
        sql="select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` inner join `turma` on `turma`.`idturma`=`aluno`.`turmaid` where   `aluno`.`tipobolsa`="+idbolsa+" and `datarefeitorio` between  '"+datainicial+"' and '"+datafinal+"' and horariorefeicao<>'00:00:00' order by "+campo+ " "+ordem;            
         
        }        
        //PESQUISANDO POR CURSO E TIPO DE BOLSA DO CURSO ESCOLHIDO
        if(tipo==4){
        sql="select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` inner join `turma` on `turma`.`idturma`=`aluno`.`turmaid` where `curso`.`idcurso` and `aluno`.`tipobolsa`="+idbolsa+" and `datarefeitorio` between  '"+datainicial+"' and '"+datafinal+"' and horariorefeicao<>'00:00:00' order by "+campo+ " "+ordem;            
  
        }
        if(tipo==5){
            //PESQUISANDO DETERMINADO PERÍODO
        sql="select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` inner join `turma` on `turma`.`idturma`=`aluno`.`turmaid` where horariorefeicao<>'00:00:00' and  `datarefeitorio` between  '"+datainicial+"' and '"+datafinal+"'  order by "+campo+ " "+ordem;            

        }
        if(tipo==7){
 
            //PESQUISANDO REFEIÇÕES DO DIA
        sql="select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` inner join `turma` on `turma`.`idturma`=`aluno`.`turmaid` where horariorefeicao<>'00:00:00' and `datarefeitorio`=CURDATE()  order by "+campo+ " "+ordem;            

        }
        if(tipo==8){
 
            //PESQUISANDO RESERVA DO DIA 
        sql="select * from `refeitorio`  inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` inner join `usuario` on `refeitorio`.`usuid`=`usuario`.`idusuario` inner join `curso` on `curso`.`idcurso`=`aluno`.`cursoid` inner join `turma` on `turma`.`idturma`=`aluno`.`turmaid` where horariorefeicao='00:00:00' and `datarefeitorio`=CURDATE()  order by "+campo+ " "+ordem;            

        }
                //System.out.println(sql);
                
         //System.out.println(sql);
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
        //System.out.println(sql);
        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setIdrefeitorio(conexao.rs.getInt("idrefeitorio"));
                refeitorio.setIdrefaluno(conexao.rs.getInt("idrefaluno"));
                refeitorio.setTipobolsa(conexao.rs.getInt("tipobolsarefeitorio"));
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setIdturma(conexao.rs.getInt("idturmarefeitorio"));
                refeitorio.setValor(conexao.rs.getDouble("valorbolsarefeitorio"));
                refeitorio.setNomeusuario(conexao.rs.getString("nomeusuario"));
                refeitorio.setDatarefeitorio(conexao.rs.getString("datarefeitorio"));
                refeitorio.setNomecurso(conexao.rs.getString("nomecurso"));
                refeitorio.setNometurma(conexao.rs.getString("nometurma"));
                refeitorio.setHorarefeitorio(conexao.rs.getString("horarefeitorio"));
                refeitorio.setHorariorefeicao(conexao.rs.getString("horariorefeicao"));
                refeitorio.setQuantidaderefeitorio(conexao.rs.getDouble("quantidaderefeitorio"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
     public List<RefeitorioController> CarregarrefeicaoResumido(String datainicial,String datafinal,int idbolsa) {
         String sql=null;
        List<RefeitorioController> refeitorios = new ArrayList<>(); //Instanciando um ArrayList.

        conexao.Conectar();
        //Comando para listar.
         //PESQUISANDO PELO ALUNO
        
        sql="select COUNT(`refeitorio`.`idrefaluno`) AS QTD, `aluno`.`nomealuno`,`aluno`.`cpfaluno` from `refeitorio` inner join `aluno` on `refeitorio`.`idrefaluno`=`aluno`.`idaluno` where `datarefeitorio` between '"+datainicial+"' and '"+datafinal+"' and `refeitorio`.`tipobolsarefeitorio`="+idbolsa+" and horariorefeicao<>'0000-00-00' GROUP BY `refeitorio`.`idrefaluno` order by `aluno`.`nomealuno`";
                

                //System.out.println(sql);
                
         //System.out.println(sql);
        conexao.ExecutarSQL(sql); //Comando SQL para realizar a pesquisa de todos os campos dentro do Banco de Dados.
        //System.out.println(sql);
        try {

            conexao.rs.first(); //Serve para filtrar o primeiro resultado.

            do {

                RefeitorioController refeitorio = new RefeitorioController();
                //Dados para carregarem os valores das linhas da Tabela.
                refeitorio.setNomealuno(conexao.rs.getString("nomealuno"));
                refeitorio.setCpf(conexao.rs.getString("cpfaluno"));
                refeitorio.setQtd(conexao.rs.getInt("QTD"));

                refeitorios.add(refeitorio); //Salvando dados na list.

            } while (conexao.rs.next()); //Enquanto houver dados continuará percorrendo o Banco de Dados.

        } catch (SQLException ex) {

        }

        conexao.Desconectar(); //Desconectando do banco.

        return refeitorios; //Retornando a ArrayList criada.

    }
}
