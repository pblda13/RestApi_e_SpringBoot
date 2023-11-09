package com.mballem.demoparkapi.web.controller;

import com.mballem.demoparkapi.web.dto.UsuarioCreateDto;
import com.mballem.demoparkapi.web.dto.mapper.UsuarioMapper;
import com.mballem.demoparkapi.web.dto.UsuarioResponseDto;
import com.mballem.demoparkapi.web.dto.UsuarioSenhaDto;
import com.mballem.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Usuarios",description = "Contém todas as operações relativas para cadastro ,edição e leitura do usuário")
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
@Operation(summary = "Criar um novo usuário",description = "Recurso para criar um novo usuário",
responses = {
        @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDto.class))),
        @ApiResponse(responseCode = "409",description = "Usuário e-mail já cadastrado",
        content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "422",description = "Recurso não processado por dados de entrada invalidos",
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

})
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto) {
        // Este método trata as solicitações HTTP POST para criar um novo usuário.
        // Ele recebe um objeto UsuarioCreateDto no corpo da solicitação e o transforma em um objeto Usuario.
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        // Em seguida, chama o serviço para salvar o usuário e retorna uma resposta com o usuário criado no corpo.
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Recuperar um usuario pelo id",description = "Recuperar um usuario pelo id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        // Este método trata solicitações HTTP GET para recuperar informações sobre um usuário com base em seu ID.
        Usuario user = usuarioService.buscarPorId(id);
        // Ele chama o serviço para buscar o usuário com o ID fornecido e retorna uma resposta com os dados do usuário no corpo.
        return ResponseEntity.ok().body(UsuarioMapper.toDto(user));
    }
    @Operation(summary = "Atualizar senha",description = "Atualizar senha",
            responses = {
                    @ApiResponse(responseCode = "204",description = "Senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",description = "Senha nao confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        // Este método lida com solicitações HTTP PATCH para atualizar a senha de um usuário com base em seu ID.
        // Ele recebe o ID do usuário e um objeto usuarioSenhaDto contendo informações de senha no corpo da solicitação.
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        // Chama o serviço para atualizar a senha do usuário e retorna uma resposta de sucesso sem corpo.
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Listar todos os usuarios ",description = "Lista todos os usuarios cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista todos os usuarios cadastrados",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class)))),

            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        // Este método trata solicitações HTTP GET para recuperar uma lista de todos os usuários.
        List<Usuario> users = usuarioService.buscarTodos();
        // Ele chama o serviço para buscar todos os usuários e retorna uma resposta com a lista de usuários no corpo.
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
