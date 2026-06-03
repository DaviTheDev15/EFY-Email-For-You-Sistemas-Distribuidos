package com.project.efy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;

@Entity(name = "email")
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String destinatarios;
    private String assunto;
    private String message;
    private String status;
    private Date created_at;
    private Date send_at;
    private String error_message;

    public Email(){

    }
    public Email(Integer id, Usuario usuario, String destinatarios, String assunto, String message, String status, Date created_at, Date send_at, String error_message){
        this.id = id;
        this.usuario = usuario;
        this.destinatarios = destinatarios;
        this.assunto = assunto;
        this.message = message;
        this.status = status;
        this.created_at = created_at;
        this.send_at = send_at;
        this.error_message = error_message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getSend_at() {
        return send_at;
    }

    public void setSend_at(Date send_at) {
        this.send_at = send_at;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
