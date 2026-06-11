package com.project.efy.controllers;

import com.project.efy.dtos.EmailDto;
import com.project.efy.model.Email;
import com.project.efy.model.Usuario;
import com.project.efy.repositories.EmailRepository;
import com.project.efy.repositories.UsuarioRepository;
import com.project.efy.service.QueueService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    EmailRepository repository;

    @Autowired
    private QueueService queueService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity getAll(){
        List<Email> listEmails = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listEmails);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody EmailDto dto){
        Integer usuarioId = dto.usuario().getId();

        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if(usuario.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado.");
        }

        var email = new Email();
        BeanUtils.copyProperties(dto, email);
        email.setUsuario(usuario.get());
        Date agora = new Date();

        email.setCreated_at(agora);
        email.setStatus("PENDING");
        email.setSend_at(null);
        Email savedEmail = repository.save(email);
        queueService.sendMessage(
                savedEmail.getId().toString()
        );
        repository.save(savedEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mensagem de Email criada com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        Optional email = repository.findById(id);
        if(email.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Email não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(email.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody EmailDto dto){
        Optional<Email> email = repository.findById(id);
        if (email.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado.");
        }
        var emailModel = email.get();
        BeanUtils.copyProperties(dto, emailModel);
        repository.save(emailModel);
        return ResponseEntity.status(HttpStatus.OK).body("Mensagem de Email atualizada com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        Optional<Email> email = repository.findById(id);
        if (email.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado.");
        }
        repository.delete(email.get());
        return ResponseEntity.status(HttpStatus.OK).body("Email Deletado.");
    }

    @GetMapping("/historico")
    public ResponseEntity getHistorico(){
        return ResponseEntity.ok(repository.findHistorico());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity atualizarStatus(
            @PathVariable Integer id,
            @RequestBody EmailDto dto
    ) {

        Optional<Email> email = repository.findById(id);

        if(email.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Email emailModel = email.get();

        emailModel.setStatus(dto.status());
        emailModel.setError_message(dto.error_message());

        if ("ENVIADO".equals(dto.status())) {
            emailModel.setSend_at(new Date());
        }

        repository.save(emailModel);

        return ResponseEntity.ok().build();
    }
}
