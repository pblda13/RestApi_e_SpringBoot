package com.mballem.demoparkapi.web.dto.mapper;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.web.dto.UsuarioCreateDto;
import com.mballem.demoparkapi.web.dto.usuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    // Método estático para mapear um objeto UsuarioCreateDto para um objeto Usuario
    public static Usuario toUsuario(UsuarioCreateDto createDto) {
        // Cria uma nova instância de ModelMapper
        return new ModelMapper().map(createDto, Usuario.class);
    }

    // Método estático para mapear um objeto Usuario para um objeto usuarioResponseDto
    public static usuarioResponseDto toDto(Usuario usuario) {
        // Extrai o nome da função do objeto Usuario e remove o prefixo "ROLE_"
        String role = usuario.getRole().name().substring("ROLE_".length());

        // Define um mapeamento personalizado entre as propriedades do Usuario e usuarioResponseDto
        PropertyMap<Usuario, usuarioResponseDto> props = new PropertyMap<Usuario, usuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role); // Mapeia a propriedade 'role'
            }
        };

        // Cria uma instância de ModelMapper e adiciona o mapeamento personalizado
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);

        // Realiza o mapeamento e retorna o objeto usuarioResponseDto resultante
        return mapper.map(usuario, usuarioResponseDto.class);
    }

    // Método estático para mapear uma lista de Usuarios para uma lista de usuarioResponseDto
    public static List<usuarioResponseDto> toListDto(List<Usuario> usuarios) {
        // Utiliza a API de Stream do Java para mapear cada elemento da lista e coletar os resultados em uma lista
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
