package be.kdg.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableJpaRepositories(entityManagerFactoryRef="emf")
public class IP2Application {

    public static void main(String[] args) {
        SpringApplication.run(IP2Application.class, args);
    }

    @Bean
    public Docket simpleDiffServiceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("calculator")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API calls team 4")
                .description("onze API calls")
                .version("1.0")
                .build();
    }
}
