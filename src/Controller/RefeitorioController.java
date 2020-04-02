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
public class RefeitorioController {
    private Integer idrefeitorio;
    private Integer idrefaluno;
    private String datarefeitorio;
    private String horarefeitorio;
    private String horariorefeicao;
    private String cpf;
    private String nomealuno;
    private String nomeusuario;
    private String nomecurso;
    private String nometurma;
    private Double quantidaderefeitorio;
    private Integer tipobolsa;
    private Integer idcurso;
    private Integer idturma;
    private double valor;
    private Integer qtd;

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
    
    private int usuid;

    public RefeitorioController() {
    }

    public Integer getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public Integer getIdturma() {
        return idturma;
    }

    public void setIdturma(Integer idturma) {
        this.idturma = idturma;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }



    public String getNomeusuario() {
        return nomeusuario;
    }

        public String getHorariorefeicao() {
        return horariorefeicao;
    }

    public void setHorariorefeicao(String horariorefeicao) {
        this.horariorefeicao = horariorefeicao;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public Integer getTipobolsa() {
        return tipobolsa;
    }

    public void setTipobolsa(Integer tipobolsa) {
        this.tipobolsa = tipobolsa;
    }

    public int getUsuid() {
        return usuid;
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

    public String getHorarefeitorio() {
        return horarefeitorio;
    }

    public void setHorarefeitorio(String horarefeitorio) {
        this.horarefeitorio = horarefeitorio;
    }

    public Integer getIdrefeitorio() {
        return idrefeitorio;
    }

    public void setIdrefeitorio(Integer idrefeitorio) {
        this.idrefeitorio = idrefeitorio;
    }

    public Integer getIdrefaluno() {
        return idrefaluno;
    }

    public void setIdrefaluno(Integer idrefaluno) {
        this.idrefaluno = idrefaluno;
    }

    public String getDatarefeitorio() {
        return datarefeitorio;
    }

    public void setDatarefeitorio(String datarefeitorio) {
        this.datarefeitorio = datarefeitorio;
    }

    public Double getQuantidaderefeitorio() {
        return quantidaderefeitorio;
    }

    public void setQuantidaderefeitorio(Double quantidaderefeitorio) {
        this.quantidaderefeitorio = quantidaderefeitorio;
    }

    public String getNomecurso() {
        return nomecurso;
    }

    public void setNomecurso(String nomecurso) {
        this.nomecurso = nomecurso;
    }

    public String getNometurma() {
        return nometurma;
    }

    public void setNometurma(String nometurma) {
        this.nometurma = nometurma;
    }
    
}
