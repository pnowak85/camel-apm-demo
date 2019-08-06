package space.pezi.demo;

import org.apache.camel.component.properties.springboot.PropertiesComponentAutoConfiguration;
import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import co.elastic.apm.opentracing.ElasticApmTracer;
import io.opentracing.Tracer;

@Configuration
@ComponentScan
@ImportAutoConfiguration({
    CamelAutoConfiguration.class,
    PropertiesComponentAutoConfiguration.class,
})
@PropertySource("classpath:application.properties")
@CamelOpenTracing
@EnableScheduling
public class DemoApp {

  public static void main(String[] args) {
    // start the app
    SpringApplication springApplication =
        new SpringApplicationBuilder().sources(DemoApp.class)
            .build();
    ConfigurableApplicationContext context = springApplication.run(args);

    // prevent overhasty program termination
    CamelSpringBootApplicationController camelController =
        context.getBean(CamelSpringBootApplicationController.class);
    camelController.run();
  }

  @Bean
  public Tracer tracer() {
    return new ElasticApmTracer();
  }
}
