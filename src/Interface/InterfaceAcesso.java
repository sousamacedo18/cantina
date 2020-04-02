/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.AcessoController;
import java.util.List;

/**
 *
 * @author Home
 */
public interface InterfaceAcesso {
      void  SalvarAcesso(AcessoController acesso);
    List<AcessoController> list();;
    public void AtualizarAcesso(AcessoController acesso); 
     List<AcessoController> CarregarAcesso(String acesso);
}
