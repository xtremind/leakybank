package fr.ing.secu.leakybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Main class of the application
 *
 * @author chouippea
 *
 */
@SpringBootApplication
public class Application implements WebServerFactoryCustomizer<JettyServletWebServerFactory> {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public UserSession userSession() {
		return new UserSession();
	}

	@Override
	public void customize(JettyServletWebServerFactory factory) {
		try {
			factory.setAddress(InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException ex) {
			throw new IllegalArgumentException("Cannot bind server to 127.0.0.1 address: '" + ex.getMessage() + "'", ex);
		}
	}
}
