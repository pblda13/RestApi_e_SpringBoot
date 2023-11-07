package com.mballem.demoparkapi.web.controller;

import com.mballem.demoparkapi.web.dto.UsuarioCreateDto;
import com.mballem.demoparkapi.web.dto.mapper.UsuarioMapper;
import com.mballem.demoparkapi.web.dto.usuarioResponseDto;
import com.mballem.demoparkapi.web.dto.usuarioSenhaDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
// A anotação @RequiredArgsConstructor é um recurso do projeto Lombok que gera automaticamente um construtor que injeta automaticamente os campos marcados como 'final' no construtor da classe.

@RestController
// A anotação @RestController é usada para definir que esta classe é um controlador Spring MVC e que seus métodos tratam as solicitações HTTP.

@RequestMapping("api/v1/usuarios")
// A anotação @RequestMapping define o mapeamento de URL para este controlador. Todas as rotas definidas nos métodos deste controlador começarão com "api/v1/usuarios".
public class UsuarioController {

    private final UsuarioService usuarioService;

// Este é um controlador Spring que lida com operações relacionadas a usuários.
// Ele injeta um serviço de usuário para executar a lógica de negócios.

    @PostMapping
    public ResponseEntity<usuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto) {
        // Este método trata as solicitações HTTP POST para criar um novo usuário.
        // Ele recebe um objeto UsuarioCreateDto no corpo da solicitação e o transforma em um objeto Usuario.
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        // Em seguida, chama o serviço para salvar o usuário e retorna uma resposta com o usuário criado no corpo.
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<usuarioResponseDto> getById(@PathVariable Long id) {
        // Este método trata solicitações HTTP GET para recuperar informações sobre um usuário com base em seu ID.
        Usuario user = usuarioService.buscarPorId(id);
        // Ele chama o serviço para buscar o usuário com o ID fornecido e retorna uma resposta com os dados do usuário no corpo.
        return ResponseEntity.ok().body(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody usuarioSenhaDto dto) {
        // Este método lida com solicitações HTTP PATCH para atualizar a senha de um usuário com base em seu ID.
        // Ele recebe o ID do usuário e um objeto usuarioSenhaDto contendo informações de senha no corpo da solicitação.
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        // Chama o serviço para atualizar a senha do usuário e retorna uma resposta de sucesso sem corpo.
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<usuarioResponseDto>> getAll() {
        // Este método trata solicitações HTTP GET para recuperar uma lista de todos os usuários.
        List<Usuario> users = usuarioService.buscarTodos();
        // Ele chama o serviço para buscar todos os usuários e retorna uma resposta com a lista de usuários no corpo.
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
