/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Blob;

/**
 *
 * @author Home
 */
public class AlunoController {
    private Integer idaluno;
    private String nomealuno;
    private String nomecurso;
    private String nometurma;
    private String emailaluno;
    private String senhaaluno;
    private String cpfaluno;
    private Integer bolsa;
    private int cursoid;
    private int turmaid;
    private String fotoaluno;
    private Integer usuid;
    private Blob digitalaluno;
    private Blob polegaraluno;
    private Blob indicadoraluno;
    private Blob medioaluno;
    private Blob anelaraluno;
    private Blob mindinhoaluno;
    private Byte dedo[];


    public AlunoController() {
    }

    public Integer getBolsa() {
        return bolsa;
    }

    public void setBolsa(Integer bolsa) {
        this.bolsa = bolsa;
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

    public int getCursoid() {
        return cursoid;
    }

    public void setCursoid(int cursoid) {
        this.cursoid = cursoid;
    }

    public int getTurmaid() {
        return turmaid;
    }

    public void setTurmaid(int turmaid) {
        this.turmaid = turmaid;
    }
    
    public Blob getDigitalaluno() {
        return digitalaluno;
    }

    public Integer getUsuid() {
        return usuid;
    }

    public void setUsuid(Integer usuid) {
        this.usuid = usuid;
    }

    public void setDigitalaluno(Blob digitalaluno) {
        this.digitalaluno = digitalaluno;
    }
 
    
    public Byte[] getDedo() {
        return dedo;
    }

    public void setDedo(Byte[] dedo) {
        this.dedo = dedo;
    }
    



    public Integer getIdaluno() {
        return idaluno;
    }

    public void setIdaluno(Integer idaluno) {
        this.idaluno = idaluno;
    }

    public String getNomealuno() {
        return nomealuno;
    }

    public void setNomealuno(String nomealuno) {
        this.nomealuno = nomealuno;
    }

    public String getEmailaluno() {
        return emailaluno;
    }

    public void setEmailaluno(String emailaluno) {
        this.emailaluno = emailaluno;
    }

    public String getSenhaaluno() {
        return senhaaluno;
    }

    public void setSenhaaluno(String senhaaluno) {
        this.senhaaluno = senhaaluno;
    }

    public String getCpfaluno() {
        return cpfaluno;
    }

    public void setCpfaluno(String cpfaluno) {
        this.cpfaluno = cpfaluno;
    }

    public String getFotoaluno() {
        return fotoaluno;
    }

    public void setFotoaluno(String fotoaluno) {
        this.fotoaluno = fotoaluno;
    }

    public Blob getPolegaraluno() {
        return polegaraluno;
    }

    public void setPolegaraluno(Blob polegaraluno) {
        this.polegaraluno = polegaraluno;
    }

    public Blob getIndicadoraluno() {
        return indicadoraluno;
    }

    public void setIndicadoraluno(Blob indicadoraluno) {
        this.indicadoraluno = indicadoraluno;
    }

    public Blob getMedioaluno() {
        return medioaluno;
    }

    public void setMedioaluno(Blob medioaluno) {
        this.medioaluno = medioaluno;
    }

    public Blob getAnelaraluno() {
        return anelaraluno;
    }

    public void setAnelaraluno(Blob anelaraluno) {
        this.anelaraluno = anelaraluno;
    }

    public Blob getMindinhoaluno() {
        return mindinhoaluno;
    }

    public void setMindinhoaluno(Blob mindinhoaluno) {
        this.mindinhoaluno = mindinhoaluno;
    }

    @Override
    public String toString() {
        return getNomealuno(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
