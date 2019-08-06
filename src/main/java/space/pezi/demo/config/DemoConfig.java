package space.pezi.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(JdbcSettings.class)
public class DemoConfig {

  @Resource
  private JdbcSettings jdbcSettings;

  @Bean
  CamelContextConfiguration contextConfiguration() {
    return new CamelContextConfiguration() {
      @Override
      public void beforeApplicationStart(CamelContext camelContext) {
        camelContext.setUseMDCLogging(true);
        camelContext.setUseBreadcrumb(true);
        camelContext.setTracing(false);
      }

      @Override
      public void afterApplicationStart(CamelContext camelContext) {
      }
    };
  }

  @Bean(name = "namedJdbcTemplate")
  public NamedParameterJdbcTemplate jdbcTemplate() {
    return new NamedParameterJdbcTemplate(messageDataSource());
  }

  @Bean
  public DataSource messageDataSource() {

    System.out.println("Initializing Hikari-Pool datasource");
    HikariConfig config = new HikariConfig();

    try {
      config.setPoolName("HikariPool-HUB-Pool");
    } catch (Exception ex) {
      System.out.println("Could not set Hikari pool name: " + ex.getMessage());
    }

    config.setJdbcUrl(jdbcSettings.getUrl());
    config.setUsername(jdbcSettings.getUser());
    config.setPassword(jdbcSettings.getPassword());
    config.setDriverClassName(jdbcSettings.getDriver());

    config.setAutoCommit(true);
    config.setValidationTimeout(5000);
    config.setLeakDetectionThreshold(10000);
    config.setMinimumIdle(2);
    config.setMaximumPoolSize(20);
    config.setIdleTimeout(600000);
    config.setMaxLifetime(1800000);
    config.setConnectionTimeout(30000);
    config.setInitializationFailTimeout(1);
    config.setAllowPoolSuspension(false);
    config.setRegisterMbeans(false);


    return new HikariDataSource(config);
  }
}
