package com.project.efy.dtos;

import com.project.efy.model.Usuario;

public record EmailDto(Usuario usuario, String destinatarios, String assunto,
                       String message, String status, String error_message) {
}
