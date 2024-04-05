package app.hivenote.component;

import app.hivenote.component.dto.request.ComponentCreateRequest;
import app.hivenote.component.dto.request.ComponentUpdateRequest;
import app.hivenote.component.entity.ComponentEntity;
import app.hivenote.exception.ApiException;
import app.hivenote.note.entity.NoteEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ComponentService {
  private final ComponentRepository componentRepository;

  public ComponentService(ComponentRepository componentRepository) {
    this.componentRepository = componentRepository;
  }

  public ComponentEntity findById(UUID id) {
    return componentRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound("Component not found"));
  }

  public ComponentEntity create(ComponentCreateRequest request) {
    ComponentEntity parent = null;
    ComponentEntity entity =
        new ComponentEntity()
            .setType(request.getType())
            .setProperties(request.getProperties())
            .setNote(new NoteEntity().setId(UUID.fromString(request.getNoteId())));

    if (request.getParentId() != null) {
      parent = findById(UUID.fromString(request.getParentId()));
      if (parent.getNote().getId() != entity.getNote().getId()) {
        throw ApiException.bad("err.component.parent.note.mismatch");
      }

      entity.setParent(parent);
    }

    return componentRepository.save(entity);
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
