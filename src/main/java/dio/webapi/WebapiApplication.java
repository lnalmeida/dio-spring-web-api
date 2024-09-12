package dio.webapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring web API",
				version = "1.0.0",
				description = "API feita com fins didáticos, durante o bootcampo DIO/Claro de Spring boot + Java. Sujeita a modificações.",
				termsOfService = "Open source - https://github.com/lnalmeida/dio-spring-web-api.git",
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				),
				contact = @Contact(
						name = "Luiz Almeida",
						email = "l.n.almeida.dev@outlook.com",
						url = "https://lna-next-js-protifolio.vercel.app/"
				)
		)
)
public class WebapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebapiApplication.class, args);
	}

}
