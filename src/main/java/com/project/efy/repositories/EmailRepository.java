package com.project.efy.repositories;

import com.project.efy.dtos.HistoricoDto;
import com.project.efy.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Integer> {
    @Query("""
           SELECT new com.project.efy.dtos.HistoricoDto(
                e.id,
                e.assunto,
                e.message,
                u.name,
                u.email,
                e.destinatarios,
                e.status,
                e.created_at,
                e.send_at,
                e.error_message
           )
           FROM email e
           JOIN e.usuario u
        """)
    List<HistoricoDto> findHistorico();
}
