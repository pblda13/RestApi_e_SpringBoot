package com.mballem.demoparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoparkapi.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
