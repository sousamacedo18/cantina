/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javafx.scene.chart.PieChart;

/**
 *
 * @author Home
 */
public class CreditoController {
    private Integer idcredito;
    private Integer idcredaluno;
    private Integer tipobolsa;
    private Integer controle;
    private String datainicial;
    private String datafinal;
    private Double creditosutilizados;
    private Double totalcreditos;
    private Double creditodia;
    private Integer bloquealuno;
    private String observacaocredito;
    private String nomealuno;
    private String nomeusuario;
    private int usuid;

    public CreditoController() {
        
    }

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public int getUsuid() {
        return usuid;
    }

    public Integer getControle() {
        return controle;
    }

    public void setControle(Integer controle) {
        this.controle = controle;
    }

    public void setUsuid(int usuid) {
        this.usuid = usuid;
    }

    public String getNomealuno() {
        return nomealuno;
    }

    public void setNomealuno(String nomealuno) {
        this.nomealuno = nomealuno;
    }

    public Integer getTipobolsa() {
        return tipobolsa;
    }

    public void setTipobolsa(Integer tipobolsa) {
        this.tipobolsa = tipobolsa;
    }

    public Double getCreditosutilizados() {
        return creditosutilizados;
    }

    public void setCreditosutilizados(Double creditosutilizados) {
        this.creditosutilizados = creditosutilizados;
    }
    
    public Integer getIdcredito() {
        return idcredito;
    }

    public void setIdcredito(Integer idcredito) {
        this.idcredito = idcredito;
    }

    public Integer getIdcredaluno() {
        return idcredaluno;
    }

    public void setIdcredaluno(Integer idcredaluno) {
        this.idcredaluno = idcredaluno;
    }

    public String getDatainicial() {
        return datainicial;
    }

    public void setDatainicial(String datainicial) {
        this.datainicial = datainicial;
    }

    public String getDatafinal() {
        return datafinal;
    }

    public void setDatafinal(String datafinal) {
        this.datafinal = datafinal;
    }



    public Double getTotalcreditos() {
        return totalcreditos;
    }

    public void setTotalcreditos(Double totalcreditos) {
        this.totalcreditos = totalcreditos;
    }

    public Double getCreditodia() {
        return creditodia;
    }

    public void setCreditodia(Double creditodia) {
        this.creditodia = creditodia;
    }

    public Integer getBloquealuno() {
        return bloquealuno;
    }

    public void setBloquealuno(Integer bloquealuno) {
        this.bloquealuno = bloquealuno;
    }

    public String getObservacaocredito() {
        return observacaocredito;
    }

    public void setObservacaocredito(String observacaocredito) {
        this.observacaocredito = observacaocredito;
    }
    
    
}
