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
public class UsuarioController {
    
     private Integer idusuario;
     private String nomeusuario;
     private String contatousuario;
     private String emailusuario;
     private String senhausuario; 
     private int adm; 
     private int bloquear; 
     private int usuid;
     private int idlogado;

    public UsuarioController() {
    
    }



    public int getIdlogado() {
        return idlogado;
    }

    public void setIdlogado(int idlogado) {
        this.idlogado = idlogado;
    }

    public int getBloquear() {
        return bloquear;
    }

    public void setBloquear(int bloquear) {
        this.bloquear = bloquear;
    }

    public int getAdm() {
        return adm;
    }

    public void setAdm(int adm) {
        this.adm = adm;
    }

    public int getUsuid() {
        return usuid;
    }

    public void setUsuid(int usuid) {
        this.usuid = usuid;
    }
     
    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public String getContatousuario() {
        return contatousuario;
    }

    public void setContatousuario(String contatousuario) {
        this.contatousuario = contatousuario;
    }

    public String getEmailusuario() {
        return emailusuario;
    }

    public void setEmailusuario(String emailusuario) {
        this.emailusuario = emailusuario;
    }

    public String getSenhausuario() {
        return senhausuario;
    }

    public void setSenhausuario(String senhausuario) {
        this.senhausuario = senhausuario;
    }
 
}
