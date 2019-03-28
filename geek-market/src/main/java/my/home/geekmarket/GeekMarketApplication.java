package my.home.geekmarket;

import my.home.geekmarket.configuration.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class GeekMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeekMarketApplication.class, args);
	}

}
