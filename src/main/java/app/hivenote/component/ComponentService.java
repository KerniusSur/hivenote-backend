package app.hivenote.component;

import app.hivenote.component.dto.request.ComponentCreateRequest;
import app.hivenote.component.dto.request.ComponentUpdateRequest;
import app.hivenote.component.entity.ComponentEntity;
import app.hivenote.exception.ApiException;
import app.hivenote.note.NoteRepository;
import app.hivenote.note.entity.NoteEntity;
import app.hivenote.socket.messages.NoteMessage;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class ComponentService {
  private final ComponentRepository componentRepository;
  private final NoteRepository noteRepository;

  public ComponentService(ComponentRepository componentRepository, NoteRepository noteRepository) {
    this.componentRepository = componentRepository;
    this.noteRepository = noteRepository;
  }

  public ComponentEntity findById(UUID id) {
    return componentRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound("Component not found"));
  }

  public List<ComponentEntity> findAllByNoteId(UUID noteId) {
    return componentRepository.findAllByNoteId(noteId);
  }

  public ComponentEntity create(ComponentCreateRequest request) {
    ComponentEntity parent = null;
    NoteEntity note =
        noteRepository
            .findById(UUID.fromString(request.getNoteId()))
            .orElseThrow(() -> ApiException.notFound("err.note.notFound"));

    ComponentEntity entity =
        new ComponentEntity()
            .setType(request.getType())
            .setPriority(note.getComponents().get(note.getChildren().size() - 1).getPriority() + 1)
            .setProperties(request.getProperties())
            .setNote(note);

    if (request.getParentId() != null) {
      parent = findById(UUID.fromString(request.getParentId()));
      if (parent.getNote().getId() != entity.getNote().getId()) {
        throw ApiException.bad("err.component.parent.note.mismatch");
      }

      entity.setParent(parent);
    }

    return componentRepository.save(entity);
  }

  public List<ComponentEntity> saveComponentsFromNoteMessage(NoteMessage message) {
    NoteEntity note =
        noteRepository
            .findById(UUID.fromString(message.getId()))
            .orElseThrow(() -> ApiException.notFound("err.note.notFound"));

    AtomicInteger priority = new AtomicInteger(1);
    List<ComponentEntity> components =
        new ArrayList<>(
            message.getComponents().stream()
                .map(
                    component -> {
                      ComponentEntity entity =
                          new ComponentEntity()
                              .setType(component.getComponentType())
                              .setPriority(priority.getAndIncrement())
                              .setProperties(component.getProperties())
                              .setNote(note);
                      try {
                        UUID id = UUID.fromString(component.getId());
                        entity.setId(id);
                      } catch (Exception ignored) {
                      }

                      return entity;
                    })
                .toList());

    components.sort(Comparator.comparing(ComponentEntity::getPriority));

    log.info("Saving components: {}", components);
    return componentRepository.saveAll(components);
  }

  public ComponentEntity update(ComponentUpdateRequest request) {
    ComponentEntity entity = findById((request.getId()));
    ComponentEntity parent = null;

    if (request.getParentId() != null) {
      parent = findById(UUID.fromString(request.getParentId()));
      if (parent.getNote().getId() != entity.getNote().getId()) {
        throw ApiException.bad("err.component.parent.note.mismatch");
      }
    }

    entity.setType(request.getType()).setProperties(request.getProperties()).setParent(parent);
    return componentRepository.save(entity);
  }

  public void delete(UUID id) {
    componentRepository.deleteById(id);
  }

  public void deleteAllByIds(List<UUID> ids) {
    componentRepository.deleteAllById(ids);
  }
}
