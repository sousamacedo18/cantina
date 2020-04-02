/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.AlunoController;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Home
 */
public interface InterfaceAluno {
     void  SalvarAluno(AlunoController aluno);
    List<AlunoController> list();
    public void ExcluirAluno(AlunoController aluno);
    public void AtualizarAluno(AlunoController aluno);
    public Vector PesquisaGeral(String pesquisa)throws SQLException;
    public Vector UsuarioTotal(String sala)throws SQLException;
    List<AlunoController> CarregarAluno(String aluno);
    List<AlunoController> CarregarTodosAlunos(); 
}
