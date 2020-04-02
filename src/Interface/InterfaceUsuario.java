/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.UsuarioController;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Home
 */
public interface InterfaceUsuario {
    void  SalvarUsuario(UsuarioController usuario);
    List<UsuarioController> list();
    public void ExcluirUsuario(UsuarioController usuario);
    public void AtualizarUsuario(UsuarioController usuario);
    public Vector PesquisaGeral(String pesquisa)throws SQLException;
    public Vector UsuarioTotal(String sala)throws SQLException;
    List<UsuarioController> CarregarUsuarios(String usuario);
    List<UsuarioController> CarregarTodosUsuarios();
    List<UsuarioController>LogarUsuario(String email,String senha);
}
