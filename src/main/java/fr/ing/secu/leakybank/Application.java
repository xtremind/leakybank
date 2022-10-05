package fr.ing.secu.leakybank;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Main class of the application
 * 
 * @author chouippea
 *
 */
@EnableAutoConfiguration
@ComponentScan
@Configuration
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private AuthenticatedInterceptor authenticatedInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticatedInterceptor);
	}

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public UserSession userSession() {
		return new UserSession();
	}

	/**
	 * Customize the servlet container to : - Listen on localhost only
	 */
	@Bean
	public EmbeddedServletContainerCustomizer getServletContainerCustomization() {

		return container -> {
			try {
				// Restrict access to localhost only
				container.setAddress(InetAddress.getByName("127.0.0.1"));
			} catch (UnknownHostException ex) {
				throw new IllegalArgumentException("Cannot bind server to 127.0.0.1 address: '" + ex.getMessage() + "'", ex);
			}
		};

	}
}