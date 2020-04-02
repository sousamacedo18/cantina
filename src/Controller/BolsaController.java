/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Home
 */
public class BolsaController {
  private int idbolsa;
  private String nomebolsa;
  private double valorbolsa;

    public BolsaController() {
    }

    public int getIdbolsa() {
        return idbolsa;
    }

    public void setIdbolsa(int idbolsa) {
        this.idbolsa = idbolsa;
    }

    public String getNomebolsa() {
        return nomebolsa;
    }

    public void setNomebolsa(String nomebolsa) {
        this.nomebolsa = nomebolsa;
    }

    public double getValorbolsa() {
        return valorbolsa;
    }

    public void setValorbolsa(double valorbolsa) {
        this.valorbolsa = valorbolsa;
    }
  
}
