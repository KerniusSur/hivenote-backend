package com.hivenote.backend.component;

import com.hivenote.backend.component.entity.ComponentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComponentRepository
    extends JpaRepository<ComponentEntity, UUID>, JpaSpecificationExecutor<ComponentEntity> {
  List<ComponentEntity> findAllByNoteId(UUID noteId);
}
