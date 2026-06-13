package com.project.efy.dtos;

import java.util.Date;

public record HistoricoDto(Integer id, String assunto,
                           String message,String nomeDoRemetente,String emailDoRemetente, String destinatarios,
                           String status, Date created_at, Date send_at, String error_message) {
}

