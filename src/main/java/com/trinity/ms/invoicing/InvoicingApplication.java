package com.trinity.ms.invoicing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ComponentScan(basePackages = {"com.trinity.commons.config", "com.trinity.ms.invoicing"})
//@ComponentScan(basePackages = { "com.trinity.commons", "com.trinity.ms.invoicing" })
@EntityScan({ "com.trinity.commons.model" })
@EnableOAuth2Sso
public class InvoicingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoicingApplication.class, args);
	}

}
