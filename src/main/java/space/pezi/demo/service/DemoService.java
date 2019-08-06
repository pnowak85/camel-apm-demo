package space.pezi.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

  @Value("${jdbc.countquerytable2}")
  private String countQueryTable;

  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Scheduled(fixedDelayString = "10000")
  public void execute() {
    queryTable2();
  }

  public void queryTable2() {
    int result = jdbcTemplate.getJdbcTemplate().queryForObject("SELECT count(*) from " + countQueryTable,
                                                               Integer.class);
    LOGGER.info("Background service DB query executed - " + result + " rows");
  }
}
