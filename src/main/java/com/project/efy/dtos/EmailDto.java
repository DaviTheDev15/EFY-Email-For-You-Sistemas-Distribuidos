package com.project.efy.dtos;

import com.project.efy.model.Usuario;

import java.util.Date;

public record EmailDto(Usuario usuario, String destinatarios, String assunto,
                       String message, String status, Date created_at,
                       Date send_at, String error_message) {
}
