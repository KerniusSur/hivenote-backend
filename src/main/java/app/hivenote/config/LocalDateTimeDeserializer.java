package app.hivenote.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
  @Override
  public LocalDateTime deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {

    String date = jsonParser.getText();

    JavaTimeModule javaTimeModule = new JavaTimeModule();
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(javaTimeModule);
    ZonedDateTime zonedDateTime = mapper.readValue(date, ZonedDateTime.class);
    return zonedDateTime.toLocalDateTime();
  }
}
