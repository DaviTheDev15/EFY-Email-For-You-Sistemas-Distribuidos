package com.project.efy.dtos;

import java.util.Date;

public record UsuarioDto(String name, String email, String password, Date created_at) {
}
