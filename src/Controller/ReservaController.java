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
public class ReservaController {
    private Integer idreserva;
    private Integer idalureserva;
    private String datareserva;
    private String horareserva;
    private String horarefeicao;
    private Integer tentativas;
    private String cpf;
    private String nome;
    private String foto;
    private int bolsa;
    
    
    
    public ReservaController() {
    }

    public String getHorarefeicao() {
        return horarefeicao;
    }

    public void setHorarefeicao(String horarefeicao) {
        this.horarefeicao = horarefeicao;
    }
    
    public int getBolsa() {
        return bolsa;
    }

    public void setBolsa(int bolsa) {
        this.bolsa = bolsa;
    }



    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(Integer idreserva) {
        this.idreserva = idreserva;
    }

    public Integer getIdalureserva() {
        return idalureserva;
    }

    public void setIdalureserva(Integer idalureserva) {
        this.idalureserva = idalureserva;
    }

    public String getDatareserva() {
        return datareserva;
    }

    public void setDatareserva(String datareserva) {
        this.datareserva = datareserva;
    }

    public String getHorareserva() {
        return horareserva;
    }

    public void setHorareserva(String horareserva) {
        this.horareserva = horareserva;
    }

    public Integer getTentativas() {
        return tentativas;
    }

    public void setTentativas(Integer tentativas) {
        this.tentativas = tentativas;
    }
    
}
