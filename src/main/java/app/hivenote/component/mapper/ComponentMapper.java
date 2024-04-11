package app.hivenote.component.mapper;

import app.hivenote.component.dto.response.ComponentResponse;
import app.hivenote.component.entity.ComponentEntity;
import app.hivenote.socket.messages.ComponentMessage;
import java.util.stream.Collectors;

public class ComponentMapper {
  public static ComponentResponse toResponse(ComponentEntity component) {
    return new ComponentResponse()
        .setId(component.getId())
        .setType(component.getType())
        .setPriority(component.getPriority())
        .setProperties(component.getProperties())
        .setParent(
            component.getParent() == null
                ? null
                : toResponse(component.getParent())
                    .setChildren(
                        component.getChildren() == null
                            ? null
                            : component.getChildren().stream()
                                .map(ComponentMapper::toResponse)
                                .collect(Collectors.toList())));
  }

  public static ComponentMessage toMessage(ComponentEntity component) {
    return new ComponentMessage()
        .setId(component.getId().toString())
        .setComponentType(component.getType())
        .setPriority(component.getPriority())
        .setProperties(component.getProperties());
  }
}
