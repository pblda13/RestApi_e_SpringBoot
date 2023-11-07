package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	// Método para salvar um usuário no banco de dados
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	// Método para buscar um usuário por ID no banco de dados
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		// Lança uma exceção se o usuário com o ID especificado não for encontrado
		return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}

	// Método para editar a senha de um usuário
	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		// Verifica se a nova senha e a confirmação de senha coincidem
		if (!novaSenha.equals(confirmaSenha)) {
			throw new RuntimeException("Nova senha não confere com a confirmação de senha");
		}

		// Busca o usuário com o ID especificado
		Usuario user = buscarPorId(id);

		// Verifica se a senha atual do usuário coincide com a senha fornecida
		if (user.getPassword().equals(senhaAtual)) {
			throw new RuntimeException("Sua senha não confere.");
		}

		// Define a nova senha para o usuário
		user.setPassword(novaSenha);

		// Retorna o usuário atualizado
		return user;
	}

	// Método para buscar todos os usuários no banco de dados
	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}
}


