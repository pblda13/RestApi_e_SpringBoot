package com.mballem.demoparkapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
// As anotações @Getter, @Setter e @NoArgsConstructor são anotações do projeto Lombok.
// - @Getter gera automaticamente métodos getter para os campos da classe.
// - @Setter gera automaticamente métodos setter para os campos da classe.
// - @NoArgsConstructor gera automaticamente um construtor sem argumentos.

@Entity
// A anotação @Entity marca esta classe como uma entidade JPA, o que significa que ela será mapeada para uma tabela no banco de dados.

@Table(name = "usuarios")
// A anotação @Table define o nome da tabela no banco de dados para a entidade 'Usuario'.

public class Usuario implements Serializable {
	// A classe 'Usuario' representa uma entidade de usuário no sistema.

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	// O campo 'id' é a chave primária da tabela e é gerado automaticamente usando estratégia de identidade.

	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;
	// O campo 'username' armazena o nome de usuário do usuário e é definido como único (não duplicado).

	@Column(name = "password", nullable = false, length = 200)
	private String password;
	// O campo 'password' armazena a senha do usuário.

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 25)
	private Role role = Role.ROLE_CLIENTE;
	// O campo 'role' representa a função do usuário, sendo definido como um valor do tipo Role.

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	// O campo 'dataCriacao' armazena a data de criação do usuário.

	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;
	// O campo 'dataModificacao' armazena a data de modificação do usuário.

	@Column(name = "criado_por")
	private String criadoPor;
	// O campo 'criadoPor' armazena o nome de quem criou o usuário.

	@Column(name = "modificado_por")
	private String modificadoPor;
	// O campo 'modificadoPor' armazena o nome de quem modificou o usuário.

	public enum Role {
		// A enumeração Role define os possíveis papéis (funções) que um usuário pode ter.
		ROLE_ADMIN, ROLE_CLIENTE
	}

	@Override
	public int hashCode() {
		// Implementação do método hashCode que gera um código hash com base no campo 'id'.
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		// Implementação do método equals para comparar objetos com base no campo 'id'.
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		// Implementação do método toString que retorna uma representação em string da entidade 'Usuario'.
		return "Usuario [id=" + id + "]";
	}
}




