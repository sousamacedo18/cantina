/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.CreditoController;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Home
 */
public interface InterfaceCredito {
    void  SalvarUsuario(CreditoController credito);
    List<CreditoController> list();
    public void ExcluirUsuario(CreditoController credito);
    public void AtualizarUsuario(CreditoController credito);
    public Vector PesquisaGeral(String pesquisa)throws SQLException;
    public Vector CreditoTotal(String sala)throws SQLException;
    List<CreditoController> CarregarUsuarios(String credito);
    List<CreditoController> CarregarTodosCredito();
    List<CreditoController>LogarUsuario(String email,String senha);
}
