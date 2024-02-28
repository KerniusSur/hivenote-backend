package app.hivenote.component;

import app.hivenote.component.entity.ComponentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<ComponentEntity, UUID> {}
