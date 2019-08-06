package space.pezi.demo.route;

import space.pezi.demo.bean.DemoBean;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DemoRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    from("timer://demotimer?period=15s&daemon=true")
        .log("Running")
        .to("direct:querydb");

    from("direct:querydb")
        .bean(DemoBean.class);
  }
}
