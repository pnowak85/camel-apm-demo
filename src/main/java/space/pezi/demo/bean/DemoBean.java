package space.pezi.demo.bean;

import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import co.elastic.apm.api.CaptureSpan;

@Service
public class DemoBean {

  @Value("${jdbc.countquerytable1}")
  private String countQueryTable;

  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Handler
  public void handle() {
    queryTable1();
  }

  @CaptureSpan(type = "db", value = "Table2 Query")
  public void queryTable1() {
    int result = jdbcTemplate.getJdbcTemplate().queryForObject("SELECT count(*) from " + countQueryTable,
                                                               Integer.class);
    System.out.println("Camel routebean DB query executed - " + result + " rows");
  }
}
