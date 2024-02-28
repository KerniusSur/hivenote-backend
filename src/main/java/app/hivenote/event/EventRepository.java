package app.hivenote.event;

import app.hivenote.event.entity.EventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {}
