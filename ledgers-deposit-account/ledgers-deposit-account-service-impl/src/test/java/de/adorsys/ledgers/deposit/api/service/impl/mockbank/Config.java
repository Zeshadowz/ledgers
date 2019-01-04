package de.adorsys.ledgers.deposit.api.service.impl.mockbank;

import java.security.Principal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.adorsys.ledgers.deposit.api.service.domain.ASPSPConfigSource;

@Configuration
public class Config {
    @Bean
    public ASPSPConfigSource configSource() {
        return new MockBankConfigSource();
    }

	@Bean
	public Principal getPrincipal(){
		return () -> "anonymous";
	}
}
