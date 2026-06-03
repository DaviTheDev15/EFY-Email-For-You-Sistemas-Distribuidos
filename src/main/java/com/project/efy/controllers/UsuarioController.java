package com.project.efy.controllers;


import com.project.efy.dtos.UsuarioDto;
import com.project.efy.model.Usuario;
import com.project.efy.repositories.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioRepository repository;

    @GetMapping
    public ResponseEntity getAll(){
        List<Usuario> listUsuarios = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listUsuarios);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UsuarioDto dto){
        var usuario = new Usuario();
        BeanUtils.copyProperties(dto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        Optional usuario = repository.findById(id);
        if(usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody UsuarioDto dto){
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var usuarioModel = usuario.get();
        BeanUtils.copyProperties(dto, usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuarioModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        repository.delete(usuario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário Deletado");
    }
}
