package com.hivenote.backend.event;

import com.hivenote.backend.event.entity.EventEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventRepository
    extends JpaRepository<EventEntity, UUID>, JpaSpecificationExecutor<EventEntity> {

  List<EventEntity> findAllByCreatedById(UUID accountId);

  Optional<EventEntity> findByIdAndCreatedById(UUID id, UUID accountId);
}
