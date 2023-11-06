package com.mballem.demoparkapi.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class usuarioSenhaDto {

    private String senhaAtual;
    private String novaSenha;

    private String confirmaSenha;
}
