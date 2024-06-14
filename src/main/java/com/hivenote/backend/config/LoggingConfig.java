package com.hivenote.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Sink;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.core.SplunkHttpLogFormatter;
import org.zalando.logbook.json.JsonHttpLogFormatter;

@Configuration
public class LoggingConfig {

  @Bean
  public Logbook logbook(Sink sink) {
    return Logbook.builder().sink(sink).build();
  }

  @Bean
  @Profile("!prod")
  public Sink sinkNotProd() {
    return new DefaultSink(new SplunkHttpLogFormatter(), new DefaultHttpLogWriter());
  }

  @Bean
  @Profile("prod")
  public Sink sinkProd() {
    return new DefaultSink(new JsonHttpLogFormatter(), new DefaultHttpLogWriter());
  }
}
