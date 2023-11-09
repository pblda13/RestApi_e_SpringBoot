package com.mballem.demoparkapi.service;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.repository.UsuarioRepository;
import com.mballem.demoparkapi.service.exception.EntityNotFoundException;
import com.mballem.demoparkapi.service.exception.PasswordInvalidException;
import com.mballem.demoparkapi.service.exception.UsernameUniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
		try {
			return usuarioRepository.save(usuario);
		}catch (DataIntegrityViolationException ex){
			throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado",usuario.getUsername()));
		}

	}

	// Método para buscar um usuário por ID no banco de dados
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		// Lança uma exceção se o usuário com o ID especificado não for encontrado
		return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado",id)));
	}

	// Método para editar a senha de um usuário
	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		if (!novaSenha.equals(confirmaSenha)) {
			throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
		}

		Usuario user = buscarPorId(id);
		if (!user.getPassword().equals(senhaAtual)) {
			throw new PasswordInvalidException("Sua senha não confere.");
		}

		user.setPassword(novaSenha);
		return user;
	}
	// Método para buscar todos os usuários no banco de dados
	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}
}


