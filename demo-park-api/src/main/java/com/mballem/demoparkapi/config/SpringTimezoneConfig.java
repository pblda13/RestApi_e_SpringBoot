package com.mballem.demoparkapi.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
// A anotação @Configuration indica que esta classe é uma classe de configuração Spring.
// Ela é usada para definir configurações específicas da aplicação.

public class SpringTimezoneConfig {
	// A classe SpringTimezoneConfig lida com a configuração do fuso horário da aplicação.

	@PostConstruct
	// A anotação @PostConstruct é usada em um método que deve ser executado após a criação da instância do bean.
	// Neste caso, o método 'timezoneConfig' será executado após a criação da instância desta classe.

	public void timezoneConfig() {
		// Este método 'timezoneConfig' é responsável por configurar o fuso horário padrão da aplicação.

		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		// Aqui, estamos definindo o fuso horário padrão da aplicação como "America/Sao_Paulo".
		// Isso garante que todas as operações de data e hora na aplicação usem o fuso horário de São Paulo.
	}
}
